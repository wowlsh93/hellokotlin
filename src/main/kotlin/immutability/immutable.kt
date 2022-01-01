
package immutability

import kotlin.jvm.Throws



class BankAccount(val id: String){

  var balance = 0.0
     private set

  fun deposit(depositAmount: Double) {
    balance += depositAmount
  }

  @Throws(InsufficientFunds::class)
  fun withdraw(withdrawFunds: Double) {
    if(balance < withdrawFunds) throw InsufficientFunds()

    balance -= withdrawFunds
  }

}




