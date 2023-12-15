package util

import org.jetbrains.kotlinx.multik.ndarray.data.Dimension
import org.jetbrains.kotlinx.multik.ndarray.data.MultiArray
import org.jetbrains.kotlinx.multik.ndarray.operations.forEachMultiIndexed
import java.io.File
import java.util.Collections.swap
import kotlin.system.measureTimeMillis

fun runPart(name: String, run: () -> Unit) {
    val elapsed = measureTimeMillis {
        println("\n---- $name ----")
        run()
    }
    println("Time: $elapsed ms")
}

fun readLines(path: String): List<String> {
    return File(path).readLines()
}

fun readInts(path: String): List<Int> {
    return readLines(path).map(String::toInt)
}

fun readLineSplit(path: String, vararg delimiters: String = arrayOf(",")) : List<String> {
    return readLines(path).first().split(delimiters = delimiters)
}

fun readFloats(path: String): List<Float> {
    return readLines(path).map(String::toFloat)
}

fun readBlocks(path: String, separator: (String) -> Boolean = { it.isEmpty()} ): List<List<String>> {
    var lines = readLines(path)
    val groups = mutableListOf<List<String>>()

    while (lines.isNotEmpty()) {
        val newGroup = lines.takeWhile { !separator(it) }
        groups.add(newGroup)
        lines = lines.drop(newGroup.size + 1)
    }
    return groups
}

fun Any?.println() {
    println(this)
}

fun String.parse(regex: Regex): List<String> {
    return regex.matchEntire(this)!!.destructured.toList()
}


fun String.parse(regex: String): List<String> {
    return regex.toRegex().matchEntire(this)!!.destructured.toList()
}

/**
* Returns first multi index of [element], or null if the collection does not contain element.
*/
fun <T, D : Dimension> MultiArray<T, D>.multiIndexOf(element: T): IntArray? {
    this.forEachMultiIndexed { index, i ->
        if (i == element) {
            return index
        }
    }
    return null
}

inline fun <T> Iterable<T>.takeWhileInclusive(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()
    for (item in this) {
        list.add(item)
        if (!predicate(item))
            break
    }
    return list
}

fun <T> List<T>.permutations(): List<List<T>> {
    val perms = mutableListOf<List<T>>()

    fun generate(k: Int, list: List<T>) {
        if (k == 1) {
            perms.add(list.toList())
        } else {
            for (i in 0..<k) {
                generate(k - 1, list)

                if (i == k-1) continue

                if (k % 2 == 0) {
                    swap(list, i, k - 1)
                } else {
                    swap(list, 0, k - 1)
                }
            }
        }
    }

    generate(this.count(), this.toList())
    return perms
}

fun <T> List<T>.pairs(): Sequence<Pair<T,T>> {
    val list = this
    return sequence {
        for (i in list.indices) {
            for (j in (i + 1)..list.lastIndex) {
                yield(list[i] to list[j])
            }
        }
    }
}

fun <T> List<T>.triplets(): Sequence<Triple<T,T,T>> {
    val list = this
    return sequence {
        for (i in list.indices) {
            for (j in (i + 1)..list.lastIndex) {
                for (k in (j + 1)..list.lastIndex) {
                    yield(Triple(list[i], list[j], list[k]))
                }
            }
        }
    }
}