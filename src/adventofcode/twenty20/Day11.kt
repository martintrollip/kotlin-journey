package adventofcode.twenty20

import java.io.File
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * @author Martin Trollip
 * @since 2020/12/11 07:05
 */
private const val DAY11_INPUT = "src/res/twenty20/day11_input"

fun main(args: Array<String>) {
    val day11 = Day11(DAY11_INPUT)
    println("${day11.part1()}")
    println("${day11.part2()}")
}

class Day11(input: String) {
    private val EMPTY = 'L'
    private val FLOOR = '.'
    private val OCCUPIED = '#'

    private var floor: Map<Pair<Int, Int>, Char>
    private val maxRowIndex: Int
    private val maxColIndex: Int

    init {
        floor = read(input)
        maxRowIndex = floor.maxBy { it.key.first }!!.key.first
        maxColIndex = floor.maxBy { it.key.second }!!.key.second
    }

    private fun read(fileName: String): Map<Pair<Int, Int>, Char> {
        val floor = hashMapOf<Pair<Int, Int>, Char>()
        var row = 0
        var col = 0
        File(fileName).readLines().forEach { line ->
            line.forEach { char ->
                floor[Pair(row, col)] = char
                col++
            }
            row++
            col = 0
        }

        return floor
    }

    fun part1(): Int {
        var previousCount = count(floor)
        var currentCount = -1;

        while (currentCount != previousCount) {
            val newFloor = step()
            currentCount = count(newFloor)
            previousCount = count(floor)
            floor = newFloor
        }
        return currentCount
    }

    private fun step(): HashMap<Pair<Int, Int>, Char> {
        val newFloor = hashMapOf<Pair<Int, Int>, Char>()

        for (row in 0..maxRowIndex) {
            for (col in 0..maxColIndex) {
                val currentSeat = Pair(row, col)
                val seat = floor[currentSeat] ?: error("Unexpected state")
                val occupiedSeats = getAdjacentOccupiedSeats(currentSeat)

                when (seat) {
                    EMPTY -> {
                        if (occupiedSeats == 0) {
                            newFloor[currentSeat] = OCCUPIED
                        } else {
                            newFloor[currentSeat] = EMPTY
                        }
                    }
                    OCCUPIED -> {
                        if (occupiedSeats >= 4) {
                            newFloor[currentSeat] = EMPTY
                        } else {
                            newFloor[currentSeat] = OCCUPIED
                        }
                    }
                    else -> newFloor[currentSeat] = seat
                }
            }
        }
        return newFloor
    }

    fun part2(): Int {
        var previousCount = count(floor)
        var currentCount = -1;

        while (currentCount != previousCount) {
            val newFloor = step2()
            currentCount = count(newFloor)
            previousCount = count(floor)
            floor = newFloor
        }
        return currentCount
    }

    private fun step2(): HashMap<Pair<Int, Int>, Char> {
        val newFloor = hashMapOf<Pair<Int, Int>, Char>()

        for (row in 0..maxRowIndex) {
            for (col in 0..maxColIndex) {
                val currentSeat = Pair(row, col)
                val seat = floor[currentSeat] ?: error("Unexpected state")
                val occupiedSeats = getVisibleOccupiedSeats(currentSeat)

                when (seat) {
                    EMPTY -> {
                        if (occupiedSeats == 0) {
                            newFloor[currentSeat] = OCCUPIED
                        } else {
                            newFloor[currentSeat] = EMPTY
                        }
                    }
                    OCCUPIED -> {
                        if (occupiedSeats >= 5) {
                            newFloor[currentSeat] = EMPTY
                        } else {
                            newFloor[currentSeat] = OCCUPIED
                        }
                    }
                    else -> newFloor[currentSeat] = seat
                }
            }
        }
        return newFloor
    }

    private fun getAdjacentOccupiedSeats(currentSeat: Pair<Int, Int>): Int {
        return adjacentSeats(currentSeat).filter { it.value == OCCUPIED }.size
    }

    private fun adjacentSeats(currentSeat: Pair<Int, Int>): Map<Pair<Int, Int>, Char> {
        return floor.filter { isAdjacent(currentSeat.first, currentSeat.second, it.key.first, it.key.second) }
    }

    private fun isAdjacent(myRow: Int, myCol: Int, row: Int, col: Int): Boolean {
        return row == myRow && (col == myCol - 1 || col == myCol + 1) ||
                col == myCol && (row == myRow - 1 || row == myRow + 1) ||
                row == myRow - 1 && (col == myCol - 1 || col == myCol + 1) ||
                row == myRow + 1 && (col == myCol - 1 || col == myCol + 1)
    }

    private fun getVisibleOccupiedSeats(currentSeat: Pair<Int, Int>): Int {
        //Start at 1 and look in all of the directions around me
        val currentRow = currentSeat.first
        val currentCol = currentSeat.second

        val lineOfSight = floor.filter { isLineOfSight(currentRow, currentCol, it.key.first, it.key.second) && it.value != FLOOR }

        val directions = hashMapOf<Double, Triple<Int, Int, Double>>()// Angel - X,Y,distance

        //Calculate distance and angle, keeping only the closest //TODO this can be simplified
        lineOfSight.forEach {
            val angle = angle(currentRow.toDouble(), currentCol.toDouble(), it.key.first.toDouble(), it.key.second.toDouble())
            val distance = distance(currentRow.toDouble(), currentCol.toDouble(), it.key.first.toDouble(), it.key.second.toDouble())

            if (!directions.containsKey(angle)) {
                directions[angle] = Triple(it.key.first, it.key.second, distance)
            } else {
                if (distance < directions[angle]!!.third) {
                    directions[angle] = Triple(it.key.first, it.key.second, distance)
                }
            }
        }
        return directions.filter { lineOfSight[Pair(it.value.first, it.value.second)] == OCCUPIED }.size
    }

    private fun isLineOfSight(myRow: Int, myCol: Int, row: Int, col: Int): Boolean {
        return !(myRow == row && myCol == col) && (row == myRow || col == myCol || (abs(row - myRow) == abs(col - myCol)))
    }

    private fun count(input: Map<Pair<Int, Int>, Char>) = input.filter { it.value == OCCUPIED }.size

    private fun distance(x1: Double, y1: Double, x2: Double, y2: Double): Double {
        return sqrt((x1 - x2).pow(2) + (y1 - y2).pow(2))
    }

    private fun angle(x1: Double, y1: Double, x2: Double, y2: Double): Double {
        return Math.toDegrees(atan2(y2 - y1, x2 - x1))
    }
}