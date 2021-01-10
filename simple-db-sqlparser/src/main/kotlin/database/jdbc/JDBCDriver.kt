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

import com.holub.database.jdbc.Console
import kotlin.Throws
import java.sql.SQLException

/** A JDBC driver for a small in-memory database that wraps
 * the [com.holub.database.Database] class. See that
 * class for a discussion of the supported SQL.
 *
 * @include /etc/license.txt
 *
 * @see com.holub.database.Database
 */
class JDBCDriver : java.sql.Driver {
    private var connection: JDBCConnection? = null

    companion object {
        init  //{=JDBCDriver.staticInitializer}
        {
            try {
                java.sql.DriverManager.registerDriver(JDBCDriver())
            } catch (e: SQLException) {
                System.err.println(e)
            }
        }
    }

    @Throws(SQLException::class)
    fun acceptsURL(url: String): Boolean {
        return url.startsWith("file:/")
    }

    @Throws(SQLException::class)
    fun connect(uri: String?, info: Properties?): Connection {
        return try {
            JDBCConnection(uri).also { connection = it }
        } catch (e: Exception) {
            throw SQLException(e.getMessage())
        }
    }

    val majorVersion: Int
        get() = 1
    val minorVersion: Int
        get() = 0

    fun jdbcCompliant(): Boolean {
        return false
    }

    @Throws(SQLException::class)
    fun getPropertyInfo(url: String?, info: Properties?): Array<DriverPropertyInfo?> {
        return arrayOfNulls<DriverPropertyInfo>(0)
    }
}