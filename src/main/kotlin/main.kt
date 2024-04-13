




interface Configuration {
  var parent : Configuration?
  val configs: MutableMap<String,String>
  fun setConfig(key: String, value: String)
  fun getConfig(key: String): String
}

enum class Config(name: String) {
  BASH("bash"),
  FILE("file"),
  CONSOLE("console")
}

class NoOpConfiguration(override var parent: Configuration? = null, override val configs: MutableMap<String, String> = mutableMapOf<String,String>()) : Configuration {
  override fun setConfig(key: String, value: String) {
    TODO("Not yet implemented")
  }

  override fun getConfig(key: String): String {
    TODO("Not yet implemented")
  }
}
class BashConfiguration(override val configs: MutableMap<String, String>, override var parent: Configuration? = null): Configuration {
  override fun setConfig(key: String, value: String) {
    configs[key] = value
  }

  override fun getConfig(key: String): String {
    return configs[key]!!
  }

  override fun toString(): String {
    return Config.BASH.name
  }
}
class FileConfiguration(override val configs: MutableMap<String, String>, override var parent: Configuration? = null) : Configuration {
  override fun setConfig(key: String, value: String) {
    configs[key] = value
  }

  override fun getConfig(key: String): String {
    return configs[key]!!
  }

  override fun toString(): String {
    return Config.FILE.name
  }
}

class ConsoleConfiguration(override val configs: MutableMap<String, String>, override var parent: Configuration? = null) : Configuration {
  override fun setConfig(key: String, value: String) {
    configs[key] = value
  }
  override fun getConfig(key: String): String {
    return configs[key]!!
  }

  override fun toString(): String {
    return Config.CONSOLE.name
  }
}


fun addConfis(arr: Array<Configuration>): Configuration{
  val par = arr.dropLast(0).takeIf { it.isNotEmpty()}?.let { addConfis(it.toTypedArray()) } ?: NoOpConfiguration()
  val config = arr.last()
  config.parent = par
  return config
}
fun main() {

  val fileConfig = FileConfiguration(mutableMapOf())
  fileConfig.setConfig("key1","key1 value")
  val bashConfig = BashConfiguration(mutableMapOf())

  val consoleConfig = ConsoleConfiguration(mutableMapOf())

  val configChain = addConfis(arrayOf(fileConfig, bashConfig, consoleConfig))

  val value = configChain.getConfig("key1")

  println(value)
}