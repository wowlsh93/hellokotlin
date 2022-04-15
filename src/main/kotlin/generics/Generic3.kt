package generics

interface Fruits<T : Fruits<T>>{
  val price : Int

  fun compareTo(other: T): Boolean {
     return price > other.price
  }
}

data class Apple(override  val price: Int) : Fruits<Apple> {

}

data class Banana(override val price: Int) : Fruits<Banana>{

}

fun main(arr : Array<String>){

  val app1 = Apple(10)
  val app2 = Apple(30)

  println(app1.compareTo(app2))

  val ban1 = Banana(100)
  val ban2 = Banana(200)

  println(ban1.compareTo(ban2))

  //println(ban1.compareTo(app1))  // error!! banana can't compare to apple
}