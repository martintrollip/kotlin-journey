package adventofcode.twenty20

import java.io.File

/**
 * @author Martin Trollip
 * @since 2020/12/05 07:26
 */
private const val DAY5_INPUT = "src/res/twenty20/day5_input"

fun main(args: Array<String>) {
    val day5 = Day5(DAY5_INPUT)
    println("What is the highest seat ID on a boarding pass? ${day5.part1()}")
    println("${day5.part2()}")
}

class Day5(input: String) {

    private val INITIAL_RANGE_LOW = 0
    private val INITIAL_RANGE_HIGH = 127

    private val INITIAL_COL_RANGE_LOW = 0
    private val INITIAL_COL_RANGE_HIGH = 7

    var highestId = 0
    var seatNumbers = mutableListOf<Int>()

    init {
        readInput(input)
    }

    private fun readInput(fileName: String) {
        File(fileName).readLines().forEach {
            val (row, col) = calculateRowCol(it)
            val rowID = calculateRowID(row, col)

            if (rowID > highestId) {
                highestId = rowID
            }

            seatNumbers.add(rowID)
        }
    }

    fun part1() : Int {
        return highestId
    }

    fun calculateRowID(row : Int, col: Int): Int {
        return (row * 8) + col
    }

    fun calculateRowCol(input: String): Pair<Int, Int> {
        val (row, colInput) = calculateHalf(input, INITIAL_RANGE_LOW, INITIAL_RANGE_HIGH)
        val (col, _) = calculateHalf(colInput.hackLR(), INITIAL_COL_RANGE_LOW, INITIAL_COL_RANGE_HIGH)

        return Pair(row, col)
    }

    fun calculateHalf(input: String, low: Int, high: Int): Pair<Int, String> {
        if (low != high) {
            return when (input[0]) {
                'F' -> calculateHalf(input.removeFirst(), low, ((low + high) / 2))
                'B' -> calculateHalf(input.removeFirst(), ((low + high) / 2) + 1, high)
                else -> {
                    println("Some unexpected input")
                    return Pair(-1, "meh")
                }
            }
        }
        return Pair(low, input)
    }

    fun part2() {
        //TODO Got this by inspecting the list, try using reduce or similar
        seatNumbers.sort()
        println(seatNumbers.reduce { acc, next -> acc + next })
    }

    private fun String.removeFirst(): String {
        return this.removeRange(0, 1)
    }

    private fun String.hackLR(): String {
        return this.replace('L', 'F').replace('R', 'B') //Hahaha so that I can reuse that same recursive function
    }
}

