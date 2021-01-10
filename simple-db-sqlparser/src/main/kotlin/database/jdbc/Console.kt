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

/** This program is a toy database-console window that lets you
 * exercise the HolubSQL database. It opens up a databse, then
 * displays two windows, one in which you enter SQL and another
 * that shows the result of the operation. If an exception is
 * encountered, a window showing the stack trace pops up.
 *
 *
 * Bugs:
 * The window does not resize elegantly, so I've disabled
 * resising altogether rather than fix the problem.
 *
 * @include /etc/license.txt
 */
class Console {
    private var connection: Connection? = null
    private var statement: Statement? = null
    private val mainFrame: JFrame = JFrame("HolubSQL Console")
    private val sqlIn: JTextArea = JTextArea(5, 60)
    private val sqlOut: JTextArea = JTextArea(20, 60)
    private val submitButton: JButton = JButton("Submit")
    private fun addToFrame(addThis: JComponent, fill: Int, weighty: Double) {
        ++constraint.gridy
        constraint.fill = fill
        constraint.weighty = weighty
        mainFrame.getContentPane().add(addThis, constraint)
    }

    //----------------------------------------------------------------------
    private fun processSQL() {
        val input: String = sqlIn.getText().replaceAll("\\s+", " ")
        val statements: Array<String> = input.split(";")
        val line = "====================================\n"
        sqlIn.setText("")
        for (i in statements.indices) {
            try {
                statements[i] = statements[i].trim()
                if (statements[i].length() === 0) continue
                if (!(statements[i].startsWith("SELECT") || statements[i].startsWith("select"))) {
                    val status: Int = statement.executeUpdate(statements[i])
                    sqlOut.setText(
                        sqlOut.getText() + line +
                                "Processed: " + statements[i] +
                                "\nStatus=" + String.valueOf(status) + "\n"
                    )
                } else {
                    val results: ResultSet = statement.executeQuery(statements[i])
                    sqlOut.setText(
                        sqlOut.getText() + line +
                                "Processed: " + statements[i] +
                                "\nResults:\n" + resultSetasString(results)
                    )
                }
            } catch (e: Exception) {
                val message = """Error while processing statement:
	${statements[i]}
${e.toString()}"""
                sqlOut.setText(sqlOut.getText() + line + message)
                displayException(message, e)
            }
        }
    }

    //----------------------------------------------------------------------
    private fun displayException(message: String, e: Exception) {
        val trace = StringWriter()
        e.printStackTrace(PrintWriter(trace))
        JOptionPane.showMessageDialog(
            mainFrame, """
     $message
     
     ${trace.toString()}
     """.trimIndent(), "Alert",
            JOptionPane.ERROR_MESSAGE
        )
    }

    //----------------------------------------------------------------------
    @Throws(SQLException::class)
    private fun resultSetasString(results: ResultSet): String {
        val metadata: ResultSetMetaData = results.getMetaData()
        val b = StringBuffer()
        val columns: Int = metadata.getColumnCount()
        for (i in 1..columns) b.append(formatColumn(metadata.getColumnName(i), 10))
        b.append("\n")
        for (i in 1..columns) b.append("--------- ")
        b.append("\n")
        while (results.next()) {
            for (i in 1..columns) b.append(formatColumn(results.getString(metadata.getColumnName(i)), 10))
            b.append("\n")
        }
        return b.toString()
    }

    //----------------------------------------------------------------------
    private fun formatColumn(msg: String, width: Int): String {
        var width = width
        val b = StringBuffer(msg)
        width -= msg.length()
        while (--width >= 0) {
            b.append(" ")
        }
        return b.toString()
    }

    //----------------------------------------------------------------------
    private fun openDatabase() {
        var databaseName: String
        while (true) {
            databaseName = JOptionPane.showInputDialog(
                """
    Enter database directory (e.g. c:/tmp/foo)
    Directory must exist.
    """.trimIndent()
            )
            if (databaseName == null) System.exit(1)
            val database = File(databaseName)
            if (database.exists() && database.isDirectory()) break else JOptionPane.showMessageDialog(
                mainFrame,
                """Directory $databaseName does not exist.
Please create it before continuing.""",
                "Error",
                JOptionPane.ERROR_MESSAGE
            )
        }
        try {
            connection = DriverManager.getConnection("file:/$databaseName", "harpo", "swordfish")
            statement = connection.createStatement()
        } catch (e: SQLException) {
            displayException("Couldn't open database: $databaseName", e)
        }
    }

    //----------------------------------------------------------------------
    private fun closeDatabase() {
        try {
            if (statement != null) statement.close()
            if (connection != null) connection.close()
        } catch (e: Exception) {
            displayException("Closing connection", e)
        }
    }

    companion object {
        private const val driverName = "com.holub.database.jdbc.JDBCDriver"

        //----------------------------------------------------------------------
        private val constraint: GridBagConstraints = GridBagConstraints(
            0,  // int gridx,
            0,  // int gridy,
            1,  // int gridwidth,
            1,  // int gridheight,
            1.0,  // double weightx,
            0.0,  // double weighty,
            GridBagConstraints.WEST,  // int anchor,
            GridBagConstraints.BOTH,  // int fill,
            Insets(0, 0, 0, 0),  // Insets insets,
            10,  // int ipadx,
            10 // int ipady)
        )

        //----------------------------------------------------------------------
        @Throws(Exception::class)
        fun main(args: Array<String?>?) {
            Console()
        }
    }

    init {
        mainFrame.getContentPane().setLayout(GridBagLayout())
    }

    init {
        sqlIn.setFont(Font("Monospaced", Font.PLAIN, 14))
    }

    init {
        sqlOut.setFont(Font("Monospaced", Font.PLAIN, 14))
    }

    init {
        submitButton.setMaximumSize(submitButton.getMinimumSize())
        submitButton.addActionListener(object : ActionListener() {
            fun actionPerformed(e: ActionEvent?) {
                processSQL()
            }
        }
        )
    }

    //----------------------------------------------------------------------
    init {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
            Class.forName(driverName).newInstance()
            openDatabase()
            addToFrame(
                JLabel(
                    "Type SQL here then click \"Submit.\" "
                            + "Separate statements with semicolons."
                ),
                GridBagConstraints.NONE, 0.0
            )
            addToFrame(JScrollPane(sqlIn), GridBagConstraints.BOTH, 0.0)
            addToFrame(submitButton, GridBagConstraints.NONE, 0.0)
            addToFrame(JLabel(""), GridBagConstraints.NONE, 0.0)
            addToFrame(JLabel("Output:"), GridBagConstraints.BOTH, 1.0)
            addToFrame(JScrollPane(sqlOut), GridBagConstraints.NONE, 0.0)
            mainFrame.addWindowListener(object : WindowAdapter() {
                fun windowClosing(e: WindowEvent?) {
                    closeDatabase()
                    System.exit(1)
                }
            }
            )
            mainFrame.setResizable(false)
            mainFrame.pack()
            mainFrame.setVisible(true)
        } catch (e: ClassNotFoundException) {
            displayException("Couldn't find driver: " + driverName, e)
        } catch (e: InstantiationException) {
            displayException("Couldn't load driver: " + driverName, e)
        } catch (e: IllegalAccessException) {
            displayException("Couldn't access driver: " + driverName, e)
        } catch (e: UnsupportedLookAndFeelException) {
            displayException("Couldn't set look and feel: " + driverName, e)
        }
    }
}