package day06

import util.*

fun part1(input: String) {
    val lines = readLines(input)
    val rowLastIndex = lines.lastIndex
    val colLastIndex = lines.first().length

    var pos: Pair<Int,Int> = 0 to 0
    val blocks = buildSet {
        readLines(input).forEachIndexed { row, line ->
            line.forEachIndexed { col, c ->
                if (c == '^')
                    pos = row to col

                if (c == '#')
                    add(row to col)
            }
        }
    }.toMutableSet()

    val directions = listOf(-1 to 0, 0 to 1, 1 to 0, 0 to -1, -1 to 0)
    var directionIdx = 0
    val visited = mutableSetOf<Pair<Int,Int>>()
    var direction: Pair<Int, Int>
    while (pos.first in 0..rowLastIndex && pos.second in 0..colLastIndex) {
        visited.add(pos)

        var nextPos: Pair<Int, Int>
        while (true) {
            direction = directions[directionIdx % directions.size]
            nextPos = pos.first + direction.first to pos.second + direction.second
            if (nextPos in blocks)
                directionIdx++
            else
                break
        }

        pos = nextPos
    }

    visited.size.println()
}

fun part2(input: String) {
    val lines = readLines(input)
    val rowLastIndex = lines.lastIndex
    val colLastIndex = lines.first().length

    var startPos: Pair<Int,Int> = 0 to 0
    val blocks = buildSet {
        readLines(input).forEachIndexed { row, line ->
            line.forEachIndexed { col, c ->
                if (c == '^')
                    startPos = row to col

                if (c == '#')
                    add(row to col)
            }
        }
    }.toMutableSet()

    val directions = listOf(-1 to 0, 0 to 1, 1 to 0, 0 to -1, -1 to 0)
    val loopBlocks = mutableSetOf<Pair<Int,Int>>()
    for (row in 0..rowLastIndex) {
        for (col in 0..colLastIndex) {
            if (row to col in blocks)
                continue

            var pos = startPos
            var directionIdx = 0
            val visited = mutableListOf<List<Pair<Int,Int>>>()
            var direction = directions[0]
            val newBlocks = blocks + setOf(row to col)
            while (pos.first in 0..rowLastIndex && pos.second in 0..colLastIndex) {
                if (listOf(pos, direction) in visited) {
                    loopBlocks.add(row to col)
                    break
                }
                visited.add(listOf(pos, direction))

                var nextPos: Pair<Int, Int>
                while (true) {
                    direction = directions[directionIdx % directions.size]
                    nextPos = pos.first + direction.first to pos.second + direction.second
                    if (nextPos in newBlocks)
                        directionIdx++
                    else
                        break
                }
                pos = nextPos
            }
        }
    }


    loopBlocks.size.println()
}

fun main() {
    val day = "6"

    println("==== Day $day ====")
    val input = getPath(day, "input.txt")
    val test = getPath(day, "test.txt")

    runPart("Part 1") {
        part1(test)
        part1(input)
    }

    runPart("Part 2") {
//         part2(test)
         part2(input)
    }
}
