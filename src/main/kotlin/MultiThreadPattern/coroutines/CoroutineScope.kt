package MultiThreadPattern.coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis


fun main() = runBlocking {
  val elapsedTime = measureTimeMillis {
    val name =  async(Dispatchers.Default) {getName()}
    val age = async (Dispatchers.Default){ getAge()}
    println("Hello, ${name.await()}! You are ${age.await()} years old.")
  }
  println("Total elapsed time: $elapsedTime ms")
}

private suspend fun getName() = coroutineScope {
  val name = async {
    delay(7000)
    "brad"
  }
  name
}
private fun getAge(): Int {
  var n = 0
  for (i in 0..20000000000)
    n += 1
  return 25
}