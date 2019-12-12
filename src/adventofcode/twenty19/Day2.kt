package adventofcode.twenty19

import java.io.File
import java.rmi.UnexpectedException

/**
 * @author Martin Trollip
 * @since 2019/12/02 07:21
 */
private const val DAY2_INPUT = "src/res/twenty19/day2_input"

fun main(args: Array<String>) {
    val day2 = Day2(DAY2_INPUT)
    val computer = IntcodeComputer()
    //Input was updated manually; before running the program, replace position 1 (was 0) with the value 12 and replace position 2 (was 0) with the value 2.
    println("What value is left at position 0 after the program halts? ${computer.execute(day2.readInput(), 0)[0]}") //4462686
    println("Noun and verb causing output of 19690720")//noun=59 verb=36 ans=5936
    day2.executeNounVerb(computer)
}


class Day2(val input: String) {

    fun readInput(): Array<Long> {
        return File(input).readLines()[0].split(",").map { it.toLong() }.toTypedArray()
    }

    //Part 2
    fun executeNounVerb(computer: IntcodeComputer) {
        for (noun in 0..99L) {
            for (verb in 0..99L) {
                val readInput = readInput()
                readInput[1] = noun
                readInput[2] = verb
                if (computer.execute(readInput, 0)[0] == 19690720L) {
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
        ADD, MULTIPLY, TERMINATE, STORE, OUTPUT, JUMP_TRUE, JUMP_FALSE, LESS_THAN, EQUALS, RELATIVE_BASE
    }

    enum class Mode {
        POSITION, IMMEDIATE, RELATIVE
    }

    data class Instruction(val type: Opcode, val mode: Long = 0, val a: Long = -1, val b: Long = -1, val result: Long = -1)

    fun getOpcode(opcode: Long): Opcode {
        return when (opcode % 100) {
            1L -> Opcode.ADD
            2L -> Opcode.MULTIPLY
            3L -> Opcode.STORE
            4L -> Opcode.OUTPUT
            5L -> Opcode.JUMP_TRUE
            6L -> Opcode.JUMP_FALSE
            7L -> Opcode.LESS_THAN
            8L -> Opcode.EQUALS
            9L -> Opcode.RELATIVE_BASE
            99L -> Opcode.TERMINATE
            else -> throw UnexpectedException("Invalid opcode $opcode")
        }
    }

    fun getMode(opcode: Long, parameterPos: Int): Mode {
        val string = opcode.toString() //TODO martin do this the right way
        val pos = string.length - 3 /*for opcode instruction (2) and zero indexed (1)*/ - parameterPos

        return if (pos < 0) {
            Mode.POSITION
        } else {
            when (string[pos]) {
                '1' -> Mode.IMMEDIATE
                '2' -> Mode.RELATIVE
                else -> Mode.POSITION
            }
        }
    }

    fun getValue(aMode: Mode, register: Long, memory: Array<Long>): Long {
        return when (aMode) {
            Mode.POSITION -> memory[register.toInt()]
            Mode.IMMEDIATE -> register
            Mode.RELATIVE -> memory[(relativeBase + register).toInt()]
        }
    }

    fun getOutputAddress(resultMode: Mode, register: Long): Int {
        return if (resultMode == Mode.RELATIVE) {
            relativeBase.toInt() + register.toInt()
        } else {
            register.toInt()
        }
    }

    //TODO Martin can this be simplified if a map is used?
    fun increaseMemorySize(memory: Array<Long>): Array<Long> {
        val original = memory.toMutableList()
        val increaseMemory = Array(memory.size * 10) { 0L }

        original.addAll(increaseMemory)

        return original.toTypedArray()
    }

    fun add(a: Long, b: Long): Long {
        return a + b
    }

    fun multiply(a: Long, b: Long): Long {
        return a * b
    }

    fun store(a: Long): Long {
        return a
    }

    fun output(a: Long): Long {
        return a
    }

    fun jumptrue(a: Long, b: Long, address: Int): Long {
        return if (a != 0L) {
            b
        } else {
            address.toLong() + 3
        }
    }

    fun jumpfalse(a: Long, b: Long, address: Int): Long {
        return if (a == 0L) {
            b
        } else {
            address.toLong() + 3
        }
    }

    fun lessthan(a: Long, b: Long): Long {
        return if (a < b) {
            1
        } else {
            0
        }
    }

    fun equals(a: Long, b: Long): Long {
        return if (a == b) {
            1
        } else {
            0
        }
    }

    fun updateRelative(a: Long): Long {
        relativeBase += a
        return relativeBase
    }

    fun getInstructionAt(instructions: Array<Long>, currentPosition: Int): Instruction {
        val opcode = getOpcode(instructions[currentPosition])

        //TODO this should be simpler
        return when (opcode) {
            Opcode.ADD -> Instruction(opcode, instructions[currentPosition], instructions[currentPosition + 1], instructions[currentPosition + 2], instructions[currentPosition + 3])
            Opcode.MULTIPLY -> Instruction(opcode, instructions[currentPosition], instructions[currentPosition + 1], instructions[currentPosition + 2], instructions[currentPosition + 3])
            Opcode.STORE -> Instruction(opcode, instructions[currentPosition], result = instructions[currentPosition + 1])
            Opcode.OUTPUT -> Instruction(opcode, instructions[currentPosition], result = instructions[currentPosition + 1])
            Opcode.JUMP_TRUE -> Instruction(opcode, instructions[currentPosition], instructions[currentPosition + 1], instructions[currentPosition + 2])
            Opcode.JUMP_FALSE -> Instruction(opcode, instructions[currentPosition], instructions[currentPosition + 1], instructions[currentPosition + 2])
            Opcode.LESS_THAN -> Instruction(opcode, instructions[currentPosition], instructions[currentPosition + 1], instructions[currentPosition + 2], instructions[currentPosition + 3])
            Opcode.EQUALS -> Instruction(opcode, instructions[currentPosition], instructions[currentPosition + 1], instructions[currentPosition + 2], instructions[currentPosition + 3])
            Opcode.RELATIVE_BASE -> Instruction(opcode, instructions[currentPosition], instructions[currentPosition + 1])
            Opcode.TERMINATE -> Instruction(opcode)
        }
    }

    private var relativeBase = 0L
    private var rememberAddress = 0
    var running = true
    fun execute(memory: Array<Long>, input: Long = 0, returnAfter: Int = -1): Array<Long> {
        val output = mutableListOf<Long>()

        do {
            val instruction = getInstructionAt(memory, rememberAddress)
            if (instruction.type == Opcode.TERMINATE) {
                break
            }

            val aMode = getMode(instruction.mode, 0)
            val bMode = getMode(instruction.mode, 1)
            val resultMode = getMode(instruction.mode, 2)

            when (instruction.type) {
                Opcode.ADD -> {
                    memory[getOutputAddress(resultMode, instruction.result)] = add(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))
                    rememberAddress += 4
                }
                Opcode.MULTIPLY -> {
                    memory[getOutputAddress(resultMode, instruction.result)] = multiply(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))
                    rememberAddress += 4
                }
                Opcode.STORE -> {
                    memory[getOutputAddress(aMode, instruction.result)] = store(input)
                    rememberAddress += 2
                }
                Opcode.OUTPUT -> {
                    val ans = getValue(aMode, instruction.result, memory)
                    output.add(ans)
//                    println(ans)
                    rememberAddress += 2

                    if (returnAfter > 0 && output.size == returnAfter) {
                        return output.toTypedArray()
                    }
                }
                Opcode.JUMP_TRUE -> {
                    rememberAddress = jumptrue(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory), rememberAddress).toInt()
                }
                Opcode.JUMP_FALSE -> {
                    rememberAddress = jumpfalse(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory), rememberAddress).toInt()
                }
                Opcode.LESS_THAN -> {
                    memory[getOutputAddress(resultMode, instruction.result)] = lessthan(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))//TODO martin move getValue
                    rememberAddress += 4
                }
                Opcode.EQUALS -> {
                    memory[getOutputAddress(resultMode, instruction.result)] = equals(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))
                    rememberAddress += 4
                }
                Opcode.RELATIVE_BASE -> {
                    updateRelative(getValue(aMode, instruction.a, memory))
                    rememberAddress += 2
                }
            }
        } while (instruction.type != Opcode.TERMINATE)

        //If output array is empty, return whole memory
        running = false
        return if (output.isEmpty()) {
            memory
        } else {
            output.toTypedArray()
        }
    }

    fun executeList(memory: Array<Long>, input: Array<Long> = arrayOf()): MutableList<Long> {
        val output = mutableListOf<Long>()

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
                    memory[instruction.result.toInt()] = add(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))
                    address += 4
                }
                Opcode.MULTIPLY -> {
                    memory[instruction.result.toInt()] = multiply(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))
                    address += 4
                }
                Opcode.STORE -> {
                    memory[instruction.result.toInt()] = store(input[inputCounter])
                    inputCounter++
                    address += 2
                }
                Opcode.OUTPUT -> {
//                    println(memory[instruction.result])
                    output.add(memory[instruction.result.toInt()])
                    address += 2
                }
                Opcode.JUMP_TRUE -> {
                    address = jumptrue(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory), address).toInt()
                }
                Opcode.JUMP_FALSE -> {
                    address = jumpfalse(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory), address).toInt()
                }
                Opcode.LESS_THAN -> {
                    memory[instruction.result.toInt()] = lessthan(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))//TODO martin move getValue in or up
                    address += 4
                }
                Opcode.EQUALS -> {
                    memory[instruction.result.toInt()] = equals(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))
                    address += 4
                }
                Opcode.RELATIVE_BASE -> {
                    updateRelative(getValue(aMode, instruction.result, memory))
                    rememberAddress += 2
                }
            }
        } while (instruction.type != Opcode.TERMINATE)
        return output
    }

    fun executeToOutput(memory: Array<Long>, input: Array<Long> = arrayOf()): Long {
        var inputCounter = 0

        do {
            val instruction = getInstructionAt(memory, rememberAddress)
            if (instruction.type == Opcode.TERMINATE) {
                break
            }

            val aMode = getMode(instruction.mode, 0)
            val bMode = getMode(instruction.mode, 1)
            val resultMode = getMode(instruction.mode, 2)

            when (instruction.type) {
                Opcode.ADD -> {
                    memory[getOutputAddress(resultMode, instruction.result)] = add(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))
                    rememberAddress += 4
                }
                Opcode.MULTIPLY -> {
                    memory[getOutputAddress(resultMode, instruction.result)] = multiply(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))
                    rememberAddress += 4
                }
                Opcode.STORE -> {
                    memory[getOutputAddress(aMode, instruction.result)] = store(input[inputCounter])
                    inputCounter++
                    rememberAddress += 2
                }
                Opcode.OUTPUT -> {
                    rememberAddress += 2
                    return memory[instruction.result.toInt()]
                }
                Opcode.JUMP_TRUE -> {
                    rememberAddress = jumptrue(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory), rememberAddress).toInt()
                }
                Opcode.JUMP_FALSE -> {
                    rememberAddress = jumpfalse(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory), rememberAddress).toInt()
                }
                Opcode.LESS_THAN -> {
                    memory[getOutputAddress(resultMode, instruction.result)] = lessthan(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))//TODO martin move getValue
                    rememberAddress += 4
                }
                Opcode.EQUALS -> {
                    memory[getOutputAddress(resultMode, instruction.result)] = equals(getValue(aMode, instruction.a, memory), getValue(bMode, instruction.b, memory))
                    rememberAddress += 4
                }
                Opcode.RELATIVE_BASE -> {
                    updateRelative(getValue(aMode, instruction.a, memory))
                    rememberAddress += 2
                }
            }
        } while (instruction.type != Opcode.TERMINATE)

        return -9999
    }
}