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
 * A class that demonstrates using an Exporter to create a UI.
 * The following code creates and populates a table, then
 * creates a [JTable] that holds a representation of the
 * [Table].
 * <PRE>
 * Table people = TableFactory.create( "people",
 * new String[]{ "First", "Last"		} );
 * people.insert( new String[]{ "Allen",	"Holub" 	} );
 * people.insert( new String[]{ "Ichabod",	"Crane" 	} );
 * people.insert( new String[]{ "Rip",		"VanWinkle" } );
 * people.insert( new String[]{ "Goldie",	"Locks" 	} );
 *
 * javax.swing.JFrame frame = new javax.swing.JFrame();
 * frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
 *
 * **JTableExporter tableBuilder = new JTableExporter();**
 * **people.export( tableBuilder );**
 *
 * frame.getContentPane().add(
 * new JScrollPane( **tableBuilder.getJTable()** ) );
 * frame.pack();
 * frame.setVisible( true );
</PRE> *
 *
 * @include /etc/license.txt
 *
 * @see CSVExporter
 */
class JTableExporter : Table.Exporter {
    private var columnHeads: Array<String?>
    private var contents: Array<Array<Object?>>
    private var rowIndex = 0
    @Throws(IOException::class)
    fun startTable() {
        rowIndex = 0
    }

    @Throws(IOException::class)
    fun storeMetadata(
        tableName: String?,
        width: Int,
        height: Int,
        columnNames: Iterator
    ) {
        contents = Array<Array<Object?>>(height) { arrayOfNulls<Object>(width) }
        columnHeads = arrayOfNulls(width)
        var columnIndex = 0
        while (columnNames.hasNext()) columnHeads[columnIndex++] = columnNames.next().toString()
    }

    @Throws(IOException::class)
    fun storeRow(data: Iterator) {
        var columnIndex = 0
        while (data.hasNext()) contents[rowIndex][columnIndex++] = data.next()
        ++rowIndex
    }

    @Throws(IOException::class)
    fun endTable() { /*nothing to do*/
    }

    /** Return the Concrete Product of this builder---a JTable
     * initialized with the table data.
     */
    val jTable: JTable
        get() = JTable(contents, columnHeads)

    /** A unit test for the JTableExporter class
     * Run it with *java com.holub.database.JTableExporter\$Test*.
     */
    object Test {
        @Throws(IOException::class)
        fun main(args: Array<String?>?) {
            val people: Table = TableFactory.create("people", arrayOf<String?>("First", "Last"))
            people.insert(arrayOf("Allen", "Holub"))
            people.insert(arrayOf("Ichabod", "Crane"))
            people.insert(arrayOf("Rip", "VanWinkle"))
            people.insert(arrayOf("Goldie", "Locks"))
            val frame = JFrame()
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
            val tableBuilder = JTableExporter()
            people.export(tableBuilder)
            frame.getContentPane().add(
                JScrollPane(tableBuilder.jTable)
            )
            frame.pack()
            frame.setVisible(true)
        }
    }
}