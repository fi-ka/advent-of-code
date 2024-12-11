package day07

import util.*

fun part1(input: String) {
    var sum = 0L
    readLines(input).forEach { line ->
        val equation = line.parse("""(\d+): (.*)""")
        val target = equation[0].toLong()
        val numbers = equation[1].split(" ").mapToLong()

        val valid = calc(target, numbers.first(), numbers.drop(1))
        if (valid) sum += target
    }
    sum.println()

}

fun part2(input: String) {
    var sum = 0L
    readLines(input).forEach { line ->
        val equation = line.parse("""(\d+): (.*)""")
        val target = equation[0].toLong()
        val numbers = equation[1].split(" ").mapToLong()

        val valid = calc2(target, numbers.first(), numbers.drop(1))
        if (valid)  {
            sum += target
        }
    }
    sum.println()

}

fun calc(target: Long, current: Long, numbers: List<Long>): Boolean {
    if (numbers.isEmpty())
        return current == target
    else if (current > target)
        return false

    return calc(target, current + numbers.first(), numbers.drop(1)) ||
           calc(target, current * numbers.first(), numbers.drop(1))
}

fun calc2(target: Long, current: Long, numbers: List<Long>): Boolean {
    if (numbers.isEmpty())
        return current == target
    else if (current > target)
        return false

    return calc2(target, current + numbers.first(), numbers.drop(1)) ||
            calc2(target, current * numbers.first(), numbers.drop(1)) ||
            calc2(target, listOf(current, numbers.first()).joinToString("").toLong(), numbers.drop(1))
}

fun main() {
    val day = "7"

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
