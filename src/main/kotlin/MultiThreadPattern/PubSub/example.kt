/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package MultiThreadPattern.PubSub

import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock


object Threadexample {
  @Throws(InterruptedException::class)
  @JvmStatic
  fun main(args: Array<String>) {
    val pc = PC()

    val t1 = Thread {
      try {
        pc.produce()
      } catch (e: InterruptedException) {
        e.printStackTrace()
      }
    }

    val t2 = Thread {
      try {
        pc.consume()
      } catch (e: InterruptedException) {
        e.printStackTrace()
      }
    }

    // Start both threads
    t1.start()
    t2.start()

    // t1 finishes before t2
    t1.join()
    t2.join()
  }

  class PC {
    var list = LinkedList<Int>()
    var capacity = 2
    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    @Throws(InterruptedException::class)
    fun produce() {
      var value = 0
      while (true) {
        lock.withLock{
          while (list.size == capacity)
            condition.await()

          println("Producer produced-" + value)
          list.add(value++)

          condition.signalAll()

          Thread.sleep(1000)
        }
      }
    }

    @Throws(InterruptedException::class)
    fun consume() {
      while (true) {
        lock.withLock {

          while (list.size == 0)
            condition.await()

          val `val` = list.removeFirst()
          println("Consumer consumed-" + `val`)
          condition.signalAll()
          Thread.sleep(1000)
        }
      }
    }
  }
}