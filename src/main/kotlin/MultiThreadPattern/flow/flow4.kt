//package MultiThreadPattern.coroutines
//
//import kotlinx.coroutines.*
//import kotlinx.coroutines.flow.*
//
//fun main() = runBlocking {
//  val nums = (1..3).asFlow()
//    .onEach { delay(29) }
//  val strs = flowOf('a', 'b', 'c', 'd')
//    .onEach { delay(37) }
//  nums.zip(strs) { a, b -> "$a -> $b" }
//    .collect { println(it) }
//}