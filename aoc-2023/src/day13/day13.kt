package day13

import util.*

fun List<String>.getHorizontalMirrorIdx(smudges: Int = 0): Int? {
    return (1..this.lastIndex).firstOrNull { idx ->
        val left = this.take(idx).reversed()
        val right = this.drop(idx)
        left.zip(right).sumOf { line -> line.first.zip(line.second).count { it.first != it.second } } == smudges
    }
}

fun List<String>.rotate(): List<String> {
    return this.first().indices.map { colIdx ->
        this.map { row -> row[colIdx] }.joinToString()
    }
}

fun day13(input: String, smudges: Int = 0) {
    val patterns = readBlocks(input)

    val sum = patterns.sumOf { pattern ->
        pattern.getHorizontalMirrorIdx(smudges)?.times(100)
            ?: pattern.rotate().getHorizontalMirrorIdx(smudges)!!
    }
    println(sum)
}

fun main() {
    val day = "13"

    println("==== Day $day ====")
    val input = """src/day${day.padStart(2, '0')}/input.txt"""
    val test = """src/day${day.padStart(2, '0')}/test.txt"""

    runPart("Part 1") {
        day13(test)
        day13(input)
    }

    runPart("Part 2") {
        day13(test, 1)
        day13(input, 1)
    }
}
