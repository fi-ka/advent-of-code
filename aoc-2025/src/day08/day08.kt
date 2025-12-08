package day08

import util.*
import kotlin.math.pow
import kotlin.math.sqrt

private fun List<Double>.distance(second: List<Double>) =
    sqrt((this[0] - second[0]).pow(2) + (this[1] - second[1]).pow(2) + (this[2] - second[2]).pow(2))

fun part1(input: String, connections: Int): Int {
    val boxes = readLines(input)
        .map { line -> line.split(",").map { it.toDouble() } }

    val pairs = boxes.pairs()
    val pairsByDistance = pairs.sortedBy { it.first.distance(it.second) }.take(connections)
    val circuits = boxes.map { setOf(it) }.toMutableList()

    pairsByDistance.forEach { (box1, box2) ->
        val firstCircuit = circuits.first { box1 in it}
        val secondCircuit = circuits.first { box2 in it}

        circuits.remove(firstCircuit)
        circuits.remove(secondCircuit)
        circuits.add(firstCircuit + secondCircuit)
    }

    val largestGroups = circuits.sortedByDescending { it.size }

    return largestGroups[0].size * largestGroups[1].size * largestGroups[2].size
}

fun part2(input: String): Long? {
    val boxes = readLines(input).map { it.split(",").map { it.toDouble() } }

    val pairs = boxes.pairs()
    val pairsByDistance = pairs.sortedBy { (box1, box2) -> box1.distance(box2) }
    val circuits = boxes.map { box -> setOf(box) }.toMutableList()

    pairsByDistance.forEach { (box1, box2) ->
        val firstCircuit = circuits.first { box1 in it}
        val secondCircuit = circuits.first { box2 in it}

        circuits.remove(firstCircuit)
        circuits.remove(secondCircuit)
        circuits.add(firstCircuit + secondCircuit)

        if (circuits.size == 1) {
            return box1[0].toLong() * box2[0].toLong()
        }
    }

    return null
}

fun main() {
    val day = "8"

    println("==== Day $day ====")
    val input = getPath(day, "input.txt")
    val test = getPath(day, "test.txt")

    measurePart("Part 1", 10) {
        //part1(test, 10)
        part1(input, 1000)
    }

    measurePart("Part 2", 10) {
        //part2(test)
        part2(input)
    }
}
