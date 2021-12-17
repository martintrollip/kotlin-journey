package adventofcode.twenty21

import java.awt.Point
import java.io.File
import java.util.*

/**
 * @author Martin Trollip
 * @since 2021/12/20 06:49
 */
private const val DAY20_INPUT = "src/res/twenty21/day20_input"

fun main() {
    val day20 = Day20()
    println(
        "? ${
            day20.part1(day20.readInput(DAY20_INPUT))
        }"
    )
    println(
        "? ${
            day20.part2(day20.readInput(DAY20_INPUT))
        }"
    )
}

class Day20 {

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
    
