package adventofcode.twenty20

import java.io.File
import java.math.BigInteger
import kotlin.math.min

/**
 * @author Martin Trollip
 * @since 2020/12/09 06:55
 */
private const val DAY9_INPUT = "src/res/twenty20/day9_input"

fun main(args: Array<String>) {
    val day9 = Day9(DAY9_INPUT)
    println("${day9.part1(25)}")
    println("${day9.part2(25)}")
}

class Day9(input: String) {

    private val xmas: List<BigInteger>

    init {
        xmas = read(input)
    }

    private fun read(fileName: String): List<BigInteger> {
        return File(fileName).readLines().map {
            it.toBigInteger()
        }
    }

    fun part1(preambleLength: Int): BigInteger {
        var invalid = (-1).toBigInteger()
        var count = 0
        xmas.windowed(preambleLength).forEach { window ->
            if (invalid < 0.toBigInteger()) {
                val nextIndex = min(count + preambleLength, xmas.size - 1)
                val sumRequired = xmas[nextIndex]

                var matched = false
                window.forEach { a ->
                    window.forEach { b ->
                        run {
                            if (a + b == sumRequired) {
                                matched = true
                            }
                        }
                    }
                }

                if (!matched && invalid < 0.toBigInteger()) {
                    invalid = sumRequired
                }
                count++
            }
        }
        return invalid
    }


    fun part2(preambleLength: Int): BigInteger {
        val sumRequired = part1(preambleLength)
        var window = emptyList<BigInteger>()

        for (windowSize in 2..xmas.size / 2) {
            var found = false
            xmas.windowed(windowSize).forEach { thisWindow ->
                if (!found) {
                    var sum = 0.toBigInteger()
                    thisWindow.forEach {
                        sum += it
                    }

                    if (sum == sumRequired) {
                        found = true
                        window = thisWindow
                    }
                }
            }

            if (found) {
                break
            }
        }
        val sorted = window.sorted()
        return sorted.first() + sorted.last()
    }
}