package adventofcode.twenty19

import java.io.File

/**
 * @author Martin Trollip
 * @since 2019/12/02 07:21
 */
private const val DAY2_INPUT = "src/res/twenty19/day2_input"

fun main(args: Array<String>) {
    val day2 = Day2(DAY2_INPUT)
    val computer = IntcodeComputer()
    //Input was updated manually; before running the program, replace position 1 (was 0) with the value 12 and replace position 2 (was 0) with the value 2.
    println("What value is left at position 0 after the program halts? ${computer.execute(day2.readInput())[0]}") //4462686
    println("Noun and verb causing output of 19690720")                                                           //noun=59 verb=36 ans=5936
    day2.executeNounVerb(computer)
}


class Day2(val input: String) {

    fun readInput(): Array<Int> {
        return File(input).readLines()[0].split(",").map { it.toInt() }.toTypedArray()
    }

    //Part 2
    fun executeNounVerb(computer: IntcodeComputer) {
        for (noun in 0..99) {
            for (verb in 0..99) {
                val readInput = readInput()
                readInput[1] = noun
                readInput[2] = verb
                if (computer.execute(readInput)[0] == 19690720) {
                    println("noun=$noun verb=$verb ans=${noun * 100 + verb}")
                    return
                }
            }
        }
        println("none")
    }
}

//TODO Martin this can be simplified
class IntcodeComputer {
    enum class Opcode {
        ADD, MULTIPLY, TERMINATE, STORE, OUTPUT
    }

    enum class Mode {
        POSITION, IMMEDIATE
    }

    data class Instruction(val type: Opcode, val mode: Int = 0, val a: Int = -1, val b: Int = -1, val result: Int = -1)

    fun getOpcode(opcode: Int): Opcode {
        return when (opcode % 100) {
            1 -> Opcode.ADD
            2 -> Opcode.MULTIPLY
            3 -> Opcode.STORE
            4 -> Opcode.OUTPUT
            else -> Opcode.TERMINATE
        }
    }

    fun getMode(opcode: Int, parameterPos: Int): Mode {
        val string = opcode.toString() //TODO martin do this the right way
        val pos = string.length - 3 /*for opcode instruction and zero indexed*/ - parameterPos

        return if (pos < 0) {
            Mode.POSITION
        } else {
            when (string[pos]) {
                '1' -> Mode.IMMEDIATE
                else -> Mode.POSITION
            }
        }
    }

    fun add(a: Int, b: Int): Int {
        return a + b
    }

    fun multiply(a: Int, b: Int): Int {
        return a * b
    }

    fun store(a: Int): Int {
        return a
    }

    fun output(a: Int): Int {
        return a
    }

    fun getInstructionAt(instructions: Array<Int>, currentPosition: Int): Instruction {
        val opcode = getOpcode(instructions[currentPosition])

        return when (opcode) {
            Opcode.ADD -> Instruction(opcode, instructions[currentPosition], instructions[currentPosition + 1], instructions[currentPosition + 2], instructions[currentPosition + 3])
            Opcode.MULTIPLY -> Instruction(opcode, instructions[currentPosition], instructions[currentPosition + 1], instructions[currentPosition + 2], instructions[currentPosition + 3])
            Opcode.STORE -> Instruction(opcode, instructions[currentPosition], result = instructions[currentPosition + 1])
            Opcode.OUTPUT -> Instruction(opcode, instructions[currentPosition], result = instructions[currentPosition + 1])
            Opcode.TERMINATE -> Instruction(opcode)
        }
    }

    fun execute(memory: Array<Int>, input: Int = 0): Array<Int> {
        var address = 0

        do {
            val instruction = getInstructionAt(memory, address)
            if (instruction.type == Opcode.TERMINATE) {
                break
            }

            val aMode = getMode(instruction.mode, 0)
            val bMode = getMode(instruction.mode, 1)

            when (instruction.type) {
                Opcode.ADD -> {
                    memory[instruction.result] = add(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))
                    address += 4
                }
                Opcode.MULTIPLY -> {
                    memory[instruction.result] = multiply(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))
                    address += 4
                }
                Opcode.STORE -> {
                    memory[instruction.result] = store(input)
                    address += 2
                }
                Opcode.OUTPUT -> {
                    println(memory[instruction.result])
                    address += 2
                }
            }
        } while (instruction.type != Opcode.TERMINATE)
        return memory
    }

    private fun getValue(aMode: Mode, register: Int, memory: Array<Int>): Int {
        return if (aMode == Mode.POSITION) {
            memory[register]
        } else {
            register
        }
    }
}