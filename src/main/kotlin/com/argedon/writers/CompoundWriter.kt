package com.argedon.writers

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.modules.SerializersModule
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.mutable.MutableNBTCompound
import com.argedon.NBTWriter

class CompoundWriter(
    private val rootName: String,
    private val parent: NBTWriter,
    override val serializersModule: SerializersModule
) : NBTWriter(parent.nbtConfiguration) {
    private val compound = MutableNBTCompound()

    override fun getNBT(): NBT = compound.toCompound()

    override fun endStructure(descriptor: SerialDescriptor) {
        parent.setNBT(rootName, getNBT())
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
        key = descriptor.getElementName(index)
        return true
    }

    override fun setNBT(key: String, nbt: NBT) {
        compound[key] = nbt
    }
}