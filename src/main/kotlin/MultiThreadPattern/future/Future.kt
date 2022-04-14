/*
 * Copyright 2012 OpusM Corp
 * written by  hama
 *
 */
//
//package MultiThreadPattern.future
//
//import java.util.concurrent.locks.ReentrantLock
//import kotlin.concurrent.withLock
//
//interface Future <T> {
//  var result: T?
//  fun get(): T
//}
//
//class MyFuture(override var result: String? =  null) : Future<String>{
//
//  val lock = ReentrantLock()
//  val condition = lock.newCondition()
//
//  override fun get(): String {
//    lock.withLock {
//      while(result == null)
//        condition.await()
//
//      return result!!
//    }
//  }
//
//  fun set(data: String) {
//    lock.withLock {
//      result = data
//      condition.signal()
//    }
//
//  }
//}
//
//fun DoSomething(): Future<String> {
//  val future = MyFuture()
//
//  Thread {
//    Thread.sleep(1000 * 10)
//    future.set("ok")
//  }.start()
//
//  return future
//}
//
//fun main() {
//
//  val future = DoSomething()
//
//  println("wait.............")
//  val result: String = future.get()
//  println(result)
//}