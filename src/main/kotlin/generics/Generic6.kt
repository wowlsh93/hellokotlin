package generics
//
//fun printString(value: String) {
//  when (value::class) {
//    String::class -> {
//      println("String : $value")
//    }
//  }
//}
//
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
//
//
//fun <T> printGenerics(value: T, classType: Class<T>) {
//  when (classType) {
//    String::class.java -> {
//      println("String : $value")
//    }
//    Int::class.java -> {
//      println("Int : $value")
//    }
//  }
//}
//
//inline fun <reified T> printGenerics(value: T) {
//  when (T::class) {
//    String::class -> {
//      println("String : $value")
//    }
//    Int::class -> {
//      println("Int : $value")
//    }
//  }
//}
//
//// compile error!
//fun getMessage(number: Int): String {
//  return "The number is : $number"
//}
//
//fun getMessage(number: Int): Int {
//  return number
//}
//
//inline fun<reified T> getMessage(number: Int): T {
//  return when (T::class) {
//    String::class -> "The number is : $number" as T
//    Int::class -> number as T
//    else -> "Not string, Not Integer" as T
//  }
//}
//
//fun main(arr : Array<String>){
//  printString("print string function")
//
//  printGenerics("print generics function", String::class.java)
//  printGenerics(1000, Int::class.java)
//
//  printGenerics1("print generics function")
//  printGenerics1(1000)
//
//  val result : Int = getMessage(10)
//  println("result: $result")
//
//  val resultString : String = getMessage(100)
//  println("result: $resultString")
//
//}