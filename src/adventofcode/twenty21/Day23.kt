package adventofcode.twenty21

import java.io.File

/**
 * @author Martin Trollip
 * @since 2021/12/23 06:49
 */
private const val DAY23_INPUT = "src/res/twenty21/day23_input"

fun main() {
    val day23 = Day23()
    println(
        "? ${
            day23.part1(day23.readInput(DAY23_INPUT))
        }"
    )
    println(
        "? ${
            day23.part2(day23.readInput(DAY23_INPUT))
        }"
    )
}

class Day23 {

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
    
