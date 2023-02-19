package com.argedon

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.modules.SerializersModule
import org.jglrxavpok.hephaistos.nbt.*
import org.jglrxavpok.hephaistos.nbt.mutable.MutableNBTCompound
import com.argedon.utils.NBTUtils
import com.argedon.writers.CompoundWriter
import com.argedon.writers.ListWriter
import com.argedon.writers.MapWriter

class NBTEncoder(
    override val serializersModule: SerializersModule,
    nbtConfiguration: NbtConfiguration
) : NBTWriter(nbtConfiguration) {
    private var nbt: NBT? = null
    private var rootCompound = MutableNBTCompound()

    override fun getNBT(): NBT {
        return nbt ?: rootCompound.toCompound()
    }

    override fun setNBT(key: String, nbt: NBT) {
        if (key.isNotEmpty()) {
            rootCompound[key] = nbt
        } else {
            this.nbt = nbt
        }
    }
}

@OptIn(ExperimentalSerializationApi::class)
abstract class NBTWriter(
    val nbtConfiguration: NbtConfiguration
) : AbstractEncoder() {
    protected var key = ""

    abstract fun getNBT(): NBT
    abstract fun setNBT(key: String, nbt: NBT)

    @OptIn(ExperimentalSerializationApi::class)
    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        return when (descriptor.kind) {
            StructureKind.CLASS, PolymorphicKind.OPEN, PolymorphicKind.SEALED -> CompoundWriter(key, this, serializersModule)
            StructureKind.MAP -> MapWriter(key, this, serializersModule)
            StructureKind.LIST -> ListWriter(key, this, NBTUtils.getNBTType(descriptor.getElementDescriptor(0).kind), serializersModule)
            else -> super.beginStructure(descriptor)
        }
    }

    override fun shouldEncodeElementDefault(descriptor: SerialDescriptor, index: Int): Boolean = nbtConfiguration.encodeDefaults
    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) = setNBT(key, NBTString(enumDescriptor.getElementName(index)))
    override fun encodeByte(value: Byte) = setNBT(key, NBTByte(value))
    override fun encodeShort(value: Short) = setNBT(key, NBTShort(value))
    override fun encodeInt(value: Int) = setNBT(key, NBTInt(value))
    override fun encodeLong(value: Long) = setNBT(key, NBTLong(value))
    override fun encodeFloat(value: Float) = setNBT(key, NBTFloat(value))
    override fun encodeDouble(value: Double) = setNBT(key, NBTDouble(value))
    override fun encodeChar(value: Char) = setNBT(key, NBTString(value.toString()))
    override fun encodeString(value: String) = setNBT(key, NBTString(value))
    override fun encodeBoolean(value: Boolean) = setNBT(key, if (value) NBT.TRUE else NBT.FALSE)
    override fun encodeNull() {}
}