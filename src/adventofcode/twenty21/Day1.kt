package adventofcode.twenty21

import java.io.File

/**
 * @author Martin Trollip
 * @since 2020/12/01 11:40
 */
private const val DAY1_INPUT = "src/res/twenty21/day1_input"

fun main() {
    val day1 = Day1()
    val input = day1.readInput(DAY1_INPUT)
    println(
        "How many measurements are larger than the previous measurement? ${
            day1.numberOfIncreasingElementsInList(
                input
            )
        }"
    )
     println("How many sums are larger than the previous sum? ${day1.numberOfIncreasingSumOfWindowedElementsInList(input)}")
}

class Day1 {

    fun readInput(fileName: String): List<Int> {
        return File(fileName).readLines().map { it.toInt() }.toList()
    }

    fun numberOfIncreasingElementsInList(list: List<Int>): Int {
        return list.filterIndexed { index, i ->
            if (index > 0) {
                list[index - 1] < i
            } else {
                false
            }
        }.size
    }

    fun numberOfIncreasingSumOfWindowedElementsInList(list: List<Int>): Int {
        return numberOfIncreasingElementsInList(list.windowed(3, 1).map { it.sum() })
    }
}

