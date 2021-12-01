package adventofcode.twenty18

import java.io.File

/**
 * @author Martin Trollip
 * @since 2018/12/05 07:03
 */

const val DAY5_INPUT = "src/res/day5_input"

fun main(args: Array<String>) {

    val sequence = File(DAY5_INPUT).readText()

    val part1 = day5Part1(sequence) - 1
    println("How many units remain after fully reacting the polymer you scanned? $part1")
    println("What is the length of the shortest polymer you can produce? (-1 for last duplicate)" + day5Part2(sequence))
}

fun day5Part1(sequence: String): Int {
    var sequenceCopy = "" + sequence //Deep copy
    while (sequenceCopy.duplicates()) {
        sequenceCopy = sequenceCopy.removeDuplicate()
    }
    return sequenceCopy.length
}

fun day5Part2(sequence: String): Map.Entry<Char, Int>? {
    val alphabet = CharArray(26) { (it + 97).toChar() }.joinToString("")

    var lengths = LinkedHashMap<Char, Int>()
    alphabet.forEach {
        var optimised = sequence.replaceAny(it)

        while (optimised.duplicates()) {
            optimised = optimised.removeDuplicate()
        }

        println("The $it sequence has ${optimised.length} characters")
        lengths.put(it, optimised.length)
    }

    return lengths.minByOrNull  { it.value }
}

fun String.duplicates(): Boolean {
    for (i in 0 until this.length - 1) {

        val charA = this[i]
        val charB = this[i + 1]

        if (isOpposites(charA, charB)) {
            return true
        }
    }
    return false
}

fun String.removeDuplicate(): String {
    var newSequence = this

    for (i in 0 until this.length - 1) {
        val charA = this[i]
        val charB = this[i + 1]

        if (isOpposites(charA, charB)) {
            return newSequence.replaceFirst("" + this[i] + this[i + 1], "")
        }
    }

    return this
}

fun String.replaceAny(char: Char): String {
    return replace("" + char, "", true)
}

fun isOpposites(a: Char, b: Char): Boolean {
    if ((a.isLowerCase() && b.isUpperCase()) || (a.isUpperCase() && b.isLowerCase())) {
        if (a.toLowerCase() == b.toLowerCase()) {
            return true
        }
    }
    return false
}