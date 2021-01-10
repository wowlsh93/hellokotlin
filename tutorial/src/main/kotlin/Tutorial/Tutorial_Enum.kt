package Tutorial

import java.util.*
import java.util.function.Function
//
//enum class TableStatus(val table1Value: String, val isTable2Value: Boolean) {
//    Y("1", true),
//    N("0", false);
//}
//
////////////////////////////////
//
//enum class CalculatorType(private val expression: (Long) -> Long ){
//    CALC_A({ value -> value }),
//    CALC_B({ value -> value * 10 }),
//    CALC_C({ value -> value * 3 });
//
//    fun calculation(value: Long): Long {
//        return expression(value)
//    }
//}
//
//fun main(arr : Array<String>) {
//    val ee : TableStatus = TableStatus.N
//    println(ee.table1Value)
//    println(ee.isTable2Value)
//
//    val ee2 : CalculatorType = CalculatorType.CALC_B;
//
//    println(ee2.calculation(10))
//
//}

///////////////////////////////////
//
//enum class PayType(val title: String) {
//    ACCOUNT_TRANSFER("AT"),
//    REMITTANCE("REM"),
//    ON_SITE_PAYMENT("ONSITE"),
//    TOSS("TOSS"),
//    CARD("CARD");
//}
//
//enum class PayGroupAdvanced(val title: String, private val payList: List<PayType?>) {
//    CASH("CASH", Arrays.asList(PayType.ACCOUNT_TRANSFER, PayType.REMITTANCE, PayType.ON_SITE_PAYMENT)),
//    CARD("CARD", Arrays.asList(PayType.TOSS, PayType.CARD)),
//    EMPTY("NO", Collections.EMPTY_LIST);
//
//    fun hasPayCode(paytype: PayType): Boolean {
//        return payList.stream()
//            .anyMatch { pay: PayType? -> pay == paytype }
//    }
//
//    companion object {
//        fun findByPayType(paytype: PayType): PayGroupAdvanced {
//            return Arrays.stream(values())
//                .filter { payGroup: PayGroupAdvanced -> payGroup.hasPayCode(paytype) }
//                .findAny()
//                .orElse(EMPTY)
//        }
//    }
//}