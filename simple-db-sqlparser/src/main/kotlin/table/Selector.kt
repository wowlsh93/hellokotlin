/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package sqlparser.table


interface Selector {
    fun approve(rows: List<Cursor?>): Boolean

    class Adapter : Selector {
        override fun approve(tables: List<Cursor?>): Boolean {
            return true
        }
    }

    companion object {
        val ALL: Selector = Adapter()
    }
}