/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package library

import java.util.*

import com.fasterxml.jackson.annotation.JsonIgnore
import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface WithLogger {
    val logger: Logger
        @JsonIgnore
        get() = LoggerFactory.getLogger(javaClass)!!
}

abstract class AbstractBookManager : WithLogger{
    val endpoint = "http://localhost:8545"

    fun uuid(): String {
        return UUID.randomUUID().toString().substring(0, 8)
    }
}

