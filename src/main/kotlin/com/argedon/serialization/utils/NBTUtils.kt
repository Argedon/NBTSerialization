package com.argedon.serialization.utils

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialKind
import org.jglrxavpok.hephaistos.nbt.NBTType

object NBTUtils {
    @OptIn(ExperimentalSerializationApi::class)
    fun getNBTType(kind: SerialKind): NBTType<*> = when (kind) {
        PrimitiveKind.INT -> NBTType.TAG_Int
        PrimitiveKind.LONG -> NBTType.TAG_Long
        PrimitiveKind.FLOAT -> NBTType.TAG_Float
        PrimitiveKind.DOUBLE -> NBTType.TAG_Double
        PrimitiveKind.STRING, PrimitiveKind.CHAR -> NBTType.TAG_String
        PrimitiveKind.SHORT -> NBTType.TAG_Short
        PrimitiveKind.BYTE, PrimitiveKind.BOOLEAN -> NBTType.TAG_Byte
        else -> NBTType.TAG_Compound
    }
}