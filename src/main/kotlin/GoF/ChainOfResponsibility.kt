//package designpatterns
//
//////////////// take 1 ////////////
//
//interface HeadersChain {
//    fun addHeader(inputHeader: String): String
//}
//
//class AuthenticationHeader(val token: String?, var next: HeadersChain? = null) : HeadersChain {
//
//    override fun addHeader(inputHeader: String): String {
//        token ?: throw IllegalStateException("Token should be not null")
//        return inputHeader + "Authorization: Bearer $token\n"
//            .let { next?.addHeader(it) ?: it }
//    }
//}
//
//class BodyPayload(val body: String, var next: HeadersChain? = null) : HeadersChain {
//
//    override fun addHeader(inputHeader: String): String =
//        inputHeader + "$body"
//            .let { next?.addHeader(it) ?: it }
//}
//
//class ContentTypeHeader(val contentType: String, var next: HeadersChain? = null) : HeadersChain {
//
//    override fun addHeader(inputHeader: String): String =
//        inputHeader + "ContentType: $contentType\n"
//            .let { next?.addHeader(it) ?: it }
//}
//
//
//class ChainOfResponsibilityTest {
//
//    @Test
//    fun `Chain Of Responsibility`() {
//        //create chain elements
//        val authenticationHeader = AuthenticationHeader("123456")
//        val contentTypeHeader = ContentTypeHeader("json")
//        val messageBody = BodyPayload("Body:\n{\n\"username\"=\"dbacinski\"\n}")
//
//        //construct chain
//        authenticationHeader.next = contentTypeHeader
//        contentTypeHeader.next = messageBody
//
//        //execute chain
//        val messageWithAuthentication =
//            authenticationHeader.addHeader("Headers with Authentication:\n")
//
//        println(messageWithAuthentication)
//
//        val messageWithoutAuth =
//            contentTypeHeader.addHeader("Headers:\n")
//        println(messageWithoutAuth)
//
//        assertThat(messageWithAuthentication).isEqualTo(
//            """
//                Headers with Authentication:
//                Authorization: Bearer 123456
//                ContentType: json
//                Body:
//                {
//                "username"="dbacinski"
//                }
//            """.trimIndent()
//        )
//
//        assertThat(messageWithoutAuth).isEqualTo(
//            """
//                Headers:
//                ContentType: json
//                Body:
//                {
//                "username"="dbacinski"
//                }
//            """.trimIndent()
//        )
//    }
//}
//
//
///////////////// take 2 ////////////
//data class CashRequest(val amount: Int, val notes: List<Pair<Int, Int>> = listOf())
//
//typealias CashRequestHandler = (CashRequest) -> CashRequest
//
//fun buildCashRequestHandlerForNote(note: Int): CashRequestHandler = { cashRequest ->
//    if (cashRequest.amount > note) CashRequest(
//        cashRequest.amount % note,
//        cashRequest.notes + Pair(note, cashRequest.amount / note)
//    )
//    else cashRequest
//}
//
//
//val cashRequestHandlerChain: CashRequestHandler = listOf(100, 50, 20, 10, 5)
//    .map(::buildCashRequestHandlerForNote)
//    .reduce { chain, handler -> { cashRequest -> handler(chain(cashRequest)) } }