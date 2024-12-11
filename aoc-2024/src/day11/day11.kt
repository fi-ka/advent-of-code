package day11

import util.*

fun Long.isEven() = this.toString().length % 2 == 0
fun Long.replaceStone(): List<Long> {
    val stoneStr = this.toString()
    return listOf(stoneStr.take(stoneStr.length/2).toLong(), stoneStr.drop(stoneStr.length/2).toLong())
}

fun countStones(blinks: Int, stone: Long, cache: MutableMap<Pair<Long, Int>, Long>): Long {
    if (blinks == 0) return 1L
    val key = stone to blinks

    if (key in cache)
        return cache[key]!!

    val numStones = when {
        stone == 0L -> countStones(blinks - 1, 1L, cache)
        stone.isEven() -> stone.replaceStone().sumOf { countStones(blinks - 1, it, cache) }
        else -> countStones(blinks - 1, stone * 2024, cache)
    }

    cache[key] = numStones
    return numStones
}

fun part(input: String, part2: Boolean = false) {
    var stones = readLines(input).first().split(" ").mapToLong()

    stones.sumOf {
        countStones(if (part2) 75 else 25, it, mutableMapOf())
    }.println()
}

fun main() {
    val day = "11"

    println("==== Day $day ====")
    val input = getPath(day, "input.txt")
    val test = getPath(day, "test.txt")

    runPart("Part 1") {
        part(test)
        part(input)
    }

    runPart("Part 2") {
         part(test, part2 = true)
         part(input, part2 = true)
    }
}
