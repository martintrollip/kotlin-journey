package adventofcode.twenty21

import java.io.File

/**
 * @author Martin Trollip
 * @since 2021/12/08 14:30
 */
private const val DAY8_INPUT = "src/res/twenty21/day8_input"

fun main() {
    val day9 = Day9()
    println(
//        "In the output values, how many times do digits 1, 4, 7, or 8 appear? ${
//            day8.part1(day8.readInput(DAY8_INPUT))
//        }"
    )
    println(
//        "What do you get if you add up all of the output values? ${
//            day8.part2(day8.readInput(DAY8_INPUT))
//        }"
    )
}

class Day9 {

    fun readInput(fileName: String): List<String> {
        return File(fileName).readLines()
    }


}

