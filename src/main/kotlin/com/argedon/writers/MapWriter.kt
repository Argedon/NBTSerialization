package com.argedon.writers

import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.modules.SerializersModule
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.mutable.MutableNBTCompound
import com.argedon.NBTWriter

class MapWriter(
    private val rootName: String,
    private val parent: NBTWriter,
    override val serializersModule: SerializersModule
) : NBTWriter(parent.nbtConfiguration) {
    private val compound = MutableNBTCompound()
    private var state = MapState.KEY

    override fun getNBT(): NBT = compound.toCompound()

    override fun endStructure(descriptor: SerialDescriptor) {
        parent.setNBT(rootName, getNBT())
    }

    override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
        state = if (index % 2 == 0) MapState.KEY else MapState.VALUE
        return true
    }

    override fun setNBT(key: String, nbt: NBT) {
        when (state) {
            MapState.KEY -> this.key = nbt.value.toString()
            MapState.VALUE -> compound[this.key] = nbt
        }
    }
}

private enum class MapState {
    KEY, VALUE
}