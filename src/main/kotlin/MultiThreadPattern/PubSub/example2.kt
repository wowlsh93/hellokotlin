package MultiThreadPattern.PubSub

import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock


class concurrntMutableList {
  var list = LinkedList<Int>()
  var capacity = 10
  private val lock = ReentrantLock()
  private val condition = lock.newCondition()

  fun add(i: Int) {
    lock.withLock {
      while (list.size == capacity)
        condition.await()

      list.add(i)
      condition.signalAll()
    }
  }

  fun poll(): Int {
    lock.withLock {
      while (list.size == 0)
        condition.await()

      val value = list.removeFirst()
      condition.signalAll()
      return value
    }
  }
}
class Produce {
  fun run(arr: concurrntMutableList){
    thread {
      var i = 0
      while(true){
        Thread.sleep(100)
        arr.add(i)
        i = i + 1
      }
    }
  }

}

class Consumer {
  fun run(arr : concurrntMutableList) {
    thread {
      while(true){
        Thread.sleep(50)
        println(arr.poll())
      }
    }
  }
}

fun main(){
  val clist = concurrntMutableList()
  Produce().run(clist)
  Consumer().run(clist)

  Thread.sleep(1000 * 100)
}


