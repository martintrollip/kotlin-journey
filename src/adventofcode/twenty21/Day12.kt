package adventofcode.twenty21

import java.awt.Point
import java.io.File
import java.util.*

/**
 * @author Martin Trollip
 * @since 2021/12/11 18:03
 */
private const val DAY12_INPUT = "src/res/twenty21/day12_input"

fun main() {
    val day12 = Day12()
//    println(
//        "How many total flashes are there after 100 steps? ${
//            day11.part1(day11.readInput(DAY11_INPUT).toMutableMap(), 100)
//        }"
//    )
//    println(
//        "What is the first step during which all octopuses flash? ${
//            day11.part2(day11.readInput(DAY11_INPUT).toMutableMap())
//        }"
//    )
}

class Day12 {

    fun readInput(fileName: String): List<String> {
        return File(fileName).readLines()
    }

    fun part1(): Int {
        return 1
    }

    fun part2(): Int {
        return 2
    }
}

