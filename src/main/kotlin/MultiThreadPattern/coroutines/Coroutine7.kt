package MultiThreadPattern.coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

val start = System.currentTimeMillis()

fun main() {
  runBlocking {
    val jobOne = runJobOne()
    val jobTwo = runJobTwo()
    printWithTime("runBlocking: finished")
  }
  printWithTime("main: finished")
}

fun runJobOne() {
  printWithTime("1: Job started")
  GlobalScope.launch {
    printWithTime("1: Coroutine started")
    delay(700L)
    printWithTime("1: Coroutine finished")
  }
  printWithTime("1: end")
}

suspend fun runJobTwo() = coroutineScope {
  printWithTime("2: Job started")
  launch {
    printWithTime("2: Coroutine started")
    delay(250L)
    printWithTime("2: Coroutine finished")
  }
  printWithTime("2: end")
}

fun printWithTime(message: String) {
  println("[${System.currentTimeMillis() - start}ms] ${message}")
}