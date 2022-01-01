/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */


// proxy는 중간에서 대리 역할을 한다.

   // decorator는 중간에서 꾸며주는 역할의 대리 역할을 한다.
   // adaptor는 중간에서 변환 해주는 역할의 대리 역할을 한다.

// proxy는 캐시역할을 할 수 있다.
// proxy는 리모트통신을 감춰준다.
// proxy는 ACL 역할을 할 수 있다.
// proxy는 트랜잭션 처리를 대행 해 준다.

package proxy

import java.lang.reflect.Proxy

interface Contract {

  fun first(value: Int): String

  fun second(value1: Int, value2: Int): String

  fun third(): String
}


class ContractProxy1 {
  val contract: Contract = Proxy.newProxyInstance(Contract::class.java.classLoader, arrayOf(Contract::class.java)) { _, method, args ->
    "method : ${method.name}, args : ${args?.joinToString()}"
  } as Contract
}

fun test2() {
  val contracts1 = ContractProxy1().contract
  println(contracts1.first(1))
  println(contracts1.second(3, 4))
  println(contracts1.third())
}

