package adventofcode.twenty19

import java.io.File

/**
 * @author Martin Trollip
 * @since 2019/12/05 06:53
 */
private const val DAY5_INPUT = "src/res/twenty19/day5_input"

fun main(args: Array<String>) {
    val day5 = Day5()

    println("What diagnostic code does the program produce? ${day5.part1()}\n") //16225258
    println("What is the diagnostic code for system ID 5?? ${day5.part2()}") //2808771
}

class Day5 {

    val computer = IntcodeComputer()

    fun readInput(input: String): Array<Long> {
        return File(input).readLines()[0].split(",").map { it.toLong() }.toTypedArray()
    }

    fun part1(): Int {
        return computer.execute(readInput(DAY5_INPUT), 1)[0].toInt()
    }

    fun part2(): Int {
        return computer.execute(readInput(DAY5_INPUT), 5)[0].toInt()
    }
}