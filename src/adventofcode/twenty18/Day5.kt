package adventofcode.twenty18

import java.io.File

/**
 * @author Martin Trollip
 * @since 2018/12/05 07:03
 */

const val DAY5_INPUT = "src/res/day5_input"

fun main(args: Array<String>) {
    val alphabet = CharArray(26) { (it + 97).toChar() }.joinToString("")

    var sequence = File(DAY5_INPUT).readText()

    var lengths = LinkedHashMap<Char, Int>()
    alphabet.forEach {
        var optimised = sequence.replaceAny(it)

        while (optimised.duplicates()) {
            optimised = optimised.removeDuplicate()
        }

        println("The $it sequence has ${optimised.length} characters")
        lengths.put(it, optimised.length)
    }

    println(lengths.minBy { it.value })
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