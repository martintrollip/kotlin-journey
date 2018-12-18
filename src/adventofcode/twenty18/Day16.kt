package adventofcode.twenty18

import java.io.File

/**
 * @author Martin Trollip
 * @since 2018/12/17 19:50
 */

const val DAY16_INPUT = "src/res/day16_input"
val SAMPLE_TEXT =
        "Before: \\[([0-9]+), ([0-9]+), ([0-9]+), ([0-9]+)\\]\r\n" +
                "([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+)\r\n" +
                "After:  \\[([0-9]+), ([0-9]+), ([0-9]+), ([0-9])+\\]"
val SAMPLE_REGEX = SAMPLE_TEXT.toRegex()

fun main(args: Array<String>) {
    val samples = readInput(DAY16_INPUT)
    val opcodes = getAllOpCodes()

    part1(samples, opcodes)
    part2(samples, opcodes)
}

private fun part1(samples: List<Sample>, opcodes: List<OpCode>) {
    var samplesCounted = 0
    for (sample in samples) {
        var opCodeMatched = 0
        var print = "$sample matches "
        for (opcode in opcodes) {
            val opResult = opcode.operation(sample.instruction.a, sample.instruction.b, sample.instruction.c, sample.before)
            if (opResult.compareWith(sample.after)) {
                print += " $opcode"
                opCodeMatched++
            }
        }
        if (opCodeMatched >= 3) {
            println(print)
            samplesCounted++
        }
    }

    println("$samplesCounted samples in the puzzle input behave like three or more opcodes") //242 is too low
}

private fun part2(samples: List<Sample>, opcodes: List<OpCode>) {
    var opcodeMatches = LinkedHashMap<Int, List<OpCode>>()


}

fun readInput(file: String): List<Sample> {
    val text = File(file).readText(Charsets.UTF_8)
    val matchResult = SAMPLE_REGEX.findAll(text)
    val samples = ArrayList<Sample>()

    matchResult.iterator().forEach {
        val (beforeR_A, beforeR_B, beforeR_C, beforeR_D,
                instructionName, instructionA, instructionB, instructionC,
                afterR_A, afterR_B) = it.destructured

        //Can only deconstruct 10 elements from $it.deconstructed
        val afterR_C = it.groupValues.get(11)
        val afterR_D = it.groupValues.get(12)

        val before = listOf(Register("A", beforeR_A.toInt()), Register("B", beforeR_B.toInt()), Register("C", beforeR_C.toInt()), Register("D", beforeR_D.toInt()))
        val after = listOf(Register("A", afterR_A.toInt()), Register("B", afterR_B.toInt()), Register("C", afterR_C.toInt()), Register("D", afterR_D.toInt()))
        val instruction = Instruction(instructionName.toInt(), instructionA.toInt(), instructionB.toInt(), instructionC.toInt())

        samples.add(Sample(before, instruction, after))
    }

    return samples
}

fun getAllOpCodes(): List<OpCode> {
    return listOf(Addr(), Addi(), Mulr(), Muli(), Banr(), Bani(), Borr(), Bori(), Setr(), Seti(), Gtir(), Gtri(), Gtrr(), Eqir(), Eqri(), Eqrr())
}

data class Register(var name: String, var value: Int)

fun List<Register>.compareWith(other: List<Register>): Boolean {
    for (i in 0 until 4) {
        if (this[i].value != other[i].value) {
            return false
        }
    }
    return true
}

abstract class OpCode(var name: Int = -1) {
    protected abstract fun execute(a: Int, b: Int, registers: List<Register>): Int

    fun operation(a: Int, b: Int, c: Int, registers: List<Register>): List<Register> {
        val newRegister = listOf(
                Register(registers[0].name, registers[0].value),
                Register(registers[1].name, registers[1].value),
                Register(registers[2].name, registers[2].value),
                Register(registers[3].name, registers[3].value))
        newRegister[c].value = execute(a, b, newRegister)
        return newRegister
    }

    override fun toString(): String {
        return this.javaClass.simpleName
    }
}

//Addition:
class Addr : OpCode() {
    //addr (add register) stores into register C the result of adding register A and register B.
    override fun execute(a: Int, b: Int, registers: List<Register>): Int {
        return registers[a].value + registers[b].value
    }
}

