/*
 * Copyright 2012 OpusM Corp
 * written by  hama
 *
 */

package MultiThreadPattern.future

class TLS {
  var name: String? = null
  companion object {
    private val cabinet = ThreadLocal<TLS>()
    var context: TLS?
    get(): TLS? {
      return cabinet.get()
    }
    set(v) {
      if(v==null){
        cabinet.remove()
      }else {
        cabinet.set(v)
      }
    }
  }
}


fun doSomething() {
  TLS.context = TLS()
  TLS.context?.name = Thread.currentThread().name
}

fun fork1() {
  Thread {
    doSomething()
    println(TLS.context?.name)
  }.start()
}


fun fork2() {
  Thread {
    doSomething()
    println(TLS.context?.name)
  }.start()
}

fun main() {
  fork1()
  fork1()

  Thread.sleep(1000* 100)
}