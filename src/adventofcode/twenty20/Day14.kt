package adventofcode.twenty20

import java.io.File
import java.lang.Math.pow

/**
 * @author Martin Trollip
 * @since 2020/12/14 14:18
 */
private const val DAY14_INPUT = "src/res/twenty20/day14_input"

fun main(args: Array<String>) {
    val day14 = Day14(DAY14_INPUT)
    println("${day14.part1()}")
    println("${day14.part2()}")
}

val BITS = 36

class Day14(val input: String) {

    private val INSTRUCTION_REGEX = "mem\\[([0-9]+)\\]\\s=\\s([0-9]+)".toRegex() //https://regex101.com/r/OSo0zc/1/
    private val MASK_REGEX = "mask\\s=\\s([0X1]+)".toRegex() //https://regex101.com/r/OSo0zc/2

    fun part1(): Long {
        val memory = mutableMapOf<Int, String>()
        var currentMask = 0.convertToBinary()

        File(input).readLines().forEach { line ->
            if (MASK_REGEX.matches(line)) {
                currentMask = MASK_REGEX.find(line)!!.groupValues[1]
            }

            if (INSTRUCTION_REGEX.matches(line)) {
                val (address, instruction) = INSTRUCTION_REGEX.find(line)!!.destructured
                performInstruction(memory, currentMask, address.toInt(), instruction.toInt().convertToBinary())
            }
        }

        return memory.values.map { it.convertToDecimal() }.sum()
    }

    private fun performInstruction(memory: MutableMap<Int, String>, currentMask: String, address: Int, instruction: String) {
        memory[address] = applyMask(currentMask.toCharArray(), instruction.toCharArray())
    }

    fun applyMask(mask: CharArray, instruction: CharArray): String {
        return instruction.mapIndexed { index, char ->
            if (mask[index] == 'X') {
                char
            } else {
                mask[index]
            }
        }.joinToString("")
    }

    fun part2(): Long {
        val memory = mutableMapOf<Long, String>()
        var currentMask = 0.convertToBinary()

        File(input).readLines().forEach { line ->
            if (MASK_REGEX.matches(line)) {
                currentMask = MASK_REGEX.find(line)!!.groupValues[1]
            }

            if (INSTRUCTION_REGEX.matches(line)) {
                val (address, instruction) = INSTRUCTION_REGEX.find(line)!!.destructured
                performInstructionV2(memory, currentMask.toCharArray(), address.toInt().convertToBinary().toCharArray(), instruction.toInt().convertToBinary())
            }
        }

        return memory.values.map { it.convertToDecimal() }.sum()
    }

    private fun performInstructionV2(memory: MutableMap<Long, String>, currentMask: CharArray, address: CharArray, instruction: String) {
        val maskedAddress = applyMaskV2(currentMask, address)
        for (memoryAddress in getMemoryAddresses(maskedAddress)) {
            memory[memoryAddress.convertToDecimal()] = instruction
        }
    }

    fun applyMaskV2(mask: CharArray, address: CharArray): String {
        return address.mapIndexed { index, char ->
            when (mask[index]) {
                '1', 'X' -> mask[index]
                else -> char
            }
        }.joinToString("")
    }

    /**
     * If the bitmask bit is 0, the corresponding memory address bit is unchanged.
     * If the bitmask bit is 1, the corresponding memory address bit is overwritten with 1.
     * If the bitmask bit is X, the corresponding memory address bit is floating.
     */
    fun getMemoryAddresses(input: String): List<String> {
        val addresses = mutableListOf<String>()
        if (input.contains('X')) {
            addresses.addAll(getMemoryAddresses(input.replaceFirst('X', '0')))
            addresses.addAll(getMemoryAddresses(input.replaceFirst('X', '1')))
        } else {
            addresses.add(input)
        }

        return addresses
    }

}

fun Int.convertToBinary(): String {
    return Integer.toBinaryString(this).padStart(36, '0')
}

fun String.convertToDecimal(): Long {
    val numbers = this.toCharArray();
    var result = 0.0

    numbers.forEachIndexed { index, number ->
        if (number == '1') {
            result += pow(2.0, BITS - index - 1.0)
        }
    }
    return result.toLong()
}