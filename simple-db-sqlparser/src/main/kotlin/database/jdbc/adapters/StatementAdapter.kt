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
package com.holub.database.jdbc.adapters

import kotlin.Throws
import java.sql.ResultSet
import java.sql.Time
import java.util.Calendar
import java.sql.Blob
import java.sql.Clob
import java.sql.ResultSetMetaData
import java.sql.SQLException

/***
 * @include /etc/license.txt
 */
class StatementAdapter : java.sql.Statement {
    @get:Throws(SQLException::class)
    @set:Throws(SQLException::class)
    var fetchSize: Int
        get() {
            throw SQLException("Statement.getFetchSize() not supported")
        }
        set(fetchSize) {
            throw SQLException("Statement.setFetchSize(int fetchSize) not supported")
        }

    @get:Throws(SQLException::class)
    @set:Throws(SQLException::class)
    var maxRows: Int
        get() {
            throw SQLException("Statement.getMaxRows() not supported")
        }
        set(max) {
            throw SQLException("Statement.setMaxRows(int max) not supported")
        }

    @get:Throws(SQLException::class)
    @set:Throws(SQLException::class)
    var fetchDirection: Int
        get() {
            throw SQLException("Statement.getFetchDirection() not supported")
        }
        set(fetchDirection) {
            throw SQLException("Statement.setFetchDirection(int fetchDirection) not supported")
        }

    @get:Throws(SQLException::class)
    val resultSetConcurrency: Int
        get() {
            throw SQLException("Statement.getResultSetConcurrency() not supported")
        }

    @get:Throws(SQLException::class)
    val resultSetHoldability: Int
        get() {
            throw SQLException("Statement.getResultSetHoldability() not supported")
        }

    @get:Throws(SQLException::class)
    val resultSetType: Int
        get() {
            throw SQLException("Statement.getResultSetType() not supported")
        }

    @get:Throws(SQLException::class)
    @set:Throws(SQLException::class)
    var queryTimeout: Int
        get() {
            throw SQLException("Statement.getQueryTimeout() not supported")
        }
        set(seconds) {
            throw SQLException("Statement.setQueryTimeout(int seconds) not supported")
        }

    @get:Throws(SQLException::class)
    val resultSet: ResultSet
        get() {
            throw SQLException("Statement.getResultSet() not supported")
        }

    @Throws(SQLException::class)
    fun executeQuery(sql: String?): ResultSet {
        throw SQLException("Statement.executeQuery(String sql) not supported")
    }

    @Throws(SQLException::class)
    fun executeUpdate(sql: String?, i: Int): Int {
        throw SQLException("Statement.executeUpdate(String sql, int i) not supported")
    }

    @Throws(SQLException::class)
    fun executeUpdate(sql: String?, cols: Array<String?>?): Int {
        throw SQLException("Statement.executeUpdate(String sql, String[] cols) not supported")
    }

    @Throws(SQLException::class)
    fun execute(sql: String?): Boolean {
        throw SQLException("Statement.execute(String sql) not supported")
    }

    @Throws(SQLException::class)
    fun execute(sql: String?, cols: Array<String?>?): Boolean {
        throw SQLException("Statement.execute(String sql, String[] cols) not supported")
    }

    @Throws(SQLException::class)
    fun execute(sql: String?, i: Int): Boolean {
        throw SQLException("Statement.execute(String sql, int i) not supported")
    }

    @Throws(SQLException::class)
    fun execute(sql: String?, cols: IntArray?): Boolean {
        throw SQLException("Statement.execute(String sql, int[] cols) not supported")
    }

    @Throws(SQLException::class)
    fun cancel() {
        throw SQLException("Statement.cancel() not supported")
    }

    @Throws(SQLException::class)
    fun clearWarnings() {
        throw SQLException("Statement.clearWarnings() not supported")
    }

    @get:Throws(SQLException::class)
    val connection: Connection
        get() {
            throw SQLException("Statement.getConnection() not supported")
        }

    @get:Throws(SQLException::class)
    val generatedKeys: ResultSet
        get() {
            throw SQLException("Statement.getGeneratedKeys() not supported")
        }

    @Throws(SQLException::class)
    fun addBatch(sql: String?) {
        throw SQLException("Statement.addBatch(String sql) not supported")
    }

    @Throws(SQLException::class)
    fun executeBatch(): IntArray {
        throw SQLException("not supported")
    }

    @Throws(SQLException::class)
    fun clearBatch() {
        throw SQLException("Statement.clearBatch() not supported")
    }

    @Throws(SQLException::class)
    fun close() {
        throw SQLException("Statement.close() not supported")
    }

    @Throws(SQLException::class)
    fun executeUpdate(sql: String?, i: IntArray?): Int {
        throw SQLException("Statement.executeUpdate(String sql, int[] i) not supported")
    }

    @Throws(SQLException::class)
    fun executeUpdate(sql: String?): Int {
        throw SQLException("Statement.executeUpdate(String sql) not supported")
    }

    @get:Throws(SQLException::class)
    @set:Throws(SQLException::class)
    var maxFieldSize: Int
        get() {
            throw SQLException("Statement.getMaxFieldSize() not supported")
        }
        set(max) {
            throw SQLException("Statement.setMaxFieldSize(int max) not supported")
        }

    @get:Throws(SQLException::class)
    val moreResults: Boolean
        get() {
            throw SQLException("Statement.getMoreResults() not supported")
        }

    @Throws(SQLException::class)
    fun getMoreResults(i: Int): Boolean {
        throw SQLException("Statement.getMoreResults(int i) not supported")
    }

    @get:Throws(SQLException::class)
    val updateCount: Int
        get() {
            throw SQLException("Statement.getUpdateCount() not supported")
        }

    @get:Throws(SQLException::class)
    val warnings: SQLWarning
        get() {
            throw SQLException("Statement.getWarnings() not supported")
        }

    @Throws(SQLException::class)
    fun setCursorName(name: String?) {
        throw SQLException("Statement.setCursorName(String name) not supported")
    }

    @Throws(SQLException::class)
    fun setEscapeProcessing(enable: Boolean) {
        throw SQLException("Statement.setEscapeProcessing(boolean enable) not supported")
    }

    @Throws(SQLException::class)
    fun checkClosed() {
        throw SQLException("Statement.checkClosed() not supported")
    }
}