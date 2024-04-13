package coding_test.dynamic_programming

import java.util.*
import kotlin.collections.ArrayDeque


// 1 1 2 3 5 8 13 21 34 55


var dp2 = IntArray(31)

fun fib2(n: Int): Int {
  // 0과 1인 상향식과 마찬가지로 값을 미리 지정
  if (n <= 1) return n

  // 미리 계산한 결과가 있다면 해당 값 리턴
  if (dp2[n] != 0)
    return dp2[n]
  dp2[n] = fib2(n - 1) + fib2(n - 2)
  return dp2[n]
}

fun main() {

  val result = fib2(10)
  println(result)

}