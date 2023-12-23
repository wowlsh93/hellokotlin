package MultiThreadPattern.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
  val nums = (1..2).asFlow()
    .onEach { check(it < 2) }
  try {
    nums.collect { println(it) }
  } catch (e: Exception) {
    println("Caught exception: $e")
  }
}