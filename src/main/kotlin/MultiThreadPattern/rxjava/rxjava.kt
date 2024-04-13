package MultiThreadPattern.rxjava

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.reactivestreams.Subscriber
import java.lang.Thread.sleep


fun main() {

  val list = listOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon")

  val ov = list.toObservable() // extension function for Iterables
    .filter { it.length >= 5 }
    .subscribeBy(  // named arguments for lambda Subscribers
      onNext = { println(it) },
      onError =  { it.printStackTrace() },
      onComplete = { println("Done!") }
    )

  Observable.just("long", "longer", "longest")
    .subscribeOn(Schedulers.io())
    .map(String::length)
    .observeOn(Schedulers.computation())
    .filter { it > 5 }
    .subscribe { length -> sleep(3000) ; println("item length $length") }


  val mySingle = Single.just("first")
  val singleObserver = mySingle
    .subscribeOn(Schedulers.io())
    .map(String::length)
    .observeOn(Schedulers.computation())
    .filter { it > 2 }
    .subscribe { data -> println("Received $data")
  }

  val ob =  Observable.create { emitter: ObservableEmitter<Any?> ->
    emitter.onNext(1)
    emitter.onNext(2)
    emitter.onNext(3)
    emitter.onComplete()
  }.subscribe { x: Any? -> println(x) }

  sleep(8000)
}