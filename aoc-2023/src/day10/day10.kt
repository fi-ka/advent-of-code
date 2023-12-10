package day10

import util.*

fun part1(input: String) {
    val lines = readLines(input)
    lateinit var startPosition: Pair<Int, Int>
    val pipes = lines.mapIndexed { r, line ->
        line.mapIndexed { c, v ->
            when (v) {
                '|' -> listOf(r-1 to c, r+1 to c)
                '-' -> listOf(r to c-1, r to c+1)
                'L' -> listOf(r-1 to c, r to c+1)
                'J' -> listOf(r-1 to c, r to c-1)
                '7' -> listOf(r to c-1, r+1 to c)
                'F' -> listOf(r to c+1, r+1 to c)
                'S' -> {
                    startPosition = r to c
                    emptyList()
                }
                else -> emptyList()
            }
        }
    }
    val startConnections = mutableListOf<Pair<Int,Int>>()
    for (r in -1..1) {
        for (c in -1..1) {
            if (r == 0 && c == 0) continue

            val pos = startPosition.first+r to startPosition.second+c
            if (pos.first !in 0..pipes.lastIndex ||
                pos.second !in 0..pipes.first().lastIndex) continue

            if (pipes[pos.first][pos.second].contains(startPosition))
                startConnections.add(pos)
        }
    }
    var steps = 1
    var positions = startConnections.toSet()
    var previous = setOf(startPosition)
    while(positions.size > 1) {

        val nextPositions = positions.map { pos ->
            val connections = pipes[pos.first][pos.second]
            connections.first { it !in previous }
        }.toSet()
        previous = positions
        positions = nextPositions
        steps += 1
    }

    println(steps)
}

fun part2(input: String) {
    val lines = readLines(input)

    // Parse pipe map and startposition
    lateinit var startPosition: Pair<Int, Int>
    val pipes = lines.mapIndexed { r, line ->
        line.mapIndexed { c, v ->
            when (v) {
                '|' -> listOf(r-1 to c, r+1 to c)
                '-' -> listOf(r to c-1, r to c+1)
                'L' -> listOf(r-1 to c, r to c+1)
                'J' -> listOf(r-1 to c, r to c-1)
                '7' -> listOf(r to c-1, r+1 to c)
                'F' -> listOf(r to c+1, r+1 to c)
                'S' -> {
                    startPosition = r to c
                    emptyList()
                }
                else -> {
                    emptyList()
                }
            }
        }.toMutableList()
    }

    // Add the connections of the start position to the pipe map
    val startConnections = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
        .map { startPosition.first + it.first to startPosition.second + it.second }
        .filter { it.first in 0..pipes.lastIndex && it.second in 0..pipes.first().lastIndex }
        .filter { pipes[it.first][it.second].contains(startPosition) }
    pipes[startPosition.first][startPosition.second] = startConnections

    // Find loop
    val loop = mutableSetOf(startPosition)
    var positions = startConnections.toSet()
    var lastPositions = setOf(startPosition)
    while (positions.size > 1) {
        val nextPositions = positions.map { pos ->
            val connections = pipes[pos.first][pos.second]
            connections.first { it !in lastPositions }
        }.toSet()
        loop.addAll(positions)
        lastPositions = positions
        positions = nextPositions
    }
    loop.addAll(positions)

    // Find positions inside the loop
    // Search the pipe map by traversing positions in between tiles
    val inside = mutableSetOf<Pair<Int, Int>>()
    val allVisited = mutableSetOf<Pair<Int, Int>>()
    for (r in 0..<pipes.lastIndex) {
        for (c in 0..<pipes.first().lastIndex) {
            if (r to c in allVisited) continue

            var outside = false
            val seenTiles = mutableSetOf<Pair<Int, Int>>()
            val visitedPositions = mutableSetOf<Pair<Int, Int>>()
            val toVisit = ArrayDeque(listOf(r to c))

            while(toVisit.isNotEmpty()) {
                val current = toVisit.removeFirst()
                visitedPositions.add(current)

                val topLeft = current.first to current.second
                val topRight = current.first to current.second + 1
                val bottomLeft = current.first+1 to current.second
                val bottomRight = current.first+1 to current.second + 1
                seenTiles.addAll(
                    listOf(topLeft, topRight, bottomLeft, bottomRight)
                    .filter { it !in loop }
                    .filter { pos ->
                        pos.first in 0..pipes.lastIndex &&
                                pos.second in 0..pipes.first().lastIndex
                    })

                // Moving is blocked by connected loop pipes
                val isBlocked = {a: Pair<Int,Int>, b: Pair<Int,Int> ->
                    pipes[a.first][a.second].contains(b) &&
                            pipes[b.first][b.second].contains(a) &&
                            a in loop
                }
                // Add non-blocked moves
                val moves = mutableSetOf<Pair<Int,Int>>()
                if (!isBlocked(topLeft, topRight))
                    moves.add(current.first-1 to current.second)
                if (!isBlocked(topLeft, bottomLeft))
                    moves.add(current.first to current.second-1)
                if (!isBlocked(bottomLeft, bottomRight))
                    moves.add(current.first+1 to current.second)
                if (!isBlocked(bottomRight, topRight))
                    moves.add(current.first to current.second+1)

                // Remember if any move reaches outside the pipe map
                outside = outside || moves.any { pos ->
                    pos.first == -1 || pos.first == pipes.lastIndex ||
                            pos.second == -1 || pos.second == pipes.first().lastIndex
                }

                // Add new moves to visit
                val availableMoves = moves
                    .filter { pos ->
                        pos.first in 0..<pipes.lastIndex &&
                                pos.second in 0..<pipes.first().lastIndex
                    }
                    .filter { it !in visitedPositions && it !in toVisit }
                toVisit.addAll(availableMoves)
            }

            allVisited.addAll(visitedPositions)

            // If no move reached the outside of the map, all seen positions are inside the loop.
            if (!outside) {
                inside.addAll(seenTiles)
            }
        }
    }

    println(inside.size)
    println()
}

fun part2EvenOdd(input: String) {
    val lines = readLines(input)

    // Parse pipe map and startposition
    lateinit var startPosition: Pair<Int, Int>
    val pipes = lines.mapIndexed { r, line ->
        line.mapIndexed { c, v ->
            when (v) {
                '|' -> listOf(r-1 to c, r+1 to c)
                '-' -> listOf(r to c-1, r to c+1)
                'L' -> listOf(r-1 to c, r to c+1)
                'J' -> listOf(r-1 to c, r to c-1)
                '7' -> listOf(r to c-1, r+1 to c)
                'F' -> listOf(r to c+1, r+1 to c)
                'S' -> {
                    startPosition = r to c
                    emptyList()
                }
                else -> {
                    emptyList()
                }
            }
        }.toMutableList()
    }

    // Add the connections of the start position to the pipe map
    val startConnections = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
        .map { startPosition.first + it.first to startPosition.second + it.second }
        .filter { it.first in 0..pipes.lastIndex && it.second in 0..pipes.first().lastIndex }
        .filter { pipes[it.first][it.second].contains(startPosition) }
    pipes[startPosition.first][startPosition.second] = startConnections

    // Find loop
    val loop = mutableSetOf(startPosition)
    var positions = startConnections.toSet()
    var lastPositions = setOf(startPosition)
    while (positions.size > 1) {
        val nextPositions = positions.map { pos ->
            val connections = pipes[pos.first][pos.second]
            connections.first { it !in lastPositions }
        }.toSet()
        loop.addAll(positions)
        lastPositions = positions
        positions = nextPositions
    }
    loop.addAll(positions)

    val isConnected = {a: Pair<Int,Int>, b: Pair<Int,Int> ->
        pipes[a.first][a.second].contains(b) &&
                pipes[b.first][b.second].contains(a) &&
                a in loop
    }

    // Find positions inside the loop
    // Search the pipe map by traversing positions in between tiles
    val inside = mutableSetOf<Pair<Int, Int>>()
    val allVisited = mutableSetOf<Pair<Int, Int>>()
    for (r in 0..pipes.lastIndex) {
        for (c in 0..pipes.first().lastIndex) {
            if (r to c in allVisited || r to c in loop) continue

            // Check loop segments to the right
            val rightTiles = generateSequence(r to c) { it.first to it.second + 1 }
                .takeWhile { it.second <= pipes.first().lastIndex }


            val connections = mutableSetOf<Pair<Int,Int>>()
            var lastLoopTile: Pair<Int,Int>? = null
            var count = 0

            rightTiles.forEach { tile ->
                if (lastLoopTile != null && isConnected(lastLoopTile!!, tile)) {
                    lastLoopTile = tile
                    connections.addAll(pipes[tile.first][tile.second])
                } else {
                    if (connections.map { it.first }.distinct().size == 2) {
                        count += 1
                    }
                    connections.clear()

                    if (tile in loop) {
                        count += 1
                        connections.add(tile)
                        connections.addAll(pipes[tile.first][tile.second])
                        lastLoopTile = tile
                    } else {
                        lastLoopTile = null
                    }
                }
            }
            if (connections.map { it.first }.distinct().size == 2) {
                count += 1
            }
            if (count % 2 != 0) {
                inside.add(r to c)
            }
        }
    }

    println(inside.size)
    println()
}

fun main() {
    val day = "10"

    println("--- Day $day ---")
    val input = """src/day${day.padStart(2, '0')}/input.txt"""
    val test = """src/day${day.padStart(2, '0')}/test.txt"""
    val test2 = """src/day${day.padStart(2, '0')}/test2.txt"""
    val test3 = """src/day${day.padStart(2, '0')}/test3.txt"""
    val test4 = """src/day${day.padStart(2, '0')}/test4.txt"""
    val test5 = """src/day${day.padStart(2, '0')}/test5.txt"""

    runPart("Part 1") {
//        part1(test)
//        part1(test2)
        part1(input)
    }

    runPart("Part 2") {
//        part2(test)
//        part2(test2)
//        part2(test3)
//        part2(test4)
//        part2(test5)
        part2(input)
    }
}
