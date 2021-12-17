package adventofcode.twenty21

import java.io.File

/**
 * @author Martin Trollip
 * @since 2021/12/14 09:22
 */
private const val DAY14_INPUT = "src/res/twenty21/day14_input"

fun main() {
    val day14 = Day14()
    println(
        "What do you get if you take the quantity of the most common element and subtract the quantity of the least common element? ${
            day14.part1(day14.readInput(DAY14_INPUT))
        }"
    )
    println(
        "What do you get if you take the quantity of the most common element and subtract the quantity of the least common element? ${
            day14.part2(day14.readInput(DAY14_INPUT), 40)
        }"
    )
}

class Day14 {

    fun readInput(fileName: String): Day14Input {
        var template = ""
        val rules = mutableMapOf<String, String>()
        File(fileName).readLines().forEachIndexed { index, line ->
            if (index == 0) {
                template = line
            } else if (line.isNotEmpty()) {
                val split = line.split(" -> ")
                rules[split[0]] = split[1]
            }
        }
        return Day14Input(template, rules)
    }

    /**
     * Replace all occurrences in string at once
     */
    fun part1(input: Day14Input, steps: Int = 10): Long {
        var (template, rules) = input

        repeat(steps) {
            //This works for part 1, but we don't have enough memory in part 2 using same code
            template = template.windowed(2, 1).joinToString("") { it[0] + rules[it]!! }.plus(template.last())
        }

        val eachCount =  template.groupBy { it }.map { it.key to it.value.size.toLong() }
        return eachCount.maxOf { it.second } - eachCount.minOf { it.second }
    }

    fun part2(input: Day14Input, steps: Int): Long {
        val pairCounts = input.template.windowed(2).groupingBy { it }.eachCount().mapValues { (_, value) -> value.toLong() }
        val finalPairCounts = generateSequence(pairCounts) { insert(it, input.rules) }.drop(steps).take(1).first()
        return 2
    }

    private fun insert(template: Map<String, Long>, rules: Map<String, String>) : Map<String, Long> {
        return mapOf()
    }

    data class Day14Input(val template: String, val rules: Map<String, String>)
}

