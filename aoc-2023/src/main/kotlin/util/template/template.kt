package util.template

import aoc.util.*

fun part1(input: String) {
    //val lines = readLines(input)
    //val cmds = readLines(input).map { it.parse("""(\w+) (\d+)""") }
    //val blocks = readBlocks(input)
}

fun part2(input: String) {
}

fun main() {
    val day = 1

    println("--- Day $day ---")
    val input = """src/main/kotlin/aoc/day$day/input.txt"""
    val test = """src/main/kotlin/aoc/day$day/test.txt"""

    runPart("Part 1") {
        part1(test)
        part1(input)
    }

    runPart("Part 2") {
        // part2(test)
        // part2(input)
    }
}
