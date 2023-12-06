package day06

import util.*

fun part1(input: String) {
    val lines = readLines(input)

    val times = lines[0].split("""\s+""".toRegex()).drop(1).mapToLong()
    val records = lines[1].split("""\s+""".toRegex()).drop(1).mapToLong()

    val races = times.zip(records)
    val raceWins = races.map {(time, record) ->
        var wins = 0L
        for (speed in 0..<time) {
            val distance = speed * (time-speed)
            if (distance > record)
                wins += 1L
        }
        wins
    }

    val result = raceWins.reduce { acc, wins -> acc * wins }
    println(result)
}

fun part2(input: String) {
    val lines = readLines(input)

    val time = lines[0].removePrefix("Time:").replace(" ", "").toLong()
    val record = lines[1].removePrefix("Distance:").replace(" ", "").toLong()

    var wins = 0L
    for (speed in 0..<time) {
        val distance = speed * (time-speed)
        if (distance > record)
            wins += 1L
    }

    println(wins)
}

fun main() {
    val day = "6"

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
