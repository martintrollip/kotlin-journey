package adventofcode.twenty21

import java.awt.Point
import java.io.File

/**
 * @author Martin Trollip
 * @since 2021/12/08 14:30
 */
private const val DAY9_INPUT = "src/res/twenty21/day9_input"

fun main() {
    val day9 = Day9()
    println(
        "What is the sum of the risk levels of all low points on your heightmap? ${
            day9.part1(day9.readInput(DAY9_INPUT))
        }"
    )
    println(
        "What do you get if you multiply together the sizes of the three largest basins? ${
            day9.part2(day9.readInput(DAY9_INPUT))
        }"
    )
}

class Day9 {

    fun readInput(fileName: String): Map<Point, Int> {
        var row = 0
        var col = 0
        val map = mutableMapOf<Point, Int>()

        File(fileName).readLines().forEach {
            it.forEach {
                map[Point(col, row)] = it.toString().toInt()
                col++
            }
            row++
            col = 0
        }
        return map
    }

    fun part1(input: Map<Point, Int>): Int {
        val lowPoints = calculateLowPoints(input)
        return lowPoints.map { it.value }.sumOf { it + 1 }
    }

    fun part2(input: Map<Point, Int>): Int {
        val basins = calculateBasins(input)
        return basins.map { it.size }.sortedDescending().take(3).reduce { acc, it -> acc * it }
    }

    /**
     * Return the points where all neighbours are greater than the current point.
     */
    private fun calculateLowPoints(input: Map<Point, Int>): Map<Point, Int> {
        val lowPoints = mutableMapOf<Point, Int>()
        input.forEach { (point, value) ->
            val neighbours = input.filter { isNeighbour(it, point) }
            if (neighbours.values.all { it > value }) {
                lowPoints[point] = value
            }
        }
        return lowPoints
    }

    private fun isNeighbour(it: Map.Entry<Point, Int>, point: Point) =
        ((it.key.x in (point.x - 1)..(point.x + 1) && it.key.y in (point.y - 1)..(point.y + 1))
                && (it.key.x == point.x || it.key.y == point.y) && !(it.key.x == point.x && it.key.y == point.y))

    /**
     * Remove points with a value of 9 and group the remaining points if they are adjacent.
     */
    private fun calculateBasins(input: Map<Point, Int>): List<List<Point>> {
        val basins = mutableListOf<List<Point>>()
        val lowPoints = calculateLowPoints(input)

        lowPoints.forEach { (point, value) ->
            run {
                val basin = mutableListOf(point)
                var neighbours = findNeighbours(input, listOf(point))

                while (neighbours.isNotEmpty()) {
                    basin.addAll(neighbours)
                    neighbours = findNeighbours(input, neighbours).filter { !basin.contains(it) }
                }
                basins.add(basin)
            }
        }
        return basins
    }
    
    private fun findNeighbours(input: Map<Point, Int>, points: List<Point>): List<Point> {
        val neighbours = mutableListOf<Point>()
        points.forEach { point ->
            val neighbour = input.filter { isNeighbour(it, point) && it.value != 9 }.map { it.key }
            neighbours.addAll(neighbour.filter { !neighbours.contains(it) })
        }
        return neighbours
    }
}

