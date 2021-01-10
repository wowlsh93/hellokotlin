package simpledb.simple_inmemory_storage

interface Table {

    fun createTable(name : String, columns : Array<String>):Unit
    fun getName():String
    fun insert(row : Array<String>)
    fun select(index: String) : Array<String>?

}