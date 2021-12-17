package adventofcode.twenty21

import java.awt.Point
import java.io.File
import java.util.*

/**
 * @author Martin Trollip
 * @since 2021/12/19 06:49
 */
private const val DAY19_INPUT = "src/res/twenty21/day19_input"

fun main() {
    val day19 = Day19()
    println(
        "? ${
            day19.part1(day19.readInput(DAY19_INPUT))
        }"
    )
    println(
        "? ${
            day19.part2(day19.readInput(DAY19_INPUT))
        }"
    )
}

class Day19 {

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
    
