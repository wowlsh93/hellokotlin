
package MultiThreadPattern.future

import java.util.*
import java.util.concurrent.*
import kotlin.concurrent.schedule


data class MyException(val msg: String): Exception(msg)

class CompletableFutureTest {
    fun test(){

        val schedule: ScheduledExecutorService  = Executors.newScheduledThreadPool(1)
        schedule.schedule({println("schedule-executor")}, 3, TimeUnit.SECONDS)

        Timer().schedule(3000, 3000){println("schdule-time")}

        //val executors
        val executor = Executors.newSingleThreadExecutor()
        executor.submit {
            println("executor submit")
        }

        val future: Future<String> = CompletableFuture.supplyAsync {
            println("hi")
            Thread.sleep(5000)
            "MultiThreadPattern.future result"
            //throw MyException("myexec")

        }

        println(future.get())



        Thread.sleep(10000)
    }
}