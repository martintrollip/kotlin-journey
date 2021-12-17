package adventofcode.twenty21

import java.awt.Point
import java.io.File
import java.util.*

/**
 * @author Martin Trollip
 * @since 2021/12/17 06:46
 */
private const val DAY16_INPUT = "src/res/twenty21/day16_input"

fun main() {
    val day16 = Day16()
    println(
        "? ${
            day16.part1(day16.readInput(DAY16_INPUT))
        }"
    )
    println(
        "? ${
            day16.part2(day16.readInput(DAY16_INPUT))
        }"
    )
}

class Day16 {

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
    
