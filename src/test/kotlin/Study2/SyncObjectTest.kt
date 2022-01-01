/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package Study2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.concurrent.thread

internal class SyncObjectTest{

  @Test
  fun test1() {

    val lock = Any()
    var n = 0

    for(i in 1..1000) {
      thread {
        Thread.sleep(20)
        synchronized(lock) {
          n = n + 1
        }
      }
    }

    Thread.sleep(1000)
    println(n)
  }
}