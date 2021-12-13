/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package sqlparser.parser

import sqlparser.lexer.ParseFailure
import sqlparser.lexer.Scanner
import sqlparser.lexer.TokenSet
import sqlparser.table.*
import java.io.IOException

class Parser {

  private val context = TableContext()
  private var expression: String? = null
  private val scanner : Scanner by lazy {
    Scanner(tokens, this.expression)
  }

  class RelationalOperator
  class MathOperator

  fun createTable(name: String, columns: List<String>){
    context.createTable(name, columns)
  }

  fun addRow(name: String, row: List<String>){
    context.addRow(name, row)
  }

  @Throws(IOException::class, ParseFailure::class)
  fun execute(expression: String?): Table? {
    return try {
      this.expression = expression
      scanner.advance()
      statement()
    } catch (e: ParseFailure) {
      throw e
    } catch (e: IOException) {
      throw e
    }
  }

  @Throws(ParseFailure::class, IOException::class)
  private fun statement(): Table? {
    if (scanner.matchAdvance(SELECT) != null) {
      val columns = idList()
      scanner.required(FROM)
      val requestedTableNames = idList()

      val where = if (scanner.matchAdvance(WHERE) == null)
        null
      else
        expr()

//       return doSelect(columns , requestedTableNames, where)

      columns?.forEach { context.requestedColumns.add(it) }
      requestedTableNames!!.forEach { context.requestedTableNames.add(it) }
      context.primaryTable = requestedTableNames.get(0)
      return doSelectByCompute(context,where)
    } else {
      error(
        "Expected select!! insert, create, drop, use, update, delete not supported yet "
      )
    }
    return null
  }

  @Throws(ParseFailure::class)
  private fun idList(): List<String>? {
    var identifiers: List<String>? = null
    if (scanner.matchAdvance(STAR) == null) {
      identifiers = mutableListOf()
      var id: String?
      while (scanner.required(IDENTIFIER).also { id = it } != null) {
        identifiers.add(id!!)
        if (scanner.matchAdvance(COMMA) == null) break
      }
    }
    return identifiers
  }

  @Throws(ParseFailure::class)
  private fun declarations(): List<String> {
    val identifiers = mutableListOf<String>()
    var id: String
    while (true) {
      if (scanner.matchAdvance(PRIMARY) != null) {
        scanner.required(KEY)
        scanner.required(LP)
        scanner.required(IDENTIFIER)
        scanner.required(RP)
      } else {
        id = scanner.required(IDENTIFIER)
        identifiers.add(id)

        if (scanner.matchAdvance(INTEGER) != null
          || scanner.matchAdvance(CHAR) != null
        ) {
          if (scanner.matchAdvance(LP) != null) {
            expr()
            scanner.required(RP)
          }
        } else if (scanner.matchAdvance(NUMERIC) != null) {
          if (scanner.matchAdvance(LP) != null) {
            expr()
            scanner.required(COMMA)
            expr()
            scanner.required(RP)
          }
        } else if (scanner.matchAdvance(DATE) != null) {
          // do nothing
        }
        scanner.matchAdvance(NOT)
        scanner.matchAdvance(NULL)
      }
      if (scanner.matchAdvance(COMMA) == null) // no more columns
        break
    }
    return identifiers
  }

  @Throws(ParseFailure::class)
  private fun exprList(): List<Expression?> {
    val expressions = mutableListOf<Expression?>()
    expressions.add(expr())
    while (scanner.matchAdvance(COMMA) != null) {
      expressions.add(expr())
    }
    return expressions
  }

  @Throws(ParseFailure::class)
  private fun expr(): Expression? {
    var left = andExpr()
    while (scanner.matchAdvance(OR) != null) left = LogicalExpression(left, OR, andExpr())
    return left
  }

  @Throws(ParseFailure::class)
  private fun andExpr(): Expression? {
    var left = relationalExpr()
    while (scanner.matchAdvance(AND) != null) left = LogicalExpression(left, AND, relationalExpr())
    return left
  }

  @Throws(ParseFailure::class)
  private fun relationalExpr(): Expression? {
    var left = additiveExpr()
    while (true) {
      var lexeme: String?
      if (scanner.matchAdvance(RELOP).also { lexeme = it } != null) {
        var op: RelationalOperator
        op = if (lexeme?.length === 1)
              if (lexeme?.get(0) === '<')
                LT
              else
                GT
        else {
          if (lexeme?.get(0) === '<' && lexeme?.get(1) === '>')
            NE
          else if (lexeme?.get(0) === '<')
            LE
          else
            GE
        }
        left = RelationalExpression(left, op, additiveExpr())
      } else if (scanner.matchAdvance(EQUAL) != null) {
        left = RelationalExpression(left, EQ, additiveExpr())
      } else if (scanner.matchAdvance(LIKE) != null) {
        left = LikeExpression(left, additiveExpr())
      } else break
    }
    return left
  }

  @Throws(ParseFailure::class)
  private fun additiveExpr(): Expression? {
    var lexeme: String?
    var left = multiplicativeExpr()
    while (scanner.matchAdvance(ADDITIVE).also { lexeme = it } != null) {
      val op = if (lexeme?.get(0) === '+') PLUS else MINUS
      left = ArithmeticExpression(left, multiplicativeExpr(), op)
    }
    return left
  }

