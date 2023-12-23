
import kotlin.reflect.KProperty

//Delegating the boolean preference saving option
class BooleanPreference(
  private val preferences: Lazy<SharedPreferences>,
  private val name: String,
  private val defaultValue: Boolean
) : ReadWriteProperty<Any, Boolean> {

  @WorkerThread
  override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
    return preferences.value.getBoolean(name, defaultValue)
  }
  override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
    preferences.value.edit().putBoolean(name, value).apply()
  }

}

fun main() {

  val e = Example()
  println(e.p)

  e.p = MySQL("BYE")

  println(e.p.getAddress())

}