package day09

import util.*

fun getDiffLists(list: List<Long>): Sequence<List<Long>> {
    return generateSequence(list) { it.zipWithNext { a, b -> b - a } }
        .takeWhile { l -> l.any { it != 0L } }
}

fun predictNext(diffLists: List<List<Long>>): Long {
    var nextValue = 0L
    diffLists.reversed().forEach {
        nextValue += it.last()
    }
    return nextValue
}

fun predictNextBySum(diffLists: List<List<Long>>): Long {
    return diffLists.sumOf { it.last() }
}

fun predictPrevious(diffLists: List<List<Long>>): Long {
    var prevValue = 0L
    diffLists.reversed().forEach {
        prevValue = it.first() - prevValue
    }
    return prevValue
}

fun part1(input: String) {
    val lines = readLines(input)

    val histories = lines.map { it.split(" ").mapToLong() }
    val nextSum = histories.sumOf { history ->
        val diffLists = getDiffLists(history)
        val next = predictNextBySum(diffLists.toList())
        next
    }
    println(nextSum)
}

fun part2(input: String) {
    val lines = readLines(input)

    val histories = lines.map { it.split(" ").mapToLong() }
    val previousSum = histories.sumOf { history ->
        val diffLists = getDiffLists(history)
        val previous = predictPrevious(diffLists.toList())
        previous
    }
    println(previousSum)
}

fun main() {
    val day = "9"

    println("--- Day $day ---")
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