  @Throws(ParseFailure::class)
  private fun multiplicativeExpr(): Expression? {
    var left = term()
    while (true) {
      if (scanner.matchAdvance(STAR) != null)
        left = ArithmeticExpression(left, term(), TIMES)
      else if (scanner.matchAdvance(SLASH) != null)
        left = ArithmeticExpression(left, term(), DIVIDE)
      else
        break
    }
    return left
  }

  @Throws(ParseFailure::class)
  private fun term(): Expression? {
    return if (scanner.matchAdvance(NOT) != null) {
      NotExpression(expr())
    } else if (scanner.matchAdvance(LP) != null) {
      val toReturn = expr()
      scanner.required(RP)
      toReturn
    } else
      factor()
  }

  @Throws(ParseFailure::class)
  private fun factor(): Expression? {
    try {
      var lexeme: String?
      val result: Value?
      if (scanner.matchAdvance(STRING).also { lexeme = it } != null)
        result = StringValue(lexeme)
      else if (scanner.matchAdvance(NUMBER).also { lexeme = it } != null)
        result = NumericValue(lexeme!!.toDouble())
      else if (scanner.matchAdvance(NULL).also { lexeme = it } != null)
        result = NullValue()
      else {
        var columnName: String = scanner.required(IDENTIFIER)
        var tableName: String? = null
        if (scanner.matchAdvance(DOT) != null) {
          tableName = columnName
          columnName = scanner.required(IDENTIFIER)
        }
        result = IdValue(tableName, columnName, context.tables)
      }
      return AtomicExpression(result)
    } catch (e: java.text.ParseException) {
    }
    error("Couldn't parse Number")
    return null
  }

  @Throws(ParseFailure::class)
  private fun doSelect(
    columns: List<String>?,
    requestedTableNames: List<String?>?,
    where: Expression?
  ): Table {
    val tableNames= requestedTableNames?.iterator()
    assert(tableNames!!.hasNext()) { "No tables to use in select!" }

    val primary: Table = context.tables.get(tableNames.next()) as Table
    val participantsInJoin = mutableListOf<Table?>()
    while (tableNames.hasNext()) {
      val participant = tableNames.next()
      participantsInJoin.add(context.tables.get(participant))
    }

    val selector: Selector = if (where == null)
        Selector.ALL
      else
        object : Selector {
          override fun approve(rows: List<Cursor?>): Boolean {
            return try {
              val result = where.evaluate(rows.toTypedArray())
              (result as BooleanValue).value()
            } catch (e: ParseFailure) {
              throw e
            }
          }
        }

    return primary.select(selector, columns?.toList(), participantsInJoin)
  }


  fun select(where: Expression?, context: TableContext): Value {
     if(where == null) {
       //val table = ConcreteTable(tableName)
       //레져마스터로 해당 테이블 로우 다 가져와서 ConcreateTable을 만든 후, UnmodifiableTable로 래핑해서 리턴
        return ConcreteTable(null, arrayListOf())
     }
    else {
       return where.compute(context)
     }
  }

  @Throws(ParseFailure::class)
  private fun doSelectByCompute(
    context: TableContext,
    where: Expression?
  ): Table {

    return select(where, context) as ConcreteTable
  }


  companion object {

    val tokens = TokenSet()
    val COMMA = tokens.create("',")

    val EQUAL = tokens.create("'=")
    val LP = tokens.create("'(")
    val RP = tokens.create("')")
    val DOT = tokens.create("'.")
    val STAR = tokens.create("'*")
    val SLASH = tokens.create("'/")
    val AND = tokens.create("'AND")
    val FROM = tokens.create("'FROM")
    val KEY = tokens.create("'KEY")
    val LIKE = tokens.create("'LIKE")
    val NOT = tokens.create("'NOT")
    val NULL = tokens.create("'NULL")
    val OR = tokens.create("'OR")
    val PRIMARY = tokens.create("'PRIMARY")
    val SELECT = tokens.create("'SELECT")
    val WHERE = tokens.create("'WHERE")
    val ADDITIVE = tokens.create("\\+|-")
    val STRING = tokens.create("(\".*?\")|('.*?')")
    val RELOP = tokens.create("[<>][=>]?")
    val NUMBER = tokens.create("[0-9]+(\\.[0-9]+)?")
    val INTEGER = tokens.create("(small|tiny|big)?int(eger)?")
    val NUMERIC = tokens.create("decimal|numeric|real|double")
    val CHAR = tokens.create("(var)?char")
    val DATE = tokens.create("date(\\s*\\(.*?\\))?")
    val IDENTIFIER = tokens.create("[a-zA-Z_0-9/\\\\:~]+")
    val EQ = RelationalOperator()
    val LT = RelationalOperator()
    val GT = RelationalOperator()
    val LE = RelationalOperator()
    val GE = RelationalOperator()
    val NE = RelationalOperator()
    val PLUS = MathOperator()
    val MINUS = MathOperator()
    val TIMES = MathOperator()
    val DIVIDE = MathOperator()
  }
}