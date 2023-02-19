package com.argedon

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class DeserializationTest {
    private val nbt = Nbt {
        serializationModule = SerializersModule {
            polymorphic(ObjContainer::class) {
                subclass(ObjContainerImpl::class)
            }
        }
    }

    @Test
    fun mapTest() {
        val toSerialize = mapOf(Int.MIN_VALUE to Int.MAX_VALUE, Int.MAX_VALUE to Int.MIN_VALUE)
        val serialized = nbt.serialize(toSerialize)

        assertEquals(toSerialize, nbt.deserialize(serialized))
    }

    @Test
    fun listTest() {
        val toSerialize = listOf(Long.MIN_VALUE, Long.MAX_VALUE, Int.MIN_VALUE.toLong(), Int.MAX_VALUE.toLong())
        val serialized = nbt.serialize(toSerialize)

        assertEquals(toSerialize, nbt.deserialize(serialized))
    }

    @Test
    fun arrayTest() {
        val toSerialize = arrayOf(Float.MIN_VALUE, 1F, 20F, 50F, Float.MAX_VALUE)
        val serialized = nbt.serialize(toSerialize)

        assertContentEquals(toSerialize, nbt.deserialize(serialized))
    }

    @Test
    fun objectTest() {
        val toSerialize = First(10, Second("test", Third(true, Fourth(10.0))))
        val serialized = nbt.serialize(toSerialize)

        assertEquals(toSerialize, nbt.deserialize(serialized))
    }

    @Test
    fun polymorphismSealedTest() {
        val toSerialize: Container = ContainerImpl(10)
        val serialized = nbt.serialize(toSerialize)

        assertEquals(toSerialize, nbt.deserialize(serialized))
    }

    @Test
    fun polymorphismObjTest() {
        val toSerialize: ObjContainer = ObjContainerImpl(10)
        val serialized = nbt.serialize(toSerialize)

        assertEquals(toSerialize, nbt.deserialize(serialized))
    }
}