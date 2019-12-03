package adventofcode.twenty19

import java.io.File

/**
 * @author Martin Trollip
 * @since 2019/12/02 07:21
 */
private const val DAY2_INPUT = "src/res/twenty19/day2_input"

fun main(args: Array<String>) {
    val day2 = Day2(DAY2_INPUT)
    //Input was updated manually; before running the program, replace position 1 (was 0) with the value 12 and replace position 2 (was 0) with the value 2.
    println("What value is left at position 0 after the program halts? ${day2.execute(day2.readInput())[0]}")
    println("Noun and verb causing output of 19690720")
    day2.executeNounVerb()
}


class Day2(val input: String) {

    enum class Opcode {
        ADD, MULTIPLY, TERMINATE
    }

    data class Instruction(val type: Opcode, val a: Int = -1, val b: Int = -1, val result: Int = -1)

    fun readInput(): Array<Int> {
        return File(input).readLines()[0].split(",").map { it.toInt() }.toTypedArray()
    }

    fun getOpcode(opcode: Int): Opcode {
        return when (opcode) {
            1 -> Opcode.ADD
            2 -> Opcode.MULTIPLY
            else -> Opcode.TERMINATE
        }
    }

    fun add(a: Int, b: Int): Int {
        return a + b
    }

    fun multiply(a: Int, b: Int): Int {
        return a * b
    }

    fun getInstructionAt(instructions: Array<Int>, currentPosition: Int): Instruction {
        val opcode = getOpcode(instructions[currentPosition])

        if (opcode == Opcode.TERMINATE) {
            return Instruction(opcode)
        }

        return Instruction(opcode, instructions[currentPosition + 1], instructions[currentPosition + 2], instructions[currentPosition + 3])
    }

    //Part 1
    fun execute(memory: Array<Int>): Array<Int> {
        var address = 0

        do {
            val instruction = getInstructionAt(memory, address)
            when (instruction.type) {
                Opcode.ADD -> memory[instruction.result] = add(memory[instruction.a], memory[instruction.b])
                Opcode.MULTIPLY -> memory[instruction.result] = multiply(memory[instruction.a], memory[instruction.b])
            }
            address += 4
        } while (instruction.type != Opcode.TERMINATE)
        return memory
    }

    //Part 2
    fun executeNounVerb() {
        for (noun in 0..99) {
            for (verb in 0..99) {
                val readInput = readInput()
                readInput[1] = noun
                readInput[2] = verb
                if (execute(readInput)[0] == 19690720) {
                    println("noun=$noun verb=$verb ans=${noun * 100 + verb}")
                    return
                }
            }
        }
        println("none")
    }
}