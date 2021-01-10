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

import kotlin.Throws
import javax.swing.JFrame

/***
 * Pass this exporter to a [Table.export] implementation to
 * create a comma-sparated-value version of a [Table].
 * For example:
 * <PRE>
 * Table people  = TableFactory.create( ... );
 * //...
 * Writer out = new FileWriter( "people.csv" );
 * people.export( new CSVExporter(out) );
 * out.close();
</PRE> *
 * The output file for a table called "name" with
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
 * @see Table
 *
 * @see Table.Exporter
 *
 * @see CSVImporter
 */
class CSVExporter(out: Writer) : Table.Exporter {
    private val out: Writer
    private var width = 0
    @Throws(IOException::class)
    fun storeMetadata(
        tableName: String?,
        width: Int,
        height: Int,
        columnNames: Iterator
    ) {
        this.width = width
        out.write(tableName ?: "<anonymous>")
        out.write("\n")
        storeRow(columnNames) // comma separated list of columns ids
    }

    @Throws(IOException::class)
    fun storeRow(data: Iterator) {
        var i = width
        while (data.hasNext()) {
            val datum: Object = data.next()

            // Null columns are represented by an empty field
            // (two commas in a row). There's nothing to write
            // if the column data is null.
            if (datum != null) out.write(datum.toString())
            if (--i > 0) out.write(",\t")
        }
        out.write("\n")
    }

    @Throws(IOException::class)
    fun startTable() { /*nothing to do*/
    }

    @Throws(IOException::class)
    fun endTable() { /*nothing to do*/
    }

    init {
        this.out = out
    }
}