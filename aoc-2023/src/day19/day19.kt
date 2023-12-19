package day19

import util.*



fun part1(input: String) {
    val blocks = readBlocks(input)

    val workflowsLines = blocks[0]
    val ratingsLines = blocks[1]

    val parts = ratingsLines.map { line ->
        """(\w)=(\d+)""".toRegex()
            .findAll(line)
            .map { it.groupValues }
            .associate { it[1] to it[2].toInt() }
    }

    val workflows = workflowsLines.associate { line ->
        val (name, rules, otherwise) = """(\w+)\{(.*),(\w+)\}""".toRegex().matchEntire(line)!!.destructured
        val ruleLambdas = """(\w)([<>])(\d+):(\w+)""".toRegex().findAll(rules)
            .map { it.groupValues }
            .map {(_, name, operator, value, result) ->
                val v = value.toInt()
                if (operator == "<") {
                    { part: Map<String, Int> -> if (part[name]!! < v) result else null}
                } else {
                    { part: Map<String, Int> -> if (part[name]!! > v) result else null}
                }
            }
        name to sequence {
            yieldAll(ruleLambdas)
            yield { _: Map<String, Int> -> otherwise }
        }
    }

    parts.filter { part ->
        var current = "in"

        do {
            val workflow = workflows[current]!!
            for (rule in workflow) {
                val next = rule(part)
                if (next != null) {
                    current = next
                    break
                }
            }
        } while(current !in listOf("A", "R"))

        current == "A"
    }.sumOf {
        it.values.sum()
    }.let { println(it) }
}

fun part2(input: String) {

}

fun main() {
    val day = "19"

    println("==== Day $day ====")
    val input = """src/day${day.padStart(2, '0')}/input.txt"""
    val test = """src/day${day.padStart(2, '0')}/test.txt"""

    runPart("Part 1") {
        part1(test)
         part1(input)
    }

    runPart("Part 2") {
        // part2(test)
        // part2(input)
    }
}
