package day11

import util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun day11(input: String, expansion: Long) {
    val lines = readLines(input)

    val rowsToExpand = lines.indices.filter { idx -> lines[idx].all { it == '.'} }
    val colsToExpand = lines.first().indices.filter { idx -> lines.all { it[idx] == '.'} }

    val galaxies = mutableSetOf<Pair<Int,Int>>()
    lines.forEachIndexed { row, line ->
        line.forEachIndexed { col, c ->
            if (c == '#')
                galaxies.add(row to col)
        }
    }

    val checked = mutableSetOf<Pair<Pair<Int,Int>, Pair<Int,Int>>>()
    val sum = galaxies.sumOf { a ->
        galaxies.sumOf { b ->
            if (a to b !in checked) {
                checked.add(a to b)
                checked.add(b to a)
                val expandedRowsCount = rowsToExpand.count { it in min(a.first, b.first)..max(a.first, b.first) }
                val expandedColCount = colsToExpand.count { it in min(a.second, b.second)..max(a.second, b.second) }
                val sum = abs(b.first-a.first) + abs(b.second-a.second) +
                        expandedRowsCount * (expansion - 1) +
                        expandedColCount * (expansion - 1)
                sum
            } else {
                0L
            }
        }
    }
    println(sum)
}

fun main() {
    val day = "11"

    println("--- Day $day ---")
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
