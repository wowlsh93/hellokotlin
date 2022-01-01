/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package immutability

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch

internal class IncrementorTest{

  @Test
  suspend fun semaphoreTest () {
    val incrementor = Incrementor()
    val scope = CoroutineScope(newFixedThreadPoolContext(4, "synchronizationPool"))
    scope.launch {
      val coroutines = 1.rangeTo(1000).map {
        launch {
          for (i in 1..1000) {
            incrementor.updateCounterIfNecessary(it % 2 == 0)
          }
        }
      }

      coroutines.forEach { corotuine ->
        corotuine.join() // wait for all coroutines to finish their jobs.
      }
    }.join()

    println("The number of shared counter is ${incrementor.sharedCounter}")
  }

  @Test
  fun countdownLatch(){
    val createdValues = mutableListOf<Int>()
    val countDownLatch = CountDownLatch(5)

    val threads = 1.rangeTo(5).map {
        number ->
      Thread {
        createdValues.add(number)
        countDownLatch.countDown()
      }.apply {
        start()
      }
    }

    val waitingThreads = 1.rangeTo(3).map {
      Thread {
        countDownLatch.await()
        println("I'm thread ${Thread.currentThread().name} and the sum of all values is: ${createdValues.sum()}")
      }.apply {
        start()
      }
    }
    threads.forEach { thread ->
      thread.join()
    }
    waitingThreads.forEach { thread ->
      thread.join()
    }
  }
}