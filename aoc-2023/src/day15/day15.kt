package day15

import util.*

data class Lens(val label: String, val focalLength: Int)

fun String.hash(): Int {
    var currentValue = 0
    this.forEach { char ->
        currentValue += char.code
        currentValue *= 17
        currentValue %= 256
    }
    return currentValue
}

fun part1(input: String) {
    val initSequence = readLines(input)
        .joinToString("")
        .split(",")

    initSequence
        .sumOf(String::hash)
        .println()
}

fun part2(input: String) {
    val initSequence = readLines(input)
        .joinToString("")
        .split(",")

    val boxes = List(256) { mutableListOf<Lens>() }
    initSequence.forEach {step ->
        val (label, operation, focal) = step.parse("""(\w+)([=-])(\d+)?""")

        val box = boxes[label.hash()]
        val lensIdx = box.indexOfFirst { it.label == label }
        when(operation) {
            "-" -> {
                if (lensIdx != -1) box.removeAt(lensIdx)
            }
            "=" -> {
                if (lensIdx != -1)
                    box[lensIdx] = Lens(label, focal.toInt())
                else
                    box.add(Lens(label, focal.toInt()))
            }
        }
    }

    boxes.withIndex().sumOf { (boxId, lenses) ->
        lenses.withIndex().sumOf { (idx,lens) ->
            (boxId+1) * (idx+1) * lens.focalLength
        }
    }.println()
}

fun main() {
    val day = "15"

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
