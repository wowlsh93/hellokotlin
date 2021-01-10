package Tutorial

fun main(args: Array<String>) {

    /**
     * raw string
     */
    val text = """Kotlin is official language  
        |announce by Google for  
        |android application development  
    """
    println(text)
    println()
    val text2 = """Kotlin is official language  
        |announce by Google for  
        |android application development  
    """.trimMargin()

    println(text2)
    println()

    val text3 = """Kotlin is official language  
        #announce by Google for  
        #android application development  
    """.trimMargin("#")
    println(text3)

    /**
     * string equality
     */

    //Structural equality (==)
    val str1 = "Hello, World!"
    val str2 = "Hello, World!"
    println(str1==str2) //true
    println(str1!=str2) //false

    //Referential equality (===)
    val str11 = buildString { "string value" }
    val str22 = buildString { "string value" }
    println(str11===str22) // false
    println(str11!==str22) // true
}