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

import FileMetadata
import NodeType
import KeyValue
import java.io.EOFException
import java.io.RandomAccessFile
import java.io.File
import java.lang.Exception
import org.apache.avro.*

class FileManager private constructor(file: File, initialMetadata: FileMetadata? = null) {
    companion object {
        fun new(file: File): FileManager {
            val fileManager = FileManager(file, createFileMetadata())
            val rootPage = fileManager.allocate(NodeType.LeafNode, mutableListOf())
            fileManager.write(rootPage)
            return fileManager
        }

        fun load(file: File) = FileManager(file)
    }

    private val file = RandomAccessFile(file, "rws")
    private val metadata = initialMetadata ?: loadMetadata()
    private val buffer = ByteArray(MAX_PAGE_SIZE)

    private var nextFreePageId: Int? by metadata

    fun allocate(nodeType: NodeType, initialRecords: MutableList<KeyValue>): Page {
        val freePageId = nextFreePageId ?: throw Exception() // TODO
        val freePage = read(freePageId)
        nextFreePageId = if (freePage == null) {
            freePageId + 1
        } else {
            freePage.nextId ?: throw Exception() // TODO
        }
        writeMetadata()
        return Page.new(freePageId, nodeType, initialRecords)
    }

    fun read(id: Int): Page? {
        return try {
            seek(id)
            file.readFully(buffer)
            Page.load(buffer.toByteBuffer())
        } catch (e: EOFException) {
            null
        }
    }

    fun write(page: Page) {
        seek(page.id)
        page.dump().toByteArray(buffer)
        file.write(buffer)
    }

    private fun loadMetadata(): FileMetadata {
        val metadataBuffer = ByteArray(METADATA_SIZE)
        file.seek(0)
        file.readFully(metadataBuffer)
        return FileMetadata.fromByteBuffer(metadataBuffer.toByteBuffer())
    }

    private fun writeMetadata() {
        file.seek(0)
        val byteBuffer = metadata.toByteBuffer()
        if (byteBuffer.limit() > METADATA_SIZE) throw Exception() // TODO
        file.write(byteBuffer.toByteArray())
    }

    private fun seek(id: Int) {
        val pos = id * MAX_PAGE_SIZE + METADATA_SIZE
        file.seek(pos.toLong())
    }
}