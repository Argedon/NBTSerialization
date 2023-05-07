package com.argedon.serialization.writers

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.modules.SerializersModule
import org.jglrxavpok.hephaistos.nbt.NBT
import com.argedon.serialization.NBTWriter
import org.jglrxavpok.hephaistos.nbt.NBTCompound

class CompoundWriter(
    private val rootName: String,
    private val parent: NBTWriter,
    override val serializersModule: SerializersModule
) : NBTWriter(parent.nbtConfiguration) {
    private val tags: MutableMap<String, NBT> = mutableMapOf()

    override fun endStructure(descriptor: SerialDescriptor) {
        parent.setNBT(rootName, NBTCompound(tags))
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
        key = descriptor.getElementName(index)
        return true
    }

    override fun setNBT(nbt: NBT) {
        tags[key] = nbt
    }
}