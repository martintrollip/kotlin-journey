package adventofcode.twenty21

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
            day6.run(day6.readInput(DAY6_INPUT), 80)
        }"
    )
    println(
        "How many lanternfish would there be after 256 days? ${
            day6.run(day6.readInput(DAY6_INPUT), 256)
        }"
    )
}

class Day6 {

    fun readInput(fileName: String): List<Int> {
        return File(fileName).readLines().first().split(",").map { it.toInt() }
    }

    /**
     * Thanks to guide from https://blog.sweller.de/
     * 
     * The problem can be simplified if we consider the number of fish with a given age. 
     */
    fun run(fish: List<Int>, days: Int) : Long {
        var fishGroupedByAges = fish.groupBy { it }.mapValues { it.value.count().toLong() } 
        
        for (day in 0 until days) {
            fishGroupedByAges = (0..7)
                .associateWith { fishGroupedByAges.getOrDefault(it + 1, 0) } // Fish loose 1 day
                .plus(6 to fishGroupedByAges.getOrDefault(0, 0) + fishGroupedByAges.getOrDefault(7, 0)) // Fish with 0 days turn to 6 (so add the fish with 0 days to the count of 6)
                .plus(8 to fishGroupedByAges.getOrDefault(0, 0)) // Fish with 0 days creates a new fish with age 8 (so add the fish with 0 days to the count of 8) 
        }
        
        return fishGroupedByAges.values.sum()
    }
}

