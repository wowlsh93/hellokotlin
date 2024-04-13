package MultiThreadPattern.javaconcurrent
//https://medium.com/hprog99/countdownlatch-and-cyclicbarrier-in-java-9fec58575f51
import java.util.concurrent.CountDownLatch


object CountDownLatchExample {
  @Throws(InterruptedException::class)
  @JvmStatic
  fun main(args: Array<String>) {
    val latch = CountDownLatch(3)
    val t1 = Thread {
      // do some work
      latch.countDown()
    }
    val t2 = Thread {
      // do some work
      latch.countDown()
    }
    val t3 = Thread {
      // do some work
      latch.countDown()
    }
    t1.start()
    t2.start()
    t3.start()

    // wait for t1, t2, and t3 to complete
    latch.await()

    // continue execution
  }
}