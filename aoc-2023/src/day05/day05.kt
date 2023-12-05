package day05

import util.*

data class Mapping(val sourceRange: LongRange, val offset: Long)

class Mapper(private val mappings: List<Mapping>) {

    /** Remember last used mapping. */
    private var mapping: Mapping? = null

    fun mapValue(value: Long): Long {
        if (mapping?.sourceRange?.contains(value) != true) {
            mapping = mappings.firstOrNull { (source, _) -> source.contains(value) }
        }
        return value + (mapping?.offset ?: 0L)
    }
}

fun createMapper(lines: List<String>): Mapper {
    val ranges = mutableListOf<Mapping>()
    lines.forEach {
        val (destination, source, length) = it.split(" ").mapToLong()
        val sourceRange = source..<source+length
        val destinationOffset = destination-source
        ranges.add(Mapping(sourceRange, destinationOffset))
    }
    return Mapper(ranges)
}

fun part1(input: String) {
    val blocks = readBlocks(input)

    val seeds = blocks.first().first().split(" ").drop(1).mapToLong()
    val seedToSoil = createMapper(blocks[1].drop(1))
    val soilToFertilizer = createMapper(blocks[2].drop(1))
    val fertilizerToWater = createMapper(blocks[3].drop(1))
    val waterToLight = createMapper(blocks[4].drop(1))
    val lightToTemperature = createMapper(blocks[5].drop(1))
    val temperatureToHumidity = createMapper(blocks[6].drop(1))
    val humidityToLocation = createMapper(blocks[7].drop(1))

    var value: Long
    val minLocation = seeds.minOf { seed ->
        value = seedToSoil.mapValue(seed)
        value = soilToFertilizer.mapValue(value)
        value = fertilizerToWater.mapValue(value)
        value = waterToLight.mapValue(value)
        value = lightToTemperature.mapValue(value)
        value = temperatureToHumidity.mapValue(value)
        value = humidityToLocation.mapValue(value)

        value
    }

    println(minLocation)
}

fun part2(input: String) {
    val blocks = readBlocks(input)

    val seeds = blocks.first().first().split(" ").drop(1).mapToLong().chunked(2)
    val seedToSoil = createMapper(blocks[1].drop(1))
    val soilToFertilizer = createMapper(blocks[2].drop(1))
    val fertilizerToWater = createMapper(blocks[3].drop(1))
    val waterToLight = createMapper(blocks[4].drop(1))
    val lightToTemperature = createMapper(blocks[5].drop(1))
    val temperatureToHumidity = createMapper(blocks[6].drop(1))
    val humidityToLocation = createMapper(blocks[7].drop(1))

    var value: Long
    val minLocation = seeds.minOf {(start, amount) ->
        (start..<start+amount).minOf { seed ->
            value = seedToSoil.mapValue(seed)
            value = soilToFertilizer.mapValue(value)
            value = fertilizerToWater.mapValue(value)
            value = waterToLight.mapValue(value)
            value = lightToTemperature.mapValue(value)
            value = temperatureToHumidity.mapValue(value)
            value = humidityToLocation.mapValue(value)

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
