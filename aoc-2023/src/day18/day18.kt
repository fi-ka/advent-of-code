package day18

import util.*
import java.lang.RuntimeException

data class Pos(val row: Int, val col: Int)
data class Instruction(val direction: Char, val meters: Int)

fun part1(input: String) {
    val cmds = readLines(input)
        .map { it.parse("""([RLUD]) (\d+) \(#([a-f0-9]+)\)""") }
        .map { (a,b,_) -> Instruction(a.first(), b.toInt()) }

    val outline = mutableSetOf<Pos>()

    var row = 0
    var col = 0
    cmds.forEach { plan ->
        when (plan.direction) {
            'D' -> {
                val end = row + plan.meters
                outline.addAll((row..<end).map { Pos(it, col) })
                row = end
            }
            'U' -> {
                val end = row - plan.meters
                outline.addAll((row downTo (end+1)).map { Pos(it, col) })
                row = end
            }
            'R' -> {
                val end = col + plan.meters
                outline.addAll((col..<end).map { Pos(row, it) })
                col = end
            }
            'L' -> {
                val end = col - plan.meters
                outline.addAll((col downTo (end+1)).map { Pos(row, it) })
                col = end
            }
        }
    }

    val toVisit = mutableListOf(Pos(1,1))
    val visited = mutableSetOf<Pos>()
    visited.addAll(outline)
    while (toVisit.isNotEmpty()) {
        val pos = toVisit.removeFirst()
        visited.add(pos)

        val next = listOf(
                Pos(pos.row-1, pos.col),
                Pos(pos.row+1, pos.col),
                Pos(pos.row, pos.col-1),
                Pos(pos.row, pos.col+1),
            ).filter { it !in visited }

        visited.addAll(next)
        toVisit.addAll(next)
    }

    println(visited.size)
}

fun part2(input: String) {

}

fun main() {
    val day = "18"

    println("==== Day $day ====")
    val input = getPath(day, "input.txt")
    val test = getPath(day, "test.txt")

    runPart("Part 1") {
        part1(test)
         part1(input)
    }

    runPart("Part 2") {
//         part2(test)
        // part2(input)
    }
}
