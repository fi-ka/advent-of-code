package day05

import util.*
import kotlin.math.max
import kotlin.math.min

fun part1(input: String): Int {
    val (rangeLines, ingredientLines) = readBlocks(input)
    val ranges = rangeLines
        .map { it.split("-").map(String::toLong) }
        .map { it[0]..it[1] }
    val ingredients = ingredientLines.map(String::toLong)

    val fresh = ingredients.count { ingredient ->
        ranges.any { range -> ingredient in range }
    }

    return fresh
}

private fun LongRange.isOverlapping(range: LongRange) =
    min(this.last, range.last) >= max(this.first, range.first)

private fun LongRange.mergeOverlapping(range: LongRange) =
    min(this.first, range.first)..max(this.last, range.last)

fun part2(input: String): Long {
    val (rangeLines, _) = readBlocks(input)
    val ranges = rangeLines
        .map { it.split("-").map(String::toLong) }
        .map { it[0]..it[1] }

    val freshRanges = mutableListOf<LongRange>()
    ranges.forEach { range ->
        val mergedRanges = mutableListOf<LongRange>()
        val newRange = freshRanges.fold(range) { merged, fresh ->
            if (merged.isOverlapping(fresh)) {
                mergedRanges.add(fresh)
                merged.mergeOverlapping(fresh)
            } else {
                merged
            }
        }
        freshRanges.removeAll(mergedRanges)
        freshRanges.add(newRange)
    }

    return freshRanges.sumOf { it.last - it.first + 1}
}

fun main() {
    val day = "5"

    println("==== Day $day ====")
    val input = getPath(day, "input.txt")
    val test = getPath(day, "test.txt")

    measurePart("Part 1", 10) {
        //part1(test)
        part1(input)
    }

    measurePart("Part 2", 10) {
        // part2(test)
        part2(input)
    }
}
