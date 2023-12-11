package day11

import util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private data class Galaxy(val row: Int, val col: Int)

private fun Pair<Galaxy, Galaxy>.shortestPath(): Int {
    return abs(this.first.row-this.second.row) + abs(this.first.col-this.second.col)
}

private fun Pair<Galaxy, Galaxy>.spansRow(col: Int): Boolean {
    return col in min(this.first.row, this.second.row)..max(this.first.row, this.second.row)
}

private fun Pair<Galaxy, Galaxy>.spansColumn(row: Int): Boolean {
    return row in min(this.first.col, this.second.col)..max(this.first.col, this.second.col)
}

fun day11(input: String, expansion: Long) {
    val lines = readLines(input)

    val height = lines.indices
    val width = lines.first().indices

    val rowsToExpand = height.filter { idx -> lines[idx].all { it == '.'} }
    val colsToExpand = width.filter { idx -> lines.all { it[idx] == '.'} }

    val galaxies = buildList {
        lines.forEachIndexed { row, line ->
            line.forEachIndexed { col, c ->
                if (c == '#')
                    add(Galaxy(row, col))
            }
        }
    }

    val sum = galaxies.pairs().sumOf { pair ->
        val expandedRowsCount = rowsToExpand.count { pair.spansRow(it) }
        val expandedColCount = colsToExpand.count { pair.spansColumn(it) }

        val sum = pair.shortestPath() +
                expandedRowsCount * (expansion - 1) +
                expandedColCount * (expansion - 1)
        sum
    }
    println(sum)
}

fun main() {
    val day = "11"

    println("==== Day $day ====")
    val input = """src/day${day.padStart(2, '0')}/input.txt"""
    val test = """src/day${day.padStart(2, '0')}/test.txt"""

    runPart("Part 1") {
        day11(test, 2L)
        day11(input, 2L)
    }

    runPart("Part 2") {
         day11(test, 10L)
         day11(test, 100L)
         day11(input, 1000000L)
    }
}
