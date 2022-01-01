/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package Study2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ImmutableTest{

  @Test
  fun test1() {
    val account1 = BankAccount("1", Human("hama"))
    val account2 = account1.clone()
    account2.human.name = "paul"
    println(account1.human.name)

  }

}