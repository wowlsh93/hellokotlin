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

internal class EntityTest{

  @Test
  fun test1() {
    val e1 = Entity("1")
    val e2 = Entity("1")

    if(e1 == e2) {
      println("same")
    }
    else {
      println("different")
    }
  }

  @Test
  fun test2() {
   val student = School("hama")
   val student2 = student.copy()
    student2.student = "paul"
    println(student.student)

  }

  @Test
  fun test3() {
    val student = School2(Student("hama"))
    val student2 = student.clone()
    student2.student.name = "paul"
    println(student.student)

  }

}