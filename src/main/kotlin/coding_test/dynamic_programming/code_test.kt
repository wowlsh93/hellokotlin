

package coding_test.dynamic_programming

import java.util.*
import kotlin.collections.ArrayDeque


fun dfs(i: Int, j: Int) {
  // 종료 로직

  // 해당 위치에 맞는 결과 계산

  // 아랫 단계 계산
  dfs(1, 1)
}

var dp = IntArray(46)

fun dynamic(n: Int): Int {
  // 종료 로직
  if (n <= 2) return n

  // 미리 계산된 중간 값
  if (dp[n] != 0)
    return dp[n]

  dp[n] = dynamic(n - 1) + dynamic(n - 2)
  return dp[n]
}

fun main() {

  with (System.`in`.bufferedReader()) {
    val a = readLine()
    val b = readLine()

    println(a)
    println(b)

    val ad = ArrayDeque<Int>()
    val stack = Stack<Int>()
    val que : Queue<Int> = LinkedList()
    val arr = arrayOf<Int>(1,2,3)
    val ar = Array(3, {IntArray(3)})
    val hs : MutableSet<Int> = HashSet()
    val hm : MutableMap<Int, MutableMap<Int, Int>> = mutableMapOf()


  }

}