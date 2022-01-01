/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package library

import com.nhaarman.mockitokotlin2.atMost
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.lang.IllegalStateException

internal class LendBookManagerTest : AbstractBookManager(){
    private val mockBookService:BookServiceImpl = mock()
    private val manager = LendBookManager(mockBookService)

    @BeforeEach
    fun setup() {
        logger.info("setup")
        Mockito.reset(mockBookService)
    }

    @Test
    fun inStockTest() {
        val service = BookServiceImpl(1,2,3,4,5)
        Assertions.assertTrue(service.inStock(1))
    }

    @Test
    fun lendTest() {
        Assertions.assertThrows(IllegalStateException::class.java) {
            val service = BookServiceImpl(1,2,3,4,5)
            service.lend(1,100)
            val manager2 = LendBookManager(service)
            manager2.checkout(1,111)
        }
    }

    @Test
    fun whenBookIsAvailable_thenLendMethodIsCalled() {
        whenever(mockBookService.inStock(100)).thenReturn(true)
        manager.checkout(100, 1)
        verify(mockBookService, atMost(1)).lend(100, 1)
    }

    @Test
    fun whenBookIsNotAvailable_thenThrowException() {
        Assertions.assertThrows(IllegalStateException::class.java) {
            whenever(mockBookService.inStock(10)).thenReturn(false)
            manager.checkout(10, 1)
        }
    }
}
