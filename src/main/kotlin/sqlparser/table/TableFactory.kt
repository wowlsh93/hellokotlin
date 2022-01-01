package sqlparser.table

import java.io.IOException
import kotlin.Throws
import javax.swing.JFrame

object TableFactory {

    fun create(name: String?, columns: List<String>): Table {
        return ConcreteTable(name, columns)
    }

//    @Throws(IOException::class)
//    fun create(importer: Table.Importer): Table {
//        return ConcreteTable(importer)
//    }
}