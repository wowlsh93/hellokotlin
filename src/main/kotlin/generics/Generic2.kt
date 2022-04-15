package generics

data class Data<T>(val name: String , val value: T)

class Repository() {

  private var _spec : Any? = null
  private val datas = ArrayList<Data<*>>()

  fun <T> setSpec(spec: T){
    _spec = spec
  }

  fun <T> getSpec(): T{
    return _spec as T
  }

  fun <T> setData(data: Data<T>) {
    datas.add(data)
  }

  fun <T> getData(name: String) : T {
    val data = datas.find {
      it.name == name
    }
    return data?.value as T
  }

}

fun main(arr : Array<String>){

  val repo = Repository()
  repo.setSpec(10)
  println(repo.getSpec<Int>().toString())

  repo.setData(Data("1", "name"))
  repo.setData(Data("2", 3))

  val result1 = repo.getData<String>("1")
  println(result1)
  val result2 = repo.getData<String>("2")
  println(result2)

}