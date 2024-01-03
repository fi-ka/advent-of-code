package day20

import util.*

data class Message(val fromModule: String, val toModule: String, val pulse: Boolean) {
    override fun toString(): String {
        val pulseText = if (pulse) "high" else "low"
        return "$fromModule -$pulseText-> $toModule"
    }
}

interface Module {
    val name: String
    val outputs: List<String>
    val sendChannel: MutableList<Message>
    fun receive(message: Message)
}

class Broadcaster(
    override val name: String,
    override val outputs: List<String>,
    override val sendChannel: MutableList<Message>
) : Module {

    override fun receive(message: Message) {
        for (output in outputs)
            sendChannel.add(Message(name, output, message.pulse))
    }
}

class FlipFlop(
    override val name: String,
    override val outputs: List<String>,
    override val sendChannel: MutableList<Message>
) : Module {
    private var state: Boolean = false

    override fun receive(message: Message) {
        if (!message.pulse) {
            state = !state
            for (output in outputs)
                sendChannel.add(Message(name, output, state))
        }
    }
}

class Conjunction(
    override val name: String,
    override val outputs: List<String>,
    override val sendChannel: MutableList<Message>
) : Module {
    private lateinit var inputsState: MutableMap<String, Boolean>

    fun setInputs(inputs: List<String>) {
        inputsState = inputs.associateWith { false }.toMutableMap()
    }

    override fun receive(message: Message) {
        inputsState[message.fromModule] = message.pulse
        val outputPulse = !inputsState.values.all { it }
        for (output in outputs)
            sendChannel.add(Message(name, output, outputPulse))
    }
}

fun part1(input: String) {
    val sendChannel = mutableListOf<Message>()

    val modules =
        readLines(input)
            .map { line ->
                val (module, outputs) = line.split(" -> ")
                    .let { it.first() to it[1].split(", ") }

                when {
                    module == "broadcaster" -> Broadcaster(module, outputs, sendChannel)
                    module.startsWith("%") -> FlipFlop(module.drop(1), outputs, sendChannel)
                    module.startsWith("&") -> Conjunction(module.drop(1), outputs, sendChannel)
                    else -> throw RuntimeException()
                }
            }.associateBy { it.name }

    modules
        .values
        .filterIsInstance<Conjunction>()
        .forEach { module ->
            val inputModules = modules.values.filter { it.outputs.contains(module.name) }
            module.setInputs(inputModules.map { it.name })
        }

    var lowPulses = 0L
    var highPulses = 0L
    repeat(1000) {
        sendChannel.add(Message("button", "broadcaster", false))
        while (sendChannel.isNotEmpty()) {
            val message = sendChannel.removeFirst()
//            message.println()
            if (message.pulse)
                highPulses += 1
            else
                lowPulses += 1

            modules[message.toModule]?.receive(message)
        }
    }

    println("High: $highPulses")
    println("Low: $lowPulses")

    println("High*Low: ${highPulses*lowPulses}")
}

fun part2(input: String) {
    val sendChannel = mutableListOf<Message>()

    val modules =
        readLines(input)
            .map { line ->
                val (module, outputs) = line.split(" -> ")
                    .let { it.first() to it[1].split(", ") }

                when {
                    module == "broadcaster" -> Broadcaster(module, outputs, sendChannel)
                    module.startsWith("%") -> FlipFlop(module.drop(1), outputs, sendChannel)
                    module.startsWith("&") -> Conjunction(module.drop(1), outputs, sendChannel)
                    else -> throw RuntimeException()
                }
            }.associateBy { it.name }

    modules
        .values
        .filterIsInstance<Conjunction>()
        .forEach { module ->
            val inputModules = modules.values.filter { it.outputs.contains(module.name) }
            module.setInputs(inputModules.map { it.name })
        }

    var buttonPresses = 0L
    do {
        var rxLowPulses = 0L
        sendChannel.add(Message("button", "broadcaster", false))
        while (sendChannel.isNotEmpty()) {
            val message = sendChannel.removeFirst()

            if (message.toModule == "rx" && !message.pulse) {
                rxLowPulses += 1
            }

            modules[message.toModule]?.receive(message)
        }
        buttonPresses += 1
        if (buttonPresses % 1_000_000L == 0L) {
            println("$buttonPresses, $rxLowPulses")
        }
    } while(rxLowPulses != 1L)

    println("Button presses: $buttonPresses")
}

fun main() {
    val day = "20"

    println("==== Day $day ====")
    val input = """src/day${day.padStart(2, '0')}/input.txt"""
    val test = """src/day${day.padStart(2, '0')}/test.txt"""
    val test2 = """src/day${day.padStart(2, '0')}/test2.txt"""

    runPart("Part 1") {
        part1(test)
        println()
        part1(test2)
        println()
        part1(input)
    }

    runPart("Part 2") {
        // part2(test)
        // More than 573000000 button presses(?)
         part2(input)
    }
}
