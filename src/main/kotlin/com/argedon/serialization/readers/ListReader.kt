package com.argedon.serialization.readers

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.modules.SerializersModule
import org.jglrxavpok.hephaistos.nbt.NBTList
import com.argedon.serialization.NBTReader

@ExperimentalSerializationApi
class ListReader(private val nbt: NBTList<*>, override val serializersModule: SerializersModule) : NBTReader() {
    private val size = nbt.size
    private var index = 0

    private fun nextValue(): Any = nbt[index++].value
    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder = beginStructure(descriptor, nbt[index++])
    override fun decodeElementIndex(descriptor: SerialDescriptor): Int = CompositeDecoder.DECODE_DONE
    override fun decodeInt(): Int = nextValue() as Int
    override fun decodeLong(): Long = nextValue() as Long
    override fun decodeFloat(): Float = nextValue() as Float
    override fun decodeDouble(): Double = nextValue() as Double
    override fun decodeByte(): Byte = nextValue() as Byte
    override fun decodeShort(): Short = nextValue() as Short
    override fun decodeBoolean(): Boolean = nextValue() as Boolean
    override fun decodeString(): String = nextValue() as String
    override fun decodeChar(): Char = (nextValue() as String)[0]
    override fun decodeValue(): Any = nextValue()
    override fun decodeCollectionSize(descriptor: SerialDescriptor): Int = size
    override fun decodeSequentially(): Boolean = true
}