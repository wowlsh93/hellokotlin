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

/** A limited version of the Connection class. All methods
 * undocumented base-class overrides throw a
 * [SQLException] if called.
 *
 *
 * Note that you can't
 * mix non-autocommit behavior with explicit
 * SQL begin/commit statements. For example, if you
 * turn off autocommit mode (which causes a SQL begin
 * to be issued), and then execute a SQL begin manually,
 * a call to `commit` will commit the inner transaction,
 * but not the outer one. In effect, you can't do
 * nested transactions using the JDBC [.commit] or
 * [.rollback]  methods.
 *
 * @include /etc/license.txt
 */
class JDBCConnection(uri: URI?) : ConnectionAdapter() {
    private var database: Database?

    // Establish a connection to the indicated database.
    //
    constructor(uri: String?) : this(URI(uri)) {}

    /** Close a database connection. A commit is issued
     * automatically if auto-commit mode is disabled.
     * @see .setAutoCommit
     */
    @Throws(SQLException::class)
    fun close() {
        database = try {
            autoCommitState.close()
            database.dump()
            null // make the memory reclaimable and
            // also force a nullPointerException
            // if anybody tries to use the
            // connection after it's closed.
        } catch (e: IOException) {
            throw SQLException(e.getMessage())
        }
    }

    @Throws(SQLException::class)
    fun createStatement(): Statement {
        return JDBCStatement(database)
    }

    /** Terminate the current transactions and start a new
     * one. Does nothing if auto-commit mode is on.
     * @see .setAutoCommit
     */
    @Throws(SQLException::class)
    fun commit() {
        autoCommitState.commit()
    }

    /** Roll back the current transactions and start a new
     * one. Does nothing if auto-commit mode is on.
     * @see .setAutoCommit
     */
    @Throws(SQLException::class)
    fun rollback() {
        autoCommitState.rollback()
    }
    /** Return true if auto-commit mode is enabled  */
    /**
     * Once set true, all SQL statements form a stand-alone
     * transaction. A begin is issued automatically when
     * auto-commit mode is disabled so that the [.commit]
     * and [.rollback] methods will work correctly.
     * Similarly, a commit is issued automatically when
     * auto-commit mode is enabled.
     *
     *
     * Auto-commit mode is on by default.
     */
    @get:Throws(SQLException::class)
    @set:Throws(SQLException::class)
    var autoCommit: Boolean
        get() = autoCommitState === enabled
        set(enable) {
            autoCommitState.setAutoCommit(enable)
        }

    //----------------------------------------------------------------------
    private interface AutoCommitBehavior {
        @Throws(SQLException::class)
        fun close()

        @Throws(SQLException::class)
        fun commit()

        @Throws(SQLException::class)
        fun rollback()

        @Throws(SQLException::class)
        fun setAutoCommit(enable: Boolean)
    }

    private val enabled: AutoCommitBehavior = object : AutoCommitBehavior {
        @Throws(SQLException::class)
        override fun close() { /* nothing to do */
        }

        override fun commit() { /* nothing to do */
        }

        override fun rollback() { /* nothing to do */
        }

        override fun setAutoCommit(enable: Boolean) {
            if (enable == false) {
                database.begin()
                autoCommitState = disabled
            }
        }
    }
    private val disabled: AutoCommitBehavior = object : AutoCommitBehavior {
        @Throws(SQLException::class)
        override fun close() {
            try {
                database.commit()
            } catch (e: ParseFailure) {
                throw SQLException(e.getMessage())
            }
        }

        @Throws(SQLException::class)
        override fun commit() {
            try {
                database.commit()
                database.begin()
            } catch (e: ParseFailure) {
                throw SQLException(e.getMessage())
            }
        }

        @Throws(SQLException::class)
        override fun rollback() {
            try {
                database.rollback()
                database.begin()
            } catch (e: ParseFailure) {
                throw SQLException(e.getMessage())
            }
        }

        @Throws(SQLException::class)
        override fun setAutoCommit(enable: Boolean) {
            try {
                if (enable == true) {
                    database.commit()
                    autoCommitState = enabled
                }
            } catch (e: ParseFailure) {
                throw SQLException(e.getMessage())
            }
        }
    }
    private var autoCommitState = enabled

    init {
        database = Database(uri)
    }
}