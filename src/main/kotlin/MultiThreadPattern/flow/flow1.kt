package MultiThreadPattern.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

val numbers = flowOf(1,1,1,2,2,3,4,4,4,4,5,5,6,6,7,7,8,9,9,10)

fun main() = runBlocking {
  numbers
    .onStart { println("onStart") }
    .distinctUntilChanged()
    .takeWhile { it != 6 }
    .onEach { println(it) }
    .onCompletion { println("onCompletion") }
    .collect()
}