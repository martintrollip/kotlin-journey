package adventofcode.twenty18

import java.io.File

/**
 * @author Martin Trollip
 * @since 2018/12/12 07:03
 */

const val DAY12_INPUT = "src/res/day12_input_small"

val PLANTS_REGEX = "([.#]{5}) => ([.#]{1})".toRegex()

const val PLANT = "#"
const val NO_PLANT = "."
const val initialState = "##....#.#.#...#.#..#.#####.#.#.##.#.#.#######...#.##....#..##....#.#..##.####.#..........#..#...#"
const val initialStateSmall = "#..#.#..##......###...###"

val notes = LinkedHashMap<String, String>()

fun main(args: Array<String>) {
    File(DAY12_INPUT).readLines().map {
        val matchResult = PLANTS_REGEX.find(it)
        var (configuration, result) = matchResult!!.destructured
        notes.put(configuration, result)
    }

    var state = "...$initialStateSmall..."
    println("  $state")
    for (generation in 0 until 20) {
        state = state.nextGeneration()+"..."
        println("$generation $state")
    }

    var sum = 0
    while(state.contains("#")) {
        sum += (state.indexOf("#") - 3)
        state = state.replaceFirst("#","X")
    }

    println("The sum is $sum")

}

fun String.nextGeneration(): String {
    var nextGeneration = this

    for (plantBox in 3..(length - 3)) {
        val configuration = substring(plantBox - 3, plantBox + 2)
        nextGeneration = nextGeneration.replaceRange(plantBox - 1..plantBox - 1, notes.getOrDefault(configuration, "."))
    }

    return nextGeneration
}

