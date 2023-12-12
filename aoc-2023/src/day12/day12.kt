package day12

import util.*

data class Record(val record: String, val groups: List<Int>)

fun Record.unfold(folds: Int): Record {
    return Record(
        generateSequence { record }.take(folds).joinToString("?"),
        generateSequence { groups }.take(folds).flatten().toList())
}

fun Record.arrangementsNaive(): Long {
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

fun Record.arrangements(): Long {
    // Caching the arrangement value for a (record, group) pair
    val cache = mutableMapOf<Pair<String, List<Int>>, Long>()

    fun countArrangements(record: String, groups: List<Int>): Long {
        if (groups.isEmpty() && record.all { it == '.' || it == '?' })
            return 1L
        else if (
            groups.isEmpty() ||
            record.length < (groups.sum() + groups.size-1)) {
            return 0L
        } else if (record to groups in cache) {
            return cache[record to groups]!!
        }

        if (record.first() == '#') {
            val groupLen = groups.first()
            val group = record.take(groupLen)
            val next = record.drop(groupLen).firstOrNull()
            val isGroup = group.all { it != '.' } && next != '#'

            return if (isGroup) {
                countArrangements(
                    record.drop(groupLen + 1).trimStart('.'),
                    groups.drop(1))
            } else {
                0L
            }
        } else { // record.first() == '?'
            val arrangements = countArrangements(
                record.drop(1).trimStart('.'),
                groups,
            ) + countArrangements(
                record.replaceFirstChar { '#' },
                groups,
            )
            cache[record to groups] = arrangements
            return arrangements
        }
    }

    return countArrangements(this.record.trim('.'), this.groups)
}

fun part1(input: String) {
    val records = readLines(input)
        .map { it.split(' ') }
        .map { (record, groups) -> Record(record, groups.split(",").mapToInt()) }
    val sum = records.sumOf { record -> record.arrangements() }

    println(sum)
}


fun part2(input: String) {
    val records = readLines(input)
        .map { it.split(' ') }
        .map { (record, groups) -> Record(record, groups.split(",").mapToInt()) }
        .map { it.unfold(5) }

    val sum = records.sumOf {it.arrangements() }

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
