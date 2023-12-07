package day07

import util.*

fun cardScore(card: Char): Int {
    return when (card) {
        'A' -> 14
        'K' -> 13
        'Q' -> 12
        'J' -> 11
        'T' -> 10
        '9' -> 9
        '8' -> 8
        '7' -> 7
        '6' -> 6
        '5' -> 5
        '4' -> 4
        '3' -> 3
        '2' -> 2
        else -> 1
    }
}

fun handScore(hand: String): Int {
    // Count jokers
    val jokers = hand.count { it == 'R' }

    // Remove jokers
    var cardCount = hand.replace("R", "").toList().groupBy { it }.map { it.value.size }.sortedDescending()

    // If there are only jokers return '5 of kind'
    val max = cardCount.maxOrNull() ?: return 7

    // Increase the highest card count by amount of jokers
    cardCount = cardCount.toMutableList()
    cardCount[cardCount.indexOf(max)] = max + jokers

    return if (cardCount.contains(5)) {
        7
    } else if (cardCount.contains(4)) {
        6
    } else if (cardCount.contains(3) && cardCount.contains(2)) {
        5
    } else if (cardCount.contains(3)) {
        4
    } else if (cardCount.count { it == 2 } == 2) {
        3
    } else if (cardCount.contains(2)) {
        2
    } else {
        1
    }
}

class HandComparator : Comparator<String> {
    override fun compare(hand1: String, hand2: String): Int {
        val score = handScore(hand1) - handScore(hand2)
        if (score != 0) return score

        hand1.zip(hand2).forEach {
            val cardScore = cardScore(it.first) - cardScore(it.second)
            if (cardScore != 0) return cardScore
        }

        return 0
    }
}

fun part1(input: String) {
    val hands = readLines(input).map { it.parse("""([A-Z1-9]+) (\d+)""") }

    val bids = hands.associate { it.first() to it[1] }
    val sortedHands = hands.map { it.first() }.sortedWith(HandComparator())
    val result = sortedHands.withIndex().sumOf { (index, hand) ->
        (index + 1) * bids[hand]!!.toLong()
    }
    println(result)
}

fun part2(input: String) {
    val hands = readLines(input).map { it.parse("""([A-Z1-9]+) (\d+)""") }
        .map { (hand, bid) ->
            val jokerHand = hand.replace("J", "R")
            listOf(jokerHand, bid)
        }

    val bids = hands.associate { it.first() to it[1] }
    val sortedHands = hands.map { it.first() }.sortedWith(HandComparator())
    val result = sortedHands.withIndex().sumOf { (index, hand) ->
        (index + 1) * bids[hand]!!.toLong()
    }
    println(result)
}

fun main() {
    val day = "7"

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
