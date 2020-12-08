package adventofcode.twenty20

import java.io.File

/**
 * @author Martin Trollip
 * @since 2020/12/08 07:02
 */
private const val DAY8_INPUT = "src/res/twenty20/day8_input"

fun main(args: Array<String>) {
    val day8 = Day8(DAY8_INPUT)
    println("Acc value when it starts to repeat ${day8.part1()}")
    println("Acc value when we fixed the loop ${day8.part2()}")
}

class Day8(input: String) {
    private val INSTRUCTION_REGEX = "([a-z]{3}) ([+-][0-9]+)".toRegex() //https://regex101.com/r/K4vMT4/1

    private val instructions: List<HandHeldGameConsole.Instruction>

    init {
        instructions = readInput(input)
    }

    private fun readInput(fileName: String): List<HandHeldGameConsole.Instruction> {
        return File(fileName).readLines().map {
            val line = INSTRUCTION_REGEX.find(it)
            val (operation, parameter) = line!!.destructured
            HandHeldGameConsole.Instruction(HandHeldGameConsole.Operation.valueOf(operation.toUpperCase()), parameter.toInt())
        }
    }

    fun part1(): Int {
        val handHeldGameConsole = HandHeldGameConsole(instructions)
        handHeldGameConsole.execute()
        return handHeldGameConsole.accMemory
    }

    fun part2(): Int {
        val handHeldGameConsole = HandHeldGameConsole(instructions)

        var index = -1
        do {
            val changedOps = changeInstructions(index)
            if (changedOps.isNotEmpty()) {
                handHeldGameConsole.newInstructions(changedOps)
                handHeldGameConsole.execute()
            }
            index++
        } while (handHeldGameConsole.loopDetected && index < instructions.size)

        return handHeldGameConsole.accMemory
    }

    private fun changeInstructions(indexToChange: Int): List<HandHeldGameConsole.Instruction> {
        if (indexToChange < 0) {
            return instructions
        }

        if (!allowedToChangeOperation(indexToChange)) {
            //Don't execute a list which didn't change
            return emptyList()
        }

        val newInstructions = mutableListOf<HandHeldGameConsole.Instruction>()

        instructions.forEachIndexed { index, instruction ->
            newInstructions.add(if (index == indexToChange) {
                HandHeldGameConsole.Instruction(changeOperation(instruction.operation), instruction.parameter)
            } else {
                HandHeldGameConsole.Instruction(instruction.operation, instruction.parameter)
            })
        }

        return newInstructions
    }

    private fun allowedToChangeOperation(indexToChange: Int) =
            instructions[indexToChange].operation == HandHeldGameConsole.Operation.NOP || instructions[indexToChange].operation == HandHeldGameConsole.Operation.JMP

    private fun changeOperation(operation: HandHeldGameConsole.Operation): HandHeldGameConsole.Operation {
        return when (operation) {
            HandHeldGameConsole.Operation.JMP -> HandHeldGameConsole.Operation.NOP
            HandHeldGameConsole.Operation.NOP -> HandHeldGameConsole.Operation.JMP
            else -> operation
        }
    }

}

class HandHeldGameConsole(var instructions: List<Instruction>, var instructionPointer: Int = 0, var accMemory: Int = 0, var loopDetected: Boolean = false) {
    enum class Operation {
        ACC,
        JMP,
        NOP
    }

    data class Instruction(val operation: Operation, val parameter: Int, var executed: Boolean = false)

    fun execute() {
        while (instructionPointerIsValid()) {
            val instruction = instructions[instructionPointer]

            if (instruction.executed) {
                loopDetected = true
                break
            }

            val (operation, parameter, _) = instruction
            when (operation) {
                Operation.ACC -> acc(parameter)
                Operation.JMP -> jmp(parameter)
                Operation.NOP -> nop()
            }
            instruction.executed = true
        }
    }

    fun newInstructions(instructions: List<Instruction>) {
        this.instructions = instructions
        this.instructionPointer = 0
        this.accMemory = 0
        this.loopDetected = false
    }

    private fun instructionPointerIsValid() = instructionPointer >= 0 && instructionPointer < instructions.size

    private fun acc(parameter: Int) {
        accMemory += parameter
        instructionPointer++
    }

    private fun jmp(parameter: Int) {
        instructionPointer += parameter
    }

    private fun nop() {
        //Do nothing
        instructionPointer++
    }
}