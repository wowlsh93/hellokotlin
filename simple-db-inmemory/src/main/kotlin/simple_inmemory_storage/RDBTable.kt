package simpledb.simple_inmemory_storage

import junit.framework.Assert
import org.junit.Test

class RDBTable (val database : RDBDatabase) : Table {

    lateinit private var space : Space
    lateinit private var name : String
    lateinit private var columns : Array<String>

    override fun createTable(name : String, columns : Array<String>) {
        this.name = name
        this.columns = columns
        space = Space(database.getName(), name)
    }

    override fun getName(): String {
        return this.name
    }

    override fun insert(row: Array<String>) {
        space.add(row)
    }

    override fun select(index: String): Array<String>? {
        return space.get(index)
    }
}


class RDBTableTest {

    @Test
    fun `Create Table`() {

        val database = RDBDatabase()
        database.createDatabase("testdb")

        val table = RDBTable(database)
        table.createTable("testtable", arrayOf("id","name","sex"))

        Assert.assertEquals(table.getName(), "testtable")

        table.insert(arrayOf("id-1","name-1","sex-1"))
        table.insert(arrayOf("id-2","name-2","sex-2"))
        table.insert(arrayOf("a-2","name-3","sex-3"))

        Assert.assertEquals(table.select("id-1")?.get(1), "name-1")
        Assert.assertEquals(table.select("a-2")?.get(1), "name-3")

    }
}
