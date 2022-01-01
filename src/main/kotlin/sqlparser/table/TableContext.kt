/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package sqlparser.table

import sqlparser.parser.Expression
import sqlparser.parser.IdValue
import sqlparser.parser.StringValue
import sqlparser.parser.Value

class TableContext {
  val tables  = mutableMapOf<String,Table>()
  val currentFilters  = mutableMapOf<IdValue, StringValue>()
  var primaryTable: String? = null

  val requestedColumns = mutableListOf<String?>()
  val requestedTableNames  = mutableListOf<String>()

  fun createTable(name: String, columns: List<String>){
    val newTable: Table = TableFactory.create(name, columns)
    tables.put(name, newTable)
  }

  fun addRow(name: String, row: List<String>){
    tables.get(name)?.apply {
      insert(row)
    }
  }

  fun addFilter(left : IdValue, right: StringValue) {
    currentFilters.put(left, right)
  }
}
