//package Delegate
//
//
////https://www.baeldung.com/kotlin/delegated-properties
//
//import kotlin.properties.ReadWriteProperty
//import kotlin.reflect.KProperty
//
//class DatabaseDelegate<in R, T>(val readQuery: String, val writeQuery: String, val id: Any) : ReadWriteProperty<R, T> {
//  override fun getValue(thisRef: R, property: KProperty<*>): T {
//    return queryForValue(readQuery, mapOf("id" to id))
//  }
//
//  override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
//    update(writeQuery, mapOf("id" to id, "value" to value))
//  }
//}
//
//class DatabaseUser(userId: String) {
//  var name: String by DatabaseDelegate(
//    "SELECT name FROM users WHERE userId = :id",
//    "UPDATE users SET name = :value WHERE userId = :id",
//    userId)
//  var email: String by DatabaseDelegate(
//    "SELECT email FROM users WHERE userId = :id",
//    "UPDATE users SET email = :value WHERE userId = :id",
//    userId)
//}