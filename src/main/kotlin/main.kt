
import Delegate.Cat
import Delegate.Dog
import Delegate.Lion
import Tree.BinarySearchTree
import Tree.Printer
import library.ClassToTest
import library.MyObject


fun library_test() {
    val lib = ClassToTest()
    lib.classFunction("hi", MyObject())
}

fun delegateTest(){

    Dog(3 ,"bob").name.also { println(it)}

    val cat = Cat(mapOf("age" to 3, "name" to "bob"))
    println(cat.age)
    println(cat.name)

    println(Lion().gettingAge())
}

fun treeTest() {
    val tree = BinarySearchTree<Int, Int>()
    tree.insert(10,10)
    tree.insert(3,3)
    tree.insert(15,15)
    tree.insert(5,5)
    tree.insert(6,6)
    tree.insert(1,1)
    tree.delete(3)

    Printer<Int,Int>().printTree(tree)
}


fun genericTest(){

}

fun proxyTest(){
    proxy.test3()
}

fun main() {
    //library_test()
    //delegateTest()
    //treeTest()
    //exceptionTest()
    //completableFuture()
    //genericTest()
    //LedgerQL().test()
    //proxyTest()
}