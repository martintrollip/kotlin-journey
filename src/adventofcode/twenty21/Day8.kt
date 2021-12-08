package adventofcode.twenty21

import java.io.File

/**
 * @author Martin Trollip
 * @since 2021/12/07 10:31
 */
private const val DAY8_INPUT = "src/res/twenty21/day8_input"

fun main() {
    val day8 = Day8()
    println(
        "In the output values, how many times do digits 1, 4, 7, or 8 appear? ${
            day8.part1(day8.readInput(DAY8_INPUT))
        }"
    )
    println(
        "What do you get if you add up all of the output values? ${
            day8.part2(day8.readInput(DAY8_INPUT))
        }"
    )
}

class Day8 {

    fun readInput(fileName: String): Display {
        val display = Display()
        File(fileName).readLines().map { display.parse(it) }
        return display
    }

    fun part1(display: Display): Int {
        return display.output.flatten().count { it.length in listOf(2 /*digit 1*/, 3 /*digit 7*/, 4 /*digit 4*/, 7 /*digit 8*/) }
    }

    fun part2(display: Display): Int {
        var sum = 0

        //Determine easy digits
        display.input.forEachIndexed { index, line ->
            run {
                val lineDigits = (0..9).associateWith { "" }.toMutableMap()

                line.forEach { display ->
                    run {
                        when (display.length) {
                            2 -> lineDigits[1] = display
                            3 -> lineDigits[7] = display
                            4 -> lineDigits[4] = display
                            7 -> lineDigits[8] = display
                        }
                    }
                }

                while (lineDigits.filter { it.value == "" }.isNotEmpty()) {
                    line.filter { it.length in listOf(5, 6) }.forEach {
                        when (it.length) {
                            5 -> {
                                // 2, 3, 5
                                // If 9 does not contain it and it does not contain 1 this is 2
                                if (!it.containsAll(lineDigits[1]!!) && !lineDigits[9]!!.containsAll(it)) {
                                    lineDigits[2] = it
                                }

                                // If it contains 1 this is 3
                                if (it.containsAll(lineDigits[1]!!)) {
                                    lineDigits[3] = it
                                }

                                // If 9 contains it and it does not contain 1 this is 5
                                if (!it.containsAll(lineDigits[1]!!) && lineDigits[9]!!.containsAll(it)) {
                                    lineDigits[5] = it
                                }
                            }
                            6 -> {
                                // 0, 6, 9
                                //If it does not have same chars as 4 but have same chars as 7 this is 0
                                if (!it.containsAll(lineDigits[4]!!) && it.containsAll(lineDigits[7]!!)) {
                                    lineDigits[0] = it
                                }

                                //If it does not have same chars as 1 this is 6
                                if (!it.containsAll(lineDigits[1]!!)) {
                                    lineDigits[6] = it
                                }

                                //If it has same chars as 4 and 7 this is 9
                                if (it.containsAll(lineDigits[4]!!) && it.containsAll(lineDigits[7]!!)) {
                                    lineDigits[9] = it
                                }
                            }
                        }
                    }
                }

                val lineSum = display.output[index].map { chars ->
                    lineDigits.filter {
                        chars.toCharArray().sorted().joinToString("") == it.value.toCharArray().sorted().joinToString("")
                    }.keys.first()
                }
                    .scan("") { acc, digit -> acc + digit }.last().toInt()

                sum += lineSum
            }
        }
        return sum
    }

    data class Display(
        val input: MutableList<List<String>> = emptyList<List<String>>().toMutableList(),
        val output: MutableList<List<String>> = emptyList<List<String>>().toMutableList()
    ) {
        fun parse(line: String) {
            val splits = line.split(" | ")
            input.add(splits[0].split(" "))
            output.add(splits[1].split(" "))
        }
    }

    /**
     * Extension function to check if all chars in other string is contained in this string
     *
     * @note feels like this should exist already
     */
    private fun String.containsAll(other: String): Boolean {
        if (this.isNotEmpty() and other.isNotEmpty()) {
            val count = other.toCharArray().count { char ->
                this.contains(char)
            }
            return count == other.length
        }
        return false
    }
}

