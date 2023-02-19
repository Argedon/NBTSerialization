# NBTSerialization
This library helps u with serializiting objects into NBT structures from [this](https://github.com/jglrxavpok/Hephaistos) library with Kotlin serialization
## API
First u need to create an instance of the Nbt format
```kotlin
val nbt = Nbt {
    encodeDefaults = true
}
```
Now we can serialize our data with this `format`
```kotlin
// Example object to serialize
val toSerialize = hashMapOf(Int.MIN_VALUE to Int.MAX_VALUE)

// We can serialize it like that
val serialized = nbt.serialize(toSerialize)
```
To deserialize our data
```kotlin
// Equivalent to `toSerialize` variable
val deserialized = nbt.deserialize<Map<Int, Int>>(serialized)
```