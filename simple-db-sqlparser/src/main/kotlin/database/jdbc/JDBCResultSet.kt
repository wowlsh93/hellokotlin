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

/** A limited version of the result-set class. All methods
 * not shown throw a [SQLException] if called. Note
 * the the underlying table actually holds nothing but
 * strings, so the numeric accessors
 * (e.g. [.getDouble])
 * are doing string-to-number and number-to-string
 * conversions. These conversions might fail if the
 * underlying String doesn't represent a number.
 *
 * @include /etc/license.txt
 */
class JDBCResultSet(cursor: Cursor) : ResultSetAdapter() {
    private val cursor: Cursor
    operator fun next(): Boolean {
        return cursor.advance()
    }

    @Throws(SQLException::class)
    fun getString(columnName: String): String? {
        return try {
            val contents: Object = cursor.column(columnName)
            if (contents == null) null else contents.toString()
        } catch (e: IndexOutOfBoundsException) {
            throw SQLException("column $columnName doesn't exist")
        }
    }

    @Throws(SQLException::class)
    fun getDouble(columnName: String): Double {
        return try {
            val contents = getString(columnName)
            if (contents == null) 0.0 else format.parse(contents).doubleValue()
        } catch (e: ParseException) {
            throw SQLException("field doesn't contain a number")
        }
    }

    @Throws(SQLException::class)
    fun getInt(columnName: String): Int {
        return try {
            val contents = getString(columnName)
            if (contents == null) 0 else format.parse(contents).intValue()
        } catch (e: ParseException) {
            throw SQLException("field doesn't contain a number")
        }
    }

    @Throws(SQLException::class)
    fun getLong(columnName: String): Long {
        return try {
            val contents = getString(columnName)
            if (contents == null) 0L else format.parse(contents).longValue()
        } catch (e: ParseException) {
            throw SQLException("field doesn't contain a number")
        }
    }

    fun updateNull(columnName: String?) {
        cursor.update(columnName, null)
    }

    fun updateDouble(columnName: String?, value: Double) {
        cursor.update(columnName, format.format(value))
    }

    fun updateInt(columnName: String?, value: Long) {
        cursor.update(columnName, format.format(value))
    }

    @get:Throws(SQLException::class)
    val metaData: ResultSetMetaData
        get() = JDBCResultSetMetaData(cursor)

    companion object {
        private val format: NumberFormat = NumberFormat.getInstance()
    }

    /** Wrap a result set around a Cursor. The cursor
     * should never have been advanced; just pass this constructor
     * the return value from [Table.rows].
     */
    init {
        this.cursor = cursor
    }
}