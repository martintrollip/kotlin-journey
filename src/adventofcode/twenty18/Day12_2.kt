package adventofcode.twenty18

import java.io.File


/**
 * My second attempt at day 12, a year later
 *
 * Tahnks @ToddGinsberg for a nice explanation on your blogpost.
 *
 * @author Martin Trollip
 * @since 2019/12/08 12:55
 */
private const val DAY12_INPUT = "src/res/day12_input"
private const val DAY12_INITIAL_CONDITIONS = "##.####..####...#.####..##.#..##..#####.##.#..#...#.###.###....####.###...##..#...##.#.#...##.##.."

fun main(args: Array<String>) {
    val day12 = Day12()

    println("After 20 generations, what is the sum of the numbers of all pots which contain a plant? ${day12.part1()}\n")//1623
    println("After 50000000000 generations, what is the sum of the numbers of all pots which contain a plant?${day12.part2()}")//1600000000401

}

class Day12 {

    fun part1(): Long {
        return generate(readInput(DAY12_INPUT), DAY12_INITIAL_CONDITIONS, 20)
    }

    fun part2(): Long {
        return generatePart2(readInput(DAY12_INPUT), DAY12_INITIAL_CONDITIONS, 50000000000)
    }

    private val PLANTS_REGEX = "([.#]{5}) => ([.#])".toRegex()
    fun readInput(inputFile: String): List<String> {
        return File(inputFile).readLines().filter {
            //Only add true to the list, the rest will assume to be false
            val matchResult = PLANTS_REGEX.find(it)
            val (_, result) = matchResult!!.destructured
            result == "#"
        }.map {
            val matchResult = PLANTS_REGEX.find(it)
            val (config, _) = matchResult!!.destructured
            config
        }
    }

    fun generate(notes: List<String>, initialState: String, numberOfGenerations: Long): Long {
        var zeroIndex = 0
        var currentState = initialState

        for (i in 0 until numberOfGenerations) {
            while (!hasEnoughLeadingPots(currentState)) {
                currentState = ".$currentState"
                zeroIndex++
            }

            while (!hasEnoughTrailingPots(currentState)) {
                currentState = "$currentState."
            }

            currentState = nextGeneration(currentState, notes) //Make a new string again

            zeroIndex -= 2
        }
        return currentState.sumPlants(zeroIndex)
    }

    fun generatePart2(notes: List<String>, initialState: String, numberOfGenerations: Long): Long {
        var generations = 0
        var previousPots = initialState
        var previousCount = 0
        var currentCount = 0

        var zeroIndex = 0
        var currentState = initialState

        do {
            previousPots = currentState
            previousCount = previousPots.sumPlants(zeroIndex).toInt()

            while (!hasEnoughLeadingPots(currentState)) {
                currentState = ".$currentState"
                zeroIndex++
            }

            while (!hasEnoughTrailingPots(currentState)) {
                currentState = "$currentState."
            }
            currentState = nextGeneration(currentState, notes) //Make a new string again

            zeroIndex -= 2
            generations++
            currentCount = currentState.sumPlants(zeroIndex).toInt()//see what the difference is just one generation later, this will be a constant until 50000000000
        } while (trim(previousPots) != trim(currentState))//only go until the pattern does not repeat
        return currentState.sumPlants(zeroIndex) + (numberOfGenerations - generations)*(currentCount - previousCount)
    }

    fun trim(input: String): String {
        val first = input.indexOfFirst { it == '#' }
        val last = input.indexOfLast { it == '#' }
        return input.substring(first, last + 1)
    }

    fun nextGeneration(currentState: String, notes: List<String>): String {
        //TODO revise windowed vs chunked (https://kotlinlang.org/docs/reference/collection-parts.html)
        return currentState.toList()
                .windowed(5, 1)
                .map { it.joinToString(separator = "") } //The window to string
                .map { if (it in notes) '#' else '.' } //Check what the next generation will be
                .joinToString(separator = "")
    }

    fun hasEnoughLeadingPots(input: String): Boolean {
        return input.startsWith(".....")
    }

    fun hasEnoughTrailingPots(input: String): Boolean {
        return input.endsWith(".....")
    }
}

fun String.sumPlants(startIndex: Int): Long = this.mapIndexed { index, char -> if (char == '#') index.toLong() - startIndex else 0 }.sum()