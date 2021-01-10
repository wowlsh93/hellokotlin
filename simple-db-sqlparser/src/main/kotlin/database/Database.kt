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

import java.text.NumberFormat

/***
 * This class implements a small SQL-subset database.
 * that provides a front end to the Table classes.
 * Find the grammar for the supported language below.
 * The remainder of the documentation of this class assumes that
 * you know a little SQL (see the
 * [HolubSQL web page](http://www.holub.com/software/HolubSQL) for a few SQL references).
 *
 *
 * My intent is to do simple things, only.
 * None of the niceties of SQL (like aliases, outer and inner joins,
 * permissions, views, etc.) are supported.
 * The file src/com/holub/database/Database.test.sql in the original
 * distribution .jar file demonstrates the SQL subset that's supported.
 *
 *
 * A database is effectively a directory, and a table is effectively
 * a file in the directory.
 * The argument to USE DATABASE specifies
 * the full path to that directory. A table name is a file name
 * with the ".csv" extension added. Note that a simple name (as in
 * "USE DATABASE foo" will create a subdirectory called "foo" in
 * the current directory. Use a full path name to get something
 * else: "USE DATABASE c:/tmp/foo"
 * See [CSVExporter] for a description of the file format.
 *
 *
 * Because database names are path names, identifier names in general
 * can contain characters that would normally go in a path
 * (/ \ : ~ _) but they cannot contain a dot or dash (so your
 * database name can't have a dot or dash in it either). Identifiers
 * can't contain spaces, and they cannot start with digits.
 *
 *
 * SELECT statements support FROM and WHERE clauses, but nothing else.
 * (DISTINCT, ORDEREDBY, etc., aren't supported; neither are subqueries.)
 * You can join an arbitrary number of tables in a SELECT, but outer
 * and inner joins (ans subqueries) aren't supported. A few operators
 * (BETWEEN, IN) aren't supported---check the grammar, below.
 * Any Java/Perl regular expression can be used as an argument to LIKE,
 * and for SQL compatibility, a % wild card is automatically mapped to ".*".
 * Selecting "into" another table works, but bear in mind that the actual
 * data is shared between tables. Since everything in the table
 * is a String, this strategy works fine *unless* you
 * use the [Table] object that's returned from [.execute]
 * to modify the table directly. Don't do that.
 *
 *
 * Though the following types are recognized by the parser (so you
 * can use them in the SQL), but they are ignored. Everything's stored
 * in the underlying database as a String. Strings that represent
 * numbers (can be parsed successfully by [java.text.NumberFormat])
 * can be used in arithmetic expressions, however.
 * <table border=1 cellpadding=3 cellspacing=0>
 * <tr><td>integer(maxDigits)<br></br>
 * int(maxDigits)<br></br>
 * smallint(maxDigits)<br></br>
 * bigint(maxDigits)<br></br>
 * tinyint(size)</td><td>integers</td></tr>
 * <tr><td>decimal(l,r)<br></br>
 * real(l,r)<br></br>
 * double(l,r)<br></br>
 * numeric(l,r)</td><td>floating point, l and r specify the maximum
 * number of digits to the left and right of the decimal.</td></tr>
 * <tr><td>char(length)</td><td>Fixed length string.</td></tr>
 * <tr><td>varchar(maximum_length)</td><td>Variable length string.</td></tr>
 * <tr><td>date(format)</td><td>Date in the Gregorian calendar with optional format.</td></tr>
</table> *
 * You may specify a "PRIMARY KEY(identifier)" in the list of columns,
 * but it's ignored, too.
 *
 *
 * Numbers in the input must begin with a digit (.10  doesn't work. 0.10 does),
 * and decimal fractions less than 1.0E-20 are assumed to be 0.
 * (That is 1.000000000000000000001 is rounded down to 1.0, and
 * will be put into the table as the integer 1.
 *
 *
 * You can't
 * store a Boolean value as such, but if you decide on some
 * string like "true" and "false" as meaningful, and use it
 * consistently, then comparisons and assignments of boolean
 * values will work fine. Null is supported.
 *
 *
 * Simple transactions (in the sense of a group of SQL statements
 * that execute atomically, which can be rolled back) are supported.
 * Initially, no transaction is active, and all SQL requests are
 * effectively committed immediately on execution.
 * This auto-commit mode is superceded once you issue a BEGIN, but
 * is reinstated as soon as the matching COMMIT or ROLLBACK is
 * encountered. All requests that occur between the BEGIN
 * and COMMIT are treated as a single unit. If you close (or DUMP)
 * the database without a formal COMMIT or ROLLBACK, then any open
 * transactions are effectively committed.
 * The [.begin], [.commit], and [.rollback]
 * methods have the same effect as issuing the equivalent SQL requests.
 *
 *
 * Transactions affect only modifications of tables.
 * Tables that are created or dumped during a transaction are not
 * destroyed (or put back in their original state on the disk)
 * if that transaction is rolled back.
 *
 *
 * An exception-toss that occurs when processing a SQL
 * expression submitted to [.execute] causes an automatic
 * rollback before the exception is tossed out to your code.
 * This automatic-rollback behavior <u>is not implemented</u> by
 * the methods that mimic SQL statements
 * ([useDatabase(...)][.useDatabase],
 * [createDatabase(...)][.createDatabase],
 * [createTable(...)][.createTable],
 * [dropTable(...)][.dropTable], and
 * [dump(...)][.dump]). If you use these methods, you'll
 * have to catch any exceptions manually and call [.rollback]
 * or [.commit] explicitly.
 *
 *
 * The modified database is not stored
 * to disk until a DUMP is issued. (In the JDBC wrapper,
 * an automatic DUMP occurs when you close the Connection).
 *
 *
 * This class wraps various [Table] derivatives, but this
 * class also relies on the fact that the table is made up entirely
 * of [String] objects. You can use this class to
 * access [Table] objects that were created directly by
 * yourself, but problems can arise if those manually created
 * tables have	anything other than Strings in them. In particular,
 * [Object.toString] method is used to get the value of
 * a cell, and if the value is modified through an UPDATE, the
 * new value is stored as a String, without regard to the
 * original field type.)
 *
 *
 * Here's the grammar I've implemented ("expr"=expression,
 * "id"=identifier, "opt"=optional, "e"=epsilon. "[...]" is
 * an optional subproduction.
 * <PRE>
 * statement       ::=
 * INSERT  INTO IDENTIFIER [LP idList RP]
 * VALUES LP exprList RP
 * |   CREATE  DATABASE IDENTIFIER
 * |   CREATE  TABLE    IDENTIFIER LP declarations RP
 * |   DROP    TABLE    IDENTIFIER
 * |   BEGIN    [WORK|TRAN[SACTION]]
 * |   COMMIT   [WORK|TRAN[SACTION]]
 * |   ROLLBACK [WORK|TRAN[SACTION]]
 * |   DUMP
 * |   USE     DATABASE IDENTIFIER
 * |   UPDATE  IDENTIFIER SET IDENTIFIER
 * EQUAL expr WHERE expr
 * |   DELETE  FROM IDENTIFIER WHERE expr
 * |   SELECT  [INTO identifier] idList
 * FROM idList [WHERE expr]
 *
 * idList          ::= IDENTIFIER idList' | STAR
 * idList'         ::= COMMA IDENTIFIER idList'
 * |   e
 *
 * declarations    ::= IDENTIFIER [type] [NOT [NULL]] declaration'
 * declarations'   ::= COMMA IDENTIFIER [type] declarations'
 * |   COMMA PRIMARY KEY LP IDENTIFIER RP
 * |   e
 *
 * type            ::=  INTEGER [ LP expr RP               ]
 * |    CHAR    [ LP expr RP               ]
 * |    NUMERIC [ LP expr COMMA expr RP    ]
 * |    DATE           // format spec is part of token
 *
 * exprList        ::=       expr exprList'
 * exprList'       ::= COMMA expr exprList'
 * |   e
 *
 * expr            ::=     andExpr expr'
 * expr'           ::= OR  andExpr expr'
 * |   e
 *
 * andExpr         ::=     relationalExpr andExpr'
 * andExpr'        ::= AND relationalExpr andExpr'
 * |   e
 *
 * relationalExpr ::=          additiveExpr relationalExpr'
 * relationalExpr'::=    RELOP additiveExpr relationalExpr'
 * | EQUAL additiveExpr relationalExpr'
 * | LIKE  additiveExpr relationalExpr'
 * | e
 *
 * additiveExpr        ::=          multiplicativeExpr additiveExpr'
 * additiveExpr'       ::= ADDITIVE multiplicativeExpr additiveExpr'
 * |   e
 *
 * multiplicativeExpr  ::=     term multiplicativeExpr'
 * multiplicativeExpr' ::= STAR  term multiplicativeExpr'
 * |   SLASH term multiplicativeExpr'
 * |   e
 *
 * term                ::= NOT factor
 * |   LP expr RP
 * |   factor
 *
 * factor              ::= compoundId | STRING | NUMBER | NULL
 *
 * compoundId          ::= IDENTIFIER compoundId'
 * compoundId'         ::= DOT IDENTIFIER
 * |   e
 *
 * Most runtime errors (including inappropriate use of nulls) cause
 * a exception toss.
</PRE> *
 * Most of the methods of this class throw a [ParseFailure]
 * (a checked [Exception]) if something goes wrong.
 *
 *
 *
 * **Modifications Since Publication of Holub on Patterns:**
 * <table border="1" cellspacing="0" cellpadding="3">
 * <tr><td valign="top">9/24/02</td>
 * <td>
 * Added a few methods to the Cursor interface (and
 * local implemenation) to make it possible to get
 * column-related metadata in the
 * [java.sql.ResultSetMetaData] class.
</td> *
</tr> *
</table> *
 *
 * @include /etc/license.txt
 */
