/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package exception

sealed class Try<out T> {
    abstract fun get(): T
    abstract fun getOrElse(default: @UnsafeVariance T): T
    abstract fun orElse(default: Try<@UnsafeVariance T>): Try<T>
}

data class Success<out T>(val value: T) : Try<T>() {
    override fun getOrElse(default: @UnsafeVariance T): T = value
    override fun get() = value
    override fun orElse(default: Try<@UnsafeVariance T>): Try<T> = this
}

data class Failure<out T>(val e: Throwable) : Try<T>() {
    override fun getOrElse(default: @UnsafeVariance T): T = default
    override fun get(): T = throw e
    override fun orElse(default: Try<@UnsafeVariance T>): Try<T> = default
}