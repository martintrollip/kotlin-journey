package adventofcode.twenty21

import java.io.File

/**
 * @author Martin Trollip
 * @since 2021/12/02 07:35
 */
private const val DAY2_INPUT = "src/res/twenty21/day2_input"

fun main() {
    val day2 = Day2()
    val input = day2.readInput(DAY2_INPUT)
    println(
        "What do you get if you multiply your final horizontal position by your final depth? ${1 * day2.getCoordinatesMutiplied(input)}")
//     println("How many sums are larger than the previous sum? ${day1.numberOfIncreasingSumOfWindowedElementsInList(input)}")
}

class Day2 {

    private val INSTRUCTION_REGEX = "([a-z]+) ([0-9]+)".toRegex() //https://regex101.com/r/LfmFsa/1

    fun readInput(fileName: String): List<Pair<String, Int>> {
        return File(fileName).readLines().map {
            if (INSTRUCTION_REGEX.matches(it)) {
                val (direction, delta) = INSTRUCTION_REGEX.find(it)!!.destructured
                Pair(direction, delta.toInt())
            } else {
                throw IllegalArgumentException("Invalid instruction: $it")
            }
        }
    }

    /**
     * @return the final coordinates after the instructions have been applied in the form of a Pair<Int, Int> with <horizontal, depth>
     */
    fun calculateCoordinates(input: List<Pair<String, Int>>) : Pair<Int, Int> {
        var horizontal = 0
        var depth = 0
        input.forEach {
            when(it.first) {
                "up" -> depth -= it.second
                "down" -> depth += it.second
                "forward" -> horizontal += it.second
                else -> throw IllegalArgumentException("Invalid direction: ${it.first}")
            }
        }
        return Pair(horizontal, depth)
    }

    fun getCoordinatesMutiplied(input:  List<Pair<String, Int>>): Int {
        val coordinates = calculateCoordinates(input)
        return coordinates.first * coordinates.second
    }
}

