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
        ADD, MULTIPLY, TERMINATE, STORE, OUTPUT, JUMPTRUE, JUMPFALSE, LESSTHAN, EQUALS
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
            5 -> Opcode.JUMPTRUE
            6 -> Opcode.JUMPFALSE
            7 -> Opcode.LESSTHAN
            8 -> Opcode.EQUALS
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

    fun jumptrue(a: Int, b: Int, address: Int): Int {
        return if (a != 0) {
            b
        } else {
            address
        }
    }

    fun jumpfalse(a: Int, b: Int, address: Int): Int {
        return if (a == 0) {
            b
        } else {
            address
        }
    }

    fun lessthan(a: Int, b: Int): Int {
        return if (a < b) {
            1
        } else {
            0
        }
    }

    fun equals(a: Int, b: Int): Int {
        return if (a == b) {
            1
        } else {
            0
        }
    }

    fun getInstructionAt(instructions: Array<Int>, currentPosition: Int): Instruction {
        val opcode = getOpcode(instructions[currentPosition])

        //TODO this should be simpler
        return when (opcode) {
            Opcode.ADD -> Instruction(opcode, instructions[currentPosition], instructions[currentPosition + 1], instructions[currentPosition + 2], instructions[currentPosition + 3])
            Opcode.MULTIPLY -> Instruction(opcode, instructions[currentPosition], instructions[currentPosition + 1], instructions[currentPosition + 2], instructions[currentPosition + 3])
            Opcode.STORE -> Instruction(opcode, instructions[currentPosition], result = instructions[currentPosition + 1])
            Opcode.OUTPUT -> Instruction(opcode, instructions[currentPosition], result = instructions[currentPosition + 1])
            Opcode.JUMPTRUE -> Instruction(opcode, instructions[currentPosition], instructions[currentPosition + 1], instructions[currentPosition + 2])
            Opcode.JUMPFALSE -> Instruction(opcode, instructions[currentPosition], instructions[currentPosition + 1], instructions[currentPosition + 2])
            Opcode.LESSTHAN -> Instruction(opcode, instructions[currentPosition], instructions[currentPosition + 1], instructions[currentPosition + 2], instructions[currentPosition + 3])
            Opcode.EQUALS -> Instruction(opcode, instructions[currentPosition], instructions[currentPosition + 1], instructions[currentPosition + 2], instructions[currentPosition + 3])
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
                Opcode.JUMPTRUE -> {
                    val a = getValue(aMode, instruction.a, memory)
                    if (a != 0) {
                        address = getValue(bMode, instruction.b, memory)
                    } else {
                        address += 3
                    }
                }
                Opcode.JUMPFALSE -> {
                    val a = getValue(aMode, instruction.a, memory)
                    if (a == 0) {
                        address = getValue(bMode, instruction.b, memory)
                    } else {
                        address += 3
                    }
                }
                Opcode.LESSTHAN -> {
                    memory[instruction.result] = lessthan(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))//TODO martin move getValue in or up
                    address += 4
                }
                Opcode.EQUALS -> {
                    memory[instruction.result] = equals(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))
                    address += 4
                }
            }
        } while (instruction.type != Opcode.TERMINATE)
        return memory
    }

    fun executeList(memory: Array<Int>, input: Array<Int> = arrayOf()): MutableList<Int> {
        val output = mutableListOf<Int>()

        var address = 0
        var inputCounter = 0

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
                    memory[instruction.result] = store(input[inputCounter])
                    inputCounter++
                    address += 2
                }
                Opcode.OUTPUT -> {
//                    println(memory[instruction.result])
                    output.add(memory[instruction.result])
                    address += 2
                }
                Opcode.JUMPTRUE -> {
                    val a = getValue(aMode, instruction.a, memory)
                    if (a != 0) {
                        address = getValue(bMode, instruction.b, memory)
                    } else {
                        address += 3
                    }
                }
                Opcode.JUMPFALSE -> {
                    val a = getValue(aMode, instruction.a, memory)
                    if (a == 0) {
                        address = getValue(bMode, instruction.b, memory)
                    } else {
                        address += 3
                    }
                }
                Opcode.LESSTHAN -> {
                    memory[instruction.result] = lessthan(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))//TODO martin move getValue in or up
                    address += 4
                }
                Opcode.EQUALS -> {
                    memory[instruction.result] = equals(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))
                    address += 4
                }
            }
        } while (instruction.type != Opcode.TERMINATE)
        return output
    }

    private var rememberAddress = 0
    fun executeToOutput(memory: Array<Int>, input: Array<Int> = arrayOf()): Int {
        var inputCounter = 0

        do {
            val instruction = getInstructionAt(memory, rememberAddress)
            if (instruction.type == Opcode.TERMINATE) {
                break
            }

            val aMode = getMode(instruction.mode, 0)
            val bMode = getMode(instruction.mode, 1)

            when (instruction.type) {
                Opcode.ADD -> {
                    memory[instruction.result] = add(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))
                    rememberAddress += 4
                }
                Opcode.MULTIPLY -> {
                    memory[instruction.result] = multiply(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))
                    rememberAddress += 4
                }
                Opcode.STORE -> {
                    memory[instruction.result] = store(input[inputCounter])
                    inputCounter++
                    rememberAddress += 2
                }
                Opcode.OUTPUT -> {
                    rememberAddress += 2
                    return memory[instruction.result]
                }
                Opcode.JUMPTRUE -> {
                    val a = getValue(aMode, instruction.a, memory)
                    if (a != 0) {
                        rememberAddress = getValue(bMode, instruction.b, memory)
                    } else {
                        rememberAddress += 3
                    }
                }
                Opcode.JUMPFALSE -> {
                    val a = getValue(aMode, instruction.a, memory)
                    if (a == 0) {
                        rememberAddress = getValue(bMode, instruction.b, memory)
                    } else {
                        rememberAddress += 3
                    }
                }
                Opcode.LESSTHAN -> {
                    memory[instruction.result] = lessthan(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))//TODO martin move getValue in or up
                    rememberAddress += 4
                }
                Opcode.EQUALS -> {
                    memory[instruction.result] = equals(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))
                    rememberAddress += 4
                }
            }
        } while (instruction.type != Opcode.TERMINATE)
        return -9999
    }

    private fun getValue(aMode: Mode, register: Int, memory: Array<Int>): Int {
        return if (aMode == Mode.POSITION) {
            memory[register]
        } else {
            register
        }
    }
}