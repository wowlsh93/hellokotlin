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

import java.math.BigDecimal

/***
 * @include /etc/license.txt
 */
class ResultSetAdapter : ResultSet {
    @Throws(SQLException::class)
    operator fun next(): Boolean {
        throw SQLException("ResultSet.next() unsupported")
    }

    @Throws(SQLException::class)
    fun findColumn(columnName: String?): Int {
        throw SQLException("ResultSet.findColumn(String columnName) unsupported")
    }

    @get:Throws(SQLException::class)
    val row: Int
        get() {
            throw SQLException("ResultSet.getRow() unsupported")
        }

    @Throws(SQLException::class)
    fun previous(): Boolean {
        throw SQLException("ResultSet.previous() unsupported")
    }

    @Throws(SQLException::class)
    fun absolute(row: Int): Boolean {
        throw SQLException("ResultSet.absolute(int row) unsupported")
    }

    @Throws(SQLException::class)
    fun relative(row: Int): Boolean {
        throw SQLException("ResultSet.relative(int row) unsupported")
    }

    @get:Throws(SQLException::class)
    @set:Throws(SQLException::class)
    var fetchDirection: Int
        get() {
            throw SQLException("ResultSet.getFetchDirection() unsupported")
        }
        set(dir) {
            throw SQLException("ResultSet.setFetchDirection(int dir) unsupported")
        }

    @get:Throws(SQLException::class)
    @set:Throws(SQLException::class)
    var fetchSize: Int
        get() {
            throw SQLException("ResultSet.getFetchSize() unsupported")
        }
        set(fsize) {
            throw SQLException("ResultSet.setFetchSize(int fsize) unsupported")
        }

