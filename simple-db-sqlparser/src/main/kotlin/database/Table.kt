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

import com.holub.database.Selector
/** A table is a database-like table that provides support for
 * queries.
 *
 * @include /etc/license.txt
 */
interface Table : Serializable, CloneableCloneable{
    /** Return a shallow copy of the table (the contents are not
     * copied.
     */
    @Throws(CloneNotSupportedException::class)
    fun clone(): Object

    /** Return the table name that was passed to the constructor
     * (or read from the disk in the case of a table that
     * was loaded from the disk.) THis is a "getter," but
     * it's a harmless one since it's just giving back a
     * piece of information that it was given.
     */
    fun name(): String?

    /** Rename the table to the indicated name. This method
     * can also be used for naming the anonymous table that's
     * returned from [select(...)][.select]
     * or one of its variants.
     */
    fun rename(newName: String?)

    /** Return true if this table has changed since it was created.
     * This status isn't entirely accurate since it's possible
     * for a user to change some object that's in the table
     * without telling the table about the change, so a certain
     * amount of user discipline is required. Returns true
     * if you modify the table using a Table method (like
     * update, insert, etc.). The dirty bit is cleared when
     * you export the table.
     */
    fun isDirty(): Boolean

    /** Insert new values into the table corresponding to the
     * specified column names. For example, the value at
     * `values[i]` is put into the column specified
     * in `columnNames[i]`. Columns that are not
     * specified are initialized to `null`.
     *
     * @return the number of rows affected by the operation.
     * @throws IndexOutOfBoundsException One of the requested columns
     * doesn't exist in either table.
     */
    fun insert(columnNames: Array<String?>?, values: Array<Object?>?): Int

    /** A convenience overload of [.insert]  */
    fun insert(columnNames: Collection?, values: Collection?): Int

    /** In this version of insert, values must have as many elements as there
     * are columns, and the values must be in the order specified when the
     * Table was created.
     * @return the number of rows affected by the operation.
     */
    fun insert(values: Array<Object?>?): Int

    /** A convenience overload of [.insert]
     */
    fun insert(values: Collection?): Int

    /**
     * Update cells in the table. The [Selector] object serves
     * as a visitor whose `includeInSelect(...)` method
     * is called for each row in the table. The return value is ignored,
     * but the Selector can modify cells as it examines them. Its your
     * responsibility not to modify primary-key and other constant
     * fields.
     * @return the number of rows affected by the operation.
     */
    fun update(where: Selector?): Int

    /** Delete from the table all rows approved by the Selector.
     * @return the number of rows affected by the operation.
     */
    fun delete(where: Selector?): Int

    /** begin a transaction  */
    fun begin()

    /** Commit a transaction.
     * @throws IllegalStateException if no [.begin] was issued.
     *
     * @param all if false, commit only the innermost transaction,
     * otherwise commit all transactions at all levels.
     * @see .THIS_LEVEL
     *
     * @see .ALL
     */
    @Throws(IllegalStateException::class)
    fun commit(all: Boolean)

    /** Roll back a transaction.
     * @throws IllegalStateException if no [.begin] was issued.
     * @param all if false, commit only the innermost transaction,
     * otherwise commit all transactions at all levels.
     * @see .THIS_LEVEL
     *
     * @see .ALL
     */
    @Throws(IllegalStateException::class)
    fun rollback(all: Boolean)

