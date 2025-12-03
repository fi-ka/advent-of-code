package day03

import util.*
import java.io.File

fun getMaxJoltage(bank: String, batteries: Int): Long {
    val indexedBank = bank.withIndex().toList()

    var startIndex = 0
    var joltage = ""

    for (remaining in batteries-1 downTo 0) {
        val availableBatteries = indexedBank.subList(startIndex, bank.length - remaining)
        val (index, battery) = availableBatteries.maxBy { it.value }

        startIndex = index + 1
        joltage += battery
    }

    return joltage.toLong()
}

fun part1(input: String): Long {
    val banks = File(input).readLines()
    return banks.sumOf { bank -> getMaxJoltage(bank, batteries = 2) }
}

fun part2(input: String): Long {
    val banks = File(input).readLines()
    return banks.sumOf { bank -> getMaxJoltage(bank, batteries = 12) }
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
