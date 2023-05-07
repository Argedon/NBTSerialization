package com.argedon.serialization

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ComplexStructureTest {
    private val nbt = Nbt {
        serializationModule = SerializersModule {
            polymorphic(ObjContainer::class) {
                subclass(ObjContainerImpl::class)
            }
        }
    }

    @Test
    fun first() {
        val toSerialize = mapOf(
            1 to mapOf(
                10 to listOf(
                    mapOf(50 to 10), mapOf(30 to 50)
                ),
                20 to listOf(
                    mapOf(30 to 40), mapOf(60 to 70)
                )
            ),
            2 to mapOf(
                30 to listOf(
                    mapOf(60 to 30),  mapOf(40 to 80)
                ),
                40 to listOf(
                    mapOf(50 to 10), mapOf(60 to 70)
                )
            )
        )
        val serialized = nbt.serialize(toSerialize)

        assertEquals(toSerialize, nbt.deserialize(serialized))
    }

    @Test
    fun second() {
        val toSerialize = ComplexPolymorphism(
            ContainerImpl(50), ObjContainerImpl(20),
            ComplexPolymorphism(
                ContainerImpl(25), null,
            ComplexPolymorphism(null, ObjContainerImpl(50), null)
            )
        )
        val serialized = nbt.serialize(toSerialize)

        assertEquals(toSerialize, nbt.deserialize(serialized))
    }

    @Test
    fun third() {
        val toSerialize = arrayOf(
            ComplexPolymorphism(
                ContainerImpl(50), ObjContainerImpl(20),
                ComplexPolymorphism(
                    ContainerImpl(25), null,
                    ComplexPolymorphism(ContainerImpl(50), ObjContainerImpl(50), null)
                )
            ),
            ComplexPolymorphism(
                ContainerImpl(123), ObjContainerImpl(32627),
                ComplexPolymorphism(
                    ContainerImpl(532), null,
                    ComplexPolymorphism(null, ObjContainerImpl(327237), null)
                )
            ),
            ComplexPolymorphism(
                ContainerImpl(37237), ObjContainerImpl(2723),
                ComplexPolymorphism(
                    ContainerImpl(23723), null,
                    ComplexPolymorphism(ContainerImpl(123), ObjContainerImpl(236236), null)
                )
            ),
            ComplexPolymorphism(
                ContainerImpl(6236), ObjContainerImpl(236236),
                ComplexPolymorphism(
                    ContainerImpl(326236), null,
                    ComplexPolymorphism(null, ObjContainerImpl(62362), null)
                )
            )
        )
        val serialized = nbt.serialize(toSerialize)

        assertContentEquals(toSerialize, nbt.deserialize(serialized))
    }
}