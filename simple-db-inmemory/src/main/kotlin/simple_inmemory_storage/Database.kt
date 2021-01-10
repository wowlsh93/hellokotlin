package simpledb.simple_inmemory_storage

interface Database {
    fun createDatabase(name: String) : Unit
    fun getName(): String
}