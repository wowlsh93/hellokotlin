/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package sqlparser.table
import kotlin.collections.Iterator

interface Cursor {
    @Throws(NoSuchElementException::class)
    fun advance(): Boolean
    fun columnCount(): Int
    fun columnName(index: Int): String?
    fun column(columnName: String): String?

    fun columns(): Iterator<String>
    fun isTraversing(t: Table?): Boolean
}