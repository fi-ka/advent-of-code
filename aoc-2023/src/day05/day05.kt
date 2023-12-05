package day05

import util.*

fun createMap(lines: List<String>): MutableList<Pair<LongRange, LongRange>> {
    val ranges = mutableListOf<Pair<LongRange,LongRange>>()
    lines.forEach {
        val (destination, source, length) = it.split(" ").mapToLong()
        ranges.add(source..<(source+length) to destination..<(destination+length))
    }
    return ranges
}

fun part1(input: String) {
    val blocks = readBlocks(input)

    val seeds = blocks.first().first().split(" ").drop(1).mapToLong()
    val seedToSoil = createMap(blocks[1].drop(1))
    val soilToFertilizer = createMap(blocks[2].drop(1))
    val fertilizerTowater = createMap(blocks[3].drop(1))
    val waterTolight = createMap(blocks[4].drop(1))
    val lightTotemperature = createMap(blocks[5].drop(1))
    val temperatureTohumidity = createMap(blocks[6].drop(1))
    val humidityTolocation = createMap(blocks[7].drop(1))

    val minLocation = seeds.minOf {

        var range = seedToSoil.firstOrNull { (source, dest) -> source.contains(it) }
        var value = if (range == null) it else it - range.first.first + range.second.first

        range = soilToFertilizer.firstOrNull { (source, dest) -> source.contains(value) }
        value = if (range == null) value else value - range.first.first + range.second.first

        range = fertilizerTowater.firstOrNull { (source, dest) -> source.contains(value) }
        value = if (range == null) value else value - range.first.first + range.second.first

        range = waterTolight.firstOrNull { (source, dest) -> source.contains(value) }
        value = if (range == null) value else value - range.first.first + range.second.first

        range = lightTotemperature.firstOrNull { (source, dest) -> source.contains(value) }
        value = if (range == null) value else value - range.first.first + range.second.first

        range = temperatureTohumidity.firstOrNull { (source, dest) -> source.contains(value) }
        value = if (range == null) value else value - range.first.first + range.second.first

        range = humidityTolocation.firstOrNull { (source, dest) -> source.contains(value) }
        value = if (range == null) value else value - range.first.first + range.second.first

        value
    }

    println(minLocation)
}

fun part2(input: String) {
    val blocks = readBlocks(input)

    val seeds = blocks.first().first().split(" ").drop(1).mapToLong().chunked(2)
    val seedToSoil = createMap(blocks[1].drop(1))
    val soilToFertilizer = createMap(blocks[2].drop(1))
    val fertilizerTowater = createMap(blocks[3].drop(1))
    val waterTolight = createMap(blocks[4].drop(1))
    val lightTotemperature = createMap(blocks[5].drop(1))
    val temperatureTohumidity = createMap(blocks[6].drop(1))
    val humidityTolocation = createMap(blocks[7].drop(1))

    val minLocation = seeds.minOf {(start, amount) ->
        (start..<start+amount).minOf {
            var range = seedToSoil.firstOrNull { (source, _) -> source.contains(it) }
            var value = if (range == null) it else it - range.first.first + range.second.first

            range = soilToFertilizer.firstOrNull { (source, _) -> source.contains(value) }
            value = if (range == null) value else value - range.first.first + range.second.first

            range = fertilizerTowater.firstOrNull { (source, _) -> source.contains(value) }
            value = if (range == null) value else value - range.first.first + range.second.first

            range = waterTolight.firstOrNull { (source, _) -> source.contains(value) }
            value = if (range == null) value else value - range.first.first + range.second.first

            range = lightTotemperature.firstOrNull { (source, _) -> source.contains(value) }
            value = if (range == null) value else value - range.first.first + range.second.first

            range = temperatureTohumidity.firstOrNull { (source, _) -> source.contains(value) }
            value = if (range == null) value else value - range.first.first + range.second.first

            range = humidityTolocation.firstOrNull { (source, _) -> source.contains(value) }
            value = if (range == null) value else value - range.first.first + range.second.first

            value
        }
    }

    println(minLocation)
}

fun main() {
    val day = "5"

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
