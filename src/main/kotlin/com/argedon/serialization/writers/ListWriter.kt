package com.argedon.serialization.writers

import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.modules.SerializersModule
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTList
import org.jglrxavpok.hephaistos.nbt.NBTType
import com.argedon.serialization.NBTWriter

class ListWriter(
    private val rootName: String,
    private val parent: NBTWriter,
    private val nbtType: NBTType<*>,
    override val serializersModule: SerializersModule
) : NBTWriter(parent.nbtConfiguration) {
    private val list = arrayListOf<NBT>()

    override fun endStructure(descriptor: SerialDescriptor) {
        parent.setNBT(rootName, NBTList(nbtType, list))
    }

    override fun setNBT(nbt: NBT) {
        list.add(nbt)
    }
}