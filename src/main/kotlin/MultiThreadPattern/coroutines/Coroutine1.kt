//package MultiThreadPattern.coroutines
//
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.runBlocking
//
//fun main() {
//  println("The main program is started")
//  GlobalScope.launch {
//    println("Background processing started")
//    delay(500L)
//    println("Background processing finished")
//  }
//  println("The main program continues")
//  runBlocking {
//    delay(1000L)
//    println("The main program is finished")
//  }
//}