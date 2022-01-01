/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package immutability

import kotlin.random.Random

class Condition {
  private val lock = java.lang.Object()
  private var items: Int = 0
  private val maxItems: Int = 100
  fun produce() = synchronized(lock) {

    while (items >= maxItems) {
      lock.wait()
    }
    Thread.sleep(Random.nextInt(100).toLong())
    items++
    println("Produced, count is $items: ${Thread.currentThread()}")
    lock.notifyAll()
  }

  fun consume() = synchronized(lock) {
    while (items <= 0) {
      lock.wait()
    }
    Thread.sleep(Random.nextInt(100).toLong())
    items--
    println("Consumed, count is $items: ${Thread.currentThread()}")
    lock.notifyAll()
  }
}
