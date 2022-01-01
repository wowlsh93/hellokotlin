/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package immutability

import org.apache.commons.pool2.impl.GenericObjectPool
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread


internal class PoolingTest{

//  @Test
//  fun poolingTest() {
//
//    val poolConfig =  GenericObjectPoolConfig<WalletBag>();
//    poolConfig.setMinIdle(0)
//    poolConfig.setMaxIdle(5)
//    poolConfig.setMaxTotal(5)
//
//
//    val f = WalletBagFactory(listOf(ClusteredWallet(1),
//      ClusteredWallet(2),
//      ClusteredWallet(3),
//      ClusteredWallet(4),
//      ClusteredWallet(5)
//    ))
//
//    val objectPool = GenericObjectPool(f, poolConfig)
//
//    for (i in 1..10) {
//      thread {
//        Thread.sleep(20)
//        var bag: WalletBag? = null;
//        try {
//          bag = objectPool?.borrowObject();
//          println("borrowed count: "+  objectPool?.borrowedCount)
//          println("returned count: "+  objectPool?.returnedCount)
//          Thread.sleep(300)
//          // objectPool.returnObject(bag);
//        } catch (e: NoSuchElementException) {
//          e.printStackTrace();
//        } catch (e: Exception) {
//          e.printStackTrace();
//        }
//      }
//    }
//  Thread.sleep(1000 * 60)
//  }

  @Test
  fun poolingTest2() {
    val f = WalletFactory(listOf(ClusteredWallet(1),
      ClusteredWallet(2),
      ClusteredWallet(3),
      ClusteredWallet(4),
      ClusteredWallet(5)
    ))

    val objectPool = FixedObjectPool(f, 5 )

    val countDownLatch = CountDownLatch(5)

    for (i in 1..5) {
      thread {
        Thread.sleep(20)
        var bag: WalletBag? = null;
        println("wait - " + Thread.currentThread().name)
        bag = objectPool?.take();
        println("take - " + Thread.currentThread().name)
        Thread.sleep(1000)
        objectPool.release(bag);
        //println("release"+ Thread.currentThread().name)
        countDownLatch.countDown()
      }
    }


    countDownLatch.await()
    println("end")
  }
}