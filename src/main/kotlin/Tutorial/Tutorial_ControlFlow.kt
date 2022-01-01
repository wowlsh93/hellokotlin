package Tutorial

class Tutorial_ControlFlow {

    fun start() {
        ifFlow();
    }

    /**
     * if
     */
    fun ifFlow() {
        val num1 = 10
        val num2 = 20
        val result = if (num1 > num2) {
            "$num1 is greater than $num2"
        } else {
            "$num1 is smaller than $num2"
        }
        println(result)
    }

    /**
     * when
     */
    fun whenFlow() {
        var number = 8

        when (number) {
            3, 4, 5, 6 ->
                println("It is summer season")
            7, 8, 9 ->
                println("It is rainy season")
            10, 11 ->
                println("It is autumn season")
            12, 1, 2 ->
                println("It is winter season")
            else -> println("invalid input")
        }
    }

    /**
     * for
     */
    fun forFlow() {
        val marks = arrayOf(80, 85, 60, 90, 70)
        for (item in marks) {
            println(item)
        }

        for (i in 1..5) print(i)
        for (i in 5 downTo 1) print(i)
        for (i in 1..5 step 2) print(i)
    }

    /**
     * while
     */
    fun whileFlow() {
        var i = 1
        while (i <= 5) {
            println(i)
            i++
        }
    }

    /**
     * breakloop
     */
    fun breakloopFlow() {
        loop@ for (i in 1..3) {
            for (j in 1..3) {
                println("i = $i and j = $j")
                if (i == 2)
                    break@loop
            }
        }
    }

    /**
     * continuloop
     */
    fun continuloopFlow() {
        labelname@ for (i in 1..3) {
            for (j in 1..3) {
                println("i = $i and j = $j")
                if (i == 2) {
                    continue@labelname
                }
                println("this is below if")
            }
        }
    }
}
