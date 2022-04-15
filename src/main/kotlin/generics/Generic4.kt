package generics


class ParameterizedProducer<out T>(private val value: T) {
  fun get(): T {
    return value
  }
}

class ParameterizedConsumer<in T> {
  fun toString(value: T): String {
    return value.toString()
  }
}


fun main(arr : Array<String>){

  val parameterizedProducer = ParameterizedProducer<String>("string")
  val consumer = ParameterizedConsumer<Number>()

}
