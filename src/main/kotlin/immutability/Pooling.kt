///*
// * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
// * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
// * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
// * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
// * Vestibulum commodo. Ut rhoncus gravida arcu.
// */
//
//package immutability
//
//import org.apache.commons.pool2.BasePooledObjectFactory
//import org.apache.commons.pool2.PooledObject
//import org.apache.commons.pool2.impl.DefaultPooledObject
//import java.lang.IllegalStateException
//import java.util.*
//import java.util.concurrent.CompletableFuture
//import java.util.concurrent.TimeUnit
//import java.util.concurrent.atomic.AtomicInteger
//import java.util.concurrent.locks.ReentrantLock
//import kotlin.concurrent.withLock
//import kotlin.random.Random
//
//class ClusteredWallet(val n : Int) {
//  fun get(): Int  {
//    return n
//  }
//}
//class WalletBag(val wallet: ClusteredWallet)
//
//class WalletBagFactory(
//  private val walletList: List<ClusteredWallet>) : BasePooledObjectFactory<WalletBag>() {
//
//  private val index = AtomicInteger()
//
//  @Throws(Exception::class)
//  override fun create(): WalletBag {
//    return WalletBag(walletList.get(index.getAndIncrement()))
//  }
//
//  override fun wrap(wallet: WalletBag): PooledObject<WalletBag> {
//    return DefaultPooledObject(wallet)
//  }
//}
//
//
//
/////////////////////////////////////////////
//
//
//interface ObjectPool<T> {
//  fun take(): T
//  fun release(obj: T)
//  fun poll(waitTime: Long, waitUnit: TimeUnit): T
//  fun newInstance(): T
//
//  fun <R> use(function: (T) -> R): R {
//    take().apply {
//      return try {
//        function(this)
//      } finally {
//        release(this)
//      }
//    }
//  }
//
//  fun <R> useAsync(function: (T) -> CompletableFuture<R>): CompletableFuture<R> {
//    return CompletableFuture.supplyAsync{
//      take()
//    }.thenCompose {
//      try{
//        function(it)
//      }
//      finally {
//        release(it)
//      }
//    }
//  }
//}
//
//interface ObjectFactory<T> {
//  fun newInstance(): T
//}
//
//class WalletFactory(
//  private val walletList: List<ClusteredWallet>)
//  : ObjectFactory<WalletBag> {
//
//  private val index = AtomicInteger()
//
//  @Throws(Exception::class)
//  override fun newInstance(): WalletBag {
//    return WalletBag(walletList.get(index.getAndIncrement()))
//  }
//}
//
//class FixedObjectPool<T>(factory: ObjectFactory<T>, size: Int, val waitSize: Int = 0 ): ObjectPool<T> {
//
//  val factory : ObjectFactory<T>
//  val lock = ReentrantLock()
//  val condition = lock.newCondition()
//  val pool = mutableListOf<T>()
//  var pause = false
//
//
//  init {
//    this.factory = factory
//    for (i in 1 .. size) {
//      pool.add(newInstance())
//    }
//  }
//
//  fun size(): Int {
//    return pool.size
//  }
//
//  override fun take(): T {
//    lock.withLock {
//      while (pool.isEmpty()){
//        condition.await()
//      }
//      val obj = pool.first()
//      pool.removeFirst()
//      return obj
//    }
//  }
//
//  override fun poll(waitTime: Long, waitUnit: TimeUnit): T {
//    lock.withLock {
//      while (pool.isEmpty()){
//        if (!condition.await(waitTime, waitUnit)) throw IllegalStateException("fail to get object")
//      }
//      val obj = pool.first()
//      pool.removeFirst()
//      return obj
//    }
//  }
//
//  override fun release(obj: T) {
//    lock.withLock {
//      if (pool.isEmpty()) {
//        pause = true;
//      }
//
//      pool.add(obj)
//
//      if (pause && pool.size > waitSize) {
//        condition.signalAll()
//        pause = false;
//      }
//    }
//  }
//
//  override fun newInstance(): T {
//    return factory.newInstance()
//  }
//}
