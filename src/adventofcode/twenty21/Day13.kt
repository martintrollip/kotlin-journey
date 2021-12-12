package adventofcode.twenty21

import java.io.File
import java.util.*

/**
 * @author Martin Trollip
 * @since 2021/12/12 12:08
 */
private const val DAY13_INPUT = "src/res/twenty21/day13_input"

fun main() {
    val day13 = Day13()
    println(
        "part1? ${
            day13.part1(day13.readInput(DAY13_INPUT))
        }"
    )
    println(
        "part2? ${
            day13.part2(day13.readInput(DAY13_INPUT))
        }"
    )
}

class Day13 {

    fun readInput(fileName: String): List<String> {
        return File(fileName).readLines()
    }

    fun part1(input: List<String>): Int {
        return 1
    }

    fun part2(input: List<String>): Int {
        return 2
    }
}

