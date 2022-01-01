//package Tutorial
//
//
//interface Fruit {
//    val size: Int
//}
//
//class Apple(override val size: Int) : Fruit, Comparable<Apple> {
//
//    override operator fun compareTo(other: Apple): Int {
//        return size.compareTo(other.size)
//    }
//}
//
//
//class Orange(override val size: Int) : Fruit, Comparable<Orange> {
//
//    override operator fun compareTo(other: Orange): Int {
//        return size.compareTo(other.size)
//    }
//}
//
///////////////
//interface Fruit2 : Comparable<Fruit2> {
//    val size: Int
//    override operator fun compareTo(other: Fruit2): Int {
//        return size.compareTo(other.size)
//    }
//}
//class Apple2(override val size: Int) : Fruit2
//class Orange2(override val size: Int) : Fruit2
//
////////////////// Type Parameter
//
////interface Fruit3<T> : Comparable<T> {
////    val size: Int
////    override operator fun compareTo(other: T): Int {
////        return size.compareTo(other.size) // Error: size not available.
////    }
////}
////
////class Apple3(override val size: Int) : Fruit<Apple3>
////class Orange3(override val size: Int) : Fruit<Orange3>
//
////////////////// Recursive Type Bound
//
//interface Fruit4<T : Fruit4<T>> : Comparable<T> {
//    val size: Int
//    override operator fun compareTo(other: T): Int {
//        return size.compareTo(other.size)
//    }
//}
//
//class Apple4(override val size: Int) : Fruit4<Apple4>
//class Orange4(override val size: Int) : Fruit4<Orange4>
//
//
//fun main(arr : Array<String>){
//    val apple1 = Apple4(1)
//    val apple2 = Apple4(2)
//    println(apple1 > apple2)   // No error
//
//    val orange1 = Orange4(1)
//    val orange2 = Orange4(2)
//    println(orange1 < orange2) // No error
//
//    println(apple1 < orange1)  // Error: different types
//
//}