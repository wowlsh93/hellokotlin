package Tutorial
//
//fun main (arr : Array<String>){
//    /**
//     * mutable array
//     */
//    var myArray = Array<Int>(5){0}
//    var myArray1 = arrayOf(1,10,4,6,15)
//    var myArray2 = arrayOf<Int>(1,10,4,6,15)
//    val myArray3 = arrayOf<String>("Ajay","Prakesh","Michel","John","Sumit")
//    var myArray4= arrayOf(1,10,4, "Ajay","Prakesh")
//    var myArray5: IntArray = intArrayOf(5,10,20,12,15)
//
//    for (index in 0..4){
//        println(myArray5[index])
//    }
//    println()
//    for (index in 0..myArray5.size-1){
//        println(myArray5[index])
//    }
//
//    /**
//     * immutable collections
//     */
////    listOf()
////    listOf<T>()
////    mapOf()
////    setOf()
//
//    var list = listOf(1,2,3,"Ajay","Vijay","Prakash")//read only, fix-size
//    for(element in list){
//        println(element)
//    }
//    println()
//    for(index in 0..list.size-1){
//        println(list[index])
//    }
//
//    val myMap: Map<Int,String> = mapOf<Int, String>(1 to "Ajay", 4 to "Vijay", 3 to "Prakash")
//
//    for(key in myMap.keys){
//        println("Element at key $key = ${myMap.get(key)}")
//    }
//
//    println(".....myMap.contains(3).......")
//    println( myMap.contains(3))
//
//    /**
//     * mutable collections
//     */
////    ArrayList<T>()
////    arrayListOf()
////    mutableListOf() - kotlin
//
////    HashMap from java
////    hashMapOf()
////    mutableMapOf() - kotlin
//
////    HashSet from java
////    hashSetOf()
////    mutableSetOf() - kotlin
//
//    var mutableListInt: MutableList<Int> = mutableListOf<Int>()
//    var mutableListString: MutableList<String> = mutableListOf<String>()
//    var mutableListAny: MutableList<Any> = mutableListOf<Any>()
//
//    mutableListInt.add(5)
//    mutableListInt.add(7)
//    mutableListInt.add(10)
//    mutableListInt.add(2,15) //add element 15 at index 2
//
//    mutableListString.add("Ajeet")
//    mutableListString.add("Ashu")
//    mutableListString.add("Mohan")
//
//    mutableListAny.add("Sunil")
//    mutableListAny.add(2)
//    mutableListAny.add(5)
//    mutableListAny.add("Raj")
//
//    val hashMap:HashMap<Int,String> = HashMap<Int,String>() //define empty hashmap
//    hashMap.put(1,"Ajay")
//    hashMap.put(3,"Vijay")
//    hashMap.put(4,"Praveen")
//    hashMap.put(2,"Ajay")
//    println(".....traversing hashmap.......")
//    for(key in hashMap.keys){
//        println("Element at key $key = ${hashMap[key]}")
//    }}
//
//    val stringMap: HashMap<String,String> = hashMapOf<String,String>()
//    stringMap.put("name", "Ashu")
//    stringMap.put("city", "Delhi")
//    stringMap.put("department", "Development")
//    stringMap.put("hobby", "Playing")
//
//    println("......traverse stringMap.......")
//    for(key in stringMap.keys){
//        println("Key = ${key} , value = ${stringMap[key]}")
//    }
//
//    println(".......stringMap.containsValue(\"Delhi\")......")
//    println(stringMap.containsValue("Delhi"))
//    println(stringMap.containsValue("Mumbai"))
//
//
//    val mutableMap1: MutableMap<Int, String> = mutableMapOf<Int, String>(1 to "Ashu", 4 to "Rohan", 2 to "Ajeet", 3 to "Vijay")
//
//    val mutableMap2: MutableMap<String, String> = mutableMapOf<String, String>()
//    mutableMap2.put("name", "Ashu")
//    mutableMap2.put("city", "Delhi")
//    mutableMap2.put("department", "Development")
//    mutableMap2.put("hobby", "Playing")
//    val mutableMap3: MutableMap<Any, Any> = mutableMapOf<Any, Any>(1 to "Ashu", "name" to "Rohsan", 2 to 200)
//    println(".....traverse mutableMap1........")
//    for (key in mutableMap1.keys) {
//        println("Key = ${key}, Value = ${mutableMap1[key]}")
//    }
//    println("......traverse mutableMap2.......")
//    for (key in mutableMap2.keys) {
//        println("Key = "+key +", "+"Value = "+mutableMap2[key])
//    }
//    println("......traverse mutableMap3......")
//    for (key in mutableMap3.keys) {
//        println("Key = ${key}, Value = ${mutableMap3[key]}")
//    }
//}