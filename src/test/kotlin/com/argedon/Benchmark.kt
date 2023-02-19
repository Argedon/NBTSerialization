package com.argedon

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 50, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 100, time = 1, timeUnit = TimeUnit.MILLISECONDS)
open class SerializationBenchmark {
    private val toSerialize = BenchmarkTestObj(mapOf(
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
    ), 50, null)
    private val nbt = Nbt {  }
    private val serialized = nbt.serialize(toSerialize)

    @Param("1", "10", "100", "1000")
    private var iterations: Int = 0

    @Benchmark
    fun serialization() {
        for (i in 0 until iterations) {
            nbt.serialize(toSerialize)
        }
    }

    @Benchmark
    fun deserialization() {
        for (i in 0 until iterations) {
            nbt.deserialize<BenchmarkTestObj>(serialized)
        }
    }
}