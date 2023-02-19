package com.argedon

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import org.jglrxavpok.hephaistos.nbt.NBT

data class Nbt(
    val nbtConfiguration: NbtConfiguration,
    val serializationModule: SerializersModule
) {
    fun <T> serialize(serializer: SerializationStrategy<T>, obj: T): NBT {
        val encoder = NBTEncoder(serializationModule, nbtConfiguration)
        serializer.serialize(encoder, obj)
        return encoder.getNBT()
    }

    inline fun <reified T> serialize(obj: T): NBT {
        return serialize(serializer(), obj)
    }

    fun <T> deserialize(deserializer: DeserializationStrategy<T>, tag: NBT): T {
        return deserializer.deserialize(NBTDecoder(serializationModule, tag))
    }

    inline fun <reified T> deserialize(tag: NBT): T {
        return deserialize(serializer(), tag)
    }
}

data class NbtConfiguration(
    val encodeDefaults: Boolean,
)

data class NbtBuilder(
    var encodeDefaults: Boolean = false,
    var serializationModule: SerializersModule = EmptySerializersModule()
) {
    fun build(): Nbt {
        return Nbt(NbtConfiguration(encodeDefaults), serializationModule)
    }
}

fun Nbt(builder: NbtBuilder.() -> Unit): Nbt {
    return NbtBuilder().apply(builder).build()
}