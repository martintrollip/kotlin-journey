package adventofcode.twenty21

import java.io.File
import kotlin.math.absoluteValue

/**
 * @author Martin Trollip
 * @since 2021/12/07 10:31
 */
private const val DAY8_INPUT = "src/res/twenty21/day8_input"

fun main() {
    val day8 = Day8()
    println(
//        "How much fuel must they spend to align to that position? ${
//            day7.part1(day7.readInput(DAY7_INPUT))
//        }"
    )
    println(
//        "How much fuel must they spend to align to that position? ${
//            day7.part2(day7.readInput(DAY7_INPUT))
//        }"
    )
}

class Day8 {

    fun readInput(fileName: String): List<String> {
        return File(fileName).readLines()
    }

    fun part1(crabLocations: List<Int>): Int {
        return 1
    }

    fun part2(crabLocations: List<Int>): Int {
        return 2
    }

}

