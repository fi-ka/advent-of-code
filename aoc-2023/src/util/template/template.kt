package util.template

import util.*

fun part1(input: String) {
    //val lines = readLines(input)
    //val cmds = readLines(input).map { it.parse("""(\w+) (\d+)""") }
    //val blocks = readBlocks(input)
}

fun part2(input: String) {
}

fun main() {
    val day = "1"

    println("--- Day $day ---")
    val input = """src/day${day.padStart(2, '0')}/input.txt"""
    val test = """src/day${day.padStart(2, '0')}/test.txt"""

    runPart("Part 1") {
        part1(test)
        // part1(input)
    }

    runPart("Part 2") {
        // part2(test)
        // part2(input)
    }
}