class Database {
    /* The directory that represents the database.
	 */
    private var location: File = File(".")

    /** The number of rows modified by the last
     * INSERT, DELETE, or UPDATE request.
     */
    private var affectedRows = 0

    /** This Map holds the tables that are currently active. I
     * have to use be a Map (as compared to a Set), because
     * HashSet uses the equals() function to resolve ambiguity.
     * This requirement would force me to define "equals" on
     * a Table as "having the same name as another table," which
     * I believe is semantically incorrect. Equals should match
     * both name and contents. I avoid the problem entirely by
     * using an external key, even if that key is also an
     * accessible attribute of the Table.
     *
     *
     * The table is actually a specialization of Map
     * that requires a Table value argument, and interacts
     * with the transaction-processing system.
     */
    private val tables: Map = TableMap(HashMap())

    /** The current transaction-nesting level, incremented for
     * a BEGIN and decremented for a COMMIT or ROLLBACK.
     */
    private var transactionLevel = 0

    /** A Map proxy that hanldes lazy instatiation of tables
     * from the disk.
     */
    private inner class TableMap(realMap: Map) : Map {
        private val realMap: Map

        /** If the requested table is already in memory, return it.
         * Otherwise load it from the disk.
         */
        operator fun get(key: Object): Object? {
            val tableName = key as String
            return try {
                var desiredTable: Table = realMap.get(tableName) as Table
                if (desiredTable == null) {
                    desiredTable = TableFactory.load("$tableName.csv", location)
                    put(tableName, desiredTable)
                }
                desiredTable
            } catch (e: IOException) {    // Can't use verify(...) or error(...) here because the
                // base-class "get" method doesn't throw any exceptions.
                // Kludge a runtime-exception toss. Call in.failure()
                // to get an exception object that calls out the
                // input file name and line number, then transmogrify
                // the ParseFailure to a RuntimeException.
                val message = """
                    Table not created internally and couldn't be loaded.(${e.getMessage()})
                    
                    """.trimIndent()
                throw RuntimeException(
                    `in`.failure(message).getMessage()
                )
            }
        }

        fun put(key: Object?, value: Object?): Object {    // If transactions are active, put the new
            // table into the same transaction state
            // as the other tables.
            for (i in 0 until transactionLevel) (value as Table?).begin()
            return realMap.put(key, value)
        }

        fun putAll(m: Map?) {
            throw UnsupportedOperationException()
        }

        fun size(): Int {
            return realMap.size()
        }

        val isEmpty: Boolean
            get() = realMap.isEmpty()

        fun remove(k: Object?): Object {
            return realMap.remove(k)
        }

        fun clear() {
            realMap.clear()
        }

        fun keySet(): Set {
            return realMap.keySet()
        }

        fun values(): Collection {
            return realMap.values()
        }

        fun entrySet(): Set {
            return realMap.entrySet()
        }

        fun equals(o: Object?): Boolean {
            return realMap.equals(o)
        }

        override fun hashCode(): Int {
            return realMap.hashCode()
        }

        fun containsKey(k: Object?): Boolean {
            return realMap.containsKey(k)
        }

        fun containsValue(v: Object?): Boolean {
            return realMap.containsValue(v)
        }

        init {
            this.realMap = realMap
        }
    }

