/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package sqlparser.table

class UnmodifiableTable(
    private val wrapped: Table) : Table, Cloneable {

    override fun insert(v: List<String>): Int {
        illegal()
        return 0
    }

    override fun select(where: Selector): Table {
        return wrapped.select(where)
    }

    override fun select(where: Selector, requestedColumns: List<String>?): Table {
       return wrapped.select(where,requestedColumns)
    }

    override fun select(where: Selector, requestedColumns: List<String>?, other: List<Table?>): Table {
       return wrapped.select(where, requestedColumns, other)
    }

    private fun illegal() {
        throw UnsupportedOperationException()
    }


    override fun rows(): Cursor {
        return wrapped.rows()
    }

    fun extract(): Table {
        return wrapped
    }
}