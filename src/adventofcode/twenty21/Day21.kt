package adventofcode.twenty21

import java.io.File

/**
 * @author Martin Trollip
 * @since 2021/12/21 06:49
 */
private const val DAY21_INPUT = "src/res/twenty21/day21_input"

fun main() {
    val day21 = Day21()
    println(
        "? ${
            day21.part1(day21.readInput(DAY21_INPUT))
        }"
    )
    println(
        "? ${
            day21.part2(day21.readInput(DAY21_INPUT))
        }"
    )
}

class Day21 {

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
    
