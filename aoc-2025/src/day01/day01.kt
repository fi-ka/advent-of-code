package day01

import util.*
import kotlin.collections.map
import kotlin.math.abs
import kotlin.text.replace

fun parseLine(line: String): Int {
    return line
        .replace("R", "")
        .replace("L", "-")
        .toInt()
}

fun part1(input: String) {
    val lines = readLines(input)
    val rotations = lines.map(::parseLine)

    var result = 0
    var dial = 50

    val l = rotations.map { value ->
        dial = (dial + value) % 100

        if (dial == 0) result++
    }

    result.println()
}

fun part2(input: String) {
    val lines = readLines(input)
    val rotations = lines.map(::parseLine)

    var zeros = 0
    var dial = 50

    rotations.forEach { rotation ->
        zeros += abs(rotation / 100)
        val remaining = rotation % 100
        val nextDial = dial + remaining

        if (dial != 0 && nextDial !in 1..99)
            zeros += 1

        dial = (100 + nextDial) % 100
    }

    zeros.println()
}

fun main() {
    val day = "1"

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
