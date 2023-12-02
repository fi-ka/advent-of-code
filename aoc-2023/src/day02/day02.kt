package day02

import util.*

fun part1(input: String) {
    val lines = readLines(input)

    val possibleLines = lines.map {line ->
        val blue = """(\d+) blue""".toRegex().findAll(line).maxOf { it.groupValues[1].toInt() }
        val green = """(\d+) green""".toRegex().findAll(line).maxOf { it.groupValues[1].toInt() }
        val red = """(\d+) red""".toRegex().findAll(line).maxOf { it.groupValues[1].toInt() }

        blue <= 14 && red <= 12 && green <= 13
    }

    val indexSum = possibleLines.withIndex().sumOf { (idx, p) ->
        if (p) (idx + 1) else 0
    }
    println(indexSum)
}

fun part2(input: String) {
    val lines = readLines(input)


    val powerSum = lines.sumOf { line ->
        val blue = """(\d+) blue""".toRegex().findAll(line).maxOf { it.groupValues[1].toInt() }
        val green = """(\d+) green""".toRegex().findAll(line).maxOf { it.groupValues[1].toInt() }
        val red = """(\d+) red""".toRegex().findAll(line).maxOf { it.groupValues[1].toInt() }

        blue * red * green
    }

    println(powerSum)
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
