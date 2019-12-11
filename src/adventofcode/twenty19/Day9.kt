package adventofcode.twenty19

import java.io.File

/**
 * The BOOST program will ask for a single input; run it in test mode by providing it the value 1.
 * It will perform a series of checks on each opcode, output any opcodes (and the associated parameter modes)
 * that seem to be functioning incorrectly, and finally output a BOOST keycode.
 *
 * Once your Intcode computer is fully functional, the BOOST program should report no malfunctioning opcodes when run in test mode;
 * it should only output a single value, the BOOST keycode. What BOOST keycode does it produce?
 *
 * @author Martin Trollip
 * @since 2019/12/09 06:39
 */
private const val DAY9_INPUT = "src/res/twenty19/day9_input"

fun main(args: Array<String>) {
    val day9 = Day9()

    println("What BOOST keycode does it produce? ${day9.part1(1L)}\n")  //4288078517
    println("What are the coordinates of the distress signal? ${day9.part2(2L)}")

}

class Day9 {

    val computer = IntcodeComputer()
    fun readInput(): Array<Long> {
        return computer.increaseMemorySize(File(DAY9_INPUT).readLines()[0].split(",").map { it.toLong() }.toTypedArray())
    }

    fun part1(input: Long): Long {
        return computer.execute(readInput(), input)[0]
    }

    fun part2(input: Long): Long {
        return computer.execute(readInput(), input)[0]
    }

}