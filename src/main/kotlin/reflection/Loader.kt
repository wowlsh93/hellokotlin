///*
// * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
// * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
// * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
// * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
// * Vestibulum commodo. Ut rhoncus gravida arcu.
// */
//
//package reflection
//
//import io.opusm.utils.configuration.*
//import kotlin.random.Random
//import kotlin.reflect.full.declaredFunctions
//import kotlin.reflect.full.declaredMemberProperties
//import kotlin.reflect.full.primaryConstructor
//
//class Loader {
//  fun load(classFile: String) {
//    val clazz = Class.forName(classFile).kotlin
//    println(clazz.simpleName)
//
//    clazz.declaredFunctions.forEach {
//      println(it)
//    }
//
//    clazz.declaredMemberProperties.forEach {
//      println(it)
//    }
//
//    val primaryConstructor = clazz.primaryConstructor
//    primaryConstructor!!.parameters.forEach {
//      println(it)
//    }
//    val instance = primaryConstructor!!.call(ZookeeperConnector("ip","port"))
//    val method = clazz.declaredFunctions.find { func -> func.name == "get"}
//    println(method!!.call(instance, "key"))
//
//    val config = instance as Configurable
//    println("------" + config.get("key"))
//
//  }
//}
//
//fun main(args: Array<String>){
//
//  println("loader test!!")
//  val arr =  arrayOf("io.opusm.utils.configuration.FileConfiguration", "io.opusm.utils.configuration.ZookeeperConfiguration")
//  val config = arr.get(Random.nextInt(2))
//
//  Loader().load("io.opusm.utils.configuration.ZookeeperConfiguration")
//}