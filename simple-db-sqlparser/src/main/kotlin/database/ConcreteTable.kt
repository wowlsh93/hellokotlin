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

import com.holub.tools.ArrayIterator

/** A concrete implementation of the [Table] interface that
 * implements an in-memory table. Most of the methods of this
 * class are documented in the [Table] class.
 *
 *
 * It's best to
 * create instances of this class using the [TableFactory]
 * rather than `new`.
 *
 * Note that a ConcreteTable is both serializable and "Cloneable",
 * so you can easily store it onto the disk in binary form
 * or make a copy of it. Clone implements a shallow copy, however,
 * so it can be used to implement a rollback of an insert or delete,
 * but not an update.
 *
 *
 * This class is not thread safe.
 *
 * @include /etc/license.txt
 */
/*package*/
internal class ConcreteTable : Table, Cloneable {
    // Supporting clone() complicates the following declarations. In
    // particular, the fields can't be final because they're modified
    // in the clone() method. Also, the rows field has to be declared
    // as a Linked list (rather than a List) because Cloneable is made
    // public at the LinkedList level. If you declare it as a list,
    // you'll get an error message because clone()---for reasons that
    // are mysterious to me---is declared protected in Object.
    //
    // Be sure to change the clone() method if you modify anything about
    // any of these fields.
    private var rowSet: LinkedList = LinkedList()
    private var columnNames: Array<String?>
    private var tableName: String?

    @kotlin.jvm.Transient
    var isDirty = false
        private set

    @kotlin.jvm.Transient
    private val transactionStack: LinkedList = LinkedList()

    /**********************************************************************
     * Create a table with the given name and columns.
     * @param tableName the name of the table.
     * @param an array of Strings that specify the column names.
     */
    constructor(tableName: String?, columnNames: Array<String?>) {
        this.tableName = tableName
        this.columnNames = columnNames.clone() as Array<String?>
    }

    /**********************************************************************
     * Return the index of the named column. Throw an
     * IndexOutOfBoundsException if the column doesn't exist.
     */
    private fun indexOf(columnName: String): Int {
        for (i in columnNames.indices) if (columnNames[i]!!.equals(columnName)) return i
        throw IndexOutOfBoundsException(
            "Column ($columnName) doesn't exist in $tableName"
        )
    }
    //@simple-construction-end
    //
    /**********************************************************************
     * Create a table using an importer. See [CSVImporter] for
     * an example.
     */
    constructor(importer: Table.Importer) {
        importer.startTable()
        tableName = importer.loadTableName()
        val width: Int = importer.loadWidth()
        var columns: Iterator = importer.loadColumnNames()
        columnNames = arrayOfNulls(width)
        var i = 0
        while (columns.hasNext()) {
            columnNames[i++] = columns.next() as String
        }
        while (importer.loadRow().also { columns = it } != null) {
            val current: Array<Object?> = arrayOfNulls<Object>(width)
            var i = 0
            while (columns.hasNext()) {
                current[i++] = columns.next()
            }
            this.insert(current)
        }
        importer.endTable()
    }

    //----------------------------------------------------------------------
    @Throws(IOException::class)
    fun export(exporter: Table.Exporter) {
        exporter.startTable()
        exporter.storeMetadata(
            tableName,
            columnNames.size,
            rowSet.size(),
            ArrayIterator(columnNames)
        )
        val i: Iterator = rowSet.iterator()
        while (i.hasNext()) {
            exporter.storeRow(ArrayIterator(i.next() as Array<Object?>))
        }
        exporter.endTable()
        isDirty = false
    }

    //@import-export-end
    //----------------------------------------------------------------------
    // Inserting
    //
    fun insert(intoTheseColumns: Array<String>, values: Array<Object?>): Int {
        assert(intoTheseColumns.size == values.size) {
            ("There must be exactly one value for "
                    + "each specified column")
        }
        val newRow: Array<Object?> = arrayOfNulls<Object>(width())
        for (i in intoTheseColumns.indices) newRow[indexOf(intoTheseColumns[i])] = values[i]
        doInsert(newRow)
        return 1
    }

    //----------------------------------------------------------------------
    fun insert(intoTheseColumns: Collection, values: Collection): Int {
        assert(intoTheseColumns.size() === values.size()) {
            ("There must be exactly one value for "
                    + "each specified column")
        }
        val newRow: Array<Object?> = arrayOfNulls<Object>(width())
        val v: Iterator = values.iterator()
        val c: Iterator = intoTheseColumns.iterator()
        while (c.hasNext() && v.hasNext()) newRow[indexOf(c.next() as String)] = v.next()
        doInsert(newRow)
        return 1
    }

