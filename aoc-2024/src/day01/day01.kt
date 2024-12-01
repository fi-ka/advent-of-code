package day01

import util.*
import java.io.File
import kotlin.math.abs

fun part1(input: String) {
    val lines = File(input).readLines()

    var (list1, list2) = lines.map { line ->
        val (id1, id2) = line.split("   ")
        id1.toLong() to id2.toLong()
    }.unzip()

    list1 = list1.sorted()
    list2 = list2.sorted()

    list1.zip(list2).sumOf { (id1, id2) ->
        abs(id1 - id2)
    }.println()
}

fun part2(input: String) {
    val lines = File(input).readLines()

    var (list1, list2) = lines.map { line ->
        val (id1, id2) = line.split("   ")
        id1.toLong() to id2.toLong()
    }.unzip()

    val count = list2
        .groupingBy { it }
        .eachCount()

    list1.sumOf { id ->
        id * (count[id]?.toLong() ?: 0L)
    }.println()
}

fun main() {
    val day = "1"

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
