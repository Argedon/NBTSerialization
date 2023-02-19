package com.argedon

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.jglrxavpok.hephaistos.nbt.*
import kotlin.test.Test
import kotlin.test.assertEquals

class SerializationTest {
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
        val expected = NBT.Kompound {
            setInt(Int.MIN_VALUE.toString(), Int.MAX_VALUE)
            setInt(Int.MAX_VALUE.toString(), Int.MIN_VALUE)
        }
        val serialized = nbt.serialize(toSerialize)

        assertEquals(expected, serialized)
    }

    @Test
    fun listTest() {
        val toSerialize = listOf(Long.MIN_VALUE, Long.MAX_VALUE, Int.MIN_VALUE.toLong(), Int.MAX_VALUE.toLong())
        val expected = NBTList(NBTType.TAG_Long, listOf(NBTLong(Long.MIN_VALUE), NBTLong(Long.MAX_VALUE), NBTLong(Int.MIN_VALUE.toLong()), NBTLong(Int.MAX_VALUE.toLong())))
        val serialized = nbt.serialize(toSerialize)

        assertEquals(expected, serialized)
    }

    @Test
    fun arrayTest() {
        val toSerialize = arrayOf(Float.MIN_VALUE, 1F, 20F, 50F, Float.MAX_VALUE)
        val expected = NBTList(NBTType.TAG_Float, listOf(NBTFloat(Float.MIN_VALUE), NBTFloat(1F), NBTFloat(20F), NBTFloat(50F), NBTFloat(Float.MAX_VALUE)))
        val serialized = nbt.serialize(toSerialize)

        assertEquals(expected, serialized)
    }

    @Test
    fun objectTest() {
        val toSerialize = First(10, Second("test", Third(true, Fourth(10.0))))
        val expected = NBT.Kompound {
            setInt("int", 10)
            set("second", NBT.Kompound {
                setString("string", "test")
                set("third", NBT.Kompound {
                    setByte("boolean", 1)
                    set("fourth", NBT.Kompound {
                        setDouble("double", 10.0)
                    })
                })
            })
        }
        val serialized = nbt.serialize(toSerialize)

        assertEquals(expected, serialized)
    }

    @Test
    fun polymorphismSealedTest() {
        val toSerialize: Container = ContainerImpl(10)
        val expected = NBT.Kompound {
            setString("type", "com.argedon.ContainerImpl")
            set("value", NBT.Kompound {
                setInt("int", 10)
            })
        }
        val serialized = nbt.serialize(toSerialize)

        assertEquals(expected, serialized)
    }

    @Test
    fun polymorphismObjTest() {
        val toSerialize: ObjContainer = ObjContainerImpl(10)
        val expected = NBT.Kompound {
            setString("type", "com.argedon.ObjContainerImpl")
            set("value", NBT.Kompound {
                setInt("int", 10)
            })
        }
        val serialized = nbt.serialize(toSerialize)

        assertEquals(expected, serialized)
    }
}