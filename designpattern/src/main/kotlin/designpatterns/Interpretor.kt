//package designpatterns
//
//sealed class Expression{
//    class Constant(val value: Int) : Expression()
//    class Mul(val left: Expression, val right: Expression) : Expression()
//    class Add(val left: Expression, val right: Expression) : Expression()
//}
//
//class Interpreter {
//    /**
//     * Interprets [extension] and returns [expression] value
//     */
//    fun interpret(expression: Expression): Int = when (expression) {
//        is Expression.Mul -> interpret(expression.left) * interpret(expression.right)
//        is Expression.Add -> interpret(expression.left) + interpret(expression.right)
//        is Expression.Constant -> expression.value
//    }
//}