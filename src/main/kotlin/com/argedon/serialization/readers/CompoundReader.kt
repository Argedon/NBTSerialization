package com.argedon.serialization.readers

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.modules.SerializersModule
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import com.argedon.serialization.NBTReader

@OptIn(ExperimentalSerializationApi::class)
class CompoundReader(private val nbt: NBTCompound, override val serializersModule: SerializersModule) : NBTReader() {
    private var elementName = ""
    private var index = -1

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        if (++index == descriptor.elementsCount) return CompositeDecoder.DECODE_DONE
        elementName = descriptor.getElementName(index)

        if (descriptor.isElementOptional(index) && !nbt.containsKey(elementName)) {
            return decodeElementIndex(descriptor)
        }

        return index
    }

    override fun decodeInt(): Int = nbt.getInt(elementName)!!
    override fun decodeLong(): Long = nbt.getLong(elementName)!!
    override fun decodeFloat(): Float = nbt.getFloat(elementName)!!
    override fun decodeDouble(): Double = nbt.getDouble(elementName)!!
    override fun decodeByte(): Byte = nbt.getByte(elementName)!!
    override fun decodeShort(): Short = nbt.getShort(elementName)!!
    override fun decodeBoolean(): Boolean = nbt.getBoolean(elementName)!!
    override fun decodeString(): String = nbt.getString(elementName)!!
    override fun decodeChar(): Char = nbt.getString(elementName)!![0]
    override fun decodeValue(): Any = nbt[elementName]!!
    override fun decodeEnum(enumDescriptor: SerialDescriptor): Int = enumDescriptor.getElementIndex(nbt.getString(elementName)
        ?: throw RuntimeException("Cannot decode enum: $enumDescriptor  $elementName"))
    override fun decodeNotNullMark(): Boolean = nbt.containsKey(elementName)
    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder = beginStructure(descriptor, nbt[elementName])
}