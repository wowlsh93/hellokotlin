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

/***
 * @include /etc/license.txt
 */
object JDBCTest {
    var data = arrayOf(
        "(1,  'John',   'Mon', 1, 'JustJoe')",
        "(2,  'JS',     'Mon', 1, 'Cappuccino')",
        "(3,  'Marie',  'Mon', 2, 'CaffeMocha')"
    )

    @Throws(Exception::class)
    fun main(args: Array<String?>?) {
        Class.forName("com.holub.database.jdbc.JDBCDriver") //{=JDBCTest.forName}
            .newInstance()
        var connection: Connection? = null
        var statement: Statement? = null
        try {
            connection = DriverManager.getConnection( //{=JDBCTest.getConnection}
                "file:/c:/src/com/holub/database/jdbc/Dbase",
                "harpo", "swordfish"
            )
            statement = connection.createStatement()
            statement.executeUpdate(
                "create table test (" +
                        "  Entry      INTEGER      NOT NULL" +
                        ", Customer   VARCHAR (20) NOT NULL" +
                        ", DOW        VARCHAR (3)  NOT NULL" +
                        ", Cups       INTEGER      NOT NULL" +
                        ", Type       VARCHAR (10) NOT NULL" +
                        ", PRIMARY KEY( Entry )" +
                        ")"
            )
            for (i in data.indices) statement.executeUpdate(
                "insert into test VALUES " + data[i]
            )

            // Test Autocommit stuff. If everything's working
            // correctly, there James should be in the databse,
            // but Fred should not.
            connection.setAutoCommit(false)
            statement.executeUpdate(
                "insert into test VALUES " +
                        "(4, 'James',  'Thu', 1, 'Cappuccino')"
            )
            connection.commit()
            statement.executeUpdate(
                "insert into test (Customer) VALUES('Fred')"
            )
            connection.rollback()
            connection.setAutoCommit(true)

            // Print everything.
            val result: ResultSet = statement.executeQuery("select * from test")
            while (result.next()) {
                System.out.println(
                    result.getInt("Entry").toString() + ", "
                            + result.getString("Customer") + ", "
                            + result.getString("DOW") + ", "
                            + result.getInt("Cups") + ", "
                            + result.getString("Type")
                )
            }
        } finally {
            try {
                if (statement != null) statement.close()
            } catch (e: Exception) {
            }
            try {
                if (connection != null) connection.close()
            } catch (e: Exception) {
            }
        }
    }
}