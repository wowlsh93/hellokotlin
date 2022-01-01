package Tutorial

import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {

    val current = LocalDateTime.now()

    println("Current Date and Time is: $current")

    ////////////


    val current2 = LocalDateTime.now()

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    val formatted = current2.format(formatter)

    println("Current Date and Time is: $formatted")

    ////////////

    val current3 = LocalDateTime.now()

    val formatter3 = DateTimeFormatter.BASIC_ISO_DATE
    val formatted3 = current3.format(formatter3)

    println("Current Date is: $formatted3")

    ////////////

    val current4 = LocalDateTime.now()

    val formatter4 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
    val formatted4 = current4.format(formatter4)

    println("Current Date is: $formatted4")


    ///////////////  unix
    val startTime = System.currentTimeMillis()
    println(startTime)

    ////// stop watch
    val elapsed: Long = measureTimeMillis {
        Thread.sleep(100)
    }
    println(elapsed)


    ////////////////////// unix to date time

    val sdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val date = java.util.Date(1532358895 * 1000)
    sdf.format(date)

    println(date)


}