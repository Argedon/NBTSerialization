package com.argedon.readers

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.modules.SerializersModule
import org.jglrxavpok.hephaistos.nbt.*
import com.argedon.NBTReader

@ExperimentalSerializationApi
class MapReader(nbt: NBTCompound, override val serializersModule: SerializersModule) : NBTReader() {
    private val size = nbt.size * 2
    private val keys = nbt.keys.toList()
    private val values = nbt.values.toList()

    private var state = MapState.KEY
    private var index = -1
    private var keyIndex = -1
    private var valueIndex = -1

    init {
        if (keys.size != values.size) {
            throw RuntimeException("Keys size and Values size are not the same!")
        }
    }

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        if (++index == size) return CompositeDecoder.DECODE_DONE

        if (index % 2 == 0) {
            state = MapState.KEY
            keyIndex++
        } else {
            state = MapState.VALUE
            valueIndex++
        }

        return index
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder = beginStructure(descriptor, values[valueIndex] as? NBT)

    override fun decodeString(): String = when (state) {
        MapState.KEY -> keys[keyIndex]
        MapState.VALUE -> (values[valueIndex] as NBTString).value
    }

    override fun decodeInt(): Int = when (state) {
        MapState.KEY -> keys[keyIndex].toInt()
        MapState.VALUE -> (values[valueIndex] as NBTInt).value
    }

    override fun decodeLong(): Long = when (state) {
        MapState.KEY -> keys[keyIndex].toLong()
        MapState.VALUE -> (values[valueIndex] as NBTLong).value
    }

    override fun decodeFloat(): Float = when (state) {
        MapState.KEY -> keys[keyIndex].toFloat()
        MapState.VALUE -> (values[valueIndex] as NBTFloat).value
    }

    override fun decodeDouble(): Double = when (state) {
        MapState.KEY -> keys[keyIndex].toDouble()
        MapState.VALUE -> (values[valueIndex] as NBTDouble).value
    }

    override fun decodeBoolean(): Boolean = when (state) {
        MapState.KEY -> keys[keyIndex].toBoolean()
        MapState.VALUE -> (values[valueIndex] as NBTByte) == NBT.TRUE
    }

    override fun decodeByte(): Byte = when (state) {
        MapState.KEY -> keys[keyIndex].toByte()
        MapState.VALUE -> (values[valueIndex] as NBTByte).value
    }

    override fun decodeShort(): Short = when (state) {
        MapState.KEY -> keys[keyIndex].toShort()
        MapState.VALUE -> (values[valueIndex] as NBTShort).value
    }

    override fun decodeChar(): Char = when (state) {
        MapState.KEY -> keys[keyIndex][0]
        MapState.VALUE -> (values[valueIndex] as NBTString).value[0]
    }
}

private enum class MapState {
    KEY, VALUE
}