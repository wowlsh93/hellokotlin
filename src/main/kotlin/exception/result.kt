package exception

//https://androidpoet.medium.com/the-magic-of-kotlin-result-class-4894f7fec4a7

//try {
//  runCatching { throwMyDoubleException() }
//    .recover { throwMyIntException() }
//    .onSuccess { println(it) } // 이 람다식은 실행되지 않는다.
//    .onFailure { println(it) } // 이 람다식은 실행되지 않는다.
//} catch (e: MyIntException) {
//  println(e) // > MyIntException
//}
//
//runCatching { throwMyDoubleException() }
//.recoverCatching { throwMyIntException() }
//.onFailure { println(it) } // > MyIntException
//
///////
//
//val fruitName = runCatching {
//  getRandomFruit()
//}.mapCatching {
//  it.toUpperCase()
//}.recoverCatching {
//  when(it) {
//    is IllegalStateException -> "Sold out"
//    is NullPointerException -> "null"
//    else -> throw it
//  }
//}.getOrDefault("")