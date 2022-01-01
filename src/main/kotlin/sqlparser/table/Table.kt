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

interface Table : Cloneable, Value {
    fun insert(values: List<String>): Int
    fun select(where: Selector): Table
    fun select(where: Selector, requestedColumns: List<String>?): Table
    fun select(where: Selector, requestedColumns: List<String>?, other: List<Table?>): Table

//    fun selectByFilter(where: Expression?): InterimTable
//    fun selectByFilter(where: Expression?, requestedColumns: List<String>?): InterimTable
//    fun selectByFilter(where: Expression?, requestedColumns: List<String>?, other: List<Table?>): InterimTable


    fun rows(): Cursor

    interface Importer //{=Table.Importer}
    {
        fun loadRow(): Table
    }
}