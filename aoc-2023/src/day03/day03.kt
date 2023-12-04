package day03

import util.readLines
import util.runPart

fun Char.isSymbol(): Boolean {
    return !this.isDigit() && this != '.'
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

    var currentNumber = ""
    val partNumbers = mutableListOf<Long>()
    lines.withIndex().forEach { (row, line) ->
        line.withIndex().forEach { (column, value) ->
            if (value.isDigit()) {
                currentNumber += value
            }

            if (currentNumber.isNotEmpty() && (!value.isDigit() || column == line.lastIndex)) {
                val startColumn = if (!value.isDigit())
                    column - currentNumber.length - 1
                else
                    column - currentNumber.length

                val surroundingCells = mutableSetOf(
                    row to startColumn,
                    row to column
                )
                surroundingCells.addAll((startColumn..column).map { row-1 to it })
                surroundingCells.addAll((startColumn..column).map { row+1 to it })

                if (surroundingCells.any { it in symbols }) {
                    partNumbers.add(currentNumber.toLong())
                }

                currentNumber = ""
            }
        }
    }
    println(partNumbers.sum())
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

    var currentNumber = ""
    val partNumbers = mutableMapOf<Set<Pair<Int,Int>>, Long>()
    lines.withIndex().forEach { (row, line) ->
        line.withIndex().forEach { (column, value) ->
            if (value.isDigit()) {
                currentNumber += value
            }

            if (currentNumber.isNotEmpty() && (!value.isDigit() || column == line.lastIndex)) {
                val startColumn = if (!value.isDigit())
                    column - currentNumber.length - 1
                else
                    column - currentNumber.length

                val surroundingCells = mutableSetOf(
                    row to startColumn,
                    row to column
                )
                surroundingCells.addAll((startColumn..column).map { row-1 to it })
                surroundingCells.addAll((startColumn..column).map { row+1 to it })

                if (surroundingCells.any { it in symbols }) {
                    partNumbers[surroundingCells] = currentNumber.toLong()
                }

                currentNumber = ""
            }
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
