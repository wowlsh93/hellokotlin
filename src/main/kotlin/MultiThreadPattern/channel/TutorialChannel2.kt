//package concurrency
//
//
//fun fetchMergeRequestsChannel(gitLabService: GitLabService, lastProductionSha: String): ReceiveChannel<MergeRequest> {
//    return produce {
//        var page = 1
//        while (true) {
//            gitLabService.delayedFetchMergeRequests(page++).forEach { send(it) }
//        }
//    }.takeWhile { it.commitSha != lastProductionSha }
//}
//
//fun main(args: Array<String>) {
//    val mrs = fetchMergeRequestsChannel(GitLabService(), "04d78f5c7cd51c52d0482d08224ff6a214da12c1")
//
//    runBlocking {
//        delay(10, TimeUnit.SECONDS)
//        mrs.consumeEach {
//            println("consuming $it")
//        }
//    }
//}