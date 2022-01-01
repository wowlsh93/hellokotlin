/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package immutability

class Entity(val id: String){

  override fun hashCode(): Int {
    return id.toInt()
  }

  override fun equals(other: Any?): Boolean {
    return if(other is Entity)
      other.id == this.id
    else
      false
  }
}

data class School(var student: String)


data class Student(var name: String)

interface Cloneable<T> {
  fun clone(): T
}
data class School2(var student: Student): Cloneable<School2> {
  override fun clone(): School2 {
     return School2(Student(student.name))
  }


}
