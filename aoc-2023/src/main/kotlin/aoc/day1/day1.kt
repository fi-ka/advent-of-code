package aoc.day1

import aoc.util.*

fun part1(input: String) {
    val lines = readLines(input)

    val sum = lines.sumOf { line ->
        val first = line.first { it.isDigit() }
        val last = line.last { it.isDigit() }
        "$first$last".toInt()
    }
    println(sum)
}

fun part2(input: String) {
    val lines = readLines(input)
    val numbers = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9",
    )

    val sum = lines.sumOf { line ->
        var first = line.findAnyOf(numbers.keys + numbers.values)!!.second
        var last = line.findLastAnyOf(numbers.keys + numbers.values)!!.second
        first = numbers.getOrDefault(first, first)
        last = numbers.getOrDefault(last, last)
        "$first$last".toInt()
    }
    println(sum)
}

fun main() {
    val day = 1

    println("--- Day $day ---")
    val input = """src/main/kotlin/aoc/day$day/input.txt"""
    val test = """src/main/kotlin/aoc/day$day/test.txt"""

    runPart("Part 1") {
        //part1(test)
        part1(input)
    }

    runPart("Part 2") {
        part2(test)
        part2(input)
    }
}
