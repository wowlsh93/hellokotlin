package simpledb.simple_inmemory_storage

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.io.File

class RDBDatabase : Database{

    private lateinit var databasePath : String

    override fun createDatabase(name: String) {
        this.databasePath = name
        val database = File(name)
        database.mkdirs()
    }

    override fun getName(): String {
       return databasePath
    }
}



class RDBDatabaseTest {

    @Test
    fun `Create Database`() {

        val database = RDBDatabase()
        database.createDatabase("testdb")
        assertEquals(database.getName(), "testdb")
    }
}

