package day04

import util.*


fun List<List<Char>>.validPosition(row: Int, col: Int): Boolean =
    row in 0..this.lastIndex && col in 0..this.first().lastIndex

fun List<List<Char>>.text(startPos: Pair<Int, Int>, direction: Pair<Int, Int>, delta: IntRange): String {
    val (row, col) = startPos
    val (dRow, dCol) = direction

    val positions = delta.map { row + it * dRow to col + it * dCol }
        .filter { (row, col) -> this.validPosition(row, col) }

    return positions.map { (row, col) -> this[row][col] }.joinToString("")
}

fun part1(input: String) {
    val grid = readLines(input).map { it.toList() }
    var sum = 0
    grid.forEachIndexed { row,line ->
        line.forEachIndexed { col,_ ->
            val directions = listOf(0 to 1, 1 to 0, 1 to 1, 1 to -1)
            for (dir in directions) {
                val text = grid.text(row to col, dir, 0..3)
                if (text == "XMAS" || text == "SAMX")
                    sum += 1
            }
        }
    }
    println(sum)
}

fun part2(input: String) {
    val grid = readLines(input).map { it.toList() }
    var sum = 0
    grid.forEachIndexed { row,line ->
        line.forEachIndexed { col,c ->
            val directions = listOf(1 to 1, 1 to -1)
            val xMas = directions.all {
                val text = grid.text(row to col, it, -1..1)
                text == "MAS" || text == "SAM"
            }
            if (xMas) sum += 1
        }
    }
    println(sum)
}

fun main() {
    val day = "4"

    println("==== Day $day ====")
    val input = getPath(day, "input.txt")
    val test = getPath(day, "test.txt")

    runPart("Part 1") {
        part1(test)
        part1(input)
    }

    runPart("Part 2") {
         part2(test)
         part2(input)
    }
}
