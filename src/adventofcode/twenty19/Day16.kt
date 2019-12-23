package adventofcode.twenty19

import java.io.File
import kotlin.math.absoluteValue

/**
 * @author Martin Trollip
 * @since 2019/12/20 15:05
 */

private const val DAY16_INPUT = "src/res/twenty19/day16_input"

fun main(args: Array<String>) {
    val day16 = Day16()
    println("After 100 phases of FFT, what are the first eight digits in the final output list? ${day16.part1()}\n")
    println("part2 ${day16.part2()}")
}

class Day16 {
    fun part1(): String {
        return fft(File(DAY16_INPUT).readLines().first(), 100).toString()
    }

    fun part2(): Int {
        return 2
    }

    fun fft(input: String, phases: Int = 100): List<Int> {

        var fft = mapStringToIntArray(input)
        for (i in 0 until phases) {
            fft = fft(fft)
        }

        return fft.take(8)
    }

    fun fft2(input: String, phases: Int = 100): List<Int> {

        var fft = mapStringToIntArray(input.repeat(10000))
        for (i in 0 until phases) {
            println("phase=$i")
            fft = fft(fft)
        }

        return fft.subList(input.take(7).toInt(), 8)
    }

    fun fft(input: String): List<Int> {
        return fft(mapStringToIntArray(input))
    }

    private fun mapStringToIntArray(input: String) = input.map { Integer.valueOf(it.toString()) }

    fun fft(input: List<Int>): List<Int> {
        val result = mutableListOf<Int>()
        input.forEachIndexed { index, it ->
            val chunked = splitIntoChunksWithSameMultiplier(input, index)
            val noZeros = dropZeros(chunked)

            var count = 0
            var sum = 0
            sum += noZeros.sumBy {
                count++
                if (count % 2 != 0) {
                    //Evens are multiplied by 1
                    it.sumBy { it }
                } else {
                    //Odds are multiplied by -1
                    it.sumBy { (-1) * it }
                }
            }
            result.add(sum.absoluteValue % 10)
        }
        return result
    }

    fun splitIntoChunksWithSameMultiplier(input: List<Int>, index: Int): List<List<Int>> {
        return input.takeLast(input.size - index).chunked(index + 1)
    }

    fun dropZeros(input: List<List<Int>>): List<List<Int>> {
        return input.filterIndexed { index, list ->
            index % 2 == 0
        }
    }
}