    private var expression // SQL expression being parsed
            : String? = null
    private var `in` // The current scanner.
            : Scanner? = null

    // Enums to identify operators not recognized at the token level
    // These are used by various inner classes, but must be declared
    // at the outer-class level because they're static.
    private class RelationalOperator
    private class MathOperator {}
    //@declarations-end
    //--------------------------------------------------------------
    /** Create a database object attached to the current directory.
     * You can specify a different directory after the object
     * is created by calling [.useDatabase].
     */
    constructor() {}

    /** Use the indicated directory for the database  */
    constructor(directory: URI?) {
        useDatabase(File(directory))
    }

    /**  Use the indicated directory for the database  */
    constructor(path: File) {
        useDatabase(path)
    }

    /**  Use the indicated directory for the database  */
    constructor(path: String?) {
        useDatabase(File(path))
    }

    /** Use this constructor to wrap one or more Table
     * objects so that you can access them using
     * SQL. You may add tables to this database using
     * SQL "CREATE TABLE" statements, and you may safely
     * extract a snapshot of a table that you create
     * in this way using:
     * <PRE>
     * Table t = execute( "SELECT * from " + tableName );
    </PRE> *
     * @param database an array of tables to use as
     * the database.
     * @param path        The default directory to search for
     * tables, and the directory to which
     * tables are dumped. Tables specified
     * in the `database` argument
     * are used in place of any table
     * on the disk that has the same name.
     */
    constructor(path: File, database: Array<Table>) {
        useDatabase(path)
        for (i in database.indices) tables.put(database[i].name(), database[i])
    }
    //--------------------------------------------------------------
    // Private parse-related workhorse functions.
    /** Asks the scanner to throw a [ParseFailure] object
     * that highlights the current input position.
     */
    @Throws(ParseFailure::class)
    private fun error(message: String) {
        throw `in`.failure(message)
    }

    /** Like [.error], but throws the exception only if the
     * test fails.
     */
    @Throws(ParseFailure::class)
    private fun verify(test: Boolean, message: String) {
        if (!test) throw `in`.failure(message)
    }
    //--------------------------------------------------------------
    // Public methods that duplicate some SQL statements.
    // The SQL interpreter calls these methods to
    // do the actual work.
    /** Use an existing "database." In the current implementation,
     * a "database" is a directory and tables are files within
     * the directory. An active database (opened by a constructor,
     * a USE DATABASE directive, or a prior call to the current
     * method) is closed and committed before the new database is
     * opened.
     * @param path A [File] object that specifies directory
     * that represents the database.
     * @throws IOException if the directory that represents the
     * database can't be found.
     */
    @Throws(IOException::class)
    fun useDatabase(path: File) {
        dump()
        tables.clear() // close old database if there is one
        location = path
    }

    /** Create a database by opening the indicated directory. All
     * tables must be files in that directory. If you don't call
     * this method (or issue a SQL CREATE DATABASE directive), then
     * the current directory is used.
     * @throws IOException if the named directory can't be opened.
     */
    @Throws(IOException::class)
    fun createDatabase(name: String?) {
        val location = File(name)
        location.mkdir()
        this.location = location
    }

    /** Create a new table. If a table by this name exists, it's
     * overwritten.
     */
    fun createTable(name: String?, columns: List) {
        val columnNames = arrayOfNulls<String>(columns.size())
        var i = 0
        val names: Iterator = columns.iterator()
        while (names.hasNext()) {
            columnNames[i++] = names.next() as String
        }
        val newTable: Table = TableFactory.create(name, columnNames)
        tables.put(name, newTable)
    }

    /** Destroy both internal and external (on the disk) versions
     * of the specified table.
     */
    fun dropTable(name: String?) {
        tables.remove(name) // ignore the error if there is one.
        val tableFile = File(location, name)
        if (tableFile.exists()) tableFile.delete()
    }

    /** Flush to the persistent store (e.g. disk) all tables that
     * are "dirty" (which have been modified since the database
     * was last committed). These tables will not be flushed
     * again unless they are modified after the current dump()
     * call. Nothing happens if no tables are dirty.
     *
     *
     * The present implemenation flushes to a .csv file whose name
     * is the table name with a ".csv" extension added.
     */
    @Throws(IOException::class)
    fun dump() {
        val values: Collection = tables.values()
        if (values != null) {
            val i: Iterator = values.iterator()
            while (i.hasNext()) {
                val current: Table = i.next() as Table
                if (current.isDirty()) {
                    val out: Writer = FileWriter(
                        File(location, current.name().toString() + ".csv")
                    )
                    current.export(CSVExporter(out))
                    out.close()
                }
            }
        }
    }

    /** Return the number of rows that were affected by the most recent
     * [.execute] call. Zero is returned for all operations except
     * for INSERT, DELETE, or UPDATE.
     */
    fun affectedRows(): Int {
        return affectedRows
    }
    //@transactions-start
    //----------------------------------------------------------------------
    // Transaction processing.
    /** Begin a transaction
     */
    fun begin() {
        ++transactionLevel
        val currentTables: Collection = tables.values()
        val i: Iterator = currentTables.iterator()
        while (i.hasNext()) {
            (i.next() as Table).begin()
        }
    }

