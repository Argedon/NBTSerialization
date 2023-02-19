package com.argedon.utils

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialKind
import org.jglrxavpok.hephaistos.nbt.NBTList
import org.jglrxavpok.hephaistos.nbt.NBTType

object NBTUtils {
    val EMPTY_LIST = NBTList(NBTType.TAG_End)

    @OptIn(ExperimentalSerializationApi::class)
    fun getNBTType(kind: SerialKind): NBTType<*> = when (kind) {
        PrimitiveKind.STRING, PrimitiveKind.CHAR -> NBTType.TAG_String
        PrimitiveKind.INT -> NBTType.TAG_Int
        PrimitiveKind.LONG -> NBTType.TAG_Long
        PrimitiveKind.FLOAT -> NBTType.TAG_Float
        PrimitiveKind.DOUBLE -> NBTType.TAG_Double
        PrimitiveKind.SHORT -> NBTType.TAG_Short
        PrimitiveKind.BYTE, PrimitiveKind.BOOLEAN -> NBTType.TAG_Byte
        else -> NBTType.TAG_Compound
    }
}