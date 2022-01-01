/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package sqlparser.parser

import sqlparser.table.Cursor
import sqlparser.table.Table


interface Value // tagging interface

class NullValue : Value {
  override fun toString(): String {
    return "null"
  }
}

internal class BooleanValue(var value: Boolean) : Value {
  fun value(): Boolean {
    return value
  }

  override fun toString(): String {
    return value.toString()
  }
}

class StringValue(lexeme: String?) : Value {
  private val value: String

  init {
    value = lexeme?.replace(Regex("['\"](.*?)['\"]"), "$1") ?: ""
  }

  fun value(): String {
    return value
  }

  override fun toString(): String {
    return value
  }
}

class NumericValue : Value {
  private var value: Double

  constructor(value: Double)
  {
    this.value = value
  }

  fun value(): Double {
    return value
  }

  override fun toString(): String // round down if the fraction is very small
  {
    return if (Math.abs(value - Math.floor(value)) < 1.0E-20)
      value.toLong().toString()
    else
      value.toString()
  }
}

class IdValue(var tableName: String?, var columnName: String, val tables: Map<String, Table>) : Value {
  fun toString(participants: Array<Cursor?>?): String? {
    var content: String? = null

    if (tableName == null)
      content = participants?.get(0)?.column(columnName) ?: null
    else {
      val container: Table = tables.get(tableName) as Table
      content = null
      for (i in participants!!.indices) {
        if (participants[i]!!.isTraversing(container)) {
          content = participants[i]!!.column(columnName)
          break
        }
      }
    }

    return content
  }

  fun value(participants: Array<Cursor?>?): Value {
    val s = toString(participants)
    try {
      return if (s == null)
        NullValue()
      else
        (NumericValue(s.toDouble()) as Value?)!!
    } catch (e: Exception) {
    }
    return StringValue(s)
  }
}

