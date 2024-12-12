package day12

import day08.isInside
import day08.plus
import day10.adj4
import util.*

fun part1(input: String) {
    val grid = readLines(input).map { it.toList() }
    val maxRow = grid.lastIndex
    val maxCol = grid.first().lastIndex

    val regions = mutableListOf<Int>()
    val visited = mutableSetOf<Pair<Int,Int>>()

    val toVisit = mutableListOf(0 to 0)
    var sum = 0L
    while(toVisit.isNotEmpty()) {
        val node = toVisit.removeFirst()
        if (node in visited) continue
        val region = grid[node.first][node.second]
        val currentToVisit = mutableListOf(node)
        var area = 0
        var perimeter = 0
        while (currentToVisit.isNotEmpty()) {
            val current = currentToVisit.removeFirst()
            if (current in visited) continue

            visited.add(current)

            val adjRegionNodes = adj4
                .map { current.plus(it) }
                .filter { adj -> adj.isInside(maxRow, maxCol) && grid[adj.first][adj.second] == region }

            val adjOtherNodes = adj4
                .map { current.plus(it) }
                .filter { adj -> adj.isInside(maxRow, maxCol) && grid[adj.first][adj.second] != region }

            area += 1
            perimeter += (4 - adjRegionNodes.size)
            currentToVisit.addAll(adjRegionNodes)
            toVisit.addAll(adjOtherNodes)
        }
        sum += area * perimeter
    }
    println(sum)
}

fun part2(input: String) {
    val grid = readLines(input).map { it.toList() }
    val maxRow = grid.lastIndex
    val maxCol = grid.first().lastIndex

    val regions = mutableListOf<Int>()
    val visited = mutableSetOf<Pair<Int,Int>>()

    val toVisit = mutableListOf(0 to 0)
    var sum = 0L
    while(toVisit.isNotEmpty()) {
        val node = toVisit.removeFirst()
        if (node in visited) continue
        val region = grid[node.first][node.second]
        var area = 0
        var perimeter = 0
        var perimetersRow = mutableMapOf<Int, MutableSet<Int>>()
        var perimetersCol = mutableMapOf<Int, MutableSet<Int>>()
        val currentToVisit = mutableListOf(node)
        while (currentToVisit.isNotEmpty()) {
            val current = currentToVisit.removeFirst()
            if (current in visited) continue

            visited.add(current)

            val adjRegion = adj4.map { current.plus(it) }
            val adjRegionNodes = adjRegion
                .filter { adj -> adj.isInside(maxRow, maxCol) && grid[adj.first][adj.second] == region }

            val adjOtherNodes = adjRegion
                .filter { adj -> adj.isInside(maxRow, maxCol) && grid[adj.first][adj.second] != region }

            val columnFences = listOf(0 to -1, 0 to 1)
                .map { current.plus(it) }
                .filter { adj -> !adj.isInside(maxRow, maxCol) || grid[adj.first][adj.second] != region }

            val rowFences = listOf(-1 to 0, 1 to 0)
                .map { current.plus(it) }
                .filter { adj -> !adj.isInside(maxRow, maxCol) || grid[adj.first][adj.second] != region }

            area += 1
            columnFences.forEach { (row, col) ->
                val fenceCol = if (col < current.second) col + 1 else col
                if (fenceCol in perimetersCol)
                    perimetersCol[fenceCol]!!.add(row)
                else
                    perimetersCol[fenceCol] = mutableSetOf(row)
            }

            rowFences.forEach { (row, col) ->
                val fenceRow = if (row < current.first) row + 1 else row
                if (fenceRow in perimetersRow)
                    perimetersRow[fenceRow]!!.add(col)
                else
                    perimetersRow[fenceRow] = mutableSetOf(col)
            }
            currentToVisit.addAll(adjRegionNodes)
            toVisit.addAll(adjOtherNodes)
        }

        val periCol2 = perimetersCol.map { (col, it) ->
            val fences = it.sorted()
            val first = fences.first()
            fences.drop(1).fold(listOf(first..first)) { acc, curr ->
                val range = acc.last()
                if (curr == range.last + 1 && perimetersRow[curr]?.any {it == col} != true)
                    acc.dropLast(1).plus(listOf(range.first..curr))
                else
                    acc.plus(listOf(curr..curr))
            }
        }
        val periRow2 = perimetersRow.map { (row, it) ->
            val fences = it.sorted()
            val first = fences.first()
            fences.drop(1).fold(listOf(first..first)) { acc, curr ->
                val range = acc.last()
                if (curr == range.last + 1  && perimetersCol[curr]?.any {it == row} != true)
                    acc.dropLast(1).plus(listOf(range.first..curr))
                else
                    acc.plus(listOf(curr..curr))
            }
        }

        perimeter = periCol2.sumOf { it.size } + periRow2.sumOf { it.size }
        sum += area * perimeter
    }

    println(sum)
}

fun main() {
    val day = "12"

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
