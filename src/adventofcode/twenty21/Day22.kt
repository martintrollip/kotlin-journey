package adventofcode.twenty21

import java.io.File

/**
 * @author Martin Trollip
 * @since 2021/12/22 06:49
 */
private const val DAY22_INPUT = "src/res/twenty21/day22_input"

fun main() {
    val day22 = Day22()
    println(
        "? ${
            day22.part1(day22.readInput(DAY22_INPUT))
        }"
    )
    println(
        "? ${
            day22.part2(day22.readInput(DAY22_INPUT))
        }"
    )
}

class Day22 {

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
    
