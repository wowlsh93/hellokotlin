/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package sqlparser.parser

import sqlparser.lexer.ParseFailure
import sqlparser.lexer.Token
import sqlparser.table.ConcreteTable
import sqlparser.table.Cursor
import sqlparser.table.TableContext


interface Expression {
  @Throws(ParseFailure::class)
  fun evaluate(tables: Array<Cursor?>?): Value
  fun compute(context: TableContext): Value
}

//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
internal class ArithmeticExpression(
  private val left: Expression?, private val right: Expression?,
  private val operator: Parser.MathOperator
) : Expression {
  @Throws(ParseFailure::class)
  override fun evaluate(tables: Array<Cursor?>?): Value {
    val leftValue = left?.evaluate(tables)
    val rightValue = right?.evaluate(tables)
    val l = (leftValue as NumericValue).value()
    val r = (rightValue as NumericValue).value()
    return NumericValue(
      if (operator === Parser.PLUS)
        l + r
      else if (operator === Parser.MINUS)
        l - r
      else if (operator === Parser.TIMES)
        l * r
      else
        l / r
    )
  }

  override fun compute(context: TableContext): Value  {
    TODO("Not yet implemented")
  }
}

//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
internal class LogicalExpression(
  left: Expression?, op: Token,
  right: Expression?
) : Expression {
  private val isAnd: Boolean
  private val left: Expression?
  private val right: Expression?

  init {
    require(op === Parser.AND || op === Parser.OR)
    isAnd = op === Parser.AND
    this.left = left
    this.right = right
  }

  @Throws(ParseFailure::class)
  override fun evaluate(tables: Array<Cursor?>?): Value {
    val leftValue = left?.evaluate(tables)
    val rightValue = right?.evaluate(tables)
    val l = (leftValue as BooleanValue).value()
    val r = (rightValue as BooleanValue).value()
    return BooleanValue(if (isAnd) l && r else l || r)
  }

  override fun compute(context: TableContext): Value  {
    val leftValue = left?.compute(context) as ConcreteTable
    val rightValue = right?.compute(context) as ConcreteTable

    return if (isAnd) {
       leftValue.and(rightValue, context)
    } else {
       leftValue.or(rightValue, context)
    }
  }
}

//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
internal class NotExpression(private val operand: Expression?) : Expression {
  @Throws(ParseFailure::class)
  override fun evaluate(tables: Array<Cursor?>?): Value {
    val value = operand?.evaluate(tables)
    return BooleanValue(!(value as BooleanValue).value())
  }

  override fun compute(context: TableContext): Value  {
    TODO("Not yet implemented")
  }
}

//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
internal class RelationalExpression(
  private val left: Expression?,
  private val operator: Parser.RelationalOperator,
  private val right: Expression?
) : Expression {
  @Throws(ParseFailure::class)
  override fun evaluate(tables: Array<Cursor?>?): Value {
    var leftValue = left?.evaluate(tables)
    var rightValue = right?.evaluate(tables)
    if (leftValue is StringValue || rightValue is StringValue) {
      val isEqual = leftValue.toString() == rightValue.toString()
      return BooleanValue(if (operator === Parser.EQ) isEqual else !isEqual)
    }
    if (rightValue is NullValue || leftValue is NullValue) {
      val isEqual = leftValue?.javaClass === rightValue?.javaClass
      return BooleanValue(if (operator === Parser.EQ) isEqual else !isEqual)
    }

    if (leftValue is BooleanValue)
      leftValue = NumericValue(if (leftValue.value()) 1.0 else 0.0)

    if (rightValue is BooleanValue)
      rightValue = NumericValue(if (rightValue.value()) 1.0 else 0.0)

    val l = (leftValue as NumericValue).value()
    val r = (rightValue as NumericValue).value()
    return BooleanValue(
      if (operator === Parser.EQ)
        l == r
      else if (operator === Parser.NE)
        l != r
      else if (operator === Parser.LT)
        l > r
      else if (operator === Parser.GT)
        l < r
      else if (operator === Parser.LE)
        l <= r
      else
        l >= r
    )
  }

  override fun compute(context: TableContext): Value  {
    var leftValue = left?.compute(context)
    var rightValue = right?.compute(context)

    if (leftValue is IdValue && rightValue is StringValue) {
      // ledger master 를 통해 가져온다.
      context.addFilter(leftValue ,  rightValue)
      return context.tables[leftValue.tableName?: context.primaryTable]!!
    }
    return ConcreteTable(null, arrayListOf())
 }
}

internal class LikeExpression(private val left: Expression?, private val right: Expression?) : Expression {
  @Throws(ParseFailure::class)
  override fun evaluate(tables: Array<Cursor?>?): Value {
    val leftValue = left?.evaluate(tables)
    val rightValue = right?.evaluate(tables)
    val compareTo = (leftValue as StringValue).value()
    var regex = (rightValue as StringValue).value()
    return BooleanValue(compareTo.matches(Regex(regex.replace("%", ".*"))))
  }

  override fun compute(context: TableContext): Value  {
    TODO("Not yet implemented")
  }
}

internal class AtomicExpression(private val atom: Value) : Expression {
  override fun evaluate(tables: Array<Cursor?>?): Value {
    return if (atom is IdValue)
      atom.value(tables)
    else
      atom
  }

  override fun compute(context: TableContext): Value  {
    return if (atom is IdValue)
      atom
    else
      atom
  }
}
