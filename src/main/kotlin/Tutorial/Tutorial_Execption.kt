package Tutorial

import java.lang.Exception

fun main(args: Array<String>) {

    /**
     * normal
     */
    try{
//code that may throw exception
    }catch(e: Exception){
//code that handles exception
    }

    try{
//code that may throw exception
    }finally{
// code finally block
    }


    /**
     *  try block as an Expression
     */

    fun getNumber(str: String): Int{
        return try {
            Integer.parseInt(str)
        } catch (e: ArithmeticException) {
            0
        }
    }

    val str = getNumber("10")
    println(str)


    /**
     *  throw keyword
     */
    fun validate(age: Int) {
        if (age < 18)
            throw ArithmeticException("under age")
        else
            println("eligible for drive")
    }
}
