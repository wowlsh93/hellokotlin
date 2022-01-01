//package Tutorial
//
//class Tutorial_Type {
//
//    /**
//     * number type
//     */
//    val byteType : Byte     = 127 // 8bit
//    val shortType : Short   = 32767 //16bit
//    val intType : Int       = 2_147_483_647 //32bit
//    val longType : Long     = 9_223_372_036_854_775_807 //64bit
//    val floatType : Float   = 3.402f // 32bit
//    val doubleType : Double = 1.796  // 64bit
//
//    /**
//     * char type
//     */
//    val char : Char = 'a'
//
//    /**
//     * boolean type
//     */
//    val isTrue : Boolean = true
//
//    /**
//     * Array type
//     */
//    val arr : Array<String> = arrayOf("hello", "world")
//    var arr2 = Array(5, {i -> i * 2})
//
//    /**
//     * String type
//     */
//    val str : String = "hello world"
//
//    val rawStr = """
//             Welcome
//                 To
//             Kotlin
//    """
//
//
//}
//
//// ByteArray -> Long
//val longBytes = byteArrayOf(1, 1, 1, 1, 1, 1, 1, 1)
//ByteBuffer.wrap(longBytes).getLong()
//
//// ByteArray -> Int
//val intBytes = byteArrayOf(1, 1, 1, 1)
//ByteBuffer.wrap(intBytes).getInt()
//
//fun byteArrayToLong(byteArray: ByteArray?): Long {
//    byteArray ?: return 0
//    var result: Long = 0
//    for (i in 0..7) {
//        val value = byteArray.getOrNull(i) ?: 0
//        result = result shl 8
//        result = result or value.toUByte().toLong()
//    }
//    return result
//}
//
//fun byteArrayToInt(byteArray: ByteArray?): Int {
//    byteArray ?: return 0
//    var result = 0
//    for (i in 0..3) {
//        result = result shl 8
//        result = result or byteArray[i].toUByte().toInt()
//    }
//    return result
//}
//。
//fun byteArrayToLong(byteArray: ByteArray): Long {
//    var result: Long = 0
//    for (i in 0..7) {
//        result = result shl 8
//        result = result or (byteArray[i] and 0xFF.toByte()).toLong()
//    }
//    return result
//}
//
//fun byteArrayToInt(byteArray: ByteArray): Int {
//    var result: Int = 0
//    for (i in 0..3) {
//        result = result shl 8
//        result = result or (byteArray[i] and 0xFF.toByte()).toInt()
//    }
//    return result
//}
//
//fun main(args: Array<String>){
//    val longBytes = byteArrayOf(1, 1, 1, 1, 1, 1, 1, 1)
//    val intBytes = byteArrayOf(1, 1, 1, 1)
//
//
//fun Double.bytes() =
//    ByteBuffer.allocate(java.lang.Long.BYTES)
//        .putLong(java.lang.Double.doubleToLongBits(this))
//        .bytes()
//
//    println("ByteBuffer#getLong(): ${ByteBuffer.wrap(longBytes).getLong()}")
//    println("ByteBuffer#getInt(): ${ByteBuffer.wrap(intBytes).getInt()}")
//
//    println("byteArrayToLong(): ${byteArrayToLong(longBytes)}")
//    println("byteArrayToInt(): ${byteArrayToInt(intBytes)}")
//}
//
//val ints = intArrayOf(0x01, 0xFF)
//val bytes = ints.foldIndexed(ByteArray(ints.size)) { i, a, v -> a.apply { set(i, v.toByte()) } }
