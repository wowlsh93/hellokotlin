package MultiThreadPattern.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
  val result = (1..5).asFlow()
    .fold(100) { accumulator, value ->
      println("accumulator=$accumulator, value=$value")
      accumulator + value
    }
  println("Result is $result")
}