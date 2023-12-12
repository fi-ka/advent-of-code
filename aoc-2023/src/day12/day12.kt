package day12

import util.*
import kotlin.RuntimeException

data class Record(val record: String, val groups: List<Int>)

fun Record.unfold(folds: Int): Record {
    return Record(
        generateSequence { record }.take(folds).joinToString("?"),
        generateSequence { groups }.take(folds).flatten().toList())
}

fun Record.arrangementsBruteForce(): Long {
    fun countArrangements(record: String, checkGroup: List<Int>): Long {
        if (record.all { it != '?' })
        {
            val recordGroups = record.split(".").filter { it.isNotEmpty() }.map { it.length }
            return if (recordGroups == checkGroup) 1L else 0L
        }

        return countArrangements(record.replaceFirst('?', '.'), checkGroup) +
                countArrangements(record.replaceFirst('?', '#'), checkGroup)
    }

    return countArrangements(this.record, this.groups)
}


fun Record.arrangements(): List<Long> {
    // Caching the arrangement value for a record and group pair
    val cache = mutableMapOf<Pair<String, List<Int>>, List<Long>>()

    fun countArrangements(record: String, checkGroup: List<Int>): List<Long> {
        if (checkGroup.isEmpty() && record.all { it == '.' || it == '?' })
            return listOf()
        else if (checkGroup.isEmpty() ||
            record.isEmpty() ||
            (checkGroup.sum() + checkGroup.size-1) > record.length) {
            return listOf(0L)
        } else if (record to checkGroup in cache) {
            return cache[record to checkGroup]!!
        }

        if (record.startsWith("#")) {
            val group = record.take(checkGroup.first())
            val next = record.drop(checkGroup.first()).firstOrNull()
            val isGroup = group.all { it != '.' } && next != '#'
            return if (isGroup) {
                listOf(1L) + countArrangements(
                    record.drop(checkGroup.first() + 1).trim('.'),
                    checkGroup.drop(1),
                )
            } else {
                listOf(0L)
            }
        } else if (record.startsWith("?")) {
            val a = countArrangements(
                record.drop(1).trimStart('.'),
                checkGroup,
            )
            val b = countArrangements(
                record.replaceFirstChar { '#' },
                checkGroup,
            )
            val combine = if (0 !in a && 0 !in b) {
                listOf(a.reduce{ acc ,l -> acc*l} + b.reduce{ acc ,l -> acc*l})
            } else if (0 !in a) {
                a
            } else if (0 !in b) {
                b
            } else {
                listOf(0L)
            }
            cache[record to checkGroup] = combine
            return combine
        } else {
            throw RuntimeException("Start with '.'")

        }
    }

    return countArrangements(this.record.trimStart('.'), this.groups)
}

fun part1(input: String) {
    val records = readLines(input).map { it.parse("""([?#\.]+) ([0-9,]+)""") }
        .map { (line, groups) -> Record(line, groups.split(",").mapToInt()) }
    val sum = records.sumOf { record -> record.arrangements().reduce { acc, l -> acc * l } }

    println(sum)
}


fun part2(input: String) {
    val records = readLines(input).map { it.parse("""([?#\.]+) ([0-9,]+)""") }
        .map { (line, groups) -> Record(line, groups.split(",").mapToInt()) }
        .map { it.unfold(5) }

    val sum = records.withIndex().sumOf { (idx, record) ->
        println("$idx: $record")
        val result = record.arrangements()
        println("$idx: $result")
        println("$idx: ${result.reduce { acc, l -> acc * l }}")
//        println(result)
//        println(result.reduce { acc, l -> acc * l })
        result.reduce { acc, l -> acc * l }
    }

    println(sum)
}

fun main() {
    val day = "12"

    println("==== Day $day ====")
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
