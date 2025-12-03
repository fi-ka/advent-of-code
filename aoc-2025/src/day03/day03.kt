package day03

import util.*

fun part1(input: String): Int {
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

    return sum
}

fun part2(input: String): Long {
    val banks = readLines(input)
    val sum = banks.sumOf { bank ->
        var joltage = ""
        var startIndex = 0
        val indexedBank = bank.withIndex().toList()

        (11 downTo 0).forEach { remainingBatteries ->
            val availableBatteries = indexedBank.subList(startIndex, bank.length - remainingBatteries)
            val (index, battery) = availableBatteries.maxBy { it.value }

            startIndex = index + 1
            joltage += battery
        }

        joltage.toLong()
    }

    return sum
}

fun main() {
    val day = "3"

    println("==== Day $day ====")
    val input = getPath(day, "input.txt")
    val test = getPath(day, "test.txt")

    measurePart("Part 1") {
        //part1(test)
        part1(input)
    }

    measurePart("Part 2") {
        //part2(test)
        part2(input)
    }
}
