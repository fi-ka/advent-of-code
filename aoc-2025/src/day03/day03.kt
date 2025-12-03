package day03

import util.*

fun part1(input: String) {
    val banks = readLines(input)
    val sum = banks.sumOf { bank ->
        val (index, first) = bank.dropLast(1)
            .withIndex()
            .maxBy {
                it.value
            }

        val second = bank
            .drop(index+1)
            .max()

        "$first$second".toInt()
    }

    sum.println()
}

fun part2(input: String) {
    val banks = readLines(input)
    val sum = banks.sumOf { bank ->
        var joltage = ""
        var nextStartIndex = 0
        val indexedBank = bank.withIndex().toList()

        (11 downTo 0).map { remainingBatteries ->
            val (index, max) = indexedBank
                .subList(nextStartIndex, bank.length - remainingBatteries)
                .maxBy { it.value }

            nextStartIndex = index + 1
            joltage += max
        }

        joltage.toLong()
    }

    sum.println()
}

fun main() {
    val day = "3"

    println("==== Day $day ====")
    val input = getPath(day, "input.txt")
    val test = getPath(day, "test.txt")

    runPart("Part 1") {
        //part1(test)
        part1(input)
    }

    runPart("Part 2") {
        //part2(test)
        part2(input)
    }
}
