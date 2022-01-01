/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package reflection

import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor

class Lexer(source: String) {
  val tokens = mutableMapOf<String,String>()
  init {
    //from source
    tokens["age"] = "29"
    tokens["name"] = "alice"
  }
}

class ClassBuilder<T: Any>(cls: KClass<T>) {
    private val className = cls.qualifiedName
    private val constructor = cls.primaryConstructor

    fun createInstance(arguments: Map<String,String>): T{
      val params = constructor!!.parameters.associateBy(
        {it},
        {generateValue(it, arguments)}
      )
      return constructor!!.callBy(params)
    }

    private fun generateValue(param: KParameter, originValue: Map<*,*>): Any? {
        val name = param.name
        return originValue.get(name)
    }
}

class ObjectMapper {
  fun <T: Any>readValue(json: String, clazz: KClass<T>): T {

    val lexer = Lexer(json)
    val builder = ClassBuilder(clazz)
    return builder.createInstance(lexer.tokens)
  }
}

data class Person(val name: String, val age: String)

fun main(arr: Array<String>) {

  val json = """ {
                | "age" : 29,
                | "name" : "alice"
                | }
    """.trimMargin()

  val mapper = ObjectMapper()
  val person = mapper.readValue(json, Person::class)

  println(person.name)
  println(person.age)
}