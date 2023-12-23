package MultiThreadPattern.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
  val sum = (1..5).asFlow()
    .reduce { accumulator, value ->
      println("accumulator=$accumulator, value=$value")
      accumulator + value
    }
  println("Sum of 1..3 is $sum")
}