package day14

import util.*

data class Robot(val p: Pair<Int,Int>, val v: Pair<Int,Int>)
fun String.toRobot(): Robot {
    val (px, py, vx, vy) = this.parse("""p=(\d+),(\d+) v=(-?\d+),(-?\d+)""").mapToInt()
    return Robot(px to py, vx to vy)
}

fun Pair<Int, Int>.move(v: Pair<Int, Int>, size: Pair<Int,Int>): Pair<Int, Int> {
    var newX = (this.first + v.first) % size.first
    var newY = (this.second + v.second) % size.second
    if (newX < 0) newX += size.first
    if (newY < 0) newY += size.second
    return newX to newY
}

fun part1(input: String, size: Pair<Int, Int>) {
    var robots = readLines(input).map(String::toRobot)
    //robots = robots.filter { it.p.first == 2 && it.p.second == 4 }
    repeat(100) {
        robots = robots.map { robot ->
            val p = robot.p
            val v = robot.v
            robot.copy(p = p.move(v, size))
        }
    }
    val midX = size.first/2 - 1
    val midY = size.second/2 - 1

    listOf(
        robots.count { it.p.first in 0..midX && it.p.second in 0..midY},
        robots.count { it.p.first in 0..midX && it.p.second in (midY+2)..size.second},
        robots.count { it.p.first in (midX+2)..size.first && it.p.second in 0..midY},
        robots.count { it.p.first in (midX+2)..size.first && it.p.second in (midY+2)..size.second}
    ).reduce(Int::times).println()
}

fun part2(input: String, size: Pair<Int,Int>) {
    var robots = readLines(input).map(String::toRobot)
    repeat(10000) {
        robots = robots.map { robot ->
            val p = robot.p
            val v = robot.v
            robot.copy(p = p.move(v, size))
        }
        if (robots.groupingBy { it.p.first }.eachCount().values.max() > 20) {
        //if (it > 499) {
            println("$it:")
            for (x in 0..(size.first-1)) {
                for (y in 0..(size.second-1)) {
                    if (robots.any { it.p == x to y })
                        print("#")
                    else
                        print(" ")
                }
                println()
            }
            println()
        }
    }
}

fun main() {
    val day = "14"

    println("==== Day $day ====")
    val input = getPath(day, "input.txt")
    val test = getPath(day, "test.txt")

    runPart("Part 1") {
        part1(test, 11 to 7)
        part1(input, 101 to 103)
    }

    runPart("Part 2") {
        part2(input, 101 to 103)
    }
}
