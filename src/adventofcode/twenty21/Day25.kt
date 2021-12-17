package adventofcode.twenty21

import java.io.File

/**
 * @author Martin Trollip
 * @since 2021/12/25 06:49
 */
private const val DAY25_INPUT = "src/res/twenty21/day25_input"

fun main() {
    val day25 = Day25()
    println(
        "? ${
            day25.part1(day25.readInput(DAY25_INPUT))
        }"
    )
    println(
        "? ${
            day25.part2(day25.readInput(DAY25_INPUT))
        }"
    )
}

class Day25 {

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
    
