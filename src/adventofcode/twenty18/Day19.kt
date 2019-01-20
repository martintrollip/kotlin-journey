package adventofcode.twenty18

import java.io.File
import java.rmi.UnexpectedException

/**
 * @author Martin Trollip
 * @since 2019/01/02 11:01
 */

const val DAY19_INPUT = "src/res/day19_input"

val POINTER_REGEX = "#ip ([0-9]+)".toRegex()
val OPERATION_REGEX = "(addr|addi|mulr|muli|banr|bani|borr|bori|setr|seti|gtir|gtri|gtrr|eqir|eqri|eqrr) ([0-9]+) ([0-9]+) ([0-9]+)".toRegex()

var registers = listOf(Register("0", 0), Register("1"), Register("2"), Register("3"), Register("4"), Register("5"))

fun main(args: Array<String>) {
    val instructions = readDay19Input(DAY19_INPUT)
    val pointer = readDay19Pointer(DAY19_INPUT)

    println("Part1: Value left in register 0 when the background process halts? ${part1(pointer, instructions)}")
    println("Part2: Value left in register 0 when the background process halts? ${10551383.factors().sum()}")//hacks, looked at the largest number during execution
}

fun part1(pointer: Pointer, instructions: Map<Int, NamedInstruction>): Int {
    return execute(pointer, instructions)[0].value
}

fun part2(pointer: Pointer, instructions: Map<Int, NamedInstruction>): Int {
    registers[0].value = 1
    return execute(pointer, instructions)[0].value
}

fun execute(pointer: Pointer, instructions: Map<Int, NamedInstruction>): List<Register> {
    val ip = registers[pointer.address]

    while (pointer.value < instructions.size) {
        //val regBefore = registers.toString()
        //val ipBefore = pointer.toString()

        val instruction = instructions[pointer.value]!!

        //Update register [pointer] to the current instruction pointer value
        ip.value = pointer.value

        //Do operation
        instruction.opcode.operation(instruction.a, instruction.b, instruction.c, registers)
        //val regAfter = registers.toString()

        //Set the instruction pointer to the value of register [pointer]
        pointer.value = ip.value

        //Add one to pointer value
        pointer.value++

        //println("$ipBefore $regBefore $instruction $regAfter")

        if(ip.value == 28 && registers[0].value > 0) {
            println(registers)
        }
    }

    return registers
}

/**
 * Thanks to Todd Ginsberg, <a href="https://todd.ginsberg.com/post/advent-of-code/2018/day19/">Day 19</a> for pointing out the
 * logic the device performs
 */
private fun Int.factors(): List<Int> =
        (1..this).mapNotNull { n ->
            if (this % n == 0) n else null
        }

fun readDay19Pointer(file: String): Pointer {
    var pointer: Pointer? = null
    File(file).readLines().forEach {
        val pointerMatch = POINTER_REGEX.find(it)
        if (pointerMatch != null) {
            val (address) = pointerMatch.destructured
            pointer = Pointer(address.toInt())
        }
    }
    return pointer!!
}

fun readDay19Input(file: String): Map<Int, NamedInstruction> {
    val operations = LinkedHashMap<Int, NamedInstruction>()

    var count = 0
    File(file).readLines().forEach {
        val operationMatch = OPERATION_REGEX.find(it)
        if (operationMatch != null) {
            val (name, a, b, c) = operationMatch.destructured
            operations[count++] = NamedInstruction(getOperationFrom(name), a.toInt(), b.toInt(), c.toInt())
        }
    }

    return operations
}

fun getOperationFrom(name: String): OpCode {
    when (name) {
        "addr" -> {
            return Addr()
        }
        "addi" -> {
            return Addi()
        }
        "mulr" -> {
            return Mulr()
        }
        "muli" -> {
            return Muli()
        }
        "bani" -> {
            return Bani()
        }
        "borr" -> {
            return Borr()
        }
        "bori" -> {
            return Bori()
        }
        "setr" -> {
            return Setr()
        }
        "seti" -> {
            return Seti()
        }
        "gtir" -> {
            return Gtir()
        }
        "gtri" -> {
            return Gtri()
        }
        "gtrr" -> {
            return Gtrr()
        }
        "eqir" -> {
            return Eqir()
        }
        "eqri" -> {
            return Eqri()
        }
        "eqrr" -> {
            return Eqrr()
        }
        else -> {
            throw UnexpectedException("$name not mapped")
        }
    }
}

open class NamedInstruction(var opcode: OpCode, var a: Int, var b: Int, var c: Int) {
    override fun toString(): String {
        return "${opcode.name} $a $b $c"
    }
}

class Pointer(var address: Int, var value: Int = 0) : NamedInstruction(Addr(), -1, -1, -1) {
    override fun toString(): String {
        return "ip=$value"
    }
}