    /** Commit transactions at the current level.
     * @throws NoSuchElementException if no `begin()` was issued.
     */
    @Throws(ParseFailure::class)
    fun commit() {
        assert(transactionLevel > 0) { "No begin() for commit()" }
        --transactionLevel
        try {
            val currentTables: Collection = tables.values()
            val i: Iterator = currentTables.iterator()
            while (i.hasNext()) {
                (i.next() as Table).commit(Table.THIS_LEVEL)
            }
        } catch (e: NoSuchElementException) {
            verify(false, "No BEGIN to match COMMIT")
        }
    }

    /** Roll back transactions at the current level
     * @throws NoSuchElementException if no `begin()` was issued.
     */
    @Throws(ParseFailure::class)
    fun rollback() {
        assert(transactionLevel > 0) { "No begin() for commit()" }
        --transactionLevel
        try {
            val currentTables: Collection = tables.values()
            val i: Iterator = currentTables.iterator()
            while (i.hasNext()) {
                (i.next() as Table).rollback(Table.THIS_LEVEL)
            }
        } catch (e: NoSuchElementException) {
            verify(false, "No BEGIN to match ROLLBACK")
        }
    }
    //@transactions-end
    //@parser-start
    /*******************************************************************
     * Execute a SQL statement. If an exception is tossed and we are in the
     * middle of a transaction (a begin has been issued but no matching
     * commit has been seen), the transaction is rolled back.
     *
     * @return a [Table] holding the result of a SELECT,
     * or null for statements other than SELECT.
     * @param expression a String holding a single SQL statement. The
     * complete statement must be present (you cannot break a long
     * statement into multiple calls), and text
     * following the SQL statement is ignored.
     * @throws com.holub.text.ParseFailure if the SQL is corrupt.
     * @throws IOException Database files couldn't be accessed or created.
     * @see .affectedRows
     */
    @Throws(IOException::class, ParseFailure::class)
    fun execute(expression: String?): Table? {
        return try {
            this.expression = expression
            `in` = Scanner(tokens, expression)
            `in`.advance() // advance to the first token.
            statement()
        } catch (e: ParseFailure) {
            if (transactionLevel > 0) rollback()
            throw e
        } catch (e: IOException) {
            if (transactionLevel > 0) rollback()
            throw e
        }
    }

    /**
     * <PRE>
     * statement
     * ::= CREATE  DATABASE IDENTIFIER
     * |   CREATE  TABLE    IDENTIFIER LP idList RP
     * |   DROP    TABLE    IDENTIFIER
     * |   USE     DATABASE IDENTIFIER
     * |   BEGIN    [WORK|TRAN[SACTION]]
     * |   COMMIT   [WORK|TRAN[SACTION]]
     * |   ROLLBACK [WORK|TRAN[SACTION]]
     * |   DUMP
     *
     * |   INSERT  INTO IDENTIFIER [LP idList RP]
     * VALUES LP exprList RP
     * |   UPDATE  IDENTIFIER SET IDENTIFIER
     * EQUAL expr [WHERE expr]
     * |   DELETE  FROM IDENTIFIER WHERE expr
     * |   SELECT  idList [INTO table] FROM idList [WHERE expr]
    </PRE> *
     *
     *
     *
     * @return a Table holding the result of a SELECT, or null for
     * other SQL requests. The result table is treated like
     * a normal database table if the SELECT contains an INTO
     * clause, otherwise it's a temporary table that's not
     * put into the database.
     *
     * @throws ParseFailure something's wrong with the SQL
     * @throws IOException a database or table couldn't be opened
     * or accessed.
     * @see .createDatabase
     *
     * @see .createTable
     *
     * @see .dropTable
     *
     * @see .useDatabase
     */
    @Throws(ParseFailure::class, IOException::class)
    private fun statement(): Table? {
        affectedRows = 0 // is modified by UPDATE, INSERT, DELETE

        // These productions map to public method calls:
        if (`in`.matchAdvance(CREATE) != null) {
            if (`in`.match(DATABASE)) {
                `in`.advance()
                createDatabase(`in`.required(IDENTIFIER))
            } else  // must be CREATE TABLE
            {
                `in`.required(TABLE)
                val tableName: String = `in`.required(IDENTIFIER)
                `in`.required(LP)
                createTable(tableName, declarations())
                `in`.required(RP)
            }
        } else if (`in`.matchAdvance(DROP) != null) {
            `in`.required(TABLE)
            dropTable(`in`.required(IDENTIFIER))
        } else if (`in`.matchAdvance(USE) != null) {
            `in`.required(DATABASE)
            useDatabase(File(`in`.required(IDENTIFIER)))
        } else if (`in`.matchAdvance(BEGIN) != null) {
            `in`.matchAdvance(WORK) // ignore it if it's there
            begin()
        } else if (`in`.matchAdvance(ROLLBACK) != null) {
            `in`.matchAdvance(WORK) // ignore it if it's there
            rollback()
        } else if (`in`.matchAdvance(COMMIT) != null) {
            `in`.matchAdvance(WORK) // ignore it if it's there
            commit()
        } else if (`in`.matchAdvance(DUMP) != null) {
            dump()
        } else if (`in`.matchAdvance(INSERT) != null) {
            `in`.required(INTO)
            val tableName: String = `in`.required(IDENTIFIER)
            var columns: List? = null
            var values: List? = null
            if (`in`.matchAdvance(LP) != null) {
                columns = idList()
                `in`.required(RP)
            }
            if (`in`.required(VALUES) != null) {
                `in`.required(LP)
                values = exprList()
                `in`.required(RP)
            }
            affectedRows = doInsert(tableName, columns, values)
        } else if (`in`.matchAdvance(UPDATE) != null) {    // First parse the expression
            val tableName: String = `in`.required(IDENTIFIER)
            `in`.required(SET)
            val columnName: String = `in`.required(IDENTIFIER)
            `in`.required(EQUAL)
            val value = expr()
            `in`.required(WHERE)
            affectedRows = doUpdate(tableName, columnName, value, expr())
        } else if (`in`.matchAdvance(DELETE) != null) {
            `in`.required(FROM)
            val tableName: String = `in`.required(IDENTIFIER)
            `in`.required(WHERE)
            affectedRows = doDelete(tableName, expr())
        } else if (`in`.matchAdvance(SELECT) != null) {
            val columns: List? = idList()
            var into: String? = null
            if (`in`.matchAdvance(INTO) != null) into =
                `in`.required(IDENTIFIER)
            `in`.required(FROM)
            val requestedTableNames: List? = idList()
            val where =
                if (`in`.matchAdvance(WHERE) == null) null else expr()
            return doSelect(
                columns, into,
                requestedTableNames, where
            )
        } else {
            error(
                "Expected insert, create, drop, use, "
                        + "update, delete or select"
            )
        }
        return null
    }