    /*** **********************************************************************
     * Create an unmodifiable table that contains selected rows
     * from the current table. The [Selector] argument
     * specifies a strategy object that  determines which
     * rows will be included in the result.
     * `Table`.
     *
     * If the `other` argument is present, this methods
     * "joins" all rows
     * from the current table and the `other` table and
     * then selects rows from the "join."
     * If the two tables contain identically named columns, then
     * only the column from the current table is included in the
     * result.
     *
     *
     * Joins are performed by creating the Cartesian product of the current
     * and "other" tables, using the Selector to determine which rows
     * of the product to include in the returned Table. For example,
     * If one table contains:
     * <pre>
     * a b
     * c d
    </pre> *
     * and the `other` table contains
     * <pre>
     * e f
     * g h
    </pre> *
     * then the Cartesian product is the table
     * <pre>
     * a b e f
     * a b g h
     * c d e f
     * c d g h
    </pre> *
     * In the case of a join, the selector is presented with rows from
     * this product.
     *
     *
     * The `Table` returned from [.select]
     * cannot be modified by you. The methods `Table`
     * methods that normally modify the
     * table (insert, update, delete, store) throw an
     * [UnsupportedOperationException] if call them.
     *
     * @param  where a selector that determines which rows to include
     * in the result.
     * Use [Selector.ALL] to include all rows.
     * @param  requestedColumns columns to include in the result.
     * null for all columns.
     * @param  other Other tables to join to this one. At most
     * three other tables may be specified.
     * This argument must be null if you're not doing a join.
     * @throws IndexOutOfBoundsException One of the requested columns
     * doesn't exist in either table.
     *
     * @return a Table that holds those rows from the Cartesian
     * product of this table and the `other` table
     * that were accepted by the [Selector].
     */
    fun select(where: Selector?, requestedColumns: Array<String?>?, other: Array<Table?>?): Table?

    /** A more efficient version of
     * `select(where, requestedColumns, null);`
     */
    fun select(where: Selector?, requestedColumns: Array<String?>?): Table?

    /** A more efficient version of `select(where, null, null);`
     */
    fun select(where: Selector?): Table?

    /** A convenience method that translates Collections to arrays, then
     * calls [.select];
     * @param requestedColumns a collection of String objects
     * representing the desired columns.
     * @param other a collection of additional Table objects to join to
     * the current one for the purposes of this SELECT
     * operation.
     */
    fun select(
        where: Selector?, requestedColumns: Collection?,
        other: Collection?
    ): Table?

    /** Convenience method, translates Collection to String array, then
     * calls String-array version.
     */
    fun select(where: Selector?, requestedColumns: Collection?): Table?

    /** Return an iterator across the rows of the current table.
     */
    fun rows(): Cursor?

    /** Build a representation of the Table using the
     * specified Exporter. Create an object from an
     * [Table.Importer] using the constructor with an
     * [Table.Importer] argument. The table's
     * "dirty" status is cleared (set false) on an export.
     * @see .isDirty
     */
    @Throws(IOException::class)
    fun export(importer: Table.Exporter?)

    /*******************************************************************
     * Used for exporting tables in various formats. Note that
     * I can add methods to this interface if the representation
     * requires it without impacting the Table's clients at all.
     */
    interface Exporter //{=Table.Exporter}
    {
        @Throws(IOException::class)
        fun startTable()

        @Throws(IOException::class)
        fun storeMetadata(
            tableName: String?,
            width: Int,
            height: Int,
            columnNames: Iterator?
        )

        @Throws(IOException::class)
        fun storeRow(data: Iterator?)

        @Throws(IOException::class)
        fun endTable()
    }

    /*******************************************************************
     * Used for importing tables in various formats.
     * Methods are called in the following order:
     *
     *  * `start()`
     *  * `loadTableName()`
     *  * `loadWidth()`
     *  * `loadColumnNames()`
     *  * `loadRow()` (multiple times)
     *  * `done()`
     *
     */
    interface Importer //{=Table.Importer}
    {
        @Throws(IOException::class)
        fun startTable()

        @Throws(IOException::class)
        fun loadTableName(): String?

        @Throws(IOException::class)
        fun loadWidth(): Int

        @Throws(IOException::class)
        fun loadColumnNames(): Iterator?

        @Throws(IOException::class)
        fun loadRow(): Iterator?

        @Throws(IOException::class)
        fun endTable()
    }
    companion object {
        /** A convenience constant that makes calls to [.commit]
         * and [.rollback] more readable when used as an
         * argument to those methods.
         * Use `commit(Table.THIS_LEVEL)` rather than
         * `commit(false)`, for example.
         */
        val THIS_LEVEL = false

        /** A convenience constant that makes calls to [.commit]
         * and [.rollback] more readable when used as an
         * argument to those methods.
         * Use `commit(Table.ALL)` rather than
         * `commit(true)`, for example.
         */
        val ALL = true
    }
}