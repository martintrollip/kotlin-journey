package adventofcode.twenty21

import java.io.File

/**
 * @author Martin Trollip
 * @since 2021/12/03 07:33
 */
private const val DAY3_INPUT = "src/res/twenty21/day3_input"

fun main() {
    val day3 = Day3()
    val input = day3.readInput(DAY3_INPUT)
    println(
        "What is the power consumption of the submarine? ${
            day3.calculatePowerConsumption(
                input
            )
        }"
    )
    println(
        "What is the life support rating of the submarine? ${
            day3.calculateLifeSupportRating(
                input
            )
        }"
    )
}

class Day3 {

    fun readInput(fileName: String): List<String> {
        val file = File(fileName)
        return file.readLines()
    }

    fun calculatePowerConsumption(input: List<String>): Int {
        val bitsPerPosition = calculateNumberOfBitsPerPosition(input)
        val mostCommonBits = calculateMostCommonBitsPerPosition(bitsPerPosition)
        val leastCommonBits = invertedBits(mostCommonBits)
        val gammaRate = calculateRate(mostCommonBits)
        val epsilonRate = calculateRate(leastCommonBits)

        return gammaRate * epsilonRate
    }

    fun calculateLifeSupportRating(input: List<String>): Int {
        val oxygenGeneratorRating = calculateOxygenRate(input)
        val co2ScrubberRating = calculateCo2Rate(input)

        return oxygenGeneratorRating * co2ScrubberRating
    }

    /**
     * @return List<Pair<Int, Int>> where each pair is the count of zero bits and one bits respectively
     */
    private fun calculateNumberOfBitsPerPosition(input: List<String>): List<Pair<Int, Int>> {
        val numberOfBits = ArrayList<Pair<Int, Int>>()

        input.forEach { line ->

            line.forEachIndexed { index, c ->
                if (numberOfBits.size <= index) {
                    numberOfBits.add(Pair(0, 0))
                }

                val columnCounts = numberOfBits[index]
                when (c) {
                    '0' -> numberOfBits[index] = Pair(columnCounts.first + 1, columnCounts.second)
                    '1' -> numberOfBits[index] = Pair(columnCounts.first, columnCounts.second + 1)
                    else -> throw IllegalArgumentException("Invalid character: $c")
                }
            }
        }

        return numberOfBits
    }

    /**
     * Calculate if 0 or 1 is the most common bit in the given list of bits
     */
    private fun calculateMostCommonBitsPerPosition(bitsPerPosition: List<Pair<Int, Int>>): List<Int> {
        val mostCommonBits = bitsPerPosition.map { pair ->
            if (pair.first > pair.second) {
                0
            } else {
                1
            }
        }.toList()
        return mostCommonBits
    }

    private fun invertedBits(bits: List<Int>): List<Int> {
        return bits.map { bit ->
            if (bit == 0) {
                1
            } else {
                0
            }
        }
    }

    private fun calculateRate(mostCommonBits: List<Int>): Int {
        return binaryToDecimal(mostCommonBits)
    }

    /**
     *To find oxygen generator rating, determine the most common value (0 or 1) in the current bit position,
     * and keep only numbers with that bit in that position.
     *
     * If 0 and 1 are equally common, keep values with a 1 in the position being considered.
     *
     */
    private fun calculateOxygenRate(input: List<String>): Int {
        var filteredInput = input

        val columns = filteredInput[0].length

        for (i in 0 until columns) {
            val bitsPerPosition = calculateNumberOfBitsPerPosition(filteredInput)
            val mostCommonBits = calculateMostCommonBitsPerPosition(bitsPerPosition)

            filteredInput = filteredInput.filter { it[i] == mostCommonBits[i].toString()[0] }

            if (filteredInput.size == 1) {
                break
            }
        }
        return binaryToDecimal(filteredInput.first().map { Character.getNumericValue(it) }.toList())
    }

    private fun calculateCo2Rate(input: List<String>): Int {
        var filteredInput = input

        val columns = filteredInput[0].length

        for (i in 0 until columns) {
            val bitsPerPosition = calculateNumberOfBitsPerPosition(filteredInput)
            val mostCommonBits = calculateMostCommonBitsPerPosition(bitsPerPosition)
            val leastCommonBits = invertedBits(mostCommonBits)

            filteredInput = filteredInput.filter { it[i] == leastCommonBits[i].toString()[0] }

            if (filteredInput.size == 1) {
                break
            }

        }
        return binaryToDecimal(filteredInput.first().map { Character.getNumericValue(it) }.toList())
    }

    private fun binaryToDecimal(bits: List<Int>): Int {
        var decimal = 0
        var multiplier = 1
        for (bit in bits.reversed()) {
            decimal += bit * multiplier
            multiplier *= 2
        }
        return decimal
    }
}

