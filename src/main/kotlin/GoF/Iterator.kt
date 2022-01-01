//package designpatterns
//
//typealias Fruit = String
//
//
//class Fruits(vararg val fruits: Fruit)
//operator fun Fruits.iterator(): Iterator<Fruit> = FruitsIterator(this)
//
//
//class FruitsIterator(container: Fruits) : Iterator<Fruit> {
//    private val iterator = container.fruits.iterator()
//
//    override fun hasNext(): Boolean = iterator.hasNext()
//    override fun next(): Fruit = iterator.next()
//
//}
//
//
//
///////////
//
//
//
//open class Game : Iterator<Game> {
//
//    protected val gameList = mutableListOf<Game>()
//    protected var currentPos = 0
//
//    fun addGame(game : Game): Game {
//        gameList.add(game)
//        return this
//    }
//
//    override fun hasNext(): Boolean {
//        if ((currentPos + 1) == gameList.size) return false
//        return true
//    }
//
//    override fun next(): Game {
//        return gameList[currentPos++]
//    }
//
//    open fun play(): Unit{
//
//    }
//}
//
//class HwatoGame : Game() {
//
//    override fun play() {
//        println("Hwato play")
//    }
//
//}
//
//
//class BoardGame : Game() {
//
//    override fun play() {
//        println("Board play")
//    }
//
//}
//
//
//fun main(args: Array<String>) {
//    println("Hello World!")
//
//    val game = Game().addGame(HwatoGame()).addGame(BoardGame())
//
//    game.forEach { it.play() }
//
//
//}