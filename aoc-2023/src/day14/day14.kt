package day14

import util.*

fun part1(input: String) {
    val lines = readLines(input)

    val map = lines.map {line ->
        line.toMutableList()
    }

    var rollingRocks = map.flatMapIndexed { rowIdx, row ->
        row.withIndex().filter { it.value == 'O' }.map { rowIdx to it.index }
    }
    rollingRocks = rollingRocks.map { (row, col) ->
        map[row][col] = '.'

        val newRowIdx = (row downTo 1).firstOrNull {
            map[it-1][col] != '.'
        } ?: 0

        map[newRowIdx][col] = 'O'
        newRowIdx to col
    }

    val sum = rollingRocks.sumOf {(row, col) ->
        lines.size - row
    }

    println(sum)
}

fun part2(input: String) {
    val lines = readLines(input)

    val map = lines.map {line ->
        line.toMutableList()
    }

    val width = map.first().size
    val height = map.first().size

    val rollingRocksFromNorth = { -> map.withIndex().flatMap { (rowIdx, row) ->
            row.withIndex().filter { it.value == 'O' }.map { rowIdx to it.index }
        }
    }

    val rollingRocksFromSouth = { -> map.withIndex().reversed().flatMap { (rowIdx, row) ->
            row.withIndex().filter { it.value == 'O' }.map { rowIdx to it.index }
        }
    }

    val rollingRocksFromWest = { -> map.first().indices.flatMap { colIdx ->
            map.withIndex().filter { it.value[colIdx] == 'O' }.map { it.index to colIdx }
        }
    }

    val rollingRocksFromEast = { -> map.first().indices.reversed().flatMap { colIdx ->
            map.withIndex().filter { it.value[colIdx] == 'O' }.map { it.index to colIdx }
        }
    }

    val tiltSequence = sequence { while(true) yieldAll(listOf("north", "west", "south", "east")) }
    val iterations = mutableMapOf<String, Int>()
    var remainingIterations: Int? = null
    var cycle = 0

    while(remainingIterations != 0) {
        cycle += 1
        tiltSequence.take(4).forEach { tilt ->

            val rocks = when(tilt) {
                "north" -> rollingRocksFromNorth()
                "east" -> rollingRocksFromEast()
                "south" -> rollingRocksFromSouth()
                "west" -> rollingRocksFromWest()
                else -> throw RuntimeException("Not possible")
            }

            rocks.forEach { (row, col) ->
                map[row][col] = '.'

                val indexes = when(tilt) {
                    "north" -> (row downTo 0).map {it to col}
                    "east" -> (col..<width).map {row to it}
                    "south" -> (row..<height).map { it to col }
                    "west" -> (col downTo 0).map { row to it }
                    else -> throw RuntimeException("Not possible")
                }

                val newIdx = indexes
                    .zipWithNext()
                    .firstOrNull { (_, next) ->
                        map[next.first][next.second] != '.'
                    }?.first
                    ?: indexes.lastOrNull()
                    ?: (row to col)

                map[newIdx.first][newIdx.second] = 'O'
            }
        }

        if (remainingIterations != null) {
            remainingIterations -= 1
        } else {
            val state = map.joinToString { it.joinToString("") }
            if (state in iterations) {
                val loopSize = cycle - iterations[state]!!
                remainingIterations = (1_000_000_000 - cycle) % loopSize
            }
            iterations[state] = cycle
        }
    }


    val sum = rollingRocksFromNorth().sumOf { (row, _) ->
        height - row
    }

    println(sum)
}

fun main() {
    val day = "14"

    println("==== Day $day ====")
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
