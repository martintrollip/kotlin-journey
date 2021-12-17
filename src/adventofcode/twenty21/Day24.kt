package adventofcode.twenty21

import java.io.File

/**
 * @author Martin Trollip
 * @since 2021/12/24 06:49
 */
private const val DAY24_INPUT = "src/res/twenty21/day24_input"

fun main() {
    val day24 = Day24()
    println(
        "? ${
            day24.part1(day24.readInput(DAY24_INPUT))
        }"
    )
    println(
        "? ${
            day24.part2(day24.readInput(DAY24_INPUT))
        }"
    )
}

class Day24 {

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
    
