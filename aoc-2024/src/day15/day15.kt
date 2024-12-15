package day15

import day08.plus
import util.*

fun printMapGrid(mapGrid: Map<Pair<Int,Int>, Char>, robot: Pair<Int,Int>) {
    val maxRow = mapGrid.keys.maxOf { it.first }
    val maxCol = mapGrid.keys.maxOf { it.second }
    for (row in 0..maxRow) {
        for (col in 0..maxCol) {
            if (row to col == robot) print('@')
            else if (row to col !in mapGrid) print('.')
            else print(mapGrid[row to col])
        }
        println()
    }
}

fun part1(input: String) {
    val (map, moves) = readBlocks(input)
    val objects = mutableMapOf<Pair<Int,Int>, Char>()
    var robot: Pair<Int,Int> = 0 to 0
    map.forEachIndexed { row, line ->
        line.forEachIndexed { col, c ->
            when (c) {
                '#' -> objects[row to col] = c
                'O' -> objects[row to col] = c
                '@' -> robot = row to col
            }
        }
    }
    val moveToDirection = mapOf(
        '^' to (-1 to 0),
        '>' to (0 to 1),
        '<' to (0 to -1),
        'v' to (1 to 0),
    )
    moves.joinToString("").forEach { move ->
        val direction = moveToDirection[move]!!
        val passed = mutableListOf<Pair<Int,Int>>()
        var nextPos = robot
        do {
            nextPos = nextPos.plus(direction)
            passed.add(nextPos)
        } while (objects[passed.last()] == 'O')

        if (passed.last() !in objects) {
            if (passed.size == 1) {
                robot = passed.last()
            } else {
                robot = passed.first()
                objects.remove(passed.first())
                objects[passed.last()] = 'O'
            }
        }
//        println(move)
//        printMapGrid(objects, robot)
    }
    objects.filterValues { it == 'O' }.keys.sumOf { it.first*100 + it.second }.println()
}

fun part2(input: String) {
    val (map, moves) = readBlocks(input)
    val objects = mutableMapOf<Pair<Int,Int>, Char>()
    var robot: Pair<Int,Int> = 0 to 0
    map.forEachIndexed { row, line ->
        line.forEachIndexed { col, c ->
            when (c) {
                '#' -> {
                    objects[row to col * 2] = '#'
                    objects[row to col * 2 + 1] = '#'
                }
                'O' -> {
                    objects[row to col * 2] = '['
                    objects[row to col * 2 + 1] = ']'
                }
                '@' -> robot = row to col*2
            }
        }
    }
    val moveToDirection = mapOf(
        '^' to (-1 to 0),
        '>' to (0 to 1),
        '<' to (0 to -1),
        'v' to (1 to 0),
    )
    //printMapGrid(objects, robot)
    moves.joinToString("").forEach { move ->
        val direction = moveToDirection[move]!!
        val passed = mutableListOf<Set<Pair<Int,Int>>>()
        var nextPosRange = setOf(robot)
        do {

            nextPosRange = nextPosRange.flatMap {
                val p = it.plus(direction)
                when (objects[p]) {
                    '[' -> if (direction.second != -1) listOf(p, p.copy(second = p.second+1)) else listOf(p)
                    ']' -> if (direction.second != 1) listOf(p.copy(second = p.second-1), p) else listOf(p)
                    '#' -> listOf(p)
                    else -> listOf()
                }
            }.toSet()
            passed.add(nextPosRange)

        } while (nextPosRange.any { it in objects } && nextPosRange.none { objects[it] == '#' })

        if (passed.last().isEmpty()) {
            if (passed.size == 1) {
                robot = robot.plus(direction)
            } else {
                robot = robot.plus(direction)
                val newPos = mutableListOf<Pair<Pair<Int,Int>, Char>>()
                passed.flatten().toSet().forEach {
                    val c = objects.remove(it)!!
                    newPos.add(it.plus(direction) to c)
                }
                objects.putAll(newPos)
            }
        }
        //println(move)
        //printMapGrid(objects, robot)
    }
    //printMapGrid(objects, robot)
    objects.filterValues { it == '['  }.keys.sumOf { it.first*100 + it.second }.println()

}

fun main() {
    val day = "15"

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