    //----------------------------------------------------------------------
    fun insert(row: Map): Int {    // A map is considered to be "ordered,"  with the order defined
        // as the order in which an iterator across a "view" returns
        // values. My reading of this statement is that the iterator
        // across the keySet() visits keys in the same order as the
        // iterator across the values() visits the values.
        return insert(row.keySet(), row.values())
    }

    //----------------------------------------------------------------------
    fun insert(values: Array<Object?>): Int {
        assert(values.size == width()) {
            ("Values-array length (" + values.size + ") "
                    + "is not the same as table width (" + width() + ")")
        }
        doInsert(values.clone() as Array<Object?>)
        return 1
    }

    //----------------------------------------------------------------------
    fun insert(values: Collection): Int {
        return insert(values.toArray())
    }

    //----------------------------------------------------------------------
    private fun doInsert(newRow: Array<Object?>) {
        rowSet.add(newRow)
        registerInsert(newRow)
        isDirty = true
    }

    //@insert-end
    //----------------------------------------------------------------------
    // Traversing and cursor-based Updating and Deleting
    //
    fun rows(): Cursor {
        return Results()
    }

    //----------------------------------------------------------------------
    private inner class Results : Cursor {
        private val rowIterator: Iterator = rowSet.iterator()
        private var row: Array<Object>? = null
        override fun tableName(): String? {
            return tableName
        }

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

        fun column(columnName: String): Object {
            return row!![indexOf(columnName)]
        }

        override fun columns(): Iterator {
            return ArrayIterator(row)
        }

        override fun isTraversing(t: Table): Boolean {
            return t === this@ConcreteTable
        }

        // This method is for use by the outer class only, and is not part
        // of the Cursor interface.
        fun cloneRow(): Array<Object> {
            return row.clone() as Array<Object>
        }

        fun update(columnName: String, newValue: Object): Object {
            val index = indexOf(columnName)

            // The following test is required for undo to work correctly.
            if (row!![index] === newValue) throw IllegalArgumentException(
                "May not replace object with itself"
            )
            val oldValue: Object = row!![index]
            row!![index] = newValue
            isDirty = true
            registerUpdate(row, index, oldValue)
            return oldValue
        }

        override fun delete() {
            val oldRow: Array<Object>? = row
            rowIterator.remove()
            isDirty = true
            registerDelete(oldRow)
        }
    }

