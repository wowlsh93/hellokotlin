/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package immutability

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.concurrent.thread

internal class BankTest{

  @Test
  fun test1() {

    val valPointer = 10
//    valPointer = 20

    var varPointer = 10
    varPointer = 20
  }


  @Test
  fun test2() {
    val account = BankAccount("accountid-11")
  //  account = BankAccount("accountid-22")
    account.deposit(1000.0)
  }

  @Test
  fun test3() {
    var num = 0
    for (i in 1..1000){
      thread {
        Thread.sleep(10)
        num += 1
      }
    }

    Thread.sleep(1000)
    println(num)
  }

  @Test
  fun test4() {
    val lock = Any()
    var num = 0
    for (i in 1..1000){
      thread {
        Thread.sleep(10)
        synchronized(lock){
          num += 1
        }
      }
    }
    Thread.sleep(1000)
    println(num)
  }

  @Test
  fun test5() {
    val lock = Any()
    val nums = mutableListOf<Int>()
    for (i in 1..1000){
      thread {
        Thread.sleep(10)
          nums.add(i)
      }
    }
    Thread.sleep(1000)
    println(nums.size)
  }

  @Test
  fun test6() {
    val lock = Any()
    var nums: List<Int> = listOf()
    for (i in 1..1000){
      thread {
        Thread.sleep(10)
        nums = nums + 1
      }
    }
    Thread.sleep(1000)
    println(nums.size)
  }
}

