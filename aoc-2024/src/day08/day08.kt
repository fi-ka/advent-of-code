package day08

import util.*

fun Pair<Int,Int>.isInside(rowMax: Int, colMax: Int) =
    this.first in 0..rowMax && this.second in 0..colMax

fun Pair<Int,Int>.plus(pair: Pair<Int,Int>) =
    this.first + pair.first to this.second + pair.second

fun Pair<Int,Int>.minus(pair: Pair<Int,Int>) =
    this.first - pair.first to this.second - pair.second

fun getAntennas(lines: List<String>): MutableMap<Char, MutableList<Pair<Int, Int>>> {
    val antennas = mutableMapOf<Char, MutableList<Pair<Int,Int>>>()
    lines.forEachIndexed { row, line ->
        line.forEachIndexed { col, c ->
            if (c != '.') {
                antennas.getOrPut(c) { mutableListOf() }
                    .add(row to col)
            }
        }
    }
    return antennas
}

fun part1(input: String) {
    val lines = readLines(input)
    val rowMax = lines.lastIndex
    val colMax = lines.first().lastIndex

    val antennas = getAntennas(lines)

    val anti = mutableSetOf<Pair<Int, Int>>()
    antennas.forEach { _, positions ->
        positions.pairs().forEach { (a, b) ->
            val diff = a.minus(b)

            val anti1 = a.plus(diff)
            if (anti1.isInside(rowMax, colMax))
                anti.add(anti1)

            val anti2 = b.minus(diff)
            if (anti2.isInside(rowMax, colMax))
                anti.add(anti2)
        }
    }

    println(anti.size)
}

fun part2(input: String) {
    val lines = readLines(input)
    val rowMax = lines.lastIndex
    val colMax = lines.first().lastIndex

    val antennas = getAntennas(lines)

    val antiNodes = mutableSetOf<Pair<Int, Int>>()
    antennas.forEach { _, positions ->
        positions.pairs().forEach { (a, b) ->
            val diff = a.minus(b)

            var anti1 = a
            while (anti1.isInside(rowMax, colMax)) {
                antiNodes.add(anti1)
                anti1 = anti1.plus(diff)
            }

            var anti2 = b
            while (anti2.isInside(rowMax, colMax)) {
                antiNodes.add(anti2)
                anti2 = anti2.minus(diff)
            }
        }
    }

    println(antiNodes.size)
}

fun main() {
    val day = "8"

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
