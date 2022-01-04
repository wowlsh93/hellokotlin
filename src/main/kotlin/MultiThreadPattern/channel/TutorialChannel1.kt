package MultiThreadPattern.channel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {

  val channel = Channel<String>() // 채널 생성

  // 생산자1
  launch {
    channel.send("A1")
    channel.send("A2")
    println("A done")
  }

  // 생산자2
  launch {
    channel.send("B1")
    println("B done")
  }

  // 소비자 1
  launch {
    repeat(3) {
      val x = channel.receive()
      println(x)
    }
  }
}