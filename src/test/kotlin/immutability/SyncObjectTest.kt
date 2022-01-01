/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package immutability

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

internal class SyncObjectTest{

  @Test
  fun addTest() {
    val obj = SyncObject(0)
    for (i in 1..1000) {
      obj.add(1)
    }
    Thread.sleep(1000)
    println(obj.get())
  }

  @Test
  fun mutexTest() {
    val lock = Any()
    val obj = SyncObject(0)
    for (i in 1..1000) {
      thread {
        Thread.sleep(10)
          obj.add(1)
      }
    }
    Thread.sleep(1000)
    println(obj.get())
  }

  @Test
  fun conditionTest() {
    val lock = ReentrantLock()
    val obj = SyncObject(0)
    for (i in 1..1000) {
      thread {
        Thread.sleep(10)
        lock.lock()
          obj.add(1)
        lock.unlock()
      }
    }
    Thread.sleep(1000)
    println(obj.get())
  }

}