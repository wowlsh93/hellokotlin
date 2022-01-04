//package MultiThreadPattern.coroutines
//
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.runBlocking
//import kotlinx.coroutines.withTimeout
//
//fun main() = runBlocking {
//  withTimeout(1000L) {
//    repeat(30) { i ->
//      println("Processing $i ...")
//      delay(300L)
//    }
//  }
//}