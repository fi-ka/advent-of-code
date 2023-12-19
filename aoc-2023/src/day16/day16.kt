package day16

import util.*

data class Pos(var row: Int, var col: Int)
data class Beam(val pos: Pos, var dRow: Int, var dCol: Int)
data class Mirror(val type: Char)
data class MirrorMap(val height: Int, val width: Int, val mirrors: Map<Pos, Mirror>)

fun Mirror.reflect(beam: Beam): List<Beam> {
    val (_, dRow, dCol) = beam
    when (type) {
        '/' -> return listOf(beam.copy(dRow = -dCol, dCol = -dRow))
        '\\' -> return listOf(beam.copy(dRow = dCol, dCol = dRow))
        '-' -> {
            if (dRow != 0) {
                return listOf(
                    beam.copy(dRow = 0, dCol = 1),
                    beam.copy(dRow = 0, dCol = -1)
                )
            }
        }
        '|' -> {
            if (dCol != 0) {
                return listOf(
                    beam.copy(dRow = 1, dCol = 0),
                    beam.copy(dRow = -1, dCol = 0)
                )
            }
        }
    }
    return listOf(beam)
}

fun MirrorMap.energizedBy(beam: Beam): Int {
    var beams = listOf(beam)
    val prevBeams = mutableSetOf<Beam>()
    do {
        val newBeams = mutableListOf<Beam>()
        for ((pos, dRow, dCol) in beams) {
            val newPos = pos.copy(row = pos.row + dRow, col = pos.col + dCol)
            val newBeam = Beam(newPos, dRow, dCol)

            if (newPos.row !in 0..<height ||
                newPos.col !in 0..<width ||
                newBeam in prevBeams)
                continue

            prevBeams.add(newBeam)

            if (newPos in mirrors)
                newBeams.addAll(mirrors[newPos]!!.reflect(newBeam))
            else
                newBeams.add(newBeam)
        }
        beams = newBeams
    } while (beams.isNotEmpty())

    return prevBeams.map { it.pos }.toSet().size
}

fun part1(input: String) {
    val lines = readLines(input)
    val mirrors = buildMap {
        lines.forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                if (char != '.')
                    put(Pos(row, col), Mirror(char))
            }
        }
    }

    val height = lines.size
    val width = lines.first().length

    val mirrorMap = MirrorMap(height, width, mirrors)
    val energized = mirrorMap.energizedBy(Beam(Pos(0,-1), 0, 1))

    println(energized)
}

fun part2(input: String) {
    val lines = readLines(input)
    val mirrors = buildMap {
        lines.forEachIndexed { row, line ->
            line.forEachIndexed { col, char ->
                if (char != '.')
                    put(Pos(row, col), Mirror(char))
            }
        }
    }

    val height = lines.size
    val width = lines.first().length

    val mirrorMap = MirrorMap(height, width, mirrors)

    val beams = buildList {
        addAll((0..<width).map { Beam(Pos(-1, it), 1, 0) })
        addAll((0..<width).map { Beam(Pos(height, it), -1, 0) })
        addAll((0..<height).map { Beam(Pos(it, -1), 0, 1) })
        addAll((0..<height).map { Beam(Pos(it, width), 0, -1) })
    }

    beams.maxOf { beam ->
        mirrorMap.energizedBy(beam)
    }.println()
}

fun main() {
    val day = "16"

    println("==== Day $day ====")
    val input = getPath(day, "input.txt")
    val test = getPath(day, "test.txt")

    runPart("Part 1") {
        part1(test)
        part1(input)
    }

    runPart("Part 2") {
         part2(test)
         part2(input)
    }
}
