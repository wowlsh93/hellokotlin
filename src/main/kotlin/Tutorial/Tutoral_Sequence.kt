////https://winterbe.com/posts/2018/07/23/kotlin-sequence-tutorial/
//
//
//data class Person(val name: String, val age: Int)
//
//val persons = listOf(
//        Person("Peter", 16),
//        Person("Anna", 23),
//        Person("Anna", 28),
//        Person("Sonya", 39)
//)
//
//val names = persons
//        .asSequence()
//        .filter { it.age > 18 }
//        .map { it.name }
//        .distinct()
//        .sorted()
//        .toList()
//
//print(names)     // [Anna, Sonya]
//
/////////////
//val result = sequenceOf(1, 2, 3, 4, 5)
//        .filter { it % 2 == 1 }
//        .toList()
//
//print(result)     // [1, 3, 5]
//
//val result = generateSequence(0) { it + 1 }
//        .take(5)
//        .filter { it % 2 == 1 }
//        .toList()
//
//print(result)     // [1, 3, 5]
//
//val result = (1..6)
//        .asSequence()
//        .filter { it % 2 == 1 }
//        .toList()
//
//print(result)   // [1, 3, 5]
//
//val sequence = sequenceOf(1, 2, 3, 4, 5)
//        .filter { it % 2 == 1 }
//println(sequence.toList())      // [1, 3, 5]
//println(sequence.first())       // 1
//Processing order#
//
/////////////
//
//sequenceOf("A", "B", "C")
//.filter {
//    println("filter: $it")
//    true
//}
//
//
//sequenceOf("A", "B", "C")
//.filter {
//    println("filter: $it")
//    true
//}
//.forEach {
//    println("forEach: $it")
//}
//
//// filter:  A
//// forEach: A
//// filter:  B
//// forEach: B
//// filter:  C
//// forEach: C
//
//val result = sequenceOf("a", "b", "c")
//        .map {
//            println("map: $it")
//            it.toUpperCase()
//        }
//        .any {
//            println("any: $it")
//            it.startsWith("B")
//        }
//
//println(result)
//
//// map: A
//// any: A
//// map: B
//// any: B
//// true
//
//
//sequenceOf("a", "b", "c", "d")
//.map {
//    println("map: $it")
//    it.toUpperCase()
//}
//.filter {
//    println("filter: $it")
//    it.startsWith("a", ignoreCase = true)
//}
//.forEach {
//    println("forEach: $it")
//}
//
//// map:     a
//// filter:  A
//// forEach: A
//// map:     b
//// filter:  B
//// map:     c
//// filter:  C
//// map:     d
//// filter:  D
//
//sequenceOf("a", "b", "c", "d")
//.filter {
//    println("filter: $it")
//    it.startsWith("a", ignoreCase = true)
//}
//.map {
//    println("map: $it")
//    it.toUpperCase()
//}
//.forEach {
//    println("forEach: $it")
//}
//
//// filter:  a
//// map:     a
//// forEach: A
//// filter:  b
//// filter:  c
//// filter:  d
//
/////////////
//
//// variant 1
//val list1 = listOf(1, 2, 3, 4, 5)
//        .asSequence()
//        .filter { it % 2 == 1 }
//        .sortedDescending()
//        .map { it.toString() }
//        .toList()
//
//print(list1)   // ["5", "3", "1"]
//
//// variant 2
//val list2 = listOf(1, 2, 3, 4, 5)
//        .filter { it % 2 == 1 }
//        .sortedDescending()
//        .map { it.toString() }
//
//print(list2)   // ["5", "3", "1"]
//
/////////////
//
//fun measure(block: () -> Unit) {
//    val nanoTime = measureNanoTime(block)
//    val millis = TimeUnit.NANOSECONDS.toMillis(nanoTime)
//    print("$millis ms")
//}
//We first start with processing a huge list of 50.000.000 numbers which takes over 8s on my machine:
//
//val list = generateSequence(1) { it + 1 }
//        .take(50_000_000)
//        .toList()
//
//measure {
//    list
//            .filter { it % 3 == 0 }
//            .average()
//}
//
/////////////
//
//// 8644 ms
//
//The same operations using sequences is 10x faster due to lazy evaluation:
//
//val sequence = generateSequence(1) { it + 1 }
//        .take(50_000_000)
//
//measure {
//    sequence
//            .filter { it % 3 == 0 }
//            .average()
//}
//
//// 822 ms
//
//data class Person(val name: String, val age: Int)
//
//val persons = listOf(
//        Person("Peter", 16),
//        Person("Anna", 28),
//        Person("Anna", 23),
//        Person("Sonya", 39)
//)
//
//val result = persons
//        .asSequence()
//        .sortedBy { it.age }
//        .toList()
//
//print(result)   // [Person(name=Peter, age=16), Person(name=Anna, age=23), Person(name=Anna, age=28), Person(name=Sonya, age=39)]
//
//val result = persons
//        .asSequence()
//        .distinctBy { it.name }
//        .toList()
//
//print(result)   // [Person(name=Peter, age=16), Person(name=Anna, age=28), Person(name=Sonya, age=39)]
//
//val result = persons
//        .asSequence()
//        .maxBy { it.age }
//
//print(result)   // Person(name=Sonya, age=39)
//
//val result = persons
//        .asSequence()
//        .groupBy { it.name }
//
//print(result)   // {Peter=[Person(name=Peter, age=16)], Anna=[Person(name=Anna, age=28), Person(name=Anna, age=23)], Sonya=[Person(name=Sonya, age=39)]}
//
//val result = persons
//        .asSequence()
//        .associateBy { it.name }
//
//print(result)   // {Peter=Person(name=Peter, age=16), Anna=Person(name=Anna, age=23), Sonya=Person(name=Sonya, age=39)}
//
//val result = sequenceOf(1, 2, 3, 4, 5)
//        .filter { it % 2 == 1 }
//        .any { it % 2 == 0 }
//
//print(result)   // false
//
//val result = sequenceOf("a", "b", "c", "d")
//        .withIndex()
//        .filter { it.index % 2 == 0 }
//        .map { it.value }
//        .toList()
//
//print(result)   // [a, c]
//
//val result = sequenceOf("a", "b", "c")
//        .plus("d")
//        .minus("c")
//        .map { it.toUpperCase() }
//        .toList()
//
//print(result)   // [A, B, D]
//
//val result = sequenceOf(listOf(1, 2, 3), listOf(4, 5, 6))
//        .flatMap {
//            it.asSequence().filter { it % 2 == 1 }
//        }
//        .toList()
//
//print(result)   // [1, 3, 5]
//
//val result = persons
//        .asSequence()
//        .map { it.name }
//        .distinct()
//        .joinToString();
//
//print(result)   // "Peter, Anna, Sonya"
//
//
//fun <T> Sequence<T>.shuffle(): Sequence<T> {
//    return toMutableList()
//            .apply { shuffle() }
//            .asSequence()
//}
//
//val result = sequenceOf(1, 2, 3, 4, 5)
//        .shuffle()
//        .toList()
//
//print(result)   // [5, 3, 2, 4, 1]
//
//
//import {asSequence} from "sequency";
//
//const result = asSequence([1, 2, 3, 4, 5])
//.filter(it => it % 2 === 1)
//.sortedDescending()
//.toArray();
//
//console.log(result)   // [5, 3, 1]
//
