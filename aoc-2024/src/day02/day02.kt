package day02

import util.*
import java.io.File

fun List<Int>.isSafe(): Boolean {
    val pairs = this.zipWithNext()
    val allDecreasing = pairs.all { pair -> pair.first - pair.second in 1..3 }
    val allIncreasing = pairs.all { pair -> pair.second - pair.first in 1..3 }
    return (allDecreasing || allIncreasing)
}

fun List<Int>.isSafeDampened(): Boolean {
    return this.indices.any { indexToExclude ->
        this.filterIndexed { index,_ -> index != indexToExclude }
            .isSafe()
    }
}

fun part1(input: String) {
    val reports = File(input)
        .readLines()
        .map { it.split(" ").mapToInt() }

    val safeReports = reports.count { it.isSafe() }
    safeReports.println()
}

fun part2(input: String) {
    val reports = File(input)
        .readLines()
        .map { it.split(" ").mapToInt() }

    val safeReports = reports.count { it.isSafeDampened() }
    safeReports.println()
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
