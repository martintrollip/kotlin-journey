package adventofcode.twenty20

import java.io.File

/**
 * @author Martin Trollip
 * @since 2020/12/02 06:38
 */
private const val DAY1_INPUT = "src/res/twenty20/day2_input"

fun main(args: Array<String>) {
    val day2 = Day2(DAY1_INPUT)
    println("Valid passwords according to their policies: ${day2.part1()}")
    println("Valid passwords according to the new interpretation of the policies? ${day2.part2()}")
}

class Day2(input: String) {
    private val PASSWORDS_REGEX = "([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)".toRegex() //https://regex101.com/r/gJ5VlN/1/

    private var validPasswords = 0
    private var validPasswordsNewPolicy = 0

    init {
        readInput(input)
    }

    private fun readInput(fileName: String) {

        part1Calculation(fileName)

        part2Calculation(fileName)
    }

    private fun part1Calculation(fileName: String) {
        File(fileName).readLines().forEach {
            val line = PASSWORDS_REGEX.find(it)
            val (min, max, letter, password) = line!!.destructured

            val count = password.count { letter.contains(it) }

            if (count <= max.toInt() && count >= min.toInt()) {
                validPasswords++
            }
        }
    }

    private fun part2Calculation(fileName: String) {
        File(fileName).readLines().forEach {
            val line = PASSWORDS_REGEX.find(it)
            val (positionA, positionB, letter, password) = line!!.destructured

            val indexA = positionA.toInt() - 1
            val indexB = positionB.toInt() - 1
            val character = letter[0]

            val containsA = password[indexA] == character
            val containsB = password[indexB] == character

            if (containsA xor containsB) {
                validPasswordsNewPolicy++
            }
        }
    }

    fun part1(): Int {
        return validPasswords
    }

    fun part2(): Int {
        return validPasswordsNewPolicy
    }
}

