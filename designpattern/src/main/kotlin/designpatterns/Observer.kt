//package designpatterns
//
//
//interface BidObserver {
//    fun updateBid(oldBid: Int, newBid: Int)
//}
//
//open class Bidder(val number: Int = 0) : BidObserver {
//    override fun updateBid(oldBid: Int, newBid: Int) {
//        println("Bidder${number} : oldBid is $oldBid, newBid is $newBid")
//    }
//}
//
//
//interface AuctioneerSubject {
//    fun register(vararg bidObservers: BidObserver)
//    fun notifyNewBid(newBid: Int)
//}
//
//class Auctioneer : AuctioneerSubject {
//    private var bid: Int = 0
//    private val bidders: MutableList<BidObserver> = mutableListOf()
//
//    override fun register(vararg bidObservers: BidObserver) {
//        bidders.addAll(bidObservers)
//    }
//
//    override fun notifyNewBid(newBid: Int) {
//        val oldBid = bid
//        bid = newBid
//        bidders.forEach { it.updateBid(oldBid, newBid) }
//    }
//}
//
//fun main() {
//    val auctioneer = Auctioneer()
//        .apply {
//            register(Bidder(1))
//            register(Bidder(2))
//        }
//
//    auctioneer.notifyNewBid(1)
//    auctioneer.notifyNewBid(2)
//    auctioneer.notifyNewBid(3)
//}