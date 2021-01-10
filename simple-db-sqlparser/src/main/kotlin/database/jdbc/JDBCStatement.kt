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

/***
 * @include /etc/license.txt
 */
class JDBCStatement(database: Database?) : StatementAdapter() {
    private val database: Database?
    @Throws(SQLException::class)
    fun executeUpdate(sqlString: String?): Int {
        return try {
            database.execute(sqlString)
            database.affectedRows()
        } catch (e: Exception) {
            throw SQLException(e.getMessage())
        }
    }

    @Throws(SQLException::class)
    fun executeQuery(sqlQuery: String?): ResultSet {
        return try {
            val result: Table = database.execute(sqlQuery)
            JDBCResultSet(result.rows())
        } catch (e: Exception) {
            throw SQLException(e.getMessage())
        }
    }

    @Throws(SQLException::class)
    fun close() {    // does nothing.
    }

    init {
        this.database = database
    }
}