package day08

import util.*
import java.lang.RuntimeException

fun part1(input: String) {
    val blocks = readBlocks(input)
    val instructions = blocks.first().first()
    val network = blocks[1]
        .map {
            it.parse("""(\w+) = \((\w+), (\w+)\)""") }
        .associate { (key, left, right) ->
        key to (left to right)
    }

    var steps = 0
    var position = "AAA"
    while (position != "ZZZ") {
        for (i in instructions) {
            when (i) {
                'L' -> position = network[position]!!.first
                'R' -> position = network[position]!!.second
            }
            steps += 1
            if (position == "ZZZ") break
        }
    }

    println(steps)
}

fun part2(input: String) {
    val blocks = readBlocks(input)
    val instructions = blocks.first().first()
    val network = blocks[1]
        .map {
            it.parse("""(\w+) = \((\w+), (\w+)\)""") }
        .associate { (key, left, right) ->
            key to (left to right)
        }

    var positions = network.keys.filter { it.endsWith('A') }
    val stepsToZ = MutableList<Long?>(positions.size) { null }
    var steps = 0L

    while (!positions.all { it.endsWith('Z') }) {
        for (i in instructions) {
            positions = positions.map { position ->
                when (i) {
                    'L' -> network[position]!!.first
                    'R' -> network[position]!!.second
                    else -> throw RuntimeException("Should not get here")
                }
            }
            steps += 1

            positions.forEachIndexed { index, it ->
                if (stepsToZ[index] == null && it.endsWith('Z')) {
                    stepsToZ[index] = steps
                }
            }
            if (stepsToZ.all { it != null } ) break
            if (positions.all { it.endsWith('Z') }) break
        }
        if (stepsToZ.all { it != null } ) break
    }

    steps = 0L
    val maxStep = stepsToZ.maxOf { it!! }
    while (true) {
        steps += maxStep
        if (stepsToZ.all { steps % it!! == 0L }) {
            break
        }
    }
    println(steps)
}

fun main() {
    val day = "8"

    println("--- Day $day ---")
    val input = """src/day${day.padStart(2, '0')}/input.txt"""
    val test1 = """src/day${day.padStart(2, '0')}/test1.txt"""
    val test2 = """src/day${day.padStart(2, '0')}/test2.txt"""

    runPart("Part 1") {
        part1(test1)
        part1(input)
    }

    runPart("Part 2") {
         part2(test2)
         part2(input)
    }
}
