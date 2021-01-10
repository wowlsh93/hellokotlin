package Tutorial

class Tutorial_Array {

    fun arrayTest(){
        var myArray1 = arrayOf(1,10,4,6,15)
        var myArray2 = arrayOf<Int>(1,10,4,6,15)
        val myArray3 = arrayOf<String>("Ajay","Prakesh","Michel","John","Sumit")
        var myArray4= arrayOf(1,10,4, "Ajay","Prakesh")

        var myArray5: IntArray = intArrayOf(5,10,20,12,15)

        var myArray = Array<Int>(5){0}

        val array1 = arrayOf(1,2,3,4)
        val array2 = arrayOf<Long>(11,12,13,14)
        array1.set(0,5)
        array1[2] = 6

        array2.set(2,10)
        array2[3] = 8

        for(element in array1){
            println(element)
        }
        println()
        for(element in array2){
            println(element)
        }
    }
}
