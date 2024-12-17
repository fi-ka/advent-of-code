package day16

import day08.isInside
import day08.plus
import util.*

val adj4 = listOf(-1 to 0, 0 to 1, 1 to 0, 0 to -1)
fun Pair<Int,Int>.left() = adj4[(adj4.indexOf(this) + adj4.size - 1) % adj4.size]
fun Pair<Int,Int>.right() = adj4[(adj4.indexOf(this) + 1) % adj4.size]

data class State(val path: List<Pair<Int,Int>>, val pos: Pair<Int,Int>, val direction: Pair<Int, Int>, val score: Long)

fun findPath(grid: List<List<Char>>, startPos: Pair<Int, Int>, startDirection: Pair<Int, Int>, end: Pair<Int, Int>): Long {
    val cache: MutableMap<Pair<Pair<Int,Int>, Pair<Int,Int>>, Long> = mutableMapOf()
    val queue = ArrayDeque<State>(listOf(State(listOf(), startPos, startDirection, 0)))
    var minScores = mutableListOf<State>()

    while (queue.isNotEmpty()) {
        val state = queue.removeFirst()
        val (path, pos, direction, score) = state

        if (cache.getOrDefault(pos to direction, Long.MAX_VALUE) < score)
            continue
        cache[pos to direction] = state.score

        if (score > (minScores.firstOrNull()?.score ?: Long.MAX_VALUE)) continue

        if (pos == end) {
            if (minScores.isEmpty())
                minScores.add(state)
            else if (minScores.first().score < score) {
                continue
            } else if (minScores.first().score == score) {
                minScores.add(state)
            } else {
                minScores = mutableListOf(state)
            }

            continue
        }

        for ((newDir, scoreDelta) in listOf(direction.left() to 1001L, direction to 1L, direction.right() to 1001L)) {
            val newPos = pos.plus(newDir)
            if (newPos.isInside(grid.lastIndex, grid.first().lastIndex) && grid[pos.first][pos.second] != '#') {
                queue.addFirst(State(path + listOf(pos), newPos, newDir, score + scoreDelta))
            }
        }
    }

    val nodes = minScores.flatMap {  it.path }.toSet().size + 1
    nodes.println()
    return minScores.first().score
}

fun part(input: String) {
    val grid = readLines(input).map { it.toList() }
    val maxRow = grid.lastIndex
    val maxCol = grid.first().lastIndex
    var pos = 0 to 0
    var end = 0 to 0
    var direction = 0 to 1
    for (row in 0..maxRow) {
        for (col in 0..maxCol) {
            when (grid[row][col]) {
                'E' -> end = row to col
                'S' -> pos = row to col
            }
        }
    }
    findPath(grid, pos, direction, end).println()
}

fun main() {
    val day = "16"

    println("==== Day $day ====")
    val input = getPath(day, "input.txt")
    val test = getPath(day, "test.txt")

    runPart("Part 1 and 2") {
        part(test)
        part(input)
    }
}
