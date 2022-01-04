//package MultiThreadPattern.coroutines
//
//import kotlinx.coroutines.CoroutineExceptionHandler
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.runBlocking
//
//fun main() = runBlocking {
//  val handler = CoroutineExceptionHandler { _, exception ->
//    println("$exception handled !")
//  }
//  val job = GlobalScope.launch(handler) {
//    throw UnsupportedOperationException()
//  }
//  job.join()
//}