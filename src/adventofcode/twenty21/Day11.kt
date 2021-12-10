package adventofcode.twenty21

import java.io.File
import java.util.*

/**
 * @author Martin Trollip
 * @since 2021/12/10 08:32
 */
private const val DAY11_INPUT = "src/res/twenty21/day11_input"

fun main() {
    val day11 = Day11()
//    println(
//        "What is the total syntax error score for those errors?? ${
//            day10.part1(day10.readInput(DAY10_INPUT))
//        }"
//    )
//    println(
//        "What is the middle score? ${
//            day10.part2(day10.readInput(DAY10_INPUT))
//        }"
//    )
}

class Day11 {

    fun readInput(fileName: String): List<String> {
        return File(fileName).readLines()
    }

    fun part1(input: List<String>): Int {
        return 1
    }

    fun part2(input: List<String>): Long {
        return 2
    }

}

