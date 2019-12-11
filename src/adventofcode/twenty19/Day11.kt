package adventofcode.twenty19

import java.io.File

/**
 * @author Martin Trollip
 * @since 2019/12/11 16:04
 */
private const val DAY11_INPUT = "src/res/twenty19/day11_input"

fun main(args: Array<String>) {
    val day11 = Day11()

    println("part1 ${day11.part1()}\n")
    println("part2 ${day11.part2()}")

}

class Day11 {
    val computer = IntcodeComputer()


    fun readInput(): Array<Long> {
        return IntcodeComputer().increaseMemorySize(File(DAY11_INPUT).readLines()[0].split(",").map { it.toLong() }.toTypedArray())
    }
    fun part1(): Int {
        val program = readInput()
        computer.executeToOutput(program, arrayOf(0L))

        return  1
    }

    fun part2(): Int {
        return 2
    }

}