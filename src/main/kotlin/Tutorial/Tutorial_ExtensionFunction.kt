package Tutorial

/**
 * Functioni Extension
 */

class Alien {
    var skills : String = "null"

    fun printMySkills() {
        print(skills)
    }
}

fun Alien.addMySkills(a:Alien):String{
    var a4 = Alien()
    a4.skills = this.skills + " " +a.skills
    return a4.skills
}



fun MutableList<Int>?.swap(index1: Int, index2: Int): Any {
    if (this == null) return "null"
    else  {
        val tmp = this[index1] // 'this' represents to the list
        this[index1] = this[index2]
        this[index2] = tmp
        return this
    }
}
fun main(args: Array<String>) {
    var  a1 = Alien()
    a1.skills = "JAVA"
    //a1.printMySkills()

    var  a2 = Alien()
    a2.skills = "SQL"
    //a2.printMySkills()

    var  a3 = Alien()
    a3.skills = a1.addMySkills(a2)
    a3.printMySkills()

    val list = mutableListOf(5,10,15)
    println("before swapping the list :$list")
    val result = list.swap(0, 2)
    println("after swapping the list :$result")
}
