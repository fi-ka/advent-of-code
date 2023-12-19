package day17

import util.*
import kotlin.math.min

data class Pos(var row: Int, var col: Int)
data class State(val pos: Pos, val rowRepeats: Int, val colRepeats: Int)

class HeatLossMap(input:String) {
    private val heatLossMap: Map<Pos, Int>
    private val width: Int
    private val height: Int
    init {
        val lines = readLines(input)
        height = lines.size
        width = lines.first().length
        heatLossMap = lines.flatMapIndexed { row, line ->
            line.mapIndexed { col, heat ->
                Pos(row, col) to heat.digitToInt()
            }
        }.toMap()
    }

    fun pathFrom(startPos: Pos): Long {

        val heatLossByState = mutableMapOf<State, Long>()
        val explore = mutableListOf(State(startPos, 0, 0) to 0L)
        var currentLow = Long.MAX_VALUE
        while (explore.isNotEmpty()) {
            val (state, currentHeatLoss) = explore.removeFirst()
            val (pos, rowRepeats, colRepeats) = state

            if (pos == Pos(height-1, width-1)) {
                currentLow = min(currentLow, currentHeatLoss)
                continue
            }

            if (currentHeatLoss >= currentLow || currentHeatLoss >= heatLossByState.getOrDefault(state, Long.MAX_VALUE))
                continue

            heatLossByState[state] = currentHeatLoss

            val next = buildList {
                when (rowRepeats) {
                    0 -> {
                        add(State(pos.copy(row = pos.row+1), 1, 0))
                        add(State(pos.copy(row = pos.row-1), -1, 0))
                    }
                    in 1..2 -> add(State(pos.copy(row = pos.row+1), rowRepeats+1, 0))
                    in -1 downTo-2 -> add(State(pos.copy(row = pos.row-1), rowRepeats-1, 0))
                }

                when (colRepeats) {
                    0 -> {
                        add(State(pos.copy(col = pos.col+1), 0, 1))
                        add(State(pos.copy(col = pos.col-1), 0, -1))
                    }
                    in 1..2 -> add(State(pos.copy(col = pos.col+1), 0, colRepeats+1))
                    in -1 downTo -2 -> add(State(pos.copy(col = pos.col-1), 0, colRepeats-1))
                }
            }

            val nextFiltered = next
                .filter { it.pos in heatLossMap }
                .map { it to heatLossMap[it.pos]!! + currentHeatLoss }

            explore.addAll(0, nextFiltered)
        }
        return currentLow
    }

    fun pathUltraFrom(startPos: Pos): Long {
        val heatLossByState = mutableMapOf<State, Long>()
        val explore = mutableListOf(State(startPos, 0, 0) to 0L)
        var currentLow = Long.MAX_VALUE
        while (explore.isNotEmpty()) {
            val (state, currentHeatLoss) = explore.removeFirst()
            val (pos, rowRepeats, colRepeats) = state

            if (pos == Pos(height-1, width-1) &&
                (rowRepeats in 4..10 ||
                rowRepeats in -4 downTo -10 ||
                colRepeats in 4..10 ||
                colRepeats in -4 downTo -10)) {
                currentLow = min(currentLow, currentHeatLoss)
                continue
            }

            if (currentHeatLoss >= currentLow || currentHeatLoss >= heatLossByState.getOrDefault(state, Long.MAX_VALUE))
                continue

            heatLossByState[state] = currentHeatLoss

            val next = if (rowRepeats == 0 && colRepeats == 0) {
                buildList {
                    add(State(pos.copy(row = pos.row + 1), 1, 0))
                    add(State(pos.copy(col = pos.col + 1), 0, 1))
                }
            } else {
                buildList {
                    when (rowRepeats) {
                        10, -10 -> {
                            add(State(pos.copy(row = pos.row, col = pos.col+1), 0, 1))
                            add(State(pos.copy(row = pos.row, col = pos.col-1), 0, -1))
                        }

                        in 1..3 -> add(State(pos.copy(row = pos.row + 1), rowRepeats + 1, 0))
                        in 4..9 -> {
                            add(State(pos.copy(row = pos.row, col = pos.col+1), 0, 1))
                            add(State(pos.copy(row = pos.row, col = pos.col-1), 0, -1))
                            add(State(pos.copy(row = pos.row + 1), rowRepeats + 1, 0))
                        }

                        in -1 downTo -3 -> add(State(pos.copy(row = pos.row - 1), rowRepeats - 1, 0))
                        in -4 downTo -9 -> {
                            add(State(pos.copy(row = pos.row, col = pos.col+1), 0, 1))
                            add(State(pos.copy(row = pos.row, col = pos.col-1), 0, -1))
                            add(State(pos.copy(row = pos.row - 1), rowRepeats - 1, 0))
                        }
                    }

                    when (colRepeats) {
                        10, -10 -> {
                            add(State(pos.copy(col = pos.col, row = pos.row+1), 1, 0))
                            add(State(pos.copy(col = pos.col, row = pos.row-1), -1, 0))
                        }

                        in 1..3 -> add(State(pos.copy(col = pos.col + 1), 0, colRepeats + 1))
                        in -1 downTo-3 -> add(State(pos.copy(col = pos.col - 1), 0, colRepeats - 1))
                        in 4..9 -> {
                            add(State(pos.copy(col = pos.col, row = pos.row+1), 1, 0))
                            add(State(pos.copy(col = pos.col, row = pos.row-1), -1, 0))
                            add(State(pos.copy(col = pos.col + 1), 0, colRepeats + 1))
                        }
                        in -4 downTo-9 -> {
                            add(State(pos.copy(col = pos.col, row = pos.row+1), 1, 0))
                            add(State(pos.copy(col = pos.col, row = pos.row-1), -1, 0))
                            add(State(pos.copy(col = pos.col - 1), 0, colRepeats - 1))
                        }
                    }
                }
            }
            val nextFiltered = next
                .filter { it.pos in heatLossMap }
                .map { it to heatLossMap[it.pos]!! + currentHeatLoss }

            explore.addAll(0, nextFiltered)
        }
        return currentLow
    }
}

fun part1(input: String) {
    val heatLossMap = HeatLossMap(input)
    println(heatLossMap.pathFrom(Pos(0,0)))
}

fun part2(input: String) {
    val heatLossMap = HeatLossMap(input)
    println(heatLossMap.pathUltraFrom(Pos(0,0)))
}

fun main() {
    val day = "17"

    println("==== Day $day ====")
    val input = getPath(day, "input.txt")
    val test = getPath(day, "test.txt")
    val test2 = getPath(day, "test2.txt")

    runPart("Part 1") {
        part1(test)
        part1(input)
    }

    runPart("Part 2") {
         part2(test)
         part2(test2)
         part2(input)
    }
}
