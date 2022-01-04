package MultiThreadPattern.PubSub

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ConcurrentLinkedQueue


fun producer(queue: ConcurrentLinkedQueue<String>) {
  Thread{
    for(i in 1..10){
      println(i)
      queue.add(i.toString())
    }
  }.start()
}

fun consumer(queue: ConcurrentLinkedQueue<String>) {
  Thread {
    while(true) {
      val poll  = queue.poll()
      println("consumer: " + poll)
    }
  }.start()
}


fun main() {
  val queue = ConcurrentLinkedQueue<String>()

  producer(queue)
  consumer(queue)

  Thread.sleep(1000 * 1000)
}