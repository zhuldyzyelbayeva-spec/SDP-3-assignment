# Assignment 3 | Bridge Pattern

**Student:** YOUR NAME
**Group:** YOUR GROUP
**Topic:** A — Drawing
**Repository:** YOUR GITHUB REPOSITORY URL
**Base commit:** 5df7bd484f74c3e81d4a81a0591f124a3f446b02

## Role map

| Bridge role | Assignment class | Source path |
|---|---|---|
| Abstraction | `Shape` | `src/Shape.java` |
| A1 | `Circle` | `src/Circle.java` |
| A2 | `Square` | `src/Square.java` |
| Implementor | `Renderer` | `src/Renderer.java` |
| I1 | `VectorRenderer` | `src/VectorRenderer.java` |
| I2 | `RasterRenderer` | `src/RasterRenderer.java` |
| I3 | `AsciiRenderer` | `src/AsciiRenderer.java` |
| Client | `Main` | `src/Main.java` |

The Bridge field is `renderer` in `Shape`. `execute()` is declared by `Shape` and implemented by `Circle` and `Square`. `setImplementation(Renderer)` is in `Shape`. The T5 runtime-switch check is in `src/Main.java`.

## Two dimensions

- Abstraction dimension: `Shape` -> `Circle`, `Square`.
- Implementation dimension: `Renderer` -> `VectorRenderer`, `RasterRenderer`, `AsciiRenderer`.

The dimensions vary independently and are connected by composition through the `Renderer` interface.

## Build and run

```bash
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
java -cp out Main --demo
```

## Expected T1-T7 outcomes

| Check | Setup | Expected |
|---|---|---|
| T1 | Circle + VectorRenderer | `VECTOR circle radius=2` |
| T2 | Circle + RasterRenderer | `RASTER circle radius=2` |
| T3 | Square + VectorRenderer | `VECTOR square side=3` |
| T4 | Square + RasterRenderer | `RASTER square side=3` |
| T5 | Same Circle object, switch Vector -> Raster | same object, unchanged ID/data, result changes from VECTOR to RASTER |
| T6 | Circle + AsciiRenderer | `ASCII circle radius=2` |
| T7 | Square + AsciiRenderer | `ASCII square side=3` |

Expected final line: `SUMMARY: 7/7 PASS`

## Extension

The working I1/I2 version was committed first. The recorded base commit is shown at the top of this file. The extension adds `AsciiRenderer` and updates `Main` for T6/T7; existing `Shape`, `Circle`, `Square`, `Renderer`, `VectorRenderer`, and `RasterRenderer` are unchanged. The exact source diff is in `extension.diff`.

## Sources

1. Astana IT University, *Assignment 3 | Bridge Pattern*, ShP-2216 Software Design Patterns, 2026-2027.
2. Astana IT University, *Lecture 4 — Bridge Pattern* (course material).
3. Freeman, E. & Freeman, E. *Head First Design Patterns*, Bridge-pattern discussion and related design-pattern material.
