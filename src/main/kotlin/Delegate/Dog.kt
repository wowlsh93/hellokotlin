/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package Delegate

import kotlin.properties.Delegates


class Dog(price: Int, name: String) {

    var age: Int = price
        private set
        get() = field * field

    var name: String = name
        set(value) {
            field = value
        }
        get() = "my name is :" + field
}

class Cat(properties: Map<String,Any>){
    val age: Int by properties
    val name: String by properties
}

data class MyProperty(val age: Int)

class Lion() {

    val props: MyProperty by lazy {
        MyProperty(5)
    }

    fun gettingAge(): Int{
        return props.age
    }

}