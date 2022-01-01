package Tutorial

//
//fun main(args: Array<String>){
//    /**
//     * nullable
//     */
//    var str1: String? = "hello"
//    str1 = null // ok
//
//    /**
//     * non-nullable
//     */
//    var str2: String = "Hello"
//    str2 = null // compile error
//
//    /**
//     * Checking for null in conditions
//     */
//    var str3: String? = "Hello"     // variable is declared as nullable
//    var len = if(str3!=null) str3.length else -1
//    println("str is : $str3")
//    println("str length is : $len")
//
//    str3 = null
//    println("str is : $str3")
//    len = if(str3!=null) str3.length else -1
//    println("b length is : $len")
//
//    /**
//     * SmartCase
//     */
//    val obj: Any = "The variable obj is automatically cast to a String in this scope"
//    if(obj is String) {
//        // No Explicit Casting needed.
//        println("String length is ${obj.length}")
//    }
//
//    val obj2: Any = "The variable obj is automatically cast to a String in this scope"
//    if(obj2 !is String) {
//        println("obj is not string")
//
//    } else
//    // No Explicit Casting needed.
//        println("String length is ${obj2.length}")
//
//
//    /**
//     * safe cast by :as?
//     */
//
//    val obj1: Any = 123
//    val str1: String = obj as String // // Throws java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
//
//    val obj2: String? = "String unsafe cast"
//    val str2: String? = obj2 as String? // Works
//    println(str2)
//
//    val location: Any = "Kotlin"
//    val safeString: String? = location as? String
//    val safeInt: Int? = location as? Int
//    println(safeString)
//    println(safeInt)
//
//    /**
//     * elvie operator
//     */
//    var str: String? = null
//    var str2: String? = "May be declare nullable string"
//    var len1:  Int = str ?.length ?: -1
//    var len2:  Int = str2 ?.length ?:  -1
//
//    println("Length of str is ${len1}")
//    println("Length of str2 is ${len2}")
//}
