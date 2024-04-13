package MultiThreadPattern.reactor

import io.reactivex.rxjava3.kotlin.toFlowable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers


fun main() {

  val time = System.currentTimeMillis()
  Flux.just("hello world", "brad")
    .subscribeOn(Schedulers.single())
    .doOnNext{ println(it) }
    .map { it -> it.length }
    .doOnNext{ println(it) }
    .subscribe {
      Thread.sleep(1000)
       println("${Thread.currentThread().name}-${it}")
     }


  println("main thread")
  Thread.sleep(3000)

  println(System.currentTimeMillis() - time)
}