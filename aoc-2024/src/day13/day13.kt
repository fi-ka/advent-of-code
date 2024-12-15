package day13

import util.*

fun part1(input: String) {
}

fun part2(input: String) {
    val blocks = readBlocks(input)
    var sum = 0L
    blocks.map {
        val a = it[0].parse("""Button A: X\+(\d+), Y\+(\d+)""").mapToLong().zipWithNext().first()
        val b = it[1].parse("""Button B: X\+(\d+), Y\+(\d+)""").mapToLong().zipWithNext().first()
        var prize = it[2].parse("""Prize: X=(\d+), Y=(\d+)""").mapToLong().zipWithNext().first()
        prize = prize.first + 10000000000000L to prize.second + 10000000000000

        // px = ax * numA + bx * numB
        // py = ay * numA + by * numB
        val numB = ((prize.second*a.first - a.second*prize.first) / (-a.second*b.first + a.first*b.second))
        val numA = (prize.first - b.first*numB) / a.first
        if (a.first*numA + b.first*numB == prize.first && a.second*numA + b.second*numB == prize.second) {
            sum += numA*3 + numB
        }
    }
    sum.println()

}

fun main() {
    val day = "13"

    println("==== Day $day ====")
    val input = getPath(day, "input.txt")
    val test = getPath(day, "test.txt")

    runPart("Part 1") {
        // part1(test)
        // part1(input)
    }

    runPart("Part 2") {
        part2(test)
        part2(input)
    }
}