    //@cursor-end
    //----------------------------------------------------------------------
    // Undo subsystem.
    //
    private interface Undo {
        fun execute()
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private inner class UndoInsert(insertedRow: Array<Object>) : Undo {
        private val insertedRow: Array<Object>
        override fun execute() {
            rowSet.remove(insertedRow)
        }

        init {
            this.insertedRow = insertedRow
        }
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private inner class UndoDelete(deletedRow: Array<Object>) : Undo {
        private val deletedRow: Array<Object>
        override fun execute() {
            rowSet.add(deletedRow)
        }

        init {
            this.deletedRow = deletedRow
        }
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private inner class UndoUpdate(row: Array<Object>, cell: Int, oldContents: Object) : Undo {
        private val row: Array<Object>
        private val cell: Int
        private val oldContents: Object
        override fun execute() {
            row[cell] = oldContents
        }

        init {
            this.row = row
            this.cell = cell
            this.oldContents = oldContents
        }
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    fun begin() {
        transactionStack.addLast(LinkedList())
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private fun register(op: Undo) {
        (transactionStack.getLast() as LinkedList).addLast(op)
    }

    private fun registerUpdate(row: Array<Object>?, cell: Int, oldContents: Object) {
        if (!transactionStack.isEmpty()) register(UndoUpdate(row, cell, oldContents))
    }

    private fun registerDelete(oldRow: Array<Object>?) {
        if (!transactionStack.isEmpty()) register(UndoDelete(oldRow))
    }

    private fun registerInsert(newRow: Array<Object?>) {
        if (!transactionStack.isEmpty()) register(UndoInsert(newRow))
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Throws(IllegalStateException::class)
    fun commit(all: Boolean) {
        if (transactionStack.isEmpty()) throw IllegalStateException("No BEGIN for COMMIT")
        do {
            val currentLevel: LinkedList = transactionStack.removeLast() as LinkedList
            if (!transactionStack.isEmpty()) (transactionStack.getLast() as LinkedList)
                .addAll(currentLevel)
        } while (all && !transactionStack.isEmpty())
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Throws(IllegalStateException::class)
    fun rollback(all: Boolean) {
        if (transactionStack.isEmpty()) throw IllegalStateException("No BEGIN for ROLLBACK")
        do {
            val currentLevel: LinkedList = transactionStack.removeLast() as LinkedList
            while (!currentLevel.isEmpty()) (currentLevel.removeLast() as Undo).execute()
        } while (all && !transactionStack.isEmpty())
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //@undo-end
    //-----------------------------------------------------------------
    fun update(where: Selector): Int {
        val currentRow = rows() as Results
        val envelope: Array<Cursor?> = arrayOf(currentRow)
        var updated = 0
        while (currentRow.advance()) {
            if (where.approve(envelope)) {
                where.modify(currentRow)
                ++updated
            }
        }
        return updated
    }

    //----------------------------------------------------------------------
    fun delete(where: Selector): Int {
        var deleted = 0
        val currentRow = rows() as Results
        val envelope: Array<Cursor?> = arrayOf(currentRow)
        while (currentRow.advance()) {
            if (where.approve(envelope)) {
                currentRow.delete()
                ++deleted
            }
        }
        return deleted
    }

    //@select-start
    //----------------------------------------------------------------------
    fun select(where: Selector): Table {
        val resultTable: Table = ConcreteTable(
            null,
            columnNames.clone() as Array<String?>
        )
        val currentRow = rows() as Results
        val envelope: Array<Cursor?> = arrayOf(currentRow)
        while (currentRow.advance()) {
            if (where.approve(envelope)) resultTable.insert(currentRow.cloneRow() as Array<Object>)
        }
        return UnmodifiableTable(resultTable)
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    fun select(where: Selector, requestedColumns: Array<String>?): Table {
        if (requestedColumns == null) return select(where)
        val resultTable: Table = ConcreteTable(
            null,
            requestedColumns.clone() as Array<String?>
        )
        val currentRow = rows() as Results
        val envelope: Array<Cursor?> = arrayOf(currentRow)
        while (currentRow.advance()) {
            if (where.approve(envelope)) {
                val newRow: Array<Object?> = arrayOfNulls<Object>(requestedColumns.size)
                for (column in requestedColumns.indices) {
                    newRow[column] = currentRow.column(requestedColumns[column])
                }
                resultTable.insert(newRow)
            }
        }
        return UnmodifiableTable(resultTable)
    }

    /**
     * This version of select does a join
     */
    fun select(
        where: Selector, requestedColumns: Array<String?>,  //{=ConcreteTable.select.default}
        otherTables: Array<Table?>?
    ): Table {
        // If we're not doing a join, use the more efficient version
        // of select().
        if (otherTables == null || otherTables.size == 0) return select(where, requestedColumns)

        // Make the current table not be a special case by effectively
        // prefixing it to the otherTables array.
        val allTables: Array<Table?> = arrayOfNulls<Table>(otherTables.size + 1)
        allTables[0] = this
        System.arraycopy(otherTables, 0, allTables, 1, otherTables.size)

        // Create places to hold the result of the join and to hold
        // iterators for each table involved in the join.
        val resultTable: Table = ConcreteTable(null, requestedColumns)
        val envelope: Array<Cursor?> = arrayOfNulls<Cursor>(allTables.size)

        // Recursively compute the Cartesian product, adding to the
        // resultTable all rows that the Selector approves
        selectFromCartesianProduct(
            0, where, requestedColumns,
            allTables, envelope, resultTable
        )
        return UnmodifiableTable(resultTable)
    }

    /**
     * A collection variant on the array version. Just converts the collection
     * to an array and then chains to the other version
     * ([see][.select]).
     * @param requestedColumns the value returned from the [.toString]
     * method of the elements of this collection are used as the
     * column names.
     * @param other Collection of tables to join to the current one,
     * `null`if none.
     * @throws ClassCastException if any elements of the `other`
     * collection do not implement the [Table] interface.
     */
    fun select(
        where: Selector?, requestedColumns: Collection?,
        other: Collection?
    ): Table {
        var columnNames: Array<String?>? = null
        var otherTables: Array<Table?>? = null
        if (requestedColumns != null) // SELECT *
        {
            // Can't cast an Object[] to a String[], so make a copy to ensure
            // type safety.
            columnNames = arrayOfNulls(requestedColumns.size())
            var i = 0
            val column: Iterator = requestedColumns.iterator()
            while (column.hasNext()) columnNames[i++] = column.next().toString()
        }
        if (other != null) otherTables = other.toArray(arrayOfNulls<Table>(other.size()))
        return select(where, columnNames, otherTables)
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    fun select(where: Selector?, requestedColumns: Collection?): Table {
        return select(where, requestedColumns, null)
    }

    //@select-end
    //----------------------------------------------------------------------
    // Housekeeping stuff
    //
    fun name(): String? {
        return tableName
    }

    fun rename(s: String?) {
        tableName = s
    }

    private fun width(): Int {
        return columnNames.size
    }

    //----------------------------------------------------------------------
    @Throws(CloneNotSupportedException::class)
    fun clone(): Object {
        val copy = super.clone() as ConcreteTable
        copy.rowSet = rowSet.clone() as LinkedList
        copy.columnNames = columnNames.clone() as Array<String?>
        copy.tableName = tableName
        return copy
    }

    //----------------------------------------------------------------------
    override fun toString(): String {
        val out = StringBuffer()
        out.append(if (tableName == null) "<anonymous>" else tableName)
        out.append("\n")
        for (i in columnNames.indices) out.append(columnNames[i].toString() + "\t")
        out.append("\n----------------------------------------\n")
        val i: Cursor = rows()
        while (i.advance()) {
            val columns: Iterator = i.columns()
            while (columns.hasNext()) {
                val next: Object = columns.next()
                if (next == null) out.append("null\t") else out.append(next.toString().toString() + "\t")
            }
            out.append('\n')
        }
        return out.toString()
    }

    //----------------------------------------------------------------------
    class Test {
        var people: Table = TableFactory.create(
            "people", arrayOf<String?>("last", "first", "addrId")
        )
        var address: Table = TableFactory.create(
            "address", arrayOf<String?>("addrId", "street", "city", "state", "zip")
        )

        fun report(t: Throwable, message: String) {
            System.out.println("$message FAILED with exception toss")
            t.printStackTrace()
            System.exit(1)
        }

        fun test() {
            try {
                testInsert()
            } catch (t: Throwable) {
                report(t, "Insert")
            }
            try {
                testUpdate()
            } catch (t: Throwable) {
                report(t, "Update")
            }
            try {
                testDelete()
            } catch (t: Throwable) {
                report(t, "Delete")
            }
            try {
                testSelect()
            } catch (t: Throwable) {
                report(t, "Select")
            }
            try {
                testStore()
            } catch (t: Throwable) {
                report(t, "Store/Load")
            }
            try {
                testJoin()
            } catch (t: Throwable) {
                report(t, "Join")
            }
            try {
                testUndo()
            } catch (t: Throwable) {
                report(t, "Undo")
            }
        }

        fun testInsert() {
            people.insert(arrayOf("Holub", "Allen", "1"))
            people.insert(arrayOf("Flintstone", "Wilma", "2"))
            people.insert(arrayOf("addrId", "first", "last"), arrayOf("2", "Fred", "Flintstone"))
            address.insert(
                arrayOf(
                    "1", "123 MyStreet",
                    "Berkeley", "CA", "99999"
                )
            )
            val l: List = ArrayList()
            l.add("2")
            l.add("123 Quarry Ln.")
            l.add("Bedrock ")
            l.add("XX")
            l.add("12345")
            assert(address.insert(l) === 1)
            l.clear()
            l.add("3")
            l.add("Bogus")
            l.add("Bad")
            l.add("XX")
            l.add("12345")
            val c: List = ArrayList()
            c.add("addrId")
            c.add("street")
            c.add("city")
            c.add("state")
            c.add("zip")
            assert(address.insert(c, l) === 1)
            System.out.println(people.toString())
            System.out.println(address.toString())
            try {
                people.insert(arrayOf("x"))
                throw AssertionError(
                    "insert wrong number of fields test failed"
                )
            } catch (t: Throwable) { /* Failed correctly, do nothing */
            }
            try {
                people.insert(arrayOf("?"), arrayOf("y"))
                throw AssertionError(
                    "insert-nonexistent-field test failed"
                )
            } catch (t: Exception) { /* Failed correctly, do nothing */
            }
        }

        fun testUpdate() {
            System.out.println("update set state='YY' where state='XX'")
            val updated: Int = address.update(object : Selector() {
                fun approve(tables: Array<Cursor>): Boolean {
                    return tables[0].column("state").equals("XX")
                }

                fun modify(current: Cursor) {
                    current.update("state", "YY")
                }
            }
            )
            print(address)
            System.out.println("$updated rows affected\n")
        }

        fun testDelete() {
            System.out.println("delete where street='Bogus'")
            val deleted: Int = address.delete(object : Adapter() {
                fun approve(tables: Array<Cursor>): Boolean {
                    return tables[0].column("street").equals("Bogus")
                }
            }
            )
            print(address)
            System.out.println("$deleted rows affected\n")
        }

        fun testSelect() {
            val flintstoneSelector: Selector = object : Adapter() {
                fun approve(tables: Array<Cursor>): Boolean {
                    return tables[0].column("last").equals("Flintstone")
                }
            }

            // SELECT first, last FROM people WHERE last = "Flintstone"
            // The collection version chains to the string version, so the
            // following call tests both versions
            val columns: List = ArrayList()
            columns.add("first")
            columns.add("last")
            var result: Table = people.select(flintstoneSelector, columns)
            print(result)

            // SELECT * FROM people WHERE last = "Flintstone"
            result = people.select(flintstoneSelector)
            print(result)

            // Check that the result is indeed unmodifiable
            try {
                result.insert(arrayOf("x", "y", "z"))
                throw AssertionError(
                    "Insert to Immutable Table test failed"
                )
            } catch (e: Exception) { /*it failed correctly*/
            }
            try {
                result.update(flintstoneSelector)
                throw AssertionError(
                    "Update of Immutable Table test failed"
                )
            } catch (e: Exception) { /*it failed correctly*/
            }
            try {
                result.delete(flintstoneSelector)
                throw AssertionError(
                    "Delete of Immutable Table test failed"
                )
            } catch (e: Exception) { /*it failed correctly*/
            }
        }

        @Throws(IOException::class, ClassNotFoundException::class)
        fun testStore() {    // Flush the table to disk, then reread it.
            // Subsequent tests that use the "people" table will
            // fail if this operation fails.
            val out: Writer = FileWriter("people")
            people.export(CSVExporter(out))
            out.close()
            val `in`: Reader = FileReader("people")
            people = ConcreteTable(CSVImporter(`in`))
            `in`.close()
        }

        fun testJoin() {
            // First test a two-way join
            System.out.println(
                """
    
    SELECT first,last,street,city,state,zip FROM people, address WHERE people.addrId = address.addrId
    """.trimIndent()
            )

            // Collection version chains to String[] version,
            // so this code tests both:
            val columns: List = ArrayList()
            columns.add("first")
            columns.add("last")
            columns.add("street")
            columns.add("city")
            columns.add("state")
            columns.add("zip")
            val tables: List = ArrayList()
            tables.add(address)
            var result: Table =  // WHERE people.addrID = address.addrID
                people.select(
                    object : Adapter() {
                        fun approve(tables: Array<Cursor>): Boolean {
                            return tables[0].column("addrId")
                                .equals(tables[1].column("addrId"))
                        }
                    },
                    columns,
                    tables
                )
            print(result)
            System.out.println("")

            // Now test a three-way join
            //
            System.out.println(
                """
                    
                    SELECT first,last,street,city,state,zip,text FROM people, address, third WHERE (people.addrId = address.addrId) AND (people.addrId = third.addrId)
                    """.trimIndent()
            )
            val third: Table = TableFactory.create(
                "third", arrayOf<String?>("addrId", "text")
            )
            third.insert(arrayOf("1", "addrId=1"))
            third.insert(arrayOf("2", "addrId=2"))
            result = people.select(object : Adapter() {
                fun approve(tables: Array<Cursor>): Boolean {
                    return (tables[0].column("addrId")
                        .equals(tables[1].column("addrId"))
                            &&
                            tables[0].column("addrId")
                                .equals(tables[2].column("addrId")))
                }
            }, arrayOf("last", "first", "state", "text"), arrayOf<Table>(address, third))
            System.out.println(result.toString().toString() + "\n")
        }

        fun testUndo() {
            // Verify that commit works properly
            people.begin()
            System.out.println(
                "begin/insert into people (Solo, Han, 5)"
            )
            people.insert(arrayOf("Solo", "Han", "5"))
            System.out.println(people.toString())
            people.begin()
            System.out.println(
                "begin/insert into people (Lea, Princess, 6)"
            )
            people.insert(arrayOf("Lea", "Princess", "6"))
            System.out.println(people.toString())
            System.out.println(
                """
    commit(THIS_LEVEL)
    rollback(Table.THIS_LEVEL)
    
    """.trimIndent()
            )
            people.commit(Table.THIS_LEVEL)
            people.rollback(Table.THIS_LEVEL)
            System.out.println(people.toString())

            // Now test that nested transactions work correctly.
            System.out.println(people.toString())
            System.out.println("begin/insert into people (Vader,Darth, 4)")
            people.begin()
            people.insert(arrayOf("Vader", "Darth", "4"))
            System.out.println(people.toString())
            System.out.println(
                "begin/update people set last=Skywalker where last=Vader"
            )
            people.begin()
            people.update(object : Selector() {
                fun approve(tables: Array<Cursor>): Boolean {
                    return tables[0].column("last").equals("Vader")
                }

                fun modify(current: Cursor) {
                    current.update("last", "Skywalker")
                }
            }
            )
            System.out.println(people.toString())
            System.out.println("delete from people where last=Skywalker")
            people.delete(object : Adapter() {
                fun approve(tables: Array<Cursor>): Boolean {
                    return tables[0].column("last").equals("Skywalker")
                }
            }
            )
            System.out.println(people.toString())
            System.out.println(
                "rollback(Table.THIS_LEVEL) the delete and update"
            )
            people.rollback(Table.THIS_LEVEL)
            System.out.println(people.toString())
            System.out.println("rollback(Table.THIS_LEVEL) insert")
            people.rollback(Table.THIS_LEVEL)
            System.out.println(people.toString())
        }

        fun print(t: Table) {    // tests the table iterator
            val current: Cursor = t.rows()
            while (current.advance()) {
                val columns: Iterator = current.columns()
                while (columns.hasNext()) {
                    System.out.print(columns.next() as String?. toString () + " ")
                }
                System.out.println("")
            }
        }

        companion object {
            fun main(args: Array<String?>?) {
                Test().test()
            }
        }
    }

    companion object {
        /**
         * Think of the Cartesian product as a kind of tree. That is
         * given one table with rows A and B, and another table with rows
         * C and D, you can look at the product like this:
         *
         * root
         * ______|______
         * |			|
         * A  			B
         * ____|____   ____|____
         * |		|	|		|
         * C		D   C		D
         *
         * The tree is as deep as the number of tables we're joining.
         * Every possible path from the root to a leaf represents one row
         * in the Cartesian product. The current method effectively traverses
         * this tree recursively without building an actual tree. It
         * assembles an array of iterators (one for each table) positioned
         * at the current place in the set of rows as it recurses to a leaf,
         * and then asks the selector whether or not to approve that row.
         * It then goes up a notch, advances the correct iterator, and
         * recurses back down.
         */
        private fun selectFromCartesianProduct(
            level: Int,
            where: Selector,
            requestedColumns: Array<String?>,
            allTables: Array<Table?>,
            allIterators: Array<Cursor?>,
            resultTable: Table
        ) {
            allIterators[level] = allTables[level].rows()
            while (allIterators[level].advance()) {    // If we haven't reached the tips of the branches yet,
                // go down one more level.
                if (level < allIterators.size - 1) selectFromCartesianProduct(
                    level + 1, where,
                    requestedColumns,
                    allTables, allIterators, resultTable
                )

                // If we are at the leaf level, then get approval for
                // the fully-assembled row, and add the row to the table
                // if it's approved.
                if (level == allIterators.size - 1) {
                    if (where.approve(allIterators)) insertApprovedRows(
                        resultTable,
                        requestedColumns, allIterators
                    )
                }
            }
        }

        /**
         * Insert an approved row into the result table:
         * <PRE>
         * for( every requested column )
         * for( every table in the join )
         * if the requested column is in the current table
         * add the associated value to the result table
         *
        </PRE> *
         * Only one column with a given name is added, even if that column
         * appears in multiple tables. Columns in tables at the beginning
         * of the list take precedence over identically named columns that
         * occur later in the list.
         */
        private fun insertApprovedRows(
            resultTable: Table,
            requestedColumns: Array<String?>,
            allTables: Array<Cursor?>
        ) {
            val resultRow: Array<Object?> = arrayOfNulls<Object>(requestedColumns.size)
            for (i in requestedColumns.indices) {
                for (table in allTables.indices) {
                    try {
                        resultRow[i] = allTables[table]!!.column(requestedColumns[i])
                        break // if the assignment worked, do the next column
                    } catch (e: Exception) {    // otherwise, try the next table
                    }
                }
            }
            resultTable.insert( /*requestedColumns,*/resultRow)
        }
    }
}