    //----------------------------------------------------------------------
    // idList			::= IDENTIFIER idList' | STAR
    // idList'			::= COMMA IDENTIFIER idList'
    // 					|	e
    // Return a Collection holding the list of columns
    // or null if a * was found.
    @Throws(ParseFailure::class)
    private fun idList(): List? {
        var identifiers: List? = null
        if (`in`.matchAdvance(STAR) == null) {
            identifiers = ArrayList()
            var id: String?
            while (`in`.required(IDENTIFIER).also { id = it } != null) {
                identifiers.add(id)
                if (`in`.matchAdvance(COMMA) == null) break
            }
        }
        return identifiers
    }

    //----------------------------------------------------------------------
    // declarations  ::= IDENTIFIER [type] declaration'
    // declarations' ::= COMMA IDENTIFIER [type] [NOT [NULL]] declarations'
    //				 |   e
    //
    // type			 ::= INTEGER [ LP expr RP 				]
    //				 |	 CHAR 	 [ LP expr RP				]
    //				 |	 NUMERIC [ LP expr COMMA expr RP	]
    //				 |	 DATE			// format spec is part of token
    @Throws(ParseFailure::class)
    private fun declarations(): List {
        val identifiers: List = ArrayList()
        var id: String
        while (true) {
            if (`in`.matchAdvance(PRIMARY) != null) {
                `in`.required(KEY)
                `in`.required(LP)
                `in`.required(IDENTIFIER)
                `in`.required(RP)
            } else {
                id = `in`.required(IDENTIFIER)
                identifiers.add(id) // get the identifier

                // Skip past a type declaration if one's there
                if (`in`.matchAdvance(INTEGER) != null
                    || `in`.matchAdvance(CHAR) != null
                ) {
                    if (`in`.matchAdvance(LP) != null) {
                        expr()
                        `in`.required(RP)
                    }
                } else if (`in`.matchAdvance(NUMERIC) != null) {
                    if (`in`.matchAdvance(LP) != null) {
                        expr()
                        `in`.required(COMMA)
                        expr()
                        `in`.required(RP)
                    }
                } else if (`in`.matchAdvance(DATE) != null) {
                    // do nothing
                }
                `in`.matchAdvance(NOT)
                `in`.matchAdvance(NULL)
            }
            if (`in`.matchAdvance(COMMA) == null) // no more columns
                break
        }
        return identifiers
    }

    // exprList 		::= 	  expr exprList'
    // exprList'		::= COMMA expr exprList'
    // 					|	e
    @Throws(ParseFailure::class)
    private fun exprList(): List {
        val expressions: List = LinkedList()
        expressions.add(expr())
        while (`in`.matchAdvance(COMMA) != null) {
            expressions.add(expr())
        }
        return expressions
    }

    /** Top-level expression production. Returns an Expression
     * object which will interpret the expression at runtime
     * when you call it's evaluate() method.
     * <PRE>
     * expr    ::=     andExpr expr'
     * expr'   ::= OR  andExpr expr'
     * |   e
    </PRE> *
     */
    @Throws(ParseFailure::class)
    private fun expr(): Expression? {
        var left = andExpr()
        while (`in`.matchAdvance(OR) != null) left = LogicalExpression(left, OR, andExpr())
        return left
    }

    // andExpr			::= 	relationalExpr andExpr'
    // andExpr'			::= AND relationalExpr andExpr'
    // 					|	e
    @Throws(ParseFailure::class)
    private fun andExpr(): Expression? {
        var left = relationalExpr()
        while (`in`.matchAdvance(AND) != null) left = LogicalExpression(left, AND, relationalExpr())
        return left
    }

    // relationalExpr ::=   		additiveExpr relationalExpr'
    // relationalExpr'::=	  RELOP additiveExpr relationalExpr'
    // 						| EQUAL additiveExpr relationalExpr'
    // 						| LIKE  additiveExpr relationalExpr'
    // 						| e
    @Throws(ParseFailure::class)
    private fun relationalExpr(): Expression? {
        var left = additiveExpr()
        while (true) {
            var lexeme: String
            if (`in`.matchAdvance(RELOP).also { lexeme = it } != null) {
                var op: RelationalOperator
                op = if (lexeme.length() === 1) if (lexeme.charAt(0) === '<') LT else GT else {
                    if (lexeme.charAt(0) === '<' && lexeme.charAt(1) === '>') NE else if (lexeme.charAt(0) === '<') LE else GE
                }
                left = RelationalExpression(left, op, additiveExpr())
            } else if (`in`.matchAdvance(EQUAL) != null) {
                left = RelationalExpression(left, EQ, additiveExpr())
            } else if (`in`.matchAdvance(LIKE) != null) {
                left = LikeExpression(left, additiveExpr())
            } else break
        }
        return left
    }

    // additiveExpr	::= 			 multiplicativeExpr additiveExpr'
    // additiveExpr'	::= ADDITIVE multiplicativeExpr additiveExpr'
    // 					|	e
    @Throws(ParseFailure::class)
    private fun additiveExpr(): Expression? {
        var lexeme: String
        var left = multiplicativeExpr()
        while (`in`.matchAdvance(ADDITIVE).also { lexeme = it } != null) {
            val op = if (lexeme.charAt(0) === '+') PLUS else MINUS
            left = ArithmeticExpression(
                left, multiplicativeExpr(), op
            )
        }
        return left
    }

    // multiplicativeExpr	::=       term multiplicativeExpr'
    // multiplicativeExpr'	::= STAR  term multiplicativeExpr'
    // 						|	SLASH term multiplicativeExpr'
    // 						|	e
    @Throws(ParseFailure::class)
    private fun multiplicativeExpr(): Expression? {
        var left = term()
        while (true) {
            if (`in`.matchAdvance(STAR) != null) left =
                ArithmeticExpression(left, term(), TIMES) else if (`in`.matchAdvance(
                    SLASH
                ) != null
            ) left = ArithmeticExpression(left, term(), DIVIDE) else break
        }
        return left
    }

