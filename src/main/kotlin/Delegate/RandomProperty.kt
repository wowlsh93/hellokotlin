package Delegate

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.random.Random
import kotlin.reflect.KProperty

class RandomProperty : ReadOnlyProperty<Any, Int> {

  override fun getValue(thisRef: Any, property: KProperty<*>): Int {
    return Random.nextInt()
  }
}

class Application {

  val randomInt by RandomProperty()

  fun main() {
    println("1st random: $randomInt")
    println("2nd random: $randomInt")
  }
}


////

class PlusOneProperty : ReadWriteProperty<Any, Int> {

  private var mValue = 0

  override fun getValue(thisRef: Any, property: KProperty<*>): Int {
    return mValue
  }

  override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
    mValue = value + 1
  }
}

class Application2 {
  var plusOne by PlusOneProperty()

  fun main() {
    plusOne = 10
    println("PlusOne is: $plusOne")
  }
}