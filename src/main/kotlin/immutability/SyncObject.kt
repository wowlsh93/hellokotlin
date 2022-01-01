/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package immutability

import java.util.concurrent.Semaphore

class SyncObject(private var balance: Int){

  fun add(n: Int){
    balance = balance +  n
  }

  fun get(): Int {
    return balance
  }
}

class Incrementor() {
  private val sharedCounterLock = Semaphore(5)
  var sharedCounter: Int = 0
    private set

  fun updateCounterIfNecessary(shouldIActuallyIncrement: Boolean) {
    if (shouldIActuallyIncrement) {
      try {
        sharedCounterLock.acquire()
        sharedCounter++
      } finally {
        sharedCounterLock.release()
      }
    }
  }
}
