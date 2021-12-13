//https://kimchanjung.github.io/design-pattern/2020/05/22/flyweight-pattern/ 발췌

package designpatterns

interface Rider {
    var center:String
    fun delivery(): String
}

class FullTimeRider(override var center: String) : Rider {
    override fun delivery() = "$center 오토바이배달"
}

class PartTimeRider(override var center: String) : Rider {
    override fun delivery() = "$center 자전거배달"
}
class RiderFactory {
    // 인스턴스 생성시 if문을 제거 하기 위해서 map활용하여 관리함
    private val riderClasses =
        mapOf("fulltime" to FullTimeRider::class, "parttime" to PartTimeRider::class)
    // 생성된 인스턴스가 관리됨
    private val riderMap = mutableMapOf<String, Rider>()

    // 강남지점-정규직라이더가 이미 있으면 리턴
    // 없으면 riderClasses에서 클래스원본을 가져와 리플렉션을 사용하여 새 인스턴스를 생성
    // 이 로직은 단순히 if 처리를 제거하기 위함임(패턴과는 관계 없음)
    fun getRider(center: String, type: String) =
        riderMap.computeIfAbsent(center + type) {
            riderClasses[type]!!.primaryConstructor!!.call(center)
        }
}

/////////////////////////////////////////////////////////
class RiderService {
    private val riderFactory = RiderFactory()
    fun delivery(center: String, type: String) = riderFactory
        .getRider(center, type)
        .delivery()
}

val riderService = RiderService()
riderService.delivery("강남","fulltime")