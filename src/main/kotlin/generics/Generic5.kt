package generics

import kotlin.reflect.KClass

//fun <T> printGenerics(value: T) {
//  when (value::class) {  // compile error!
//    String::class.java -> {
//      println("String : $value")
//    }
//    Int::class.java -> {
//      println("Integer : $value")
//    }
//  }
//}

fun <T> printGenerics(value: T, classType: KClass<*>) {
  when (classType) {
    String::class -> {
      println("String : $value")
    }
    Int::class -> {
      println("Int : $value")
    }
  }
}

inline fun <reified T> printGenerics(value: T) {
  when (T::class) {
    String::class -> {
      println("String : $value")
    }
    Int::class -> {
      println("Int : $value")
    }
  }
}

inline fun<reified T> getMessage(number: Int): T {
  return when (T::class) {
    String::class -> "The number is : $number" as T
    Int::class -> number as T
    else -> "Not string, Not Integer" as T
  }
}

fun main(arr : Array<String>){

  printGenerics("print generics function1", String::class)
  printGenerics(1000, Int::class)

  printGenerics("print generics function2")
  printGenerics(2000)

  val result : Int = getMessage(10)
  println("result: $result")

  val resultString : String = getMessage(100)
  println("result: $resultString")

}