package day07

import util.*

fun part1(input: String): Int {
    val lines = readLines(input)
    val startPos = lines.first().indexOf('S')
    var beams = setOf(startPos)
    var splits = 0

    lines.forEach { line ->
        val nextBeams = mutableSetOf<Int>()
        val splitters = line.mapIndexed { index, c -> if (c == '^') index else null }.filterNotNull()
        beams.forEach {
            if (it in splitters) {
                nextBeams.add(it - 1)
                nextBeams.add(it + 1)
                splits++
            } else {
                nextBeams.add(it)
            }
        }
        beams = nextBeams
    }

    return splits
}

fun part2(input: String): Long {
    val lines = readLines(input)
    val startPos = 1 to lines.first().indexOf('S')
    val splitters = buildSet {
        lines.forEachIndexed { row, line ->
            line.forEachIndexed { col, c ->
                if (c == '^')
                    add(row to col)
            }
        }
    }

    return beamRecursive(startPos, splitters, lines.lastIndex, mutableMapOf())
}

fun beamRecursive(pos: Pair<Int,Int>, splitters: Set<Pair<Int,Int>>, limit: Int, cache: MutableMap<Pair<Int,Int>, Long>): Long {
    if (pos.first > limit)
        return 1

    if (pos in cache)
        return cache[pos]!!

    if (pos in splitters) {
        val timeLines = beamRecursive(pos.first + 1 to pos.second - 1, splitters, limit, cache) +
                beamRecursive(pos.first + 1 to pos.second + 1, splitters, limit, cache)
        cache[pos] = timeLines
        return timeLines
    } else {
        val timeLines = beamRecursive(pos.first + 1 to pos.second, splitters, limit, cache)
        cache[pos] = timeLines
        return timeLines
    }
}

fun main() {
    val day = "7"

    println("==== Day $day ====")
    val input = getPath(day, "input.txt")
    val test = getPath(day, "test.txt")

    runPart("Part 1") {
        //part1(test)
        part1(input)
    }

    runPart("Part 2") {
        //part2(test)
        part2(input)
    }
}
