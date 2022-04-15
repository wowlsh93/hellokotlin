package generics


fun copy(from: Array<out Any>, to: Array<Any?>) {
  assert(from.size == to.size)
  for (i in from.indices)
    to[i] = from[i]
}

fun fill(dest: Array<in Int>, value: Int) {
  dest[0] = value
}

fun main(arr : Array<String>){

  val ints: Array<Int> = arrayOf(1, 2, 3)
  val any: Array<Any?> = arrayOfNulls(3)

  copy(ints, any)

  assertEquals(any[0], 1)
  assertEquals(any[1], 2)
  assertEquals(any[2], 3)

  /////

  val objects: Array<Any?> = arrayOfNulls(1)
  fill(objects, 1)
  assertEquals(objects[0], 1)

}

fun assertEquals(any: Any?, i: Int) {
    if(any == i)
      println("ok")
    else
      println("error")
}

fun assertTrue(b: Boolean) {
  when(b) {
    true -> "true"
    false -> "false"
  }
}
