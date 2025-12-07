package day06

import util.*
import kotlin.text.split

fun part1(input: String): Long {
    val lines = readLines(input)

    val rotated = mutableListOf<MutableList<String>>()
    lines.forEachIndexed { lineIndex, line ->
        val parts = line.split("""\s+""".toRegex()).filterNot { it.isEmpty() }
        parts.forEachIndexed { partIndex, part ->
            if (lineIndex == 0)
                rotated.add(mutableListOf(part))
            else
                rotated[partIndex].add(part)
        }
    }

    val totals = rotated.map {
        val operator = it.last()

        when (operator) {
            "*" -> it.dropLast(1).map(String::toLong).reduce { a,b -> a*b }
            else -> it.dropLast(1).sumOf(String::toLong)

        }
    }

    return totals.sum()
}

fun part2(input: String): Long {
    val lines = readLines(input).map { it.toList() }
    val length = lines.maxOf { it.size }
    val operators = lines.last().filterNot { it == ' ' }.toMutableList()
    val numberLines = lines.dropLast(1)
    val numbers = mutableListOf<Long>()
    val totals = mutableListOf<Long>()
    (0..length).forEach { index ->
        val number = numberLines.map { it.getOrElse(index, {_ -> ' ' }) }.joinToString("").trim()
        if (number.isNotEmpty())
            numbers.add(number.toLong())
        else {
            val operator = operators.removeAt(0)
            val total = when (operator) {
                '*' -> numbers.reduce { a,b -> a*b }
                else -> numbers.sum()
            }
            totals.add(total)
            numbers.clear()
        }
    }

    return totals.sum()
}

fun main() {
    val day = "6"

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
