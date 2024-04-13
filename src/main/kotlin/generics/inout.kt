//package generics
//
////https://betulnecanli.medium.com/kotlin-generics-in-out-where-terms-with-examples-445dc0bb45d6
//
//interface ReadOnly {
//  fun read(): Any
//}
//
//class ReadWrite<in T>(private var value: T) : ReadOnly {
//  override fun read(): Any = value
//
//  // 'in' keyword allows to use T as an input only
//  // so, the following line will give a compile error
//  // fun write(value: T) { this.value = value }
//}
//
//interface Consumer<in T> {
//  fun consume(item: T)
//}
//
//
//interface WriteOnly {
//  fun write(value: Any)
//}
//
//class ReadWrite<out T>(private var value: T) : WriteOnly {
//  // 'out' keyword allows to use T as an output only
//  // so, the following line will give a compile error
//  // fun read(): T = value
//
//  override fun write(value: Any) {
//    this.value = value
//  }
//}
//
//interface Producer<out T> {
//  fun produce(): T
//}
//
//class StringProducer : Producer<String> {
//  override fun produce(): String = "Hello"
//}
//
//class AnyProducer : Producer<Any> {
//  override fun produce(): Any = "Hello"
//}
//
//fun main() {
//  val stringProducer = StringProducer()
//  println(stringProducer.produce()) // prints "Hello"
//
//  val anyProducer: Producer<Any> = AnyProducer()
//  println(anyProducer.produce()) // prints "Hello"
//}
//
///////
//
//interface Processor<T> where T : CharSequence, T : Comparable<T> {
//  fun process(value: T): Int
//}
//
//class StringProcessor : Processor<String> {
//  override fun process(value: String): Int = value.length
//}
//
//
//interface Processor<T> where T : CharSequence, T : Comparable<T> {
//  fun process(value: T): Int
//}
//
//class StringProcessor : Processor<String> {
//  override fun process(value: String): Int = value.length
//}
//
//fun main() {
//  val stringProcessor = StringProcessor()
//  println(stringProcessor.process("Hello")) // prints "5"
//}