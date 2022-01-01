//package concurrency
//
//val basket = Channel<String>()
//
//launch { // coroutine1
//    val fruits = listOf("Apple", "Orange")
//    for (fruit in fruits) {
//        println("coroutine1: Sending $fruit")
//        basket.send(fruit)
//    }
//}
//
//launch { // coroutine2
//    repeat(2) {
//        delay(100)
//        println("coroutine2: Received ${basket.receive()}")
//    }
//}
//
////////////
//
//val basket = Channel<String>(1)
//
//launch { // coroutine1
//    val fruits = listOf("Apple", "Orange", "Banana")
//    for (fruit in fruits) {
//        println("coroutine1: Sending $fruit")
//        basket.send(fruit)
//    }
//}
//
//launch { // coroutine2
//    repeat(3) {
//        delay(100)
//        println("coroutine2: Received ${basket.receive()}")
//    }
//}
//
///////////////////////////
//
//
//val channel = Channel<Int>(UNLIMITED)
//
//launch { // coroutine1
//    repeat(100) {
//        println("coroutine1: Sending $it")
//        channel.send(it)
//    }
//}
//
//launch { // coroutine2
//    repeat(100) {
//        println("coroutine2: Received ${channel.receive()}")
//    }
//}
//
///////////////
//
//val basket = Channel<String>(1)
//
//launch { // coroutine1
//    val fruits = listOf("Apple", "Orange", "Banana")
//    for (fruit in fruits) {
//        println("coroutine1: Sending $fruit")
//        basket.send(fruit)
//    }
//}
//
//launch { // coroutine2
//    repeat(3) {
//        delay(100)
//        println("coroutine2: Received ${basket.receive()}")
//    }
//}
//
//////////////////////
//
//fun CoroutineScope.produceFruits(): ReceiveChannel<String> = produce {
//    val fruits = listOf("Apple", "Orange", "Apple")
//    for (fruit in fruits) send(fruit)
//}
//
//val fruitChannel = produceFruits()
//for (fruit in fruitChannel) {
//    println(fruit)
//}
//
//////////////////////////
//
//fun CoroutineScope.producePizzaOrders(): ReceiveChannel<String> = produce {
//    var x = 1
//    while (true) {
//        send("Pizza Order No. ${x++}")
//        delay(100)
//    }
//}
//
//fun CoroutineScope.pizzaOrderProcessor(id: Int, orders: ReceiveChannel<String>) = launch {
//    for (order in orders) {
//        println("Processor #$id is processing $order")
//    }
//}
//
//fun main() = runBlocking {
//    val pizzaOrders = producePizzaOrders()
//    repeat(3) {
//        pizzaOrderProcessor(it + 1, pizzaOrders)
//    }
//
//    delay(1000)
//    pizzaOrders.cancel()
//}
//
//
//////////////////////////
//
//suspend fun fetchYoutubeVideos(channel: SendChannel<String>) {
//    val videos = listOf("cat video", "food video")
//    for (video in videos) {
//        delay(100)
//        channel.send(video)
//    }
//}
//suspend fun fetchTweets(channel: SendChannel<String>) {
//    val tweets = listOf("tweet: Earth is round", "tweet: Coroutines and channels are cool")
//    for (tweet in tweets) {
//        delay(100)
//        channel.send(tweet)
//    }
//}
//
//fun main() = runBlocking {
//    val aggregate = Channel<String>()
//    launch { fetchYoutubeVideos(aggregate) }
//    launch { fetchTweets(aggregate) }
//
//    repeat(4) {
//        println(aggregate.receive())
//    }
//
//    coroutineContext.cancelChildren()
//}
//
///////////////
//
//fun CoroutineScope.baking(orders: ReceiveChannel<PizzaOrder>) = produce {
//    for (order in orders) {
//        delay(200)
//        println("Baking ${order.orderNumber}")
//        send(order.copy(orderStatus = BAKED))
//    }
//}
//
//fun CoroutineScope.topping(orders: ReceiveChannel<PizzaOrder>) = produce {
//    for (order in orders) {
//        delay(50)
//        println("Topping ${order.orderNumber}")
//        send(order.copy(orderStatus = TOPPED))
//    }
//}
//
//fun CoroutineScope.produceOrders(count: Int) = produce {
//    repeat(count) {
//        delay(50)
//        send(PizzaOrder(orderNumber = it + 1))
//    }
//}
//
//fun main() = runBlocking {
//    val orders = produceOrders(3)
//
//    val readyOrders = topping(baking(orders))
//
//    for (order in readyOrders) {
//        println("Serving ${order.orderNumber}")
//    }
//
//    delay(3000)
//    coroutineContext.cancelChildren()
//}
//
/////////////////
//
//fun stockPrice(stock: String): Double {
//    log("Fetching stock price of $stock")
//    return Random.nextDouble(2.0, 3.0)
//}
//
//fun main() = runBlocking {
//    val tickerChannel = ticker(Duration.ofSeconds(5).toMillis())
//
//    repeat(3) {
//        tickerChannel.receive()
//        log(stockPrice("TESLA"))
//    }
//
//    delay(Duration.ofSeconds(11).toMillis())
//    tickerChannel.cancel()
//}