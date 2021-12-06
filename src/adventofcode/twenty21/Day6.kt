package adventofcode.twenty21

import adventofcode.twenty18.readInput
import java.io.File

/**
 * @author Martin Trollip
 * @since 2021/12/06 16:57
 */
private const val DAY6_INPUT = "src/res/twenty21/day6_input"

fun main() {
    val day6 = Day6()
    println(
        "How many lanternfish would there be after 80 days? ${
            day6.run(day6.readInput(DAY6_INPUT),80)
        }"
    )
//    println(
//        "At how many points do at least two lines overlap? (all lines) ${
//            day5.calculateAllVents()
//        }"
//    )
}

class Day6 {

    fun readInput(fileName: String): List<Int> {
        return File(fileName).readLines().first().split(",").map { it.toInt() }
    }

    fun run(seed: List<Int>, days: Int) {
        val groupedAges = seed.groupBy { it }.mapValues { it.value.count() }

        for (day in 0 .. days) {
        //TODO
        }

        //TODO
        val newAges = groupedAges.map { it.key to it.value + 1 }.toMap()
            val newGroupedAges = newAges.groupBy { it.value }.mapValues { it.value.map { it.key } }
            groupedAges.clear()
            groupedAges.putAll(newGroupedAges)
    }
}

