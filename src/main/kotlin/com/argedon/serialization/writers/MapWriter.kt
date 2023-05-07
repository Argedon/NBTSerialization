package com.argedon.serialization.writers

import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.modules.SerializersModule
import org.jglrxavpok.hephaistos.nbt.NBT
import com.argedon.serialization.NBTWriter
import org.jglrxavpok.hephaistos.nbt.NBTCompound

class MapWriter(
    private val rootName: String,
    private val parent: NBTWriter,
    override val serializersModule: SerializersModule
) : NBTWriter(parent.nbtConfiguration) {
    private val tags: MutableMap<String, NBT> = mutableMapOf()
    private var state = MapState.KEY

    override fun endStructure(descriptor: SerialDescriptor) {
        parent.setNBT(rootName, NBTCompound(tags))
    }

    override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
        state = if (index % 2 == 0) MapState.KEY else MapState.VALUE
        return true
    }

    override fun setNBT(nbt: NBT) {
        when (state) {
            MapState.KEY -> key = nbt.toString()
            MapState.VALUE -> tags[key] = nbt
        }
    }
}

private enum class MapState {
    KEY, VALUE
}