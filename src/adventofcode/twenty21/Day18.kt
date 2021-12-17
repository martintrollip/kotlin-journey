package adventofcode.twenty21

import java.awt.Point
import java.io.File
import java.util.*

/**
 * @author Martin Trollip
 * @since 2021/12/18 06:49
 */
private const val DAY18_INPUT = "src/res/twenty21/day18_input"

fun main() {
    val day18 = Day18()
    println(
        "? ${
            day18.part1(day18.readInput(DAY18_INPUT))
        }"
    )
    println(
        "? ${
            day18.part2(day18.readInput(DAY18_INPUT))
        }"
    )
}

class Day18 {

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
    
