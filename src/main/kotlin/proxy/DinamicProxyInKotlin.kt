/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

import java.lang.IllegalArgumentException
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy


interface Contract {

  fun first(value: Int): Int

  fun second(value1: Int, value2: Int): String

  fun third(): String
}

object  MyInvocationHandler : InvocationHandler {
  override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
    println("method : ${method?.name}, args : ${args?.joinToString()}")

    return when(method?.name) {
      "first" ->  return 1
      "second" -> return "2"
      "third" -> return "3"
      else -> IllegalArgumentException("No method in the Object")
    }
  }

}



class ContractProxy1 {

  fun make(): Contract{
    return Proxy.newProxyInstance(Contract::class.java.classLoader,
      arrayOf(Contract::class.java),
      MyInvocationHandler) as Contract
  }
}

fun main() {
  val contracts1 = ContractProxy1().make()
  println(contracts1.first(1))
  println(contracts1.second(3, 4))
  println(contracts1.third())
}

