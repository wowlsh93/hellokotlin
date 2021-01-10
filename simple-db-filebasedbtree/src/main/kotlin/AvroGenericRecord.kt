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

import org.apache.avro.Schema
import org.apache.avro.generic.*
import org.apache.avro.io.*
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

open class AvroGenericRecord(private val io: IO) : GenericData.Record(io.schema) {
    fun load(byteBuffer: ByteBuffer) {
        io.decode(this, byteBuffer)
    }

    fun load(json: String) {
        io.decodeJson(this, json)
    }

    fun toByteBuffer(): ByteBuffer {
        return io.encode(this)
    }

    fun toJson(): String {
        return io.encodeJson(this)
    }

    class IO(val schema: Schema) {
        init {
            if (schema.type != Schema.Type.RECORD) {
                throw IllegalArgumentException("Schema type must be record: $schema")
            }
        }

        private val writer = GenericDatumWriter<GenericRecord>(schema)
        private val reader = GenericDatumReader<GenericRecord>(schema)

        private var binaryEncoder: BinaryEncoder? = null
        private var binaryDecoder: BinaryDecoder? = null

        fun encode(record: AvroGenericRecord): ByteBuffer {
            val output = ByteArrayOutputStream()
            binaryEncoder = EncoderFactory.get().binaryEncoder(output, binaryEncoder)
            writer.write(record, binaryEncoder)
            binaryEncoder?.flush()
            return output.toByteArray().toByteBuffer()
        }

        fun decode(record: AvroGenericRecord, byteBuffer: ByteBuffer) {
            binaryDecoder = DecoderFactory.get().binaryDecoder(byteBuffer.toByteArray(), binaryDecoder)
            reader.read(record, binaryDecoder)
        }

        fun encodeJson(record: AvroGenericRecord): String {
            val output = ByteArrayOutputStream()
            val encoder = EncoderFactory.get().jsonEncoder(schema, output)
            writer.write(record, encoder)
            encoder.flush()
            return output.toString()
        }

        fun decodeJson(record: AvroGenericRecord, input: String) {
            val decoder = DecoderFactory.get().jsonDecoder(schema, input)
            reader.read(record, decoder)
        }

        fun compare(a: ByteArray, b: ByteArray): Int {
            return BinaryData.compare(a, 0 , b, 0, schema)
        }

        fun compare(a: ByteBuffer, b: ByteBuffer): Int {
            return compare(a.toByteArray(), b.toByteArray())
        }

        fun compare(a: AvroGenericRecord, b: AvroGenericRecord): Int {
            return compare(a.toByteBuffer(), b.toByteBuffer())
        }
    }
}