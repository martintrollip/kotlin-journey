package adventofcode.twenty19

import java.io.File

/**
 * @author Martin Trollip ***REMOVED***
 * @since 2019/12/05 06:53
 */
private const val DAY5_INPUT = "src/res/twenty19/day5_input"

fun main(args: Array<String>) {
    val day5 = Day5()
    val input = File(DAY5_INPUT).readLines()

    println("What diagnostic code does the program produce? ${day5.part1()}\n")
    println("part2? ${day5.part2()}")
}

class Day5 {

    val computer = IntcodeComputer()

    fun readInput(input: String): Array<Int> {
        return File(input).readLines()[0].split(",").map { it.toInt() }.toTypedArray()
    }

    fun part1(): Int {
        return computer.execute(readInput(DAY5_INPUT),1).get(0)
    }

    fun part2(): Int {
       return 201
    }
}