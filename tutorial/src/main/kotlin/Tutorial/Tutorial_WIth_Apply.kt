//package Tutorial
//
//
///**
// * with
// */
//
////inline fun <T, R> with(receiver: T, block: T.() -> R): R {
////    return receiver.block()
////}
//
//class Person {
//    var name: String? = null
//    var age: Int? = null
//}
//val person: Person = getPerson()
//print(person.name)
//print(person.age)
//
//
//val person: Person = getPerson()
//with(person) {
//    print(name)
//    print(age)
//}
//
///**
// * apply
// */
//
//// fun <T> T.apply(block: T.() -> Unit): T
//
//val param = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
//
//param.gravity = Gravity.CENTER_HORIZONTAL
//param.weight = 1f
//param.topMargin = 100
//param.bottomMargin = 100
//
//val param = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
//    gravity = Gravity.CENTER_HORIZONTAL
//    weight = 1f
//    topMargin = 100
//    bottomMargin = 100
//}