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

### Chapter 13 - Visibility Rules
- Public
  - visible everywhere across all boundaries
- Protected
  - visible to defining type, derived types and nested types
  - visible only within the same package and subpackages
- Private
  - visible only within the defining type and nested types
  - visible only in same package
- Scoped Protected
  - visible to `scope` which could be package, type or `this` instance
- Scoped Private
  - similar to Scoped Protected, except under inheritance
- Unspecified visibilities will default to Public

### Chapter 14 - Scala's Type System Part 1
- Parameterized Types
  - Variance Annotations
  - Type Constructors
  - Type Parameter Names
- Type Bounds
  - Upper Type Bounds
  - Lower Type Bounds
- Context Bounds
- View Bounds
- Understanding Abstract Types
  - Comparing Abstract Types and Parameterized Types
- Self-Type Annotations
- Structural Types
- Compound Types
  - Type Refinements
- Existential Types

### Chapter 15 - Scala's Type System Part 1
- Path-Dependent Types
  - C.this
  - C.super
  - path.x
- Dependent Method Types
- Type Projections
  - Singleton Types
- Types for Values
  - Tuple Types
  - Function Types
  - Infix Types
- Higher-Kinded Types
- Type Lambdas
- Self-Recursive Types: F-Bounded Polymorphism