class Addi : OpCode() {
    //addi (add immediate) stores into register C the result of adding register A and value B.
    override fun execute(a: Int, b: Int, registers: List<Register>): Int {
        return registers[a].value + b
    }
}

//Multiplication:
class Mulr : OpCode() {
    //mulr (multiply register) stores into register C the result of multiplying register A and register B.
    override fun execute(a: Int, b: Int, registers: List<Register>): Int {
        return registers[a].value * registers[b].value
    }
}

class Muli : OpCode() {
    //muli (multiply immediate) stores into register C the result of multiplying register A and value B.
    override fun execute(a: Int, b: Int, registers: List<Register>): Int {
        return registers[a].value * b
    }
}

//Bitwise AND:
class Banr : OpCode() {
    //banr (bitwise AND register) stores into register C the result of the bitwise AND of register A and register B.
    override fun execute(a: Int, b: Int, registers: List<Register>): Int {
        return registers[a].value and registers[b].value
    }
}

class Bani : OpCode() {
    //bani (bitwise AND immediate) stores into register C the result of the bitwise AND of register A and value B.
    override fun execute(a: Int, b: Int, registers: List<Register>): Int {
        return registers[a].value and b
    }
}

//Bitwise OR:
class Borr : OpCode() {
    //borr (bitwise OR register) stores into register C the result of the bitwise OR of register A and register B.
    override fun execute(a: Int, b: Int, registers: List<Register>): Int {
        return registers[a].value or registers[b].value
    }
}

class Bori : OpCode() {
    //bori (bitwise OR immediate) stores into register C the result of the bitwise OR of register A and value B.
    override fun execute(a: Int, b: Int, registers: List<Register>): Int {
        return registers[a].value or b
    }
}

//Assignment:
class Setr : OpCode() {
    //setr (set register) copies the contents of register A into register C. (Input B is ignored.)
    override fun execute(a: Int, b: Int, registers: List<Register>): Int {
        return registers[a].value
    }
}

class Seti : OpCode() {
    //seti (set immediate) stores value A into register C. (Input B is ignored.)
    override fun execute(a: Int, b: Int, registers: List<Register>): Int {
        return a
    }
}

//Greater-than testing:
class Gtir : OpCode() {
    //gtir (greater-than immediate/register) sets register C to 1 if value A is greater than register B. Otherwise, register C is set to 0.
    override fun execute(a: Int, b: Int, registers: List<Register>): Int {
        return if (a > registers[b].value) 1 else 0
    }
}

class Gtri : OpCode() {
    //gtri (greater-than register/immediate) sets register C to 1 if register A is greater than value B. Otherwise, register C is set to 0.
    override fun execute(a: Int, b: Int, registers: List<Register>): Int {
        return if (registers[a].value > b) 1 else 0
    }
}

class Gtrr : OpCode() {
    //gtrr (greater-than register/register) sets register C to 1 if register A is greater than register B. Otherwise, register C is set to 0.
    override fun execute(a: Int, b: Int, registers: List<Register>): Int {
        return if (registers[a].value > registers[b].value) 1 else 0
    }
}

//Equality testing:
class Eqir : OpCode() {
    //eqir (equal immediate/register) sets register C to 1 if value A is equal to register B. Otherwise, register C is set to 0.
    override fun execute(a: Int, b: Int, registers: List<Register>): Int {
        return if (a == registers[b].value) 1 else 0
    }
}

class Eqri : OpCode() {
    //eqri (equal register/immediate) sets register C to 1 if register A is equal to value B. Otherwise, register C is set to 0.
    override fun execute(a: Int, b: Int, registers: List<Register>): Int {
        return if (registers[a].value == b) 1 else 0
    }
}

class Eqrr : OpCode() {
    //eqrr (equal register/register) sets register C to 1 if register A is equal to register B. Otherwise, register C is set to 0.
    override fun execute(a: Int, b: Int, registers: List<Register>): Int {
        return if (registers[a].value == registers[b].value) 1 else 0
    }
}

data class Instruction(var opCodeName: Int, var a: Int, var b: Int, var c: Int)

data class Sample(var before: List<Register>, var instruction: Instruction, var after: List<Register>)