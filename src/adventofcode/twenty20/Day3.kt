package adventofcode.twenty20

import java.io.File

/**
 * @author Martin Trollip
 * @since 2020/12/03 06:55
 */
private const val DAY3_INPUT = "src/res/twenty20/day3_input"

fun main(args: Array<String>) {
    val day3 = Day2(DAY3_INPUT)
    println("Right 3, down 1, how many trees would you encounter? ${day3.part1()}")
    println("${day3.part2()}")
}

class Day3(input: String) {

    private val ROW_COUNT: Int
    private val COL_COUNT: Int
    private val trees: HashSet<Pair<Int, Int>>


    init {
        val (lineCount, colCount, treeSet) = readInput(input)
        ROW_COUNT = lineCount
        COL_COUNT = colCount
        trees = treeSet
    }

    private fun readInput(fileName: String): Triple<Int, Int, HashSet<Pair<Int, Int>>> {
        var rowCount = 0
        var colCount = 0
        val hashSet = HashSet<Pair<Int, Int>>()
        File(fileName).readLines().forEachIndexed { row, line ->
            rowCount++
            colCount = 0
            line.forEachIndexed { col, char ->
                colCount++
                if (char == '#') {
                    hashSet.add(Pair(row, col))
                }

            }
        }
        return Triple(rowCount, colCount, hashSet)
    }

    private val MOVE_RIGHT = 3
    private val MOVE_DOWN = 1
    fun part1(): Int {
        return calculateCollisions(MOVE_RIGHT, MOVE_DOWN)
    }

    private val SLOPES = listOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))

    fun part2(): Int {
        var product = 1
        SLOPES.forEach {
            product *= calculateCollisions(it.first, it.second)
        }
        return product
    }

    private fun calculateCollisions(right: Int, down: Int): Int {
        var currentRow = 0
        var currentCol = 0

        var treeCollisions = 0
        while (currentRow <= ROW_COUNT) {
            if (trees.contains(Pair(currentRow, currentCol % COL_COUNT))) {
                treeCollisions++
            }
            currentRow += down
            currentCol += right
        }

        return treeCollisions
    }
}

