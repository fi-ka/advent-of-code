package aoc.util

fun Iterable<String>.mapToInt(): List<Int> {
    return this.map { it.toInt() }
}

fun Iterable<String>.mapToLong(): List<Long> {
    return this.map { it.toLong() }
}

fun Iterable<String>.mapToFloat(): List<Float> {
    return this.map { it.toFloat() }
}

fun Iterable<String>.mapToDouble(): List<Double> {
    return this.map { it.toDouble() }
}

fun Iterable<Char>.mapToDigit(): List<Int> {
    return this.map { it.digitToInt() }
}

