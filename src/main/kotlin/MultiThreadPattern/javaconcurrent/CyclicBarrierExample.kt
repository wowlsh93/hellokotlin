package MultiThreadPattern.javaconcurrent

import java.util.concurrent.CyclicBarrier
//https://medium.com/hprog99/countdownlatch-and-cyclicbarrier-in-java-9fec58575f51

object CyclicBarrierExample {
  @Throws(InterruptedException::class)
  @JvmStatic
  fun main(args: Array<String>) {
    val barrier = CyclicBarrier(3)
    val t1 = Thread {
      // do some work
      barrier.await()
    }
    val t2 = Thread {
      // do some work
      barrier.await()
    }
    val t3 = Thread {
      // do some work
      barrier.await()
    }
    t1.start()
    t2.start()
    t3.start()
  }
}