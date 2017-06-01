# Iteration 4
Iteration 4 of the scala learning modules.

## Book Work
Working along with O'Reilly's Programming Scala 2nd Edition.

### Chapter 12 - The Scala Collections Library
- Descriptive list of Immutable collections: pg. 336
- Parallel collections
  - http://www.scala-lang.org/api/current/scala/collection/parallel/immutable/index.html
- List
  - O(1) for prepending, reading head element
  - O(n) to access all other elements
- Vectors
  - O(1) for all operations
- ArrayBuffer
  - O(1) for appending, updating and random access
  - O(n) for prepending and deleting
- immutable.Map - good for O(1) retrieval of values by key
- immutable.Set - good for checking existence of value
- Collection Idioms
  - `Builder` to abstract over construction
  - `CanBuildFrom` to provide implicit factories for constructing suitable `Builder` instances for a given context
  - `Like` traits that add the necessary return type parameter needed by `Builder` and `CanBuildFrom`, as well as providing most of the method implementations
- Specialized Collection Types
  - Example: `class SpecialVector[@specialized(Int, Double, Boolean) T] { ... }`
  - Can generate a lot of code in your library when overused
