package day03

import util.readLines
import util.runPart

private fun Char.isSymbol(): Boolean {
    return !this.isDigit() && this != '.'
}

private fun getSurroundingCells(columns: IntRange, row: Int): Set<Pair<Int, Int>> {
    val before = columns.first - 1
    val after = columns.last + 1
    val surroundingCells = mutableSetOf(
        row to before,
        row to after
    )
    surroundingCells.addAll((before..after).map { row - 1 to it })
    surroundingCells.addAll((before..after).map { row + 1 to it })
    return surroundingCells
}

fun part1(input: String) {
    val lines = readLines(input)

    val symbols = mutableSetOf<Pair<Int,Int>>()
    lines.withIndex().forEach { (row, line) ->
        line.withIndex().forEach { (column, value) ->
            if (value.isSymbol()) {
                symbols.add(row to column)
            }
        }
    }

    val partNumbers = mutableMapOf<Set<Pair<Int,Int>>, Long>()
    lines.withIndex().forEach { (row, line) ->
        val matches = """(\d+)""".toRegex().findAll(line)
        matches.forEach { match ->
            val surroundingCells = getSurroundingCells(match.range, row)
            partNumbers[surroundingCells] = match.value.toLong()
        }
    }

    val result = partNumbers.map { (cells, number) ->
        if (cells.any { it in symbols })
            number
        else
            0L
    }.sum()
    println(result)
}

fun part2(input: String) {
    val lines = readLines(input)

    val symbols = mutableSetOf<Pair<Int,Int>>()
    lines.withIndex().forEach { (row, line) ->
        line.withIndex().forEach { (column, value) ->
            if (value == '*') {
                symbols.add(row to column)
            }
        }
    }

    val partNumbers = mutableMapOf<Set<Pair<Int,Int>>, Long>()
    lines.withIndex().forEach { (row, line) ->
        val matches = """(\d+)""".toRegex().findAll(line)
        matches.forEach { match ->
            val surroundingCells = getSurroundingCells(match.range, row)
            partNumbers[surroundingCells] = match.value.toLong()
        }
    }

    val result = symbols.sumOf { symbolPos ->
        val parts = partNumbers.filter { (surroundingCells) ->
            surroundingCells.contains(symbolPos)
        }.values.toList()
        if (parts.size == 2) {
            parts[0] * parts[1]
        } else {
            0
        }
    }
    println(result)
}

fun main() {
    val day = "3"

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
