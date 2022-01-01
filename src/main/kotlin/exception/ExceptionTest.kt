/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package exception

import exception.bankexception.LowBalanceException
import exception.bankexception.NoAccountException
import kotlin.jvm.Throws


interface Transfer {
    fun moveTo(id : String)
}

interface Asset{
    val id: String
    val name: String
}

enum class AssetKind{
    Token,
    NFT
}

data class Token(
    override val id: String,
    override val name: String,
    var balance: Int) : Asset,Transfer{
    override fun moveTo(id: String) {
        TODO("Not yet implemented")
    }

}
data class NFT(
    override val id: String,
    override val name: String) : Asset,Transfer {
    override fun moveTo(id: String) {
        TODO("Not yet implemented")
    }

}

data class Account(val id: String, val name: String?): Transfer {
    val assets = mutableListOf<Asset>()
    override fun moveTo(id: String) {
        TODO("Not yet implemented")
    }

    fun createToken(token: Token) {
        assets.add(token)
    }

    fun withrawToken(id: String, balance: Int): Int {
        val token = assets.find { it -> it.id == id  } as Token
        require(token.balance > balance )
        token.balance = token.balance - balance
        return balance
    }
}

class Bank{
    val accounts = mutableMapOf<String, Account>()
    fun createAccount(account: Account){
        accounts.put(account.id, account)
    }
    fun getAccount(id: String): Account? {
        return accounts.get(id)
    }

    @Throws(NoAccountException::class)
    fun createToken(accountId: String, token: Token){
        accounts.get(accountId)?.createToken(token) ?: throw NoAccountException("there is no $accountId in our bank")
    }

    fun withrawToken(accountId: String, tokenId: String, balance: Int):Int{
        return  kotlin.runCatching { accounts.get(accountId)?.withrawToken(tokenId, balance)}
        .onFailure { println(" withraw token failure") }
        .onSuccess { println(" withraw token success") }
        .getOrDefault(0)!!
    }
}