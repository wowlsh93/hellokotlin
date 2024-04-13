package Tutorial


import java.math.BigDecimal
import java.math.RoundingMode


class BigDecimalUtils {

  companion object {

    const val SCALE = 3
    val BANKERS_ROUNDING_MODE = RoundingMode.HALF_EVEN
  }
}

operator fun BigDecimal.div(other: BigDecimal): BigDecimal =
  this.divide(other, BigDecimalUtils.SCALE, BigDecimalUtils.BANKERS_ROUNDING_MODE)

operator fun BigDecimal.times(other: BigDecimal): BigDecimal =
  this.multiply(other).setScale(BigDecimalUtils.SCALE)

fun main() {
  val number1 = BigDecimal.valueOf(250.125);
  val number2 = BigDecimal.valueOf(150.352);

  println(number1 / number2)

  println(number1 * number2)
}