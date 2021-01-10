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
package com.holub.database

import com.holub.tools.ArrayIterator

/***
 * Pass this importer to a [Table] constructor (such
 * as
 * {link com.holub.database.ConcreteTable#ConcreteTable(Table.Importer)}
 * to initialize
 * a `Table` from
 * a comma-sparated-value repressentation. For example:
 * <PRE>
 * Reader in = new FileReader( "people.csv" );
 * people = new ConcreteTable( new CSVImporter(in) );
 * in.close();
</PRE> *
 * The input file for a table called "name" with
 * columns "first," "last," and "addrId" would look
 * like this:
 * <PRE>
 * name
 * first,	last,	addrId
 * Fred,	Flintstone,	1
 * Wilma,	Flintstone,	1
 * Allen,	Holub,	0
</PRE> *
 * The first line is the table name, the second line
 * identifies the columns, and the subsequent lines define
 * the rows.
 *
 * @include /etc/license.txt
 *
 * @see Table
 *
 * @see Table.Importer
 *
 * @see CSVExporter
 */
class CSVImporter(`in`: Reader?) : Table.Importer {
    private var `in` // null once end-of-file reached
            : BufferedReader?
    private var columnNames: Array<String>
    private var tableName: String? = null
    @Throws(IOException::class)
    fun startTable() {
        tableName = `in`.readLine().trim()
        columnNames = `in`.readLine().split("\\s*,\\s*")
    }

    @Throws(IOException::class)
    fun loadTableName(): String? {
        return tableName
    }

    @Throws(IOException::class)
    fun loadWidth(): Int {
        return columnNames.size
    }

    @Throws(IOException::class)
    fun loadColumnNames(): Iterator {
        return ArrayIterator(columnNames) //{=CSVImporter.ArrayIteratorCall}
    }

    @Throws(IOException::class)
    fun loadRow(): Iterator? {
        var row: Iterator? = null
        if (`in` != null) {
            val line: String = `in`.readLine()
            if (line == null) `in` = null else row = ArrayIterator(line.split("\\s*,\\s*"))
        }
        return row
    }

    @Throws(IOException::class)
    fun endTable() {
    }

    init {
        this.`in` = if (`in` is BufferedReader) `in` as BufferedReader? else BufferedReader(`in`)
    }
}