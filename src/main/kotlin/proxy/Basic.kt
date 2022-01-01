/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package proxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Proxy


interface ITest {
  fun testIt(): Int
}

//class Test : ITest {
//  override fun testIt(): Int {
//    return 11
//  }
//}

internal class MyDynamicInvocationHandler : InvocationHandler {
  override fun invoke(proxy: Any?, method: Method, args: Array<Any?>?): Any? {
    return if ("testIt" == method.getName()) {
      5
    } else null
  }
}

fun test3() {
    val myTestInvocationHandler = MyDynamicInvocationHandler()
    val test = Proxy.newProxyInstance(
      ITest::class.java.classLoader, arrayOf<Class<*>>(
        ITest::class.java
      ), myTestInvocationHandler
    ) as ITest
    println(test.testIt())
}
/////


object ProxyTest {
  interface If {
    fun originalMethod(s: String?)
  }

  internal class Original : If {
    override fun originalMethod(s: String?) {
      println(s)
    }
  }

  internal class Handler(private val original: If) : InvocationHandler {
    @Throws(IllegalAccessException::class, IllegalArgumentException::class, InvocationTargetException::class)
    override fun invoke(proxy: Any, method: Method, args: Array<Any>): Any? {
      println("BEFORE")
      method.invoke(original, args)
      println("AFTER")
      return null
    }
  }

  @JvmStatic
  fun main(args: Array<String>) {
    val original = Original()
    val handler = Handler(original)
    val f = Proxy.newProxyInstance(
      If::class.java.classLoader, arrayOf<Class<*>>(If::class.java),
      handler
    ) as If
    f.originalMethod("Hallo")
  }
}
