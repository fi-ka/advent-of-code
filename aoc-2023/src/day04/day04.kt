package day04

import util.mapToLong
import util.readLines
import util.runPart
import kotlin.math.pow

fun part1(input: String) {
    val lines = readLines(input)
    val result = lines.sumOf { line ->
        val (_, winningLine, numbersLine) = line.split(":", "|")
        val winning = winningLine.split(" ").filter { it.isNotBlank() }.toSet()
        val numbers = numbersLine.split(" ").filter { it.isNotBlank() }.toSet()

        val winningNumbers = numbers.intersect(winning)
        if (winningNumbers.isNotEmpty())
            2.0.pow(winningNumbers.size - 1)
        else 0.0
    }
    println(result)
}

fun part2(input: String) {
    val lines = readLines(input)
    val cards = lines.associate { line ->

        val (cardLine, winningLine, numbersLine) = line.split(":", "|")
        val (cardNumber) = """(\d+)""".toRegex().find(cardLine)!!.destructured

        val winning = winningLine.split(" ").filter { it.isNotBlank() }.toSet()
        val numbers = numbersLine.split(" ").filter { it.isNotBlank() }.toSet()

        val newCards = numbers.intersect(winning).indices.map { it + cardNumber.toInt() + 1 }.map { it.toString() }

        cardNumber to newCards
    }

    var cardAmount = 0
    var remaining = cards.keys.toList()

    while(remaining.isNotEmpty()) {
        cardAmount += remaining.size
        val newCards = remaining.flatMap { cardNumber -> cards[cardNumber]!! }
        remaining = newCards
    }

    println(cardAmount)
}

fun main() {
    val day = "4"

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
