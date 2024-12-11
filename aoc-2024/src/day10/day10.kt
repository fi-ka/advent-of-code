package day10

import day08.isInside
import day08.plus
import util.*

val adj4 = listOf(-1 to 0, 0 to 1, 1 to 0, 0 to -1)

fun part(input: String, part2: Boolean = false) {

    val lines = readLines(input)
    val maxRow = lines.lastIndex
    val maxCol = lines.first().lastIndex

    val tops = mutableListOf<Pair<Int,Int>>()
    val heightMap = mutableMapOf<Pair<Int,Int>, Int>()

    lines.forEachIndexed { row, line ->
        line.forEachIndexed { col, spot ->
            val height = if (spot == '.') 100 else spot.digitToInt()
            heightMap[row to col] = height

            if (height == 9)
                tops.add(row to col)
        }
    }

    var score = 0
    tops.forEach {
        val toVisit = mutableListOf(it)
        while (toVisit.isNotEmpty()) {
            val pos = toVisit.removeFirst()
            val height = heightMap[pos]!!
            if (height == 0) {
                score += 1
                continue
            }

            adj4.forEach { delta ->
                val nextPos = pos.plus(delta)
                if (nextPos.isInside(maxRow, maxCol)) {
                    val nextHeight = heightMap[nextPos]
                    if (nextHeight == height - 1 && (nextPos !in toVisit || part2))
                        toVisit.add(nextPos)
                }
            }
        }
    }

    score.println()
}

fun main() {
    val day = "10"

    println("==== Day $day ====")
    val input = getPath(day, "input.txt")
    val test = getPath(day, "test.txt")

    runPart("Part 1") {
        part(test)
        part(input)
    }

    runPart("Part 2") {
        part(test, part2 = true)
        part(input, part2 = true)
    }
}
