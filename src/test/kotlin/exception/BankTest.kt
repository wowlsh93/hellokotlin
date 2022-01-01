/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package exception

import exception.bankexception.NoAccountException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

internal class BankTest {
    lateinit var bank: Bank

    @BeforeEach
    fun setUp() {
        bank = Bank()
        bank.createAccount(Account("1", "hama"))
    }
    @Test
    fun getAccount() {
        val result = bank.getAccount("1")?.name
        Assertions.assertEquals("hama", result)
    }

    @Test
    fun dipositToken() {
        Assertions.assertThrows(NoAccountException::class.java, {
            bank.createToken("2", exception.Token("token_1", "opusm", 1000))
        })
    }

    @Test
    fun withrawToken() {
        bank.createToken("1", exception.Token("token_1", "opusm", 1000))
        val result = bank.withrawToken("1", "token_1", 5000)
        Assertions.assertEquals(0, result)
    }
}