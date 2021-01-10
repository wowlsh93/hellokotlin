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
package com.holub.database.jdbc

import com.holub.database.*

/** A limited version of the result-set metadata class. All methods
 * not shown throw a [SQLException] if called.
 *
 * @include /etc/license.txt
 */
class JDBCResultSetMetaData(cursor: Cursor) : ResultSetMetaDataAdapter() {
    private val cursor: Cursor
    @Throws(SQLException::class)
    fun getColumnType(column: Int): Int {
        return java.sql.Types.VARCHAR
    }

    @Throws(SQLException::class)
    fun getColumnTypeName(column: Int): String {
        return "VARCHAR"
    }

    @Throws(SQLException::class)
    fun getColumnName(index: Int): String {    // The Cursor is zero indexed, which makes sense from a Java
        // perspective. JDBC is 1 indexed, however; thus the -1.
        return cursor.columnName(index - 1)
    }

    @get:Throws(SQLException::class)
    val columnCount: Int
        get() = cursor.columnCount()

    @get:Throws(SQLException::class)
    val tableName: String
        get() = cursor.tableName()

    init {
        this.cursor = cursor
    }
}