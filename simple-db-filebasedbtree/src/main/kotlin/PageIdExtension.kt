/**
 * Copyright 2019 Ryosuke IWANAGA <me@riywo.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.riywo.ninja.bptree

import PageId
import java.nio.ByteBuffer

fun PageId.getInt(): Int? {
    val value = bytes().toByteBuffer().int
    return if (value == AVRO_PAGE_ID_NULL_VALUE) null else value
}

fun PageId.putInt(value: Int?) {
    val byteBuffer = ByteBuffer.allocate(AVRO_PAGE_ID_BYTES)
    byteBuffer.putInt(value ?: AVRO_PAGE_ID_NULL_VALUE)
    bytes(byteBuffer.toByteArray())
}

fun createPageId(id: Int? = null): PageId {
    val pageId = PageId()
    pageId.putInt(id)
    return pageId
}
