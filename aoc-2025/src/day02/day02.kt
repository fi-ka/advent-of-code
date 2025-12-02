package day02

import util.*

fun part1(input: String) {
    val ranges = readLine(input)
        .split(",", "-")
        .mapToLong()
        .chunked(2)
        .map { it[0]..it[1] }

    var sum = 0L
    ranges.forEach { range ->
        range.forEach { id ->
            val stringId = id.toString()

            if (stringId.length % 2 == 0) {
                val (first, second) = stringId.chunked(stringId.length / 2)
                if (first == second)
                    sum += id
            }
        }
    }

    sum.println()
}

fun part2(input: String) {
    val ranges = readLine(input)
        .split(",", "-")
        .mapToLong()
        .chunked(2)
        .map { it[0]..it[1] }

    var sum = 0L
    ranges.forEach { range ->
        range.forEach { id ->
            val stringId = id.toString()
            for (n in 1..(stringId.length / 2)) {
                if (stringId.length % n != 0)
                    continue

                val parts = stringId.chunked(n)
                if (parts.all { it == parts.first() }) {
                    sum += id
                    break
                }
            }
        }
    }

    sum.println()
}

fun main() {
    val day = "2"

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
