package Tutorial

class Tutorial_Function {
    /**
     * normal function
     */
    fun sum (a : Int, b : Int) : Int = a + b

    /**
     * recursion function
     */
    fun factorial(n: Int): Long {
        return if(n == 1){
            n.toLong()
        }
        else{
            n*factorial(n-1)
        }
    }

    /**
     * tail recursion function
     */
    tailrec fun factorial (n: Int, run: Int = 1): Long {

        return if (n == 1) {
            run.toLong()
        }else {
            factorial(n-1, run * n)
        }
    }

    /**
     * default and Named argument
     */

    fun run(num:Int= 5, latter: Char ='x'){
        print("parameter in function definition $num and $latter")
    }

    fun runInvoke(){
        run(latter='a')
    }

    /**
     * inline function
     */

    inline fun inlineFunction(myFun: () -> Unit ) {
        myFun()
        print("code inside inline function")
    }

    fun invokeInlineFunction(){
        inlineFunction({ println("calling inline functions")})
    }
}