    // term				::=	NOT expr
    // 					|	LP expr RP
    // 					|	factor
    @Throws(ParseFailure::class)
    private fun term(): Expression? {
        return if (`in`.matchAdvance(NOT) != null) {
            NotExpression(expr())
        } else if (`in`.matchAdvance(LP) != null) {
            val toReturn = expr()
            `in`.required(RP)
            toReturn
        } else factor()
    }

    // factor			::= compoundId | STRING | NUMBER | NULL
    // compoundId		::= IDENTIFIER compoundId'
    // compoundId'		::= DOT IDENTIFIER
    // 					|	e
    @Throws(ParseFailure::class)
    private fun factor(): Expression? {
        try {
            var lexeme: String?
            val result: Value
            if (`in`.matchAdvance(STRING).also { lexeme = it } != null) result =
                StringValue(lexeme) else if (`in`.matchAdvance(
                    NUMBER
                ).also { lexeme = it } != null
            ) result = NumericValue(lexeme) else if (`in`.matchAdvance(NULL).also { lexeme = it } != null) result =
                NullValue() else {
                var columnName: String = `in`.required(IDENTIFIER)
                var tableName: String? = null
                if (`in`.matchAdvance(DOT) != null) {
                    tableName = columnName
                    columnName = `in`.required(IDENTIFIER)
                }
                result = IdValue(tableName, columnName)
            }
            return AtomicExpression(result)
        } catch (e: java.text.ParseException) { /* fall through */
        }
        error("Couldn't parse Number") // Always throws a ParseFailure
        return null
    }

    //@parser-end
    //@expression-start
    //======================================================================
    // The methods that parse the the productions rooted in expr work in
    // concert to build an Expression object that evaluates the expression.
    // This is an example of both the Interpreter and Composite pattern.
    // An expression is represented in memory as an abstract syntax tree
    // made up of instances of the following classes, each of which
    // references its subexpressions.
    private interface Expression {
        /* Evaluate an expression using rows identified by the
		 * two iterators passed as arguments. <code>j</code>
		 * is null unless a join is being processed.
		 */
        @Throws(ParseFailure::class)
        fun evaluate(tables: Array<Cursor?>?): Value
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private inner class ArithmeticExpression(
        private val left: Expression, private val right: Expression,
        private val operator: MathOperator
    ) : Expression {
        @Throws(ParseFailure::class)
        override fun evaluate(tables: Array<Cursor?>?): Value {
            val leftValue = left.evaluate(tables)
            val rightValue = right.evaluate(tables)
            verify(
                leftValue is NumericValue
                        && rightValue is NumericValue,
                "Operands to < > <= >= = must be Boolean"
            )
            val l = (leftValue as NumericValue).value()
            val r = (rightValue as NumericValue).value()
            return NumericValue(
                if (operator === PLUS) l + r else if (operator === MINUS) l - r else if (operator === TIMES) l * r else  /* operator == DIVIDE  */ l / r
            )
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private inner class LogicalExpression(
        left: Expression, op: Token,
        right: Expression
    ) : Expression {
        private val isAnd: Boolean
        private val left: Expression
        private val right: Expression
        @Throws(ParseFailure::class)
        override fun evaluate(tables: Array<Cursor?>?): Value {
            val leftValue = left.evaluate(tables)
            val rightValue = right.evaluate(tables)
            verify(
                leftValue is BooleanValue
                        && rightValue is BooleanValue,
                "operands to AND and OR must be logical/relational"
            )
            val l = (leftValue as BooleanValue).value()
            val r = (rightValue as BooleanValue).value()
            return BooleanValue(if (isAnd) l && r else l || r)
        }

        init {
            assert(op === AND || op === OR)
            isAnd = op === AND
            this.left = left
            this.right = right
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private inner class NotExpression(private val operand: Expression) : Expression {
        @Throws(ParseFailure::class)
        override fun evaluate(tables: Array<Cursor?>?): Value {
            val value = operand.evaluate(tables)
            verify(
                value is BooleanValue,
                "operands to NOT must be logical/relational"
            )
            return BooleanValue(!(value as BooleanValue).value())
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private inner class RelationalExpression(
        private val left: Expression,
        private val operator: RelationalOperator,
        private val right: Expression
    ) : Expression {
        @Throws(ParseFailure::class)
        override fun evaluate(tables: Array<Cursor?>?): Value {
            var leftValue = left.evaluate(tables)
            var rightValue = right.evaluate(tables)
            if (leftValue is StringValue
                || rightValue is StringValue
            ) {
                verify(
                    operator === EQ || operator === NE,
                    "Can't use < <= > or >= with string"
                )
                val isEqual = leftValue.toString().equals(rightValue.toString())
                return BooleanValue(if (operator === EQ) isEqual else !isEqual)
            }
            if (rightValue is NullValue
                || leftValue is NullValue
            ) {
                verify(
                    operator === EQ || operator === NE,
                    "Can't use < <= > or >= with NULL"
                )

                // Return true if both the left and right sides are instances
                // of NullValue.
                val isEqual = leftValue.getClass() === rightValue.getClass()
                return BooleanValue(if (operator === EQ) isEqual else !isEqual)
            }

            // Convert Boolean values to numbers so we can compare them.
            //
            if (leftValue is BooleanValue) leftValue = NumericValue(
                if (leftValue.value()) 1 else 0
            )
            if (rightValue is BooleanValue) rightValue = NumericValue(
                if (rightValue.value()) 1 else 0
            )
            verify(
                leftValue is NumericValue
                        && rightValue is NumericValue,
                "Operands must be numbers"
            )
            val l = (leftValue as NumericValue).value()
            val r = (rightValue as NumericValue).value()
            return BooleanValue(
                if (operator === EQ) l == r else if (operator === NE) l != r else if (operator === LT) l > r else if (operator === GT) l < r else if (operator === LE) l <= r else  /* operator == GE	 */ l >= r
            )
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private inner class LikeExpression(private val left: Expression, private val right: Expression) : Expression {
        @Throws(ParseFailure::class)
        override fun evaluate(tables: Array<Cursor?>?): Value {
            val leftValue = left.evaluate(tables)
            val rightValue = right.evaluate(tables)
            verify(
                leftValue is StringValue
                        && rightValue is StringValue,
                "Both operands to LIKE must be strings"
            )
            val compareTo = (leftValue as StringValue).value()
            var regex = (rightValue as StringValue).value()
            regex = regex.replaceAll("%", ".*")
            return BooleanValue(compareTo.matches(regex))
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private inner class AtomicExpression(private val atom: Value) : Expression {
        override fun evaluate(tables: Array<Cursor>): Value {
            return if (atom is IdValue) atom.value(tables) // lookup cell in table and
            else atom // convert to appropriate type
        }
    }

    //@expression-end
    //@value-start
    //--------------------------------------------------------------
    // The expression classes pass values around as they evaluate
    // the expression.  // There  are four value subtypes that represent
    // the possible/ operands to an expression (null, numbers,
    // strings, table.column). The implementors of Value provide
    // convenience methods for using those operands.
    //
    private interface Value // tagging interface

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private class NullValue : Value {
        override fun toString(): String {
            return null
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private class BooleanValue(var value: Boolean) : Value {
        fun value(): Boolean {
            return value
        }

        override fun toString(): String {
            return String.valueOf(value)
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private class StringValue(lexeme: String?) : Value {
        private val value: String
        fun value(): String {
            return value
        }

        override fun toString(): String {
            return value
        }

        init {
            value = lexeme.replaceAll("['\"](.*?)['\"]", "$1")
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private inner class NumericValue : Value {
        private var value: Double

        constructor(value: Double) // initialize from a double.
        {
            this.value = value
        }

        constructor(s: String?) {
            value = NumberFormat.getInstance().parse(s).doubleValue()
        }

        fun value(): Double {
            return value
        }

        override fun toString(): String // round down if the fraction is very small
        {
            return if (Math.abs(value - Math.floor(value)) < 1.0E-20) String.valueOf(value.toLong()) else String.valueOf(
                value
            )
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private inner class IdValue(var tableName: String?, var columnName: String) : Value {
        /** Using the cursor, extract the referenced cell from
         * the current Row and return it's contents as a String.
         * @return the value as a String or null if the cell
         * was null.
         */
        fun toString(participants: Array<Cursor>): String? {
            var content: Object? = null

            // If no name is to the left of the dot, then use
            // the (only) table.
            if (tableName == null) content = participants[0].column(columnName) else {
                val container: Table = tables.get(tableName) as Table

                // Search for the table whose name matches
                // the one to the left of the dot, then extract
                // the desired column from that table.
                content = null
                for (i in participants.indices) {
                    if (participants[i].isTraversing(container)) {
                        content = participants[i].column(columnName)
                        break
                    }
                }
            }

            // All table contents are converted to Strings, whatever
            // their original type. This conversion can cause
            // problems if the table was created manually.
            return if (content == null) null else content.toString()
        }

        /** Using the cursor, extract the referenced cell from the
         * current row of the appropriate table, convert the
         * contents to a [NullValue], [NumericValue],
         * or [StringValue], as appropriate, and return
         * that value object.
         */
        fun value(participants: Array<Cursor>): Value {
            val s = toString(participants)
            try {
                return if (s == null) NullValue() else (NumericValue(s) as Value?)!!
            } catch (e: java.text.ParseException) {    // The NumericValue constructor failed, so it must be
                // a string. Fall through to the return-a-string case.
            }
            return StringValue(s)
        }
    }

    //@value-end
    //@workhorse-start
    //======================================================================
    // Workhorse methods called from the parser.
    //
    @Throws(ParseFailure::class)
    private fun doSelect(
        columns: List?, into: String?,
        requestedTableNames: List?,
        where: Expression?
    ): Table {
        val tableNames: Iterator = requestedTableNames.iterator()
        assert(tableNames.hasNext()) { "No tables to use in select!" }

        // The primary table is the first one listed in the
        // FROM clause. The participantsInJoin are the other
        // tables listed in the FROM clause. We're passed in the
        // table names; use these names to get the actual Table
        // objects.
        val primary: Table = tables.get(tableNames.next() as String) as Table
        val participantsInJoin: List = ArrayList()
        while (tableNames.hasNext()) {
            val participant = tableNames.next() as String
            participantsInJoin.add(tables.get(participant))
        }

        // Now do the select operation. First create a Strategy
        // object that picks the correct rows, then pass that
        // object through to the primary table's select() method.
        val selector: Selector = if (where == null) Selector.ALL else  //{=Database.selector}
            object : Adapter() {
                fun approve(tables: Array<Cursor?>?): Boolean {
                    return try {
                        val result = where.evaluate(tables)
                        verify(
                            result is BooleanValue,
                            "WHERE clause must yield boolean result"
                        )
                        (result as BooleanValue).value()
                    } catch (e: ParseFailure) {
                        throw ThrowableContainer(e)
                    }
                }
            }
        return try {
            var result: Table = primary.select(selector, columns, participantsInJoin)

            // If this is a "SELECT INTO <table>" request, remove the 
            // returned table from the UnmodifiableTable wrapper, give
            // it a name, and put it into the tables Map.
            if (into != null) {
                result = (result as UnmodifiableTable).extract()
                result.rename(into)
                tables.put(into, result)
            }
            result
        } catch (container: ThrowableContainer) {
            throw container.contents() as ParseFailure
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Throws(ParseFailure::class)
    private fun doInsert(tableName: String, columns: List?, values: List?): Int {
        val processedValues: List = LinkedList()
        val t: Table = tables.get(tableName) as Table
        val i: Iterator = values.iterator()
        while (i.hasNext()) {
            val current = i.next() as Expression
            processedValues.add(
                current.evaluate(null).toString()
            )
        }

        // finally, put the values into the table.
        if (columns == null) return t.insert(processedValues)
        verify(
            columns.size() === values.size(),
            "There must be a value for every listed column"
        )
        return t.insert(columns, processedValues)
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Throws(ParseFailure::class)
    private fun doUpdate(
        tableName: String, columnName: String,
        value: Expression?, where: Expression?
    ): Int {
        val t: Table = tables.get(tableName) as Table
        return try {
            t.update(object : Selector() {
                fun approve(tables: Array<Cursor?>?): Boolean {
                    return try {
                        val result = where!!.evaluate(tables)
                        verify(
                            result is BooleanValue,
                            "WHERE clause must yield boolean result"
                        )
                        (result as BooleanValue).value()
                    } catch (e: ParseFailure) {
                        throw ThrowableContainer(e)
                    }
                }

                fun modify(current: Cursor) {
                    try {
                        val newValue: Value = value.evaluate(arrayOf<Cursor>(current))
                        current.update(columnName, newValue.toString())
                    } catch (e: ParseFailure) {
                        throw ThrowableContainer(e)
                    }
                }
            }
            )
        } catch (container: ThrowableContainer) {
            throw container.contents() as ParseFailure
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Throws(ParseFailure::class)
    private fun doDelete(tableName: String, where: Expression?): Int {
        val t: Table = tables.get(tableName) as Table
        return try {
            t.delete(object : Adapter() {
                fun approve(tables: Array<Cursor?>?): Boolean {
                    return try {
                        val result = where!!.evaluate(tables)
                        verify(
                            result is BooleanValue,
                            "WHERE clause must yield boolean result"
                        )
                        (result as BooleanValue).value()
                    } catch (e: ParseFailure) {
                        throw ThrowableContainer(e)
                    }
                }
            }
            )
        } catch (container: ThrowableContainer) {
            throw container.contents() as ParseFailure
        }
    }

    //@workhorse-end
    //--------------------------------------------------------------
    object Test {
        @Throws(IOException::class, ParseFailure::class)
        fun main(args: Array<String?>?) {
            val theDatabase = Database()

            // Read a sequence of SQL statements in from the file
            // Database.test.sql and execute them.
            val sql = BufferedReader(
                FileReader("Database.test.sql")
            )
            var test: String
            while (sql.readLine().also { test = it } != null) {
                test = test.trim()
                if (test.length() === 0) continue
                while (test.endsWith("\\")) {
                    test = test.substring(0, test.length() - 1)
                    test += sql.readLine().trim()
                }
                System.out.println("Parsing: $test")
                val result: Table? = theDatabase.execute(test)
                if (result != null) // it was a SELECT of some sort
                    System.out.println(result.toString())
            }
            try {
                theDatabase.execute("insert garbage SQL")
                System.out.println("Database FAILED")
                System.exit(1)
            } catch (e: ParseFailure) {
                System.out.println(
                    """
    Correctly found garbage SQL:
    $e
    ${e.getErrorReport()}
    """.trimIndent()
                )
            }
            theDatabase.dump()
            System.out.println("Database PASSED")
            System.exit(0)
        }
    }

    companion object {
        //@token-start
        //--------------------------------------------------------------
        // The token set used by the parser. Tokens automatically
        // The Scanner object matches the specification against the
        // input in the order of creation. For example, it's important
        // that the NUMBER token is declared before the IDENTIFIER token
        // since the regular expression associated with IDENTIFIERS
        // will also recognize some legitimate numbers.
        private val tokens: TokenSet = TokenSet()
        private val COMMA: Token = tokens.create("',")

        //{=Database.firstToken}
        private val EQUAL: Token = tokens.create("'=")
        private val LP: Token = tokens.create("'(")
        private val RP: Token = tokens.create("')")
        private val DOT: Token = tokens.create("'.")
        private val STAR: Token = tokens.create("'*")
        private val SLASH: Token = tokens.create("'/")
        private val AND: Token = tokens.create("'AND")
        private val BEGIN: Token = tokens.create("'BEGIN")
        private val COMMIT: Token = tokens.create("'COMMIT")
        private val CREATE: Token = tokens.create("'CREATE")
        private val DATABASE: Token = tokens.create("'DATABASE")
        private val DELETE: Token = tokens.create("'DELETE")
        private val DROP: Token = tokens.create("'DROP")
        private val DUMP: Token = tokens.create("'DUMP")
        private val FROM: Token = tokens.create("'FROM")
        private val INSERT: Token = tokens.create("'INSERT")
        private val INTO: Token = tokens.create("'INTO")
        private val KEY: Token = tokens.create("'KEY")
        private val LIKE: Token = tokens.create("'LIKE")
        private val NOT: Token = tokens.create("'NOT")
        private val NULL: Token = tokens.create("'NULL")
        private val OR: Token = tokens.create("'OR")
        private val PRIMARY: Token = tokens.create("'PRIMARY")
        private val ROLLBACK: Token = tokens.create("'ROLLBACK")
        private val SELECT: Token = tokens.create("'SELECT")
        private val SET: Token = tokens.create("'SET")
        private val TABLE: Token = tokens.create("'TABLE")
        private val UPDATE: Token = tokens.create("'UPDATE")
        private val USE: Token = tokens.create("'USE")
        private val VALUES: Token = tokens.create("'VALUES")
        private val WHERE: Token = tokens.create("'WHERE")
        private val WORK: Token = tokens.create("WORK|TRAN(SACTION)?")
        private val ADDITIVE: Token = tokens.create("\\+|-")
        private val STRING: Token = tokens.create("(\".*?\")|('.*?')")
        private val RELOP: Token = tokens.create("[<>][=>]?")
        private val NUMBER: Token = tokens.create("[0-9]+(\\.[0-9]+)?")
        private val INTEGER: Token = tokens.create("(small|tiny|big)?int(eger)?")
        private val NUMERIC: Token = tokens.create("decimal|numeric|real|double")
        private val CHAR: Token = tokens.create("(var)?char")
        private val DATE: Token = tokens.create("date(\\s*\\(.*?\\))?")
        private val IDENTIFIER: Token = tokens.create("[a-zA-Z_0-9/\\\\:~]+") //{=Database.lastToken}
        private val EQ = RelationalOperator()
        private val LT = RelationalOperator()
        private val GT = RelationalOperator()
        private val LE = RelationalOperator()
        private val GE = RelationalOperator()
        private val NE = RelationalOperator()
        private val PLUS = MathOperator()
        private val MINUS = MathOperator()
        private val TIMES = MathOperator()
        private val DIVIDE = MathOperator()
    }
}