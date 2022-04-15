package generics


class Column<T> (val data: T) {
  fun get(): T {
    return data
  }
}

fun main(arr : Array<String>){
  val col = Column(10)
  println(col.get())
}