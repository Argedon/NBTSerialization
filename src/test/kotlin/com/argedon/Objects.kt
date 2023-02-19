package com.argedon

import kotlinx.serialization.Serializable

@Serializable
data class First(val int: Int, val second: Second)

@Serializable
data class Second(val string: String, val third: Third)

@Serializable
data class Third(val boolean: Boolean, val fourth: Fourth)

@Serializable
data class Fourth(val double: Double)

// polymorphism

@Serializable
sealed class Container

@Serializable
data class ContainerImpl(val int: Int) : Container()

@Serializable
abstract class ObjContainer

@Serializable
data class ObjContainerImpl(val int: Int) : ObjContainer()

// complex
@Serializable
data class ComplexPolymorphism(val one: Container?, val two: ObjContainer?, val three: ComplexPolymorphism?)

// benchmark
@Serializable
data class BenchmarkTestObj(val s: Map<Int, Map<Int, List<Map<Int, Int>>>>, val t: Int?, val h: String?, val b: Container, val c: ObjContainer)