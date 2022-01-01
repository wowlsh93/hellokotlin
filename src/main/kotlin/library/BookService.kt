/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package library

interface BookService {
    fun inStock(bookId: Int): Boolean
    fun lend(bookId: Int, memberId: Int)
}

class BookServiceImpl (vararg array: Int): BookService {
    private val inStockBooks  = mutableListOf<Int>()
    private val lendBooks = mutableListOf<Int>()

    init {
        inStockBooks.addAll(array.toList())
    }

    override fun inStock(bookId: Int): Boolean {
        return inStockBooks.contains(bookId)
    }

    override fun lend(bookId: Int, memberId: Int) {
        lendBooks.add(bookId)
        inStockBooks.remove(bookId)
    }

}

class LendBookManager(val bookService:BookService) {
    fun checkout(bookId: Int, memberId: Int) {
        if(bookService.inStock(bookId)) {
            bookService.lend(bookId, memberId)
        } else {
            throw IllegalStateException("Book is not available")
        }
    }
}

class MyObject {
    fun doObjectAction(str: String) {
        println(str)
    }
}
class ClassToTest {
    fun classFunction(message: String, objectX: MyObject) {
        objectX.doObjectAction(message)
    }
}