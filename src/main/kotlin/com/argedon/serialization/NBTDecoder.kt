package com.argedon.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.modules.SerializersModule
import org.jglrxavpok.hephaistos.nbt.*
import com.argedon.serialization.readers.CompoundReader
import com.argedon.serialization.readers.ListReader
import com.argedon.serialization.readers.MapReader

class NBTDecoder(
    override val serializersModule: SerializersModule,
    private val nbt: NBT
) : NBTReader() {
    override fun decodeElementIndex(descriptor: SerialDescriptor): Int = CompositeDecoder.DECODE_DONE
    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder = beginStructure(descriptor, nbt)
    override fun decodeInt(): Int = (nbt as? NBTInt)?.value ?: 0
    override fun decodeLong(): Long = (nbt as? NBTLong)?.value ?: 0
    override fun decodeFloat(): Float = (nbt as? NBTFloat)?.value ?: 0F
    override fun decodeDouble(): Double = (nbt as? NBTDouble)?.value ?: 0.0
    override fun decodeByte(): Byte = (nbt as? NBTByte)?.value ?: 0
    override fun decodeChar(): Char = (nbt as? NBTString)?.value?.get(0) ?: ' '
    override fun decodeShort(): Short = (nbt as? NBTShort)?.value ?: 0
    override fun decodeString(): String = (nbt as? NBTString)?.value ?: ""
    override fun decodeBoolean(): Boolean = (nbt as? NBTByte) == NBT.TRUE
    override fun decodeValue(): Any = nbt.value
}

@OptIn(ExperimentalSerializationApi::class)
abstract class NBTReader : AbstractDecoder() {
    fun beginStructure(descriptor: SerialDescriptor, nbt: NBT?): CompositeDecoder = when (descriptor.kind) {
        StructureKind.CLASS, PolymorphicKind.OPEN, PolymorphicKind.SEALED -> CompoundReader(nbt as NBTCompound, serializersModule)
        StructureKind.LIST -> ListReader(nbt as NBTList<*>, serializersModule)
        StructureKind.MAP -> MapReader(nbt as NBTCompound, serializersModule)
        else -> throw RuntimeException("Error: beginStructure ${descriptor.kind} $nbt")
    }
}