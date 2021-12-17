package adventofcode.twenty21

import java.awt.Point
import java.io.File
import java.util.*

/**
 * @author Martin Trollip
 * @since 2021/12/17 06:49
 */
private const val DAY17_INPUT = "src/res/twenty21/day17_input"

fun main() {
    val day17 = Day17()
    println(
        "? ${
            day17.part1(day17.readInput(DAY17_INPUT))
        }"
    )
    println(
        "? ${
            day17.part2(day17.readInput(DAY17_INPUT))
        }"
    )
}

class Day17 {

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
    
