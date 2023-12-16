package day16

import util.*

data class Delta(val row: Int, val col: Int)
data class Pos(val row: Int, val col: Int)
data class Beam(val pos: Pos, val delta: Delta)
data class Mirror(val type: Char, val pos: Pos)
data class MirrorMap(val height: Int, val width: Int, val mirrors: Map<Pos, Mirror>)

fun Mirror.reflect(beam: Beam): List<Beam> {
    return when (type) {
        '/' -> listOf(beam.copy(delta = Delta(row = -beam.delta.col, col = -beam.delta.row)))
        '\\' -> listOf(beam.copy(delta = Delta(row = beam.delta.col, col = beam.delta.row)))
        '-' -> {
            if (beam.delta.row != 0) {
                listOf(
                    beam.copy(delta = Delta(row = 0, col = 1)),
                    beam.copy(delta = Delta(row = 0, col = -1))
                )
            } else {
                listOf(beam)
            }
        }
        '|' -> {
            if (beam.delta.col != 0) {
                listOf(
                    beam.copy(delta = Delta(row = 1, col = 0)),
                    beam.copy(delta = Delta(row = -1, col = 0))
                )
            } else {
                listOf(beam)
            }
        }
        else -> listOf(beam)
    }
}

fun MirrorMap.energizedBy(beam: Beam): Int {
    var beams = listOf(beam)
    val prevBeams = mutableSetOf<Beam>()
    val energized = mutableSetOf<Pos>()
    do {
        beams = beams.flatMap { (pos, delta) ->
            val newPos = pos.copy(row = pos.row + delta.row, col = pos.col + delta.col)
            val newBeam = Beam(newPos, delta)

            if (newPos.row !in 0..<height ||
                newPos.col !in 0..<width ||
                newBeam in prevBeams)
                return@flatMap listOf()

            prevBeams.add(newBeam)
            energized.add(newPos)

            mirrors[newPos]?.reflect(newBeam)
                ?: listOf(newBeam)
        }
    } while (beams.isNotEmpty())

    return energized.size
}

fun part1(input: String) {
    val lines = readLines(input)
    val mirrors = buildMap {
        lines.forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                if (char != '.')
                    put(Pos(row, col), Mirror(char, Pos(row, col)))
            }
        }
    }

    val height = lines.size
    val width = lines.first().length

    val mirrorMap = MirrorMap(height, width, mirrors)
    val energized = mirrorMap.energizedBy(Beam(Pos(0,-1), Delta(0, 1)))

    println(energized)
}

fun part2(input: String) {
    val lines = readLines(input)
    val mirrors = buildMap {
        lines.forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                if (char != '.')
                    put(Pos(row, col), Mirror(char, Pos(row, col)))
            }
        }
    }

    val height = lines.size
    val width = lines.first().length

    val mirrorMap = MirrorMap(height, width, mirrors)

    val beams = buildList {
        addAll((0..<width).map { Beam(Pos(-1, it), Delta(1, 0)) })
        addAll((0..<width).map { Beam(Pos(height, it), Delta(-1, 0)) })
        addAll((0..<height).map { Beam(Pos(it, -1), Delta(0, 1)) })
        addAll((0..<height).map { Beam(Pos(it, width), Delta(0, -1)) })
    }

    beams.maxOf { beam ->
        mirrorMap.energizedBy(beam)
    }.println()
}

fun main() {
    val day = "16"

    println("==== Day $day ====")
    val input = """src/day${day.padStart(2, '0')}/input.txt"""
    val test = """src/day${day.padStart(2, '0')}/test.txt"""

    runPart("Part 1") {
        part1(test)
        part1(input)
    }

    runPart("Part 2") {
         part2(test)
         part2(input)
    }
}
