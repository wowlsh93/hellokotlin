package Tutorial

fun main(args: Array<String>) {
//    var str = "Kotlin Tutorial - Replace String - Programs"
//    val oldValue = "Programs"
//    val newValue = "Examples"
//
//    val output = str.replace(oldValue, newValue)
//
//    println(output)
//
//    //////////
//
//
//    var str = "Kotlin Tutorial - Replace String - Programs"
//    val oldValue = "PROGRAMS"
//    val newValue = "Examples"
//
//    val output = str.replace(oldValue, newValue, ignoreCase = true)
//
//    println(output)

    /////////////  remove whitspace
//    var sentence = "T    his is b  ett     er."
//    println("Original sentence: $sentence")
//
//    sentence = sentence.replace("\\s".toRegex(), "")
//    println("After replacement: $sentence")

//    Original sentence: T    his is b  ett     er.
//    After replacement: Thisisbetter.
//
//    ///////////////// match result
//    val regex = """a([bc]+)d?""".toRegex()
//    val matchResult = regex.find("abcb abbd")
//
//    println(matchResult!!.value)
//
//    //////////////// group destructing
//
//    val regex = """([\w\s]+) is (\d+) years old""".toRegex()
//    val matchResult = regex.find("Mickey Mouse is 95 years old")
//    val (name, age) = matchResult!!.destructured
//
//    assertEquals("Mickey Mouse", name)
//    assertEquals("95", age)
//    //////////////// multiple match
//
//    val regex = """a([bc]+)d?""".toRegex()
//    var matchResult = regex.find("abcb abbd")
//
//    assertEquals("abcb", matchResult!!.value)
//
//    matchResult = matchResult.next()
//    assertEquals("abbd", matchResult!!.value)
//
//    matchResult = matchResult.next()
//    assertNull(matchResult)
//
//
//    /////////////// replace ////////////////
//
//    val regex = """(red|green|blue)""".toRegex()
//    val beautiful = "Roses are red, Violets are blue"
//    val grim = regex.replace(beautiful, "dark")
//
//    assertEquals("Roses are dark, Violets are dark", grim)
//
//    val shiny = regex.replaceFirst(beautiful, "rainbow")
//
//    assertEquals("Roses are rainbow, Violets are blue", shiny)
//
//    /////////// splitting //////////
//
//    val regex = """\W+""".toRegex()
//    val beautiful = "Roses are red, Violets are blue"
//
//    assertEquals(listOf(
//        "Roses", "are", "red", "Violets", "are", "blue"), regex.split(beautiful))
}