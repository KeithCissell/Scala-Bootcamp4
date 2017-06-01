# Iteration 4
Iteration 4 of the scala learning modules.

## Book Work
Working along with O'Reilly's Programming Scala 2nd Edition.

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