    @Throws(SQLException::class)
    fun getString(columnIndex: Int): String {
        throw SQLException("ResultSet.getString(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getString(columnName: String?): String {
        throw SQLException("ResultSet.getString(String columnName) unsupported")
    }

    @Throws(SQLException::class)
    fun getInt(columnIndex: Int): Int {
        throw SQLException("ResultSet.getInt(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getInt(columnName: String?): Int {
        throw SQLException("ResultSet.getInt(String columnName) unsupported")
    }

    @Throws(SQLException::class)
    fun getBoolean(columnIndex: Int): Boolean {
        throw SQLException("ResultSet.getBoolean(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getBoolean(columnName: String?): Boolean {
        throw SQLException("ResultSet.getBoolean(String columnName) unsupported")
    }

    @get:Throws(SQLException::class)
    val metaData: ResultSetMetaData
        get() {
            throw SQLException("ResultSet.getMetaData() unsupported")
        }

    @Throws(SQLException::class)
    fun getShort(columnName: String?): Short {
        throw SQLException("ResultSet.getShort(String columnName) unsupported")
    }

    @Throws(SQLException::class)
    fun getShort(columnIndex: Int): Short {
        throw SQLException("ResultSet.getShort(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getTime(columnIndex: Int): Time {
        throw SQLException("ResultSet.getTime(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getTime(columnName: String?): Time {
        throw SQLException("ResultSet.getTime(String columnName) unsupported")
    }

    @Throws(SQLException::class)
    fun getTime(columnIndex: Int, cal: Calendar?): Time {
        throw SQLException("ResultSet.getTime(int columnIndex, java.util.Calendar cal) unsupported")
    }

    @Throws(SQLException::class)
    fun getTime(columnName: String?, cal: Calendar?): Time {
        throw SQLException("ResultSet.getTime(String columnName, java.util.Calendar cal) unsupported")
    }

    @Throws(SQLException::class)
    fun getTimestamp(columnName: String?): java.sql.Timestamp {
        throw SQLException("ResultSet.getTimestamp(String columnName) unsupported")
    }

    @Throws(SQLException::class)
    fun getTimestamp(columnIndex: Int): java.sql.Timestamp {
        throw SQLException("ResultSet.getTimestamp(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getTimestamp(columnName: String?, cal: Calendar?): java.sql.Timestamp {
        throw SQLException("ResultSet.getTimestamp(String columnName, java.util.Calendar cal) unsupported")
    }

    @Throws(SQLException::class)
    fun getTimestamp(columnIndex: Int, cal: Calendar?): java.sql.Timestamp {
        throw SQLException("ResultSet.getTimestamp(int columnIndex, java.util.Calendar cal) unsupported")
    }

    @Throws(SQLException::class)
    fun getDate(columnIndex: Int): java.sql.Date {
        throw SQLException("ResultSet.getDate(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getDate(columnIndex: Int, cal: Calendar?): java.sql.Date {
        throw SQLException("ResultSet.getDate(int columnIndex, java.util.Calendar cal) unsupported")
    }

    @Throws(SQLException::class)
    fun getDate(columnIndex: String?, cal: Calendar?): java.sql.Date {
        throw SQLException("ResultSet.getDate(String columnIndex, java.util.Calendar cal) unsupported")
    }

    @Throws(SQLException::class)
    fun getDate(columnName: String?): java.sql.Date {
        throw SQLException("ResultSet.getDate(String columnName) unsupported")
    }

    @Throws(SQLException::class)
    fun getDouble(columnName: String?): Double {
        throw SQLException("ResultSet.getDouble(String columnName) unsupported")
    }

    @Throws(SQLException::class)
    fun getDouble(columnIndex: Int): Double {
        throw SQLException("ResultSet.getDouble(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getFloat(columnName: String?): Float {
        throw SQLException("ResultSet.getFloat(String columnName) unsupported")
    }

    @Throws(SQLException::class)
    fun getFloat(columnIndex: Int): Float {
        throw SQLException("ResultSet.getFloat(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getLong(columnName: String?): Long {
        throw SQLException("ResultSet.getLong(String columnName) unsupported")
    }

    @Throws(SQLException::class)
    fun getLong(columnIndex: Int): Long {
        throw SQLException("ResultSet.getLong(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getUnicodeStream(columnIndex: Int): java.io.InputStream {
        throw SQLException("ResultSet.getUnicodeStream(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getUnicodeStream(columnName: String?): java.io.InputStream {
        throw SQLException("ResultSet.getUnicodeStream(String columnName) unsupported")
    }

    @Throws(SQLException::class)
    fun getAsciiStream(columnName: String?): java.io.InputStream {
        throw SQLException("ResultSet.getAsciiStream(String columnName) unsupported")
    }

    @Throws(SQLException::class)
    fun getAsciiStream(columnIndex: Int): java.io.InputStream {
        throw SQLException("ResultSet.getAsciiStream(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getBigDecimal(columnName: String?): BigDecimal {
        throw SQLException("ResultSet.getBigDecimal(String columnName) unsupported")
    }

    @Throws(SQLException::class)
    fun getBigDecimal(columnName: String?, scale: Int): BigDecimal {
        throw SQLException("ResultSet.getBigDecimal(String columnName, int scale) unsupported")
    }

    @Throws(SQLException::class)
    fun getBigDecimal(columnIndex: Int): BigDecimal {
        throw SQLException("ResultSet.getBigDecimal(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getBigDecimal(columnIndex: Int, scale: Int): BigDecimal {
        throw SQLException("ResultSet.getBigDecimal(int columnIndex, int scale) unsupported")
    }

    @Throws(SQLException::class)
    fun getBinaryStream(columnIndex: Int): java.io.InputStream {
        throw SQLException("ResultSet.getBinaryStream(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getBinaryStream(columnName: String?): java.io.InputStream {
        throw SQLException("ResultSet.getBinaryStream(String columnName) unsupported")
    }

    @Throws(SQLException::class)
    fun getByte(columnIndex: Int): Byte {
        throw SQLException("ResultSet.getByte(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getByte(columnName: String?): Byte {
        throw SQLException("ResultSet.getByte(String columnName) unsupported")
    }

    @Throws(SQLException::class)
    fun getBytes(columnIndex: Int): ByteArray {
        throw SQLException("unsupported")
    }

    @Throws(SQLException::class)
    fun getBytes(columnName: String?): ByteArray {
        throw SQLException("unsupported")
    }

    @get:Throws(SQLException::class)
    val cursorName: String?
        get() = null

    @Throws(SQLException::class)
    fun getObject(columnName: String?): Object {
        throw SQLException("ResultSet.getObject(String columnName) unsupported")
    }

    @Throws(SQLException::class)
    fun getObject(columnIndex: Int): Object {
        throw SQLException("ResultSet.getObject(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getObject(columnIndex: Int, map: Map<*, *>?): Object {
        throw SQLException("ResultSet.getObject(int columnIndex, java.util.Map map) unsupported")
    }

    @Throws(SQLException::class)
    fun getObject(columnIndex: String?, map: Map<*, *>?): Object {
        throw SQLException("ResultSet.getObject(String columnIndex, java.util.Map map) unsupported")
    }

    @Throws(SQLException::class)
    fun getRef(columnIndex: Int): java.sql.Ref {
        throw SQLException("ResultSet.getRef(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getRef(columnIndex: String?): java.sql.Ref {
        throw SQLException("ResultSet.getRef(String columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getBlob(columnIndex: Int): Blob {
        throw SQLException("ResultSet.getBlob(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getBlob(columnIndex: String?): Blob {
        throw SQLException("ResultSet.getBlob(String columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getClob(columnIndex: Int): Clob {
        throw SQLException("ResultSet.getClob(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getClob(columnIndex: String?): Clob {
        throw SQLException("ResultSet.getClob(String columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getArray(columnIndex: Int): java.sql.Array {
        throw SQLException("ResultSet.getArray(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getArray(columnIndex: String?): java.sql.Array {
        throw SQLException("ResultSet.getArray(String columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getCharacterStream(columnIndex: Int): java.io.Reader {
        throw SQLException("ResultSet.getCharacterStream(int columnIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun getCharacterStream(columnName: String?): java.io.Reader {
        throw SQLException("ResultSet.getCharacterStream(String columnName) unsupported")
    }

    @get:Throws(SQLException::class)
    val warnings: SQLWarning
        get() {
            throw SQLException("ResultSet.getWarnings() unsupported")
        }

    @Throws(SQLException::class)
    fun wasNull(): Boolean {
        throw SQLException("ResultSet.wasNull() unsupported")
    }

    @Throws(SQLException::class)
    fun clearWarnings() {
        throw SQLException("ResultSet.clearWarnings() unsupported")
    }

    @get:Throws(SQLException::class)
    val isFirst: Boolean
        get() {
            throw SQLException("ResultSet.isFirst() unsupported")
        }

    @get:Throws(SQLException::class)
    val isBeforeFirst: Boolean
        get() {
            throw SQLException("ResultSet.isBeforeFirst() unsupported")
        }

    @Throws(SQLException::class)
    fun beforeFirst() {
        throw SQLException("ResultSet.beforeFirst() unsupported")
    }

    @Throws(SQLException::class)
    fun first(): Boolean {
        throw SQLException("ResultSet.first() unsupported")
    }

    @Throws(SQLException::class)
    fun getURL(s: String?): java.net.URL {
        throw SQLException("ResultSet.getURL(String s) unsupported")
    }

    @Throws(SQLException::class)
    fun getURL(i: Int): java.net.URL {
        throw SQLException("ResultSet.getURL(int i) unsupported")
    }

    @get:Throws(SQLException::class)
    val isAfterLast: Boolean
        get() {
            throw SQLException("ResultSet.isAfterLast() unsupported")
        }

    @Throws(SQLException::class)
    fun afterLast() {
        throw SQLException("ResultSet.afterLast() unsupported")
    }

    @get:Throws(SQLException::class)
    val isLast: Boolean
        get() {
            throw SQLException("ResultSet.isLast() unsupported")
        }

    @Throws(SQLException::class)
    fun last(): Boolean {
        throw SQLException("ResultSet.last() unsupported")
    }

    @get:Throws(SQLException::class)
    val type: Int
        get() {
            throw SQLException("ResultSet.getType() unsupported")
        }

    @get:Throws(SQLException::class)
    val concurrency: Int
        get() {
            throw SQLException("ResultSet.getConcurrency() unsupported")
        }

    @Throws(SQLException::class)
    fun rowUpdated(): Boolean {
        throw SQLException("ResultSet.rowUpdated() unsupported")
    }

    @Throws(SQLException::class)
    fun rowInserted(): Boolean {
        throw SQLException("ResultSet.rowInserted() unsupported")
    }

    @Throws(SQLException::class)
    fun rowDeleted(): Boolean {
        throw SQLException("ResultSet.rowDeleted() unsupported")
    }

    @Throws(SQLException::class)
    fun updateRef(s: String?, r: Ref?) {
        throw SQLException("ResultSet.updateRef(String s, Ref r) unsupported")
    }

    @Throws(SQLException::class)
    fun updateRef(s: Int, r: Ref?) {
        throw SQLException("ResultSet.updateRef(int s, Ref r) unsupported")
    }

    @Throws(SQLException::class)
    fun updateClob(s: String?, c: Clob?) {
        throw SQLException("ResultSet.updateClob(String s, Clob c) unsupported")
    }

    @Throws(SQLException::class)
    fun updateClob(i: Int, c: Clob?) {
        throw SQLException("ResultSet.updateClob(int i, Clob c) unsupported")
    }

    @Throws(SQLException::class)
    fun updateClob(c: Clob?) {
        throw SQLException("ResultSet.updateClob(Clob c) unsupported")
    }

    @Throws(SQLException::class)
    fun updateBlob(i: Int, c: Blob?) {
        throw SQLException("ResultSet.updateBlob(int i, Blob c) unsupported")
    }

    @Throws(SQLException::class)
    fun updateBlob(s: String?, c: Blob?) {
        throw SQLException("ResultSet.updateBlob(String s, Blob c) unsupported")
    }

    @Throws(SQLException::class)
    fun updateArray(i: Int, c: Array?) {
        throw SQLException("ResultSet.updateArray(int i, Array c) unsupported")
    }

    @Throws(SQLException::class)
    fun updateArray(s: String?, c: Array?) {
        throw SQLException("ResultSet.updateArray(String s, Array c) unsupported")
    }

    @Throws(SQLException::class)
    fun insertRow() {
        throw SQLException("ResultSet.insertRow() unsupported")
    }

    @Throws(SQLException::class)
    fun updateRow() {
        throw SQLException("ResultSet.updateRow() unsupported")
    }

    @Throws(SQLException::class)
    fun deleteRow() {
        throw SQLException("ResultSet.deleteRow() unsupported")
    }

    @Throws(SQLException::class)
    fun refreshRow() {
        throw SQLException("ResultSet.refreshRow() unsupported")
    }

    @Throws(SQLException::class)
    fun cancelRowUpdates() {
        throw SQLException("ResultSet.cancelRowUpdates() unsupported")
    }

    @Throws(SQLException::class)
    fun moveToInsertRow() {
        throw SQLException("ResultSet.moveToInsertRow() unsupported")
    }

    @Throws(SQLException::class)
    fun moveToCurrentRow() {
        throw SQLException("ResultSet.moveToCurrentRow() unsupported")
    }

    @Throws(SQLException::class)
    fun updateNull(colIndex: Int) {
        throw SQLException("ResultSet.updateNull(int colIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun updateBoolean(colIndex: Int, b: Boolean) {
        throw SQLException("ResultSet.updateBoolean(int colIndex, boolean b) unsupported")
    }

    @Throws(SQLException::class)
    fun updateByte(colIndex: Int, b: Byte) {
        throw SQLException("ResultSet.updateByte(int colIndex, byte b) unsupported")
    }

    @Throws(SQLException::class)
    fun updateShort(colIndex: Int, b: Short) {
        throw SQLException("ResultSet.updateShort(int colIndex, short b) unsupported")
    }

    @Throws(SQLException::class)
    fun updateInt(colIndex: Int, b: Int) {
        throw SQLException("ResultSet.updateInt(int colIndex, int b) unsupported")
    }

    @Throws(SQLException::class)
    fun updateLong(colIndex: Int, b: Long) {
        throw SQLException("ResultSet.updateLong(int colIndex, long b) unsupported")
    }

    @Throws(SQLException::class)
    fun updateFloat(colIndex: Int, f: Float) {
        throw SQLException("ResultSet.updateFloat(int colIndex, float f) unsupported")
    }

    @Throws(SQLException::class)
    fun updateDouble(colIndex: Int, f: Double) {
        throw SQLException("ResultSet.updateDouble(int colIndex, double f) unsupported")
    }

    @Throws(SQLException::class)
    fun updateBigDecimal(colIndex: Int, f: BigDecimal?) {
        throw SQLException("ResultSet.updateBigDecimal(int colIndex, BigDecimal f) unsupported")
    }

    @Throws(SQLException::class)
    fun updateString(colIndex: Int, s: String?) {
        throw SQLException("ResultSet.updateString(int colIndex, String s) unsupported")
    }

    @Throws(SQLException::class)
    fun updateBytes(colIndex: Int, s: ByteArray?) {
        throw SQLException("ResultSet.updateBytes(int colIndex, byte[] s) unsupported")
    }

    @Throws(SQLException::class)
    fun updateDate(colIndex: Int, d: java.sql.Date?) {
        throw SQLException("ResultSet.updateDate(int colIndex, java.sql.Date d) unsupported")
    }

    @Throws(SQLException::class)
    fun updateTime(colIndex: Int, t: Time?) {
        throw SQLException("ResultSet.updateTime(int colIndex, java.sql.Time t) unsupported")
    }

    @Throws(SQLException::class)
    fun updateTimestamp(colIndex: Int, t: java.sql.Timestamp?) {
        throw SQLException("ResultSet.updateTimestamp(int colIndex, java.sql.Timestamp t) unsupported")
    }

    @Throws(SQLException::class)
    fun updateAsciiStream(colIndex: Int, `in`: java.io.InputStream?, s: Int) {
        throw SQLException("ResultSet.updateAsciiStream(int colIndex, java.io.InputStream in, int s) unsupported")
    }

    @Throws(SQLException::class)
    fun updateBinaryStream(colIndex: Int, `in`: java.io.InputStream?, s: Int) {
        throw SQLException("ResultSet.updateBinaryStream(int colIndex, java.io.InputStream in, int s) unsupported")
    }

    @Throws(SQLException::class)
    fun updateCharacterStream(colIndex: Int, `in`: java.io.Reader?, s: Int) {
        throw SQLException("ResultSet.updateCharacterStream(int colIndex, java.io.Reader in, int s) unsupported")
    }

    @Throws(SQLException::class)
    fun updateObject(colIndex: Int, obj: Object?) {
        throw SQLException("ResultSet.updateObject(int colIndex, Object obj) unsupported")
    }

    @Throws(SQLException::class)
    fun updateObject(colIndex: Int, obj: Object?, s: Int) {
        throw SQLException("ResultSet.updateObject(int colIndex, Object obj, int s) unsupported")
    }

    @Throws(SQLException::class)
    fun updateNull(colIndex: String?) {
        throw SQLException("ResultSet.updateNull(String colIndex) unsupported")
    }

    @Throws(SQLException::class)
    fun updateBoolean(colIndex: String?, b: Boolean) {
        throw SQLException("ResultSet.updateBoolean(String colIndex, boolean b) unsupported")
    }

    @Throws(SQLException::class)
    fun updateByte(colIndex: String?, b: Byte) {
        throw SQLException("ResultSet.updateByte(String colIndex, byte b) unsupported")
    }

    @Throws(SQLException::class)
    fun updateShort(colIndex: String?, b: Short) {
        throw SQLException("ResultSet.updateShort(String colIndex, short b) unsupported")
    }

    @Throws(SQLException::class)
    fun updateInt(colIndex: String?, b: Int) {
        throw SQLException("ResultSet.updateInt(String colIndex, int b) unsupported")
    }

    @Throws(SQLException::class)
    fun updateLong(colIndex: String?, b: Long) {
        throw SQLException("ResultSet.updateLong(String colIndex, long b) unsupported")
    }

    @Throws(SQLException::class)
    fun updateFloat(colIndex: String?, f: Float) {
        throw SQLException("ResultSet.updateFloat(String colIndex, float f) unsupported")
    }

    @Throws(SQLException::class)
    fun updateDouble(colIndex: String?, f: Double) {
        throw SQLException("ResultSet.updateDouble(String colIndex, double f) unsupported")
    }

    @Throws(SQLException::class)
    fun updateBigDecimal(colIndex: String?, f: BigDecimal?) {
        throw SQLException("ResultSet.updateBigDecimal(String colIndex, BigDecimal f) unsupported")
    }

    @Throws(SQLException::class)
    fun updateString(colIndex: String?, s: String?) {
        throw SQLException("ResultSet.updateString(String colIndex, String s) unsupported")
    }

    @Throws(SQLException::class)
    fun updateBytes(colIndex: String?, s: ByteArray?) {
        throw SQLException("ResultSet.updateBytes(String colIndex, byte[] s) unsupported")
    }

    @Throws(SQLException::class)
    fun updateDate(colIndex: String?, d: java.sql.Date?) {
        throw SQLException("ResultSet.updateDate(String colIndex, java.sql.Date d) unsupported")
    }

    @Throws(SQLException::class)
    fun updateTime(colIndex: String?, t: Time?) {
        throw SQLException("ResultSet.updateTime(String colIndex, java.sql.Time t) unsupported")
    }

    @Throws(SQLException::class)
    fun updateTimestamp(colIndex: String?, t: java.sql.Timestamp?) {
        throw SQLException("ResultSet.updateTimestamp(String colIndex, java.sql.Timestamp t) unsupported")
    }

    @Throws(SQLException::class)
    fun updateAsciiStream(colIndex: String?, `in`: java.io.InputStream?, s: Int) {
        throw SQLException("ResultSet.updateAsciiStream(String colIndex, java.io.InputStream in, int s) unsupported")
    }

    @Throws(SQLException::class)
    fun updateBinaryStream(colIndex: String?, `in`: java.io.InputStream?, s: Int) {
        throw SQLException("ResultSet.updateBinaryStream(String colIndex, java.io.InputStream in, int s) unsupported")
    }

    @Throws(SQLException::class)
    fun updateCharacterStream(colIndex: String?, `in`: java.io.Reader?, s: Int) {
        throw SQLException("ResultSet.updateCharacterStream(String colIndex, java.io.Reader in, int s) unsupported")
    }

    @Throws(SQLException::class)
    fun updateObject(colIndex: String?, obj: Object?) {
        throw SQLException("ResultSet.updateObject(String colIndex, Object obj) unsupported")
    }

    @Throws(SQLException::class)
    fun updateObject(colIndex: String?, obj: Object?, s: Int) {
        throw SQLException("ResultSet.updateObject(String colIndex, Object obj, int s) unsupported")
    }

    @get:Throws(SQLException::class)
    val statement: Statement
        get() {
            throw SQLException("ResultSet.getStatement() unsupported")
        }

    @Throws(SQLException::class)
    fun close() {
        throw SQLException("ResultSet.close() unsupported")
    }

    @Throws(SQLException::class)
    fun checkClosed() {
        throw SQLException("ResultSet.checkClosed() unsupported")
    }
}