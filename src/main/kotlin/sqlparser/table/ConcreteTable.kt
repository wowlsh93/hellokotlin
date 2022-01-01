/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package sqlparser.table

import sqlparser.parser.Expression
import sqlparser.parser.Value
import sqlparser.table.exceptions.NoSuchColumnExceptions
import kotlin.collections.Iterator

internal class ConcreteTable (
    val tableName: String?,
    val columnNames: List<String>
): Table {

    val rowSet = mutableListOf<List<String>>()

    @Throws(NoSuchColumnExceptions::class)
    private fun indexOf(columnName: String): Int {
        for (i in columnNames.indices) {
            if (columnNames[i].equals(columnName))
                return i
        }

        throw NoSuchColumnExceptions(
            "Column ($columnName) doesn't exist in $tableName"
        )
    }

    override fun insert(values: List<String>): Int {
//        require(values.size == width()) {
//            ("Values-array length ($values.size) is not the same as table width (" + width() + ")")
//        }
        rowSet.add(values)
        return 1
    }

    override fun select(where: Selector): Table {
        val resultTable: Table = ConcreteTable(
            tableName,
            columnNames
        )
        val currentRow = rows() as Results
        val envelope: List<Cursor?> = listOf(currentRow)
        while (currentRow.advance()) {
            if (where.approve(envelope))
                resultTable.insert(currentRow.cloneRow())
        }
        return UnmodifiableTable(resultTable)
    }

    override fun select(where: Selector, requestedColumns: List<String>?): Table {
        if (requestedColumns == null) return select(where)
        val resultTable: Table = ConcreteTable(
            tableName,
            columnNames,
        )
        val currentRow = rows() as Results
        val envelope: List<Cursor?> = listOf(currentRow)
        while (currentRow.advance()) {
            if (where.approve(envelope)) {
                val newRow = kotlin.Array(requestedColumns.size){""}
                for (column in requestedColumns.indices) {
                    newRow[column] = currentRow.column(requestedColumns[column])!!
                }
                resultTable.insert(newRow.toList())
            }
        }
        return UnmodifiableTable(resultTable)
    }


    override fun select(where: Selector, requestedColumns: List<String>?, other: List<Table?>): Table {
        if (other.size == 0) {
            if (requestedColumns == null)
                return select(where)
            return select(where, requestedColumns)
        }

        val allTables: Array<Table?> = arrayOfNulls<Table>(other.size + 1)
        allTables[0] = this
        other.forEachIndexed{idx, table ->  allTables[idx+1] = table }

        val resultTable: Table = ConcreteTable(null, requestedColumns!!)
        val envelope: Array<Cursor?> = arrayOfNulls<Cursor>(allTables.size)

        cartesianProduct(0, where, requestedColumns, allTables.toList(), envelope, resultTable)
        return UnmodifiableTable(resultTable)
    }

//    override fun selectByFilter(where: Expression?) {
//        where?.filtering(context)
//
//    }
//
//    override fun selectByFilter(where: Expression?, requestedColumns: List<String>?): InterimTable {
//        if (requestedColumns == null) return selectByFilter(where)
//
//        val resultTable = InterimTable()
//        val currentRow = rows() as Results
//        val envelope: List<Cursor?> = listOf(currentRow)
//
//        return resultTable
//    }
//
//    override fun selectByFilter(
//        where: Expression?,
//        requestedColumns: List<String>?,
//        other: List<Table?>
//    ): InterimTable {
//        if (other.size == 0) {
//            if (requestedColumns == null)
//                return selectByFilter(where)
//            return selectByFilter(where, requestedColumns)
//        }
//
//        val allTables: Array<Table?> = arrayOfNulls<Table>(other.size + 1)
//        allTables[0] = this
//        other.forEachIndexed{idx, table ->  allTables[idx+1] = table }
//
//        val resultTable = InterimTable()
//        val envelope: Array<Cursor?> = arrayOfNulls<Cursor>(allTables.size)
//
//       // cartesianProduct(0, where, requestedColumns, allTables.toList(), envelope, resultTable)
//        return resultTable
//    }

    override fun rows(): Cursor {
        return Results()
    }

    private inner class Results : Cursor {
        private val rowIterator = rowSet.iterator()
        private lateinit var row: List<String>
        override fun advance(): Boolean {
            if (rowIterator.hasNext()) {
                row = rowIterator.next()
                return true
            }
            return false
        }

        override fun columnCount(): Int {
            return columnNames.size
        }

        override fun columnName(index: Int): String? {
            return columnNames[index]
        }

        override fun column(columnName: String): String? {
            return runCatching { row[indexOf(columnName)] }.getOrNull()
        }

        override fun columns(): Iterator<String> {
            return row.iterator()
        }

        override fun isTraversing(t: Table?): Boolean {
            return t === this@ConcreteTable
        }

        fun cloneRow(): List<String>{
            return row
        }
    }

    private fun width(): Int {
        return columnNames.size
    }

    private fun andFiltering(context: TableContext, cursor: Cursor): Boolean {
        var approved = true
        context.currentFilters.forEach { t, u ->
            if(cursor.column(t.columnName) != u.toString()){
                approved = false
            }
        }
        return approved
    }

    private fun orFiltering(context: TableContext, cursor: Cursor): Boolean {
        var approved = false
        context.currentFilters.forEach { t, u ->
            if(cursor.column(t.columnName) == u.toString()){
                approved = true
            }
        }
        return approved
    }

    fun and(other: Table, context: TableContext) : Value {
        val result = ConcreteTable(null, arrayListOf())
        val thisCursor = Results()
        val otherCursor = (other as ConcreteTable).Results()

        val tempResult = ConcreteTable(null, arrayListOf())
        while(thisCursor.advance()) {
            var isSame = false
            while (otherCursor.advance()) {
                if(thisCursor.cloneRow().get(0) == otherCursor.cloneRow().get(0)){
                    isSame = true
                }
                if(andFiltering(context, otherCursor)) {
                    tempResult.insert(otherCursor.cloneRow())
                }
            }

            if(!isSame) {
                if(andFiltering(context, thisCursor)) tempResult.insert(thisCursor.cloneRow())
            }
        }

        return result
    }

    fun or(other: Table, context: TableContext) : Value {
        val result = ConcreteTable(null, arrayListOf())
        val thisCursor = Results()
        val otherCursor = (other as ConcreteTable).Results()

        val tempResult = ConcreteTable(null, arrayListOf())
        while(thisCursor.advance()) {
            var isSame = false
            while (otherCursor.advance()) {
                if(thisCursor.cloneRow().get(0) == otherCursor.cloneRow().get(0)){
                    isSame = true
                }
                if(orFiltering(context, otherCursor)) tempResult.insert(otherCursor.cloneRow())
            }

            if(!isSame) {
                if(orFiltering(context, thisCursor)) tempResult.insert(thisCursor.cloneRow())
            }
        }

        return result
    }
    private fun cartesianProduct(
        level: Int,
        where: Selector,
        requestedColumns: List<String>,
        allTables: List<Table?>,
        allIterators: Array<Cursor?>,
        resultTable: Table
    ) {
        allIterators[level] = allTables[level]?.rows()
        while (allIterators[level]!!.advance()) {
            if (level < allIterators.size - 1) {
                cartesianProduct(
                    level + 1,
                    where,
                    requestedColumns,
                    allTables,
                    allIterators,
                    resultTable
                )
            }

            if (level == allIterators.size - 1) {
                if (where.approve(allIterators.toList())) {
                    insertApprovedRows(resultTable, requestedColumns, allIterators.toList())
                }
            }
        }
    }

    private fun insertApprovedRows(
        resultTable: Table,
        requestedColumns: List<String>,
        allTables: List<Cursor?>
    ) {
        val resultRow = Array(requestedColumns.size){""}
        for (colum_idx in requestedColumns.indices) {
            for (table_idx in allTables.indices) {
                try {
                    resultRow[colum_idx] = allTables[table_idx]!!.column(requestedColumns[colum_idx]!!)!!
                    break
                } catch (e: Exception) {
                }
            }
        }
        resultTable.insert(resultRow.toList())
    }
}


public class InterimTable {

    var tableName: String? = null
    var columnNames: List<String>? = null

    val rowSet = mutableListOf<List<String>>()

    @Throws(NoSuchColumnExceptions::class)
    private fun indexOf(columnName: String): Int {
        for (i in columnNames!!.indices) {
            if (columnNames!![i].equals(columnName)) return i
        }

        throw NoSuchColumnExceptions(
            "Column ($columnName) doesn't exist in $tableName"
        )
    }

    fun insert(values: List<String>): Int {
        require(values.size == width()) {
            ("Values-array length ($values.size) is not the same as table width (" + width() + ")")
        }
        rowSet.add(values)
        return 1
    }

    fun rows(): Cursor {
        return Results()
    }

    private inner class Results : Cursor {
        private val rowIterator = rowSet.iterator()
        private lateinit var row: List<String>
        override fun advance(): Boolean {
            if (rowIterator.hasNext()) {
                row = rowIterator.next()
                return true
            }
            return false
        }

        override fun columnCount(): Int {
            return columnNames!!.size
        }

        override fun columnName(index: Int): String? {
            return columnNames!![index]
        }

        override fun column(columnName: String): String {
            return row[indexOf(columnName)]
        }

        override fun columns(): Iterator<String> {
            return row.iterator()
        }

        override fun isTraversing(t: Table?): Boolean {
            return false
        }

        fun isTraversing(t: InterimTable?): Boolean {
            return t === this@InterimTable
        }

        fun cloneRow(): List<String>{
            return row
        }
    }

    private fun width(): Int {
        return columnNames!!.size
    }
}