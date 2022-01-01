/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package reflection

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll

internal class ClassCacheTest {

    val cache: ClassCache = ClassCache(clazz = Baby::class, name = "dojun", age = 5)

//    @BeforeAll
//    fun setup(){
//        cache = ClassCache(clazz = Baby::class, name = "dojun", age = 5)
//    }

    @Test
    fun hasAnnotion() {
        Assertions.assertTrue(cache.hasAnnotion(Convert::class))
    }
}