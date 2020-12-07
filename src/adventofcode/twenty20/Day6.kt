package adventofcode.twenty20

import java.io.File

/**
 * @author Martin Trollip
 * @since 2020/12/07 09:25
 */
private const val DAY6_INPUT = "src/res/twenty20/day6_input"

fun main(args: Array<String>) {
    val day6 = Day6(DAY6_INPUT)
    println("${day6.part1()}")
    println("${day6.part2()}")
}

class Day6(input: String) {

    private var atLeastOneYes = 0
    private var allYes = 0

    init {
        readInput(input)
    }

    private fun readInput(fileName: String) {
        var people = 0
        var tempQuestion = hashSetOf<Char>()
        var tempQuestionCounts = hashMapOf<Char, Int>()
        File(fileName).readLines().forEach { line ->
            if (line.isBlank()) {
                atLeastOneYes += tempQuestion.size
                allYes += tempQuestionCounts.filter { it.value == people }.size

                people = 0
                tempQuestion = hashSetOf()
                tempQuestionCounts = hashMapOf()
            } else {
                people++
                line.toCharArray().forEach { char ->
                    tempQuestion.add(char)

                    if (tempQuestionCounts.contains(char)) {
                        tempQuestionCounts[char] = tempQuestionCounts[char]!! + 1
                    } else {
                        tempQuestionCounts[char] = 1
                    }
                }
            }
        }
        atLeastOneYes += tempQuestion.size
        allYes += tempQuestionCounts.filter { it.value == people }.size
    }

    fun part1(): Int {
        return atLeastOneYes
    }

    fun part2(): Int {
        return allYes
    }
}

