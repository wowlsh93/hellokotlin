package Tutorial

class Class1 {
}
class Class2 constructor(val id : Int){

}
class Class3 (val id: Int) {

}
class Class4 (id : Int = 100){

}
class Class5 {
    var acc_no: Int = 0
    var name: String? = null
    var amount: Float = 0f

    val array  = arrayOf("hello", "world")
    val array2 = Array<String>(5){"hi"}
    val array3 = mutableListOf<String>()

    val map : Map<Int, String> = mapOf(1 to "korea")

    val map2 = mutableMapOf<Int,String>()
    val map3 = HashMap<Int,String>()
}


class Account {
    var acc_no: Int = 0
    var name: String =  ""
    var amount: Float = 0.toFloat()
    fun insert(ac: Int,n: String, am: Float ) {
        acc_no=ac
        name=n
        amount=am
        println("Account no: ${acc_no} holder :${name} amount :${amount}")
    }

    fun deposit() {
        //deposite code
    }

    fun withdraw() {
        // withdraw code
    }

    fun checkBalance() {
        //balance check code
    }
}

class Account2 (var acc_no:Int =0, var name: String ="", var amount: Float = 0.0f){

}

//
//class outerClass{
//    private var name: String = "Ashu"
//    class nestedClass{
//        var description: String = "code inside nested class"
//        private var id: Int = 101
//        fun foo(){
//            //  print("name is ${name}") // cannot access the outer class member
//            println("Id is ${id}")
//        }
//    }
//}
//
//class outerClass{
//    private  var name: String = "Ashu"
//    inner class  innerClass{
//        var description: String = "code inside inner class"
//        private var id: Int = 101
//        fun foo(){
//            println("name is ${name}") // access the outer class member even private
//            println("Id is ${id}")
//        }
//    }
//}
//
//
//class myClass(password: String){
//
//    constructor(name: String, id: Int, password: String): this(password){
//        println("Name = ${name}")
//        println("Id = ${id}")
//        println("Password = ${password}")
//    }
//}
//
//class myClass{
//
//    constructor(name: String, id: Int): this(name,id, "mypassword"){
//        println("this executes next")
//        println("Name = ${name}")
//        println("Id = ${id}")
//    }
//
//    constructor(name: String, id: Int,pass: String){
//        println("this executes first")
//        println("Name = ${name}")
//        println("Id = ${id}")
//        println("Password = ${pass}")
//    }
//}

//fun main(arr : Array<String>){
//    Account()
//    var acc= Account()
//    acc.insert(832345,"Ankit",1000f) //accessing member function
//    println("${acc.name}") //accessing class property
//
//    // nested class must be initialize
//    println(outerClass.nestedClass().description) // accessing property
//    var obj = outerClass.nestedClass() // object creation
//    obj.foo() // access member function
//
//    println(outerClass().innerClass().description) // accessing property
//    var obj = outerClass().innerClass() // object creation
//    obj.foo() // access member function
//
//    val myclass = myClass ("Ashu", 101, "mypassword")
//
//}
