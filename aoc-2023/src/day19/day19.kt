package day19

import util.*
import kotlin.math.max
import kotlin.math.min

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
    val blocks = readBlocks(input)

    val workflowsLines = blocks[0]

    val workflows = workflowsLines.associate { line ->
        val (name, ruleLines, otherwise) = """(\w+)\{(.*),(\w+)\}""".toRegex().matchEntire(line)!!.destructured
        val ruleMatches = """(\w)([<>])(\d+):(\w+)""".toRegex().findAll(ruleLines)
            .map { it.groupValues }

        val inverse = mutableMapOf<String, IntRange>()
        val rules = mutableMapOf<String, MutableMap<String, IntRange>>()
        for ((_, left, operator, value, result) in ruleMatches) {
            if (result !in rules)
                rules[result] = mutableMapOf()

            val v = value.toInt()
            if (operator == "<") {
                rules[result]!![left] = 1..<v
                inverse[left] = (v+1)..4000
            } else {
                rules[result]!![left] = (v+1)..4000
                inverse[left] = 1..v
            }
        }
        rules[otherwise] = inverse
        name to rules
    }

    val partsInWorkflow = mutableMapOf(
        "in" to mutableMapOf(
            "x" to 1..4000,
            "m" to 1..4000,
            "a" to 1..4000,
            "s" to 1..4000
        )
    )
    val acceptedParts = mutableListOf<Map<String, IntRange>>()
    while (partsInWorkflow.keys.any { it !in listOf("A", "R") }) {
        println(partsInWorkflow)

        partsInWorkflow.keys.filter { it !in listOf("A", "R") }.forEach { workflowName ->
            workflows[workflowName]!!.forEach { (target, ranges) ->
                if (target !in partsInWorkflow)
                    partsInWorkflow[target] = partsInWorkflow[workflowName]!!.toMutableMap()


                ranges.forEach { (key, value) ->
                    if (key !in partsInWorkflow[target]!!) {
                        partsInWorkflow[target]!![key] = value
                    } else {
                        val currRange = partsInWorkflow[target]!![key]!!
                        val intersection: IntRange = max(currRange.first, value.first)..min(currRange.last, value.last)
                        if (!intersection.isEmpty()) {
                            partsInWorkflow[target]!![key] = intersection
                        }
                    }
                }
                if (target == "A") {
                    acceptedParts.add(partsInWorkflow[target]!!)
                    partsInWorkflow.remove("A")
                }

            }
            partsInWorkflow.remove(workflowName)
        }
    }

    println(partsInWorkflow)

    val acceptedParts2 = mutableListOf<Map<String, IntRange>>()
    acceptedParts.map {

    }
    println(acceptedParts)

//    println(partsInWorkflow["A"]!!
//        .values
//        .fold(1L) { acc, r -> acc * (r.last-r.first+1) }
//    )

    // R={x=1..4000, m=2091..4000, a=1..2005, s=1..1350}
    // A={x=1..4000, m=2091..4000, a=1..2005, s=1..1350}
    //println(acceptedParts)
//    parts.filter { part ->
//        var current = "in"
//
//        do {
//            val workflow = workflows[current]!!
//            for (rule in workflow) {
//                val next = rule(part)
//                if (next != null) {
//                    current = next
//                    break
//                }
//            }
//        } while(current !in listOf("A", "R"))
//
//        current == "A"
//    }.sumOf {
//        it.values.sum()
//    }.let { println(it) }
}

fun main() {
    val day = "19"

    println("==== Day $day ====")
    val input = """src/day${day.padStart(2, '0')}/input.txt"""
    val test = """src/day${day.padStart(2, '0')}/test.txt"""

    runPart("Part 1") {
//        part1(test)
//         part1(input)
    }

    runPart("Part 2") {
         part2(test)
        // part2(input)
    }
}
