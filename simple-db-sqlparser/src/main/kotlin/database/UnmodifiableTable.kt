/*  (c) 2004 Allen I. Holub. All rights reserved.
 *
 *  This code may be used freely by yourself with the following
 *  restrictions:
 *
 *  o Your splash screen, about box, or equivalent, must include
 *    Allen Holub's name, copyright, and URL. For example:
 *
 *      This program contains Allen Holub's SQL package.<br>
 *      (c) 2005 Allen I. Holub. All Rights Reserved.<br>
 *              http://www.holub.com<br>
 *
 *    If your program does not run interactively, then the foregoing
 *    notice must appear in your documentation.
 *
 *  o You may not redistribute (or mirror) the source code.
 *
 *  o You must report any bugs that you find to me. Use the form at
 *    http://www.holub.com/company/contact.html or send email to
 *    allen@Holub.com.
 *
 *  o The software is supplied <em>as is</em>. Neither Allen Holub nor
 *    Holub Associates are responsible for any bugs (or any problems
 *    caused by bugs, including lost productivity or data)
 *    in any of this code.
 */
package com.holub.database

import kotlin.Throws
import javax.swing.JFrame

/** This decorator of the Table class just wraps another table,
 * but restricts access to methods that don't modify the table.
 * The following methods toss an
 * [UnsupportedOperationException] when called:
 * <PRE>
 * public void  insert( String[] columnNames, Object[] values )
 * public void  insert( Object[] values )
 * public void  update( Selector where )
 * public void  delete( Selector where )
 * public void  store ()
</PRE> *
 * Other methods delegate to the wrapped Table. All methods of
 * the [Table] that are declared to return a
 * `Table` actually return an
 * `UnmodifiableTable`.
 *
 *
 * Refer to the [Table] interface for method documentation.
 *
 * @include /etc/license.txt
 */
class UnmodifiableTable(wrapped: Table) : Table, Cloneable {
    private var wrapped: Table

    /** Return an UnmodifieableTable that wraps a clone of the
     * currently wrapped table. (A deep copy is used.)
     */
    @Throws(CloneNotSupportedException::class)
    fun clone(): Object {
        val copy = super.clone() as UnmodifiableTable
        copy.wrapped = wrapped.clone() as Table
        return copy
    }

    fun insert(c: Array<String?>?, v: Array<Object?>?): Int {
        illegal()
        return 0
    }

    fun insert(v: Array<Object?>?): Int {
        illegal()
        return 0
    }

    fun insert(c: Collection?, v: Collection?): Int {
        illegal()
        return 0
    }

    fun insert(v: Collection?): Int {
        illegal()
        return 0
    }

    fun update(w: Selector?): Int {
        illegal()
        return 0
    }

    fun delete(w: Selector?): Int {
        illegal()
        return 0
    }

    fun begin() {
        illegal()
    }

    fun commit(all: Boolean) {
        illegal()
    }

    fun rollback(all: Boolean) {
        illegal()
    }

    private fun illegal() {
        throw UnsupportedOperationException()
    }

    fun select(w: Selector?, r: Array<String?>?, o: Array<Table?>?): Table {
        return wrapped.select(w, r, o)
    }

    fun select(where: Selector?, requestedColumns: Array<String?>?): Table {
        return wrapped.select(where, requestedColumns)
    }

    fun select(where: Selector?): Table {
        return wrapped.select(where)
    }

    fun select(w: Selector?, r: Collection?, o: Collection?): Table {
        return wrapped.select(w, r, o)
    }

    fun select(w: Selector?, r: Collection?): Table {
        return wrapped.select(w, r)
    }

    fun rows(): Cursor {
        return wrapped.rows()
    }

    @Throws(IOException::class)
    fun export(exporter: Table.Exporter?) {
        wrapped.export(exporter)
    }

    override fun toString(): String {
        return wrapped.toString()
    }

    fun name(): String {
        return wrapped.name()
    }

    fun rename(s: String?) {
        wrapped.rename(s)
    }

    val isDirty: Boolean
        get() = wrapped.isDirty()

    /** Extract the wrapped table. The existence of this method is
     * problematic, since it allows someone to defeat the unmodifiability
     * of the table. On the other hand, the wrapped table came in from
     * outside, so external access is possible through the reference
     * that was passed to the constructor. Use the method with care.
     */
    fun extract(): Table {
        return wrapped
    }

    init {
        this.wrapped = wrapped
    }
}