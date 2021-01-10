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
class ConnectionAdapter : java.sql.Connection {
    constructor() {} // Not an error if this one is called.
    constructor(driver: java.sql.Driver?, url: String?, info: java.util.Properties?) {
        throw SQLException("ConnectionAdapter constructor unsupported")
    }

    @get:Throws(SQLException::class)
    @set:Throws(SQLException::class)
    var holdability: Int
        get() {
            throw SQLException("Connection.getHoldability() unsupported")
        }
        set(h) {
            throw SQLException("Connection.setHoldability(int h) unsupported")
        }

    //...
    //@middle
    @Throws(SQLException::class)
    fun setSavepoint(): Savepoint {
        throw SQLException("Connection.setSavepoint() unsupported")
    }

    @Throws(SQLException::class)
    fun setSavepoint(name: String?): Savepoint {
        throw SQLException("Connection.setSavepoint(String name) unsupported")
    }

    @Throws(SQLException::class)
    fun createStatement(): Statement {
        throw SQLException("Connection.createStatement() unsupported")
    }

    @Throws(SQLException::class)
    fun createStatement(a: Int, b: Int, c: Int): Statement {
        throw SQLException("Connection.createStatement(int a, int b, int c) unsupported")
    }

    @Throws(SQLException::class)
    fun createStatement(resultSetType: Int, resultSetConcurrency: Int): Statement {
        throw SQLException("Connection.createStatement(int resultSetType, int resultSetConcurrency) unsupported")
    }

    @get:Throws(SQLException::class)
    val metaData: DatabaseMetaData
        get() {
            throw SQLException("Connection.getMetaData() unsupported")
        }

    @Throws(SQLException::class)
    fun close() {
        throw SQLException("Connection.close() unsupported")
    }

    @get:Throws(SQLException::class)
    val isClosed: Boolean
        get() {
            throw SQLException("Connection.isClosed() unsupported")
        }

    @get:Throws(SQLException::class)
    @set:Throws(SQLException::class)
    var isReadOnly: Boolean
        get() {
            throw SQLException("Connection.isReadOnly() unsupported")
        }
        set(readOnly) {
            throw SQLException("Connection.setReadOnly(boolean readOnly) unsupported")
        }

    @Throws(SQLException::class)
    fun clearWarnings() {
        throw SQLException("Connection.clearWarnings() unsupported")
    }

    @Throws(SQLException::class)
    fun commit() {
        throw SQLException("Connection.commit() unsupported")
    }

    @get:Throws(SQLException::class)
    @set:Throws(SQLException::class)
    var autoCommit: Boolean
        get() {
            throw SQLException("Connection.getAutoCommit() unsupported")
        }
        set(autoCommit) {
            throw SQLException("Connection.setAutoCommit(boolean autoCommit) unsupported")
        }

    @get:Throws(SQLException::class)
    @set:Throws(SQLException::class)
    var catalog: String?
        get() {
            throw SQLException("Connection.getCatalog() unsupported")
        }
        set(catalog) {
            throw SQLException("Connection.setCatalog(String catalog) unsupported")
        }

    @get:Throws(SQLException::class)
    @set:Throws(SQLException::class)
    var transactionIsolation: Int
        get() {
            throw SQLException("Connection.getTransactionIsolation() unsupported")
        }
        set(level) {
            throw SQLException("Connection.setTransactionIsolation(int level) unsupported")
        }

    @get:Throws(SQLException::class)
    val warnings: SQLWarning
        get() {
            throw SQLException("Connection.getWarnings() unsupported")
        }

    @Throws(SQLException::class)
    fun nativeSQL(sql: String?): String {
        throw SQLException("Connection.nativeSQL(String sql) unsupported")
    }

    @Throws(SQLException::class)
    fun prepareCall(sql: String?): CallableStatement {
        throw SQLException("Connection.prepareCall(String sql) unsupported")
    }

    @Throws(SQLException::class)
    fun prepareCall(sql: String?, a: Int, b: Int, c: Int): CallableStatement {
        throw SQLException("Connection.prepareCall(String sql, int a, int b, int c) unsupported")
    }

    @Throws(SQLException::class)
    fun prepareCall(sql: String?, x: Int, y: Int): CallableStatement {
        throw SQLException("Connection.prepareCall(String sql, int x, int y) unsupported")
    }

    @Throws(SQLException::class)
    fun prepareStatement(sql: String?): PreparedStatement {
        throw SQLException("Connection.prepareStatement(String sql) unsupported")
    }

    @Throws(SQLException::class)
    fun prepareStatement(sql: String?, x: Int, y: Int): PreparedStatement {
        throw SQLException("Connection.prepareStatement(String sql, int x, int y) unsupported")
    }

    @Throws(SQLException::class)
    fun prepareStatement(sql: String?, columnNames: Array<String?>?): PreparedStatement {
        throw SQLException("Connection.prepareStatement(String sql,String[] columnNames) unsupported")
    }

    @Throws(SQLException::class)
    fun prepareStatement(sql: String?, columnIndexes: IntArray?): PreparedStatement {
        throw SQLException("Connection.prepareStatement(String sql,int[] columnIndexes) unsupported")
    }

    @Throws(SQLException::class)
    fun prepareStatement(sql: String?, a: Int, b: Int, c: Int): PreparedStatement {
        throw SQLException("Connection.prepareStatement(String sql,int a, int b, int c) unsupported")
    }

    @Throws(SQLException::class)
    fun prepareStatement(sql: String?, a: Int): PreparedStatement {
        throw SQLException("Connection.prepareStatement(String sql,int a) unsupported")
    }

    @Throws(SQLException::class)
    fun releaseSavepoint(s: Savepoint?) {
        throw SQLException("Connection.releaseSavepoint( Savepoint s ) unsupported")
    }

    @Throws(SQLException::class)
    fun rollback() {
        throw SQLException("Connection.rollback() unsupported")
    }

    @Throws(SQLException::class)
    fun rollback(s: Savepoint?) {
        throw SQLException("Connection.rollback(Savepoint s) unsupported")
    }

    @get:Throws(SQLException::class)
    @set:Throws(SQLException::class)
    var typeMap: Map<*, *>?
        get() {
            throw SQLException("unsupported")
        }
        set(map) {
            throw SQLException("Connection.setTypeMap(java.util.Map map) unsupported")
        }
}