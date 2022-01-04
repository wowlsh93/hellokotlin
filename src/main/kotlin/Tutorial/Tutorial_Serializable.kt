//import kotlinx.serialization.*
//import kotlinx.serialization.json.*
//
//
////https://www.androidhuman.com/2020-11-08-kotlin_1_4_serialization
//
//
//@Serializable
//data class User(val name: String, val yearOfBirth: Int)
//
//fun main() {
//    ///////////////////////////////// json ///////////////////////
//
//    val data = User("Louis", 1901)
//    val string = Json.encodeToString(data)
//    println(string) // {"name":"Louis","yearOfBirth":1901}
//
//    val obj = Json.decodeFromString<User>(string)
//    println(obj) // User(name=Louis, yearOfBirth=1901)
//
//
//
//}