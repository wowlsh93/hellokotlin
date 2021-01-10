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
class ResultSetMetaDataAdapter : ResultSetMetaData {
    @Throws(SQLException::class)
    fun getCatalogName(column: Int): String {
        throw SQLException("ResultSetMetaData.getCatalogName(int column) not supported")
    }

    @Throws(SQLException::class)
    fun getColumnClassName(column: Int): String {
        throw SQLException("ResultSetMetaData.getColumnClassName(int column) not supported")
    }

    @get:Throws(SQLException::class)
    val columnCount: Int
        get() {
            throw SQLException("ResultSetMetaData.getColumnCount() not supported")
        }

    @Throws(SQLException::class)
    fun getColumnDisplaySize(column: Int): Int {
        throw SQLException("ResultSetMetaData.getColumnDisplaySize(int column) not supported")
    }

    @Throws(SQLException::class)
    fun getColumnLabel(column: Int): String {
        throw SQLException("ResultSetMetaData.getColumnLabel(int column) not supported")
    }

    @Throws(SQLException::class)
    fun getColumnName(column: Int): String {
        throw SQLException("ResultSetMetaData.getColumnName(int column) not supported")
    }

    @Throws(SQLException::class)
    fun getColumnType(column: Int): Int {
        throw SQLException("ResultSetMetaData.getColumnType(int column) not supported")
    }

    @Throws(SQLException::class)
    fun getColumnTypeName(column: Int): String {
        throw SQLException("ResultSetMetaData.getColumnTypeName(int column) not supported")
    }

    @Throws(SQLException::class)
    fun getPrecision(column: Int): Int {
        throw SQLException("ResultSetMetaData.getPrecision(int column) not supported")
    }

    @Throws(SQLException::class)
    fun getScale(column: Int): Int {
        throw SQLException("ResultSetMetaData.getScale(int column) not supported")
    }

    @Throws(SQLException::class)
    fun getSchemaName(column: Int): String {
        throw SQLException("ResultSetMetaData.getSchemaName(int column) not supported")
    }

    @Throws(SQLException::class)
    fun getTableName(column: Int): String {
        throw SQLException("ResultSetMetaData.getTableName(int column) not supported")
    }

    @Throws(SQLException::class)
    fun isAutoIncrement(column: Int): Boolean {
        throw SQLException("ResultSetMetaData.isAutoIncrement(int column) not supported")
    }

    @Throws(SQLException::class)
    fun isCaseSensitive(column: Int): Boolean {
        throw SQLException("ResultSetMetaData.isCaseSensitive(int column) not supported")
    }

    @Throws(SQLException::class)
    fun isCurrency(column: Int): Boolean {
        throw SQLException("ResultSetMetaData.isCurrency(int column) not supported")
    }

    @Throws(SQLException::class)
    fun isDefinitelyWritable(column: Int): Boolean {
        throw SQLException("ResultSetMetaData.isDefinitelyWritable(int column) not supported")
    }

    @Throws(SQLException::class)
    fun isNullable(column: Int): Int {
        throw SQLException("ResultSetMetaData.isNullable(int column) not supported")
    }

    @Throws(SQLException::class)
    fun isReadOnly(column: Int): Boolean {
        throw SQLException("ResultSetMetaData.isReadOnly(int column) not supported")
    }

    @Throws(SQLException::class)
    fun isSearchable(column: Int): Boolean {
        throw SQLException("ResultSetMetaData.isSearchable(int column) not supported")
    }

    @Throws(SQLException::class)
    fun isSigned(column: Int): Boolean {
        throw SQLException("ResultSetMetaData.isSigned(int column) not supported")
    }

    @Throws(SQLException::class)
    fun isWritable(column: Int): Boolean {
        throw SQLException("ResultSetMetaData.isWritable(int column) not supported")
    }
}