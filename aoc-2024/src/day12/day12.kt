package day12

import day08.isInside
import day08.plus
import day10.adj4
import util.*

fun part1(input: String) {
    val grid = readLines(input).map { it.toList() }
    val maxRow = grid.lastIndex
    val maxCol = grid.first().lastIndex

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

    val visited = mutableSetOf<Pair<Int,Int>>()

    val toVisit = mutableListOf(0 to 0)
    var sum = 0L
    while(toVisit.isNotEmpty()) {
        val node = toVisit.removeFirst()
        if (node in visited) continue
        val region = grid[node.first][node.second]
        var area = 0
        var perimeter = 0
        var rowFences = mutableMapOf<Int, MutableSet<Int>>()
        var colFences = mutableMapOf<Int, MutableSet<Int>>()
        val currentToVisit = mutableListOf(node)
        while (currentToVisit.isNotEmpty()) {
            val current = currentToVisit.removeFirst()
            if (current in visited) continue

            visited.add(current)
            adj4.forEach { step ->
                val adj = current.plus(step)
                val adjIsInside = adj.isInside(maxRow, maxCol)
                val adjIsInRegion = adjIsInside && grid[adj.first][adj.second] == region

                if (adjIsInRegion) {
                    currentToVisit.add(adj)
                } else if (adjIsInside) {
                    toVisit.add(adj)
                }

                if (!adjIsInRegion) {
                    val rowStep = step.first != 0
                    if (rowStep) {
                        val fenceRow = if (step.first == -1) adj.first + 1 else adj.first
                        rowFences.getOrPut(fenceRow) { mutableSetOf() }.add(adj.second)
                    } else {
                        val fenceCol = if (step.second == -1) adj.second + 1 else adj.second
                        colFences.getOrPut(fenceCol) { mutableSetOf() }.add(adj.first)
                    }
                }
            }

            area += 1
        }

        val mergedColFences = colFences.map { (col, it) ->
            val fences = it.sorted()
            val first = fences.first()
            fences.drop(1).fold(listOf(first..first)) { acc, row ->
                val fence = acc.last()
                if (row == fence.last + 1 && rowFences[row]?.any {it == col} != true)
                    acc.dropLast(1) + listOf(fence.first..row)
                else
                    acc + listOf(row..row)
            }
        }

        val mergedRowFences = rowFences.map { (row, it) ->
            val fences = it.sorted()
            val first = fences.first()
            fences.drop(1).fold(listOf(first..first)) { acc, col ->
                val fence = acc.last()
                if (col == fence.last + 1  && colFences[col]?.any {it == row} != true)
                    acc.dropLast(1) + listOf(fence.first..col)
                else
                    acc + listOf(col..col)
            }
        }

        perimeter = mergedColFences.sumOf { it.size } + mergedRowFences.sumOf { it.size }
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
//        part1(test)
        part1(input)
    }

    runPart("Part 2") {
//         part2(test)
         part2(input)
    }
}
