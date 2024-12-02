package day02

import util.*
import java.io.File


fun part1(input: String) {
    val lines = File(input).readLines()
    lines.sumOf { line ->
        val levels = line.split(" ").mapToInt()
        if (levels.isSafe()) 1L else 0L
    }.println()
}

fun List<Int>.isSafe(): Boolean {
    val pairs = this.zipWithNext()
    val allDecreasing = pairs.all { pair -> pair.first - pair.second in 1..3 }
    val allIncreasing = pairs.all { pair -> pair.second - pair.first in 1..3 }
    return (allDecreasing || allIncreasing)
}

fun part2(input: String) {
    val lines = File(input).readLines()

    val safeReports = lines.filter { line ->
        val levels = line.split(" ").mapToInt()

        levels.indices.any { indexToExclude ->
            val dampened = levels.filterIndexed { index,_ -> index != indexToExclude }
            dampened.isSafe()
        }
    }

    safeReports.size.println()
}

fun main() {
    val day = "2"

    println("--- Day $day ---")
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
