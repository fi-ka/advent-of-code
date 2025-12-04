package day04

import util.*

private fun Pair<Int,Int>.neighbours() : List<Pair<Int,Int>> {
    val indexes = (-1..1).flatMap { xDelta ->
        (-1..1).mapNotNull { yDelta ->
            if (yDelta == 0 && xDelta == 0)
                null
            else
                Pair(this.first + xDelta, this.second + yDelta)
        }
    }

    return indexes
}

private fun List<Pair<Int,Int>>.filterInbounds(rows: Int, columns: Int): List<Pair<Int, Int>> {
    return this.filter { it.first >= 0 && it.second >= 0 && it.first < rows && it.second < columns}
}

private fun findAccessiblePaperRolls(
    grid: List<MutableList<Char>>
): MutableList<Pair<Int, Int>> {
    val rows = grid.size
    val columns = grid.first().size

    val accessible = mutableListOf<Pair<Int,Int>>()
    grid.forEachIndexed { x, row ->
        row.forEachIndexed { y, pos ->
            if (pos == '@') {
                val position = x to y
                val neighbourIndexes = position.neighbours().filterInbounds(rows, columns)

                if (neighbourIndexes.count { grid[it.first][it.second] == '@' } < 4)
                    accessible.add(position)
            }
        }
    }

    return accessible
}

fun part1(input: String): Int {
    val grid = readLines(input).map { it.toMutableList() }

    val paperRolls = findAccessiblePaperRolls(grid)
    return paperRolls.count()
}

fun part2(input: String): Int {
    val grid = readLines(input).map { it.toMutableList() }

    var removed = 0
    do {
        val paperRolls = findAccessiblePaperRolls(grid)
        paperRolls.forEach { (row, column) ->
            grid[row][column] = '.'
            removed++
        }
    } while (paperRolls.isNotEmpty())

    return removed
}

fun main() {
    val day = "4"

    println("==== Day $day ====")
    val input = getPath(day, "input.txt")
    val test = getPath(day, "test.txt")

    measurePart("Part 1", 10) {
        // part1(test)
        part1(input)
    }

    measurePart("Part 2", 10) {
        //part2(test)
        part2(input)
    }
}
