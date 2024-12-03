package day03

import util.*


fun part1(input: String) {
    val instructionRegex = """mul\((\d+),(\d+)\)""".toRegex()

    readLines(input).sumOf { line ->
        val scan = instructionRegex.findAll(line)
        scan.sumOf { match ->
           val args = match.groupValues.drop(1).mapToLong()
           args.reduce(Long::times)
        }
    }.println()
}

fun part2(input: String) {
    val instructionRegex = listOf(
        """do\(\)""",
        """don't\(\)""",
        """mul\((\d+),(\d+)\)"""
    ).joinToString("|").toRegex()

    var enabled = true
    var sum = 0L

    readLines(input).forEach { line ->
        val scan = instructionRegex.findAll(line)
        scan.forEach { match ->
            val instruction = match.groupValues.first()
            val args = match.groupValues.drop(1)

            when {
                instruction.startsWith("don't") -> enabled = false
                instruction.startsWith("do") -> enabled = true
                enabled -> sum += args.mapToLong().reduce(Long::times)
            }
        }
    }

    println(sum)
}

fun main() {
    val day = "3"

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
