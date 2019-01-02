package adventofcode.twenty18

import java.io.File

/**
 * @author Martin Trollip
 * @since 2019/01/02 11:01
 */

const val DAY19_INPUT = "src/res/day19_input"

val POINTER_REGEX = "#ip ([0-9]+)".toRegex()
val OPERATION_REGEX = "(addi|seti|mulr|eqrr|addr|gtrr|muli|setr) ([0-9]+) ([0-9]+) ([0-9]+)".toRegex()

val allOpcodes = mapOf(Pair("addr", Addr()), Pair("addi", Addi()), Pair("mulr", Mulr()), Pair("muli", Muli()), Pair("banr", Banr()),
        Pair("bani", Bani()), Pair("borr", Borr()), Pair("bori", Bori()), Pair("setr", Setr()), Pair("seti", Seti()),
        Pair("gtir", Gtir()), Pair("gtri", Gtri()), Pair("gtrr", Gtrr()), Pair("eqir", Eqir()), Pair("eqri", Eqri()),
        Pair("eqrr", Eqrr()))

var registers = listOf(Register("0"), Register("1"), Register("2"), Register("3"), Register("4"), Register("5"))

fun main(args: Array<String>) {
    val instructions = readDay19Input(DAY19_INPUT)
    val pointer = readDay19Pointer(DAY19_INPUT)

    println("Value left in register 0 when the background process halts? ${part1(pointer, instructions)}")
}

fun part1(pointer: Pointer, instructions: ArrayList<NamedInstruction>): Int {
    return execute(pointer, instructions)[0].value
}

fun execute(pointer: Pointer, instructions: ArrayList<NamedInstruction>): List<Register> {
    while (pointer.value < instructions.size) {
        val regBefore = registers.toString()
        val ipBefore = pointer.toString()

        val instruction = instructions[pointer.value]
        val operation = getOperation(pointer, instructions)

        //Update register [pointer] to the current instruction pointer value
        registers[pointer.address].value = pointer.value

        //Do operation
        registers = operation.operation(instruction.a, instruction.b, instruction.c, registers)
        val regAfter = registers.toString()

        //Set the instruction pointer to the value of register [pointer]
        pointer.value = registers[pointer.address].value

        //Add one to pointer value
        pointer.value++

//        println("$ipBefore $regBefore $instruction $regAfter")
    }

    return registers
}

fun getOperation(pointer: Pointer, instructions: ArrayList<NamedInstruction>): OpCode {
    return allOpcodes[instructions[(pointer.value)].opCodeName]!!
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

fun readDay19Input(file: String): ArrayList<NamedInstruction> {
    val operations = ArrayList<NamedInstruction>()

    File(file).readLines().forEach {
        val operationMatch = OPERATION_REGEX.find(it)
        if (operationMatch != null) {
            val (name, a, b, c) = operationMatch.destructured
            operations.add(NamedInstruction(name, a.toInt(), b.toInt(), c.toInt()))
        }
    }

    return operations
}

open class NamedInstruction(var opCodeName: String, var a: Int, var b: Int, var c: Int) {
    override fun toString(): String {
        return "$opCodeName $a $b $c"
    }
}

class Pointer(var address: Int, var value: Int = 0) : NamedInstruction("ip", -1, -1, -1) {
    override fun toString(): String {
        return "$opCodeName=$value"
    }
}