//package Tutorial
//
//import com.github.avrokotlin.avro4k.Avro
//import com.github.avrokotlin.avro4k.io.AvroInputStream
//import com.github.avrokotlin.avro4k.io.AvroOutputStream
//import kotlinx.serialization.*
//import java.io.File
//import java.nio.ByteBuffer
//
//
////https://github.com/avro-kotlin/avro4k
//
//@Serializable
//data class Ingredient(val name: String, val sugar: Double, val fat: Double)
//
//@Serializable
//data class Pizza(val name: String, val ingredients: List<Ingredient>, val vegetarian: Boolean, val kcals: Int)
//
//
//fun ByteArray.toByteBuffer(): ByteBuffer = ByteBuffer.wrap(this)
//
//fun main() {
//
//    val ll = byteArrayOf().toByteBuffer()
//
//    println(ll)
//
//    val veg = Pizza("veg", listOf(Ingredient("peppers", 0.1, 0.3), Ingredient("onion", 1.0, 0.4)), true, 265)
//    val hawaiian = Pizza("hawaiian", listOf(Ingredient("ham", 1.5, 5.6), Ingredient("pineapple", 5.2, 0.2)), false, 391)
//
//    val schema = Avro.default.schema(Pizza.serializer())
//    println(schema.toString(true))
//
////    // serialization
////    val os = AvroOutputStream.binary(schema, Pizza.serializer()).to(File("pizzas.avro"))
////    os.write(listOf(veg, hawaiian))
////    os.flush()
////
////    // deserialization
////    val input = AvroInputStream.binary(Pizza.serializer(), schema).from(File("pizzas.avro"))
////    input.iterator().forEach { println(it) }
////    input.close()
//}