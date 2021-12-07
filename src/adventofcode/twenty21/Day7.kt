package adventofcode.twenty21

import java.io.File
import kotlin.math.absoluteValue

/**
 * @author Martin Trollip
 * @since 2021/12/07 09:03
 */
private const val DAY7_INPUT = "src/res/twenty21/day7_input"

fun main() {
    val day7 = Day7()
    println(
        "How much fuel must they spend to align to that position? ${
            day7.part1(day7.readInput(DAY7_INPUT))
        }"
    )
    println(
        "How much fuel must they spend to align to that position? ${
            day7.part2(day7.readInput(DAY7_INPUT))
        }"
    )
}

class Day7 {

    fun readInput(fileName: String): List<Int> {
        return File(fileName).readLines().first().split(",").map { it.toInt() }
    }

    fun part1(crabLocations: List<Int>): Int {
        return calculateMinimalFuelCost(crabLocations, ::calculateFuelCost)
    }

    fun part2(crabLocations: List<Int>): Int {
        return calculateMinimalFuelCost(crabLocations, ::calculateFuelCost2)
    }

    /**
     * Calculate the minimal amount of fuel required to align to the position.
     *
     * Determine the range, so we can have the possible positions to align to
     * Calculate the minimal fuel cost for each position
     * Find the minimal fuel cost
     */
    private fun calculateMinimalFuelCost(crabLocations: List<Int>, costFunction: (List<Int>, Int) -> Int): Int {
        return determineRange(crabLocations)
            .associateWith { costFunction(crabLocations, it) }
            .minByOrNull { it.value }!!.value
    }

    private fun determineRange(crabLocations: List<Int>): IntRange {
        return IntRange(crabLocations.minByOrNull { it }!!, crabLocations.maxByOrNull { it }!!)
    }

    private fun calculateFuelCost(crabLocations: List<Int>, position: Int): Int {
        return crabLocations.sumOf { (it - position).absoluteValue }
    }

    /**
     * Each change of 1 step in horizontal position costs 1 more unit of fuel than the last: the first step costs 1, the second step costs 2, the third step costs 3, and so on.
     *
     * Use the nth triangular number to calculate the cost
     *
     * @see https://en.wikipedia.org/wiki/1_%2B_2_%2B_3_%2B_4_%2B_%E2%8B%AF
     * */
    private fun calculateFuelCost2(crabLocations: List<Int>, position: Int): Int {
        return crabLocations.sumOf { calculateNthTriangularNumber((it - position).absoluteValue) }
    }

    private fun calculateNthTriangularNumber(n: Int): Int {
        return (n * (n + 1)) / 2
    }

}

