package simpledb.simple_inmemory_storage

class Page (val pageIndex : Int) {
    private val datas = mutableMapOf<String, Array<String>>()

    fun get(index: String): Array<String>? {
        return datas.get(index)
    }

    fun set(index: String , rows: Array<String>):Unit{
        datas.putIfAbsent(index, rows)
    }
}

interface PageManager {

}

class PageTree : PageManager {


}

fun hasing(str : String) = str[0].toInt()

class PageMap : PageManager{

    private val pages :  MutableMap<Int, Page> = mutableMapOf()

    companion object {
        val maxPageSize = 1024
    }

    fun findPageByIndex(index : String): Page? {
        val pageIndex  = hasing(index)
        pages.putIfAbsent(pageIndex,Page(pageIndex))
        return pages[pageIndex]
    }
}

class Space (val path: String, val spaceName : String) {

    private val pageProvider = PageMap()

    fun get(index : String ) : Array<String>? {
        val page = pageProvider.findPageByIndex(index)
        val rows = page?.get(index)
        return rows

    }

    fun add(row: Array<String>) {
        val index = row[0]
        val page = pageProvider.findPageByIndex(index)
        page?.set(index, row)
    }

}