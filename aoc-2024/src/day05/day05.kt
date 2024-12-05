package day05

import util.*

fun List<Int>.isValid(pageNumberByIndex: Map<Int, Int>): Boolean {
    if (this[0] !in pageNumberByIndex) return true
    if (this[1] !in pageNumberByIndex) return true
    return pageNumberByIndex[this[0]]!! < pageNumberByIndex[this[1]]!!
}

fun part1(input: String) {
    val (ruleLines, updateLines) = readBlocks(input)
    val rules = ruleLines.map { rule ->
        rule.split("|").mapToInt()
    }

    updateLines.sumOf { line ->
        val update = line.split(",").mapToInt()
        val pageNumberByIndex = update
            .withIndex()
            .associate { (idx, pageNumber) ->
                pageNumber to idx
            }

        if (rules.all { it.isValid(pageNumberByIndex) }) {
            update[update.size/2]
        } else 0
    }.println()
}

fun part2(input: String) {
    val (ruleLines, updateLines) = readBlocks(input)
    val rules = ruleLines.map { rule ->
        rule.split("|").mapToInt()
    }.groupBy({ it.last() }) { it.first() }

    updateLines.sumOf { line ->
        val update = line.split(",").mapToInt()
        val orderedUpdate = update.sortedBy { pageNum ->
            rules[pageNum]?.filter { it in update }?.size ?: 0
        }
        if (update != orderedUpdate)
            orderedUpdate[orderedUpdate.size/2]
        else
            0
    }.println()
}

fun main() {
    val day = "5"

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
