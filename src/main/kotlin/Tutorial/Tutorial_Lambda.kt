package Tutorial

class Tutorial_Lambda {

    /**
     * normal lambda
     */
    fun addNumber(a: Int, b: Int, mylambda: (Int) -> Unit ){   //high level function lambda as parameter
        val add = a + b
        mylambda(add)
    }

    fun myFun(org: String,portal: String, fn: (String,String) -> String): Unit {
        val result = fn(org,portal)
        println(result)
    }

    fun invokeAddNumber(){
        //addNumber(1,2, {sum : Int -> println(sum)})  or
        addNumber(1,2, {println(it)})

        val fn:(String,String)->String={org,portal->"$org develop $portal"}
        myFun("sssit.org","javatpoint.com",fn)
    }

    /**
     * lambda with receiver
     */
    fun buildString1(action: (StringBuilder) -> Unit): String {
        val builder = StringBuilder()
        action(builder)
        return builder.toString()
    }

    fun buildString2(action: (StringBuilder).() -> Unit): String {
        val builder = StringBuilder()
        action(builder)
        return builder.toString()
    }

    fun invokeBuildString(){

        buildString1 { builder ->
            builder.append("<")
            builder.append("MindOrks")
            builder.append(">")
        }

        buildString2 {
            append("<")
            append("MindOrks")
            append(">")
        }

    }


}
