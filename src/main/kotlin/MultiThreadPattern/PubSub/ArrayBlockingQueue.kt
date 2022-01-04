package MultiThreadPattern.PubSub

import java.util.concurrent.ArrayBlockingQueue


fun producer(queue: ArrayBlockingQueue<String>) {
  Thread{
    for(i in 1..10){
      println(i)
      queue.put(i.toString())
    }
  }.start()
}

fun consumer(queue: ArrayBlockingQueue<String>) {
   Thread {
   while(true) {
    val poll  = queue.take()
     println("consumer: " + poll)
    }
  }.start()
}


fun main() {
  val queue = ArrayBlockingQueue<String>(5)

  producer(queue)
  consumer(queue)

  Thread.sleep(1000 * 1000)
}