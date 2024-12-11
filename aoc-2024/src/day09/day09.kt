package day09

import util.*

fun part1(input: String) {
    var space = false
    var id = 0
    var spaces = ArrayDeque<Int>()
    var data = ArrayDeque<Pair<Int,Int>>()
    readLines(input).first().map { it.digitToInt() }.forEach { ch ->
        if (space) spaces.add(ch)
        else data.add(id to ch)

        if (space) id += 1
        space = !space
    }
    val fragmented = mutableListOf(data.removeFirst())
    while (spaces.isNotEmpty() && data.isNotEmpty()) {
        var space = spaces.removeFirst()
        if (space <= 0) {
            fragmented.add(data.removeFirst())
            continue
        }

        val (id, size) = data.removeLast()
        if (size <= space) {
            fragmented.add(id to size)
            spaces.addFirst(space - size)
        } else {
            fragmented.add(id to space)
            data.addLast(id to size - space)
            fragmented.add(data.removeFirst())
        }
    }
    fragmented.addAll(data)
    
    var sum = 0L
    var totalSize = 0L

    fragmented.forEach { (id, size) ->
        if (id != -1)
            sum += (0..size-1).sumOf { (totalSize + it) * id }
        totalSize += size
    }
    sum.println()
}

fun part2(input: String) {
    var space = false
    var id = 0
    var disk = ArrayDeque<Pair<Int,Int>>()

    readLines(input).first().map { it.digitToInt() }.forEach { ch ->
        if (space) disk.add(-1 to ch)
        else disk.add(id to ch)

        if (space) id += 1
        space = !space
    }

    val data = disk.filter { (id, _) -> id != -1 }
    data.reversed().forEach { moveFile ->
        val (moveId, moveSize) = moveFile
        val moveIndex = disk.indexOfFirst { (id, _) -> id == moveId }
        val (idx, file) = disk.withIndex().first { (_, file) ->
            val (fileId, size) = file
            (fileId == -1 &&  moveSize <= size) || fileId == moveId
        }
        val (fileId, size) = file
        if (fileId == -1) {
            val newDisk = ArrayDeque<Pair<Int,Int>>(disk.size)
            newDisk.addAll(disk.subList(0, idx))
            newDisk.add(moveFile)
            if (moveSize < size) {
                newDisk.add(-1 to size - moveSize)
            }
            newDisk.addAll(disk.subList(idx+1, moveIndex))
            newDisk.add(-1 to moveSize)
            newDisk.addAll(disk.subList(moveIndex+1, disk.size))
            disk = newDisk
        }

    }

    var sum = 0L
    var totalSize = 0L

    disk.forEach { (id, size) ->
        if (id != -1)
            sum += (0..size-1).sumOf { (totalSize + it) * id }
        totalSize += size
    }
    sum.println()
}

fun main() {
    val day = "9"

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
