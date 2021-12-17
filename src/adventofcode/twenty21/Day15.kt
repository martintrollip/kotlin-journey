package adventofcode.twenty21

import java.awt.Point
import java.io.File
import java.util.*

/**
 * @author Martin Trollip
 * @since 2021/12/16 07:25
 */
private const val DAY15_INPUT = "src/res/twenty21/day15_input"

fun main() {
    val day15 = Day15()
    println(
        "What do you get if you take the quantity of the most common element and subtract the quantity of the least common element?What is the lowest total risk of any path from the top left to the bottom right? ${
            day15.part1(day15.readInput(DAY15_INPUT))
        }"
    )
    println(
        "Using the full map, what is the lowest total risk of any path from the top left to the bottom right? ${
            day15.part2(day15.readInput(DAY15_INPUT))
        }"
    )
}

typealias ChitonsCave = List<List<Int>>

class Day15 {

    fun readInput(fileName: String): ChitonsCave {
        return File(fileName).readLines().map { line -> line.map { it.toString().toInt() } }
    }

    fun part1(input: ChitonsCave): Int {
        return calculateRisk(input, Point(0, 0), lastPoint(input))
    }

    private fun calculateRisk(input: ChitonsCave, start: Point, end: Point): Int {
        val visited = mutableListOf(start)
        val queue = PriorityQueue<Vertex>()
        queue.add(Vertex(start, 0))

        while (queue.peek().position != end) {
            val (position, risk) = queue.poll()
            position.getNeighbours(end.x, end.y)
                .filter { !visited.contains(it) }.forEach { neighbour ->
                    (risk + input.get(neighbour.x, neighbour.y)).let { neighbourRisk ->
                        queue.add(Vertex(neighbour, neighbourRisk))
                    }
                    visited.add(neighbour)
                }
        }
        return queue.poll().risk
    }

    fun part2(input: ChitonsCave): Int {
        val lastPoint = lastPoint(input)
        return calculateRisk(input, Point(0, 0), Point((lastPoint.x + 1) * 5 - 1, (lastPoint.y + 1) * 5 - 1))
    }

    private fun lastPoint(input: ChitonsCave): Point {
        return Point(input.lastIndex, input.last().lastIndex)
    }

    private fun Point.getNeighbours(maxX: Int, maxY: Int): List<Point> {
        return listOf(Point(x - 1, y), Point(x + 1, y), Point(x, y + 1), Point(x, y - 1)).filter {
            it.x in 0..maxX && it.y in 0..maxY
        }
    }

    private fun ChitonsCave.get(x: Int, y: Int): Int {
        return if (x in 0..lastIndex && y in 0..last().lastIndex) {
            this[y][x]
        } else {
            val maxX = this.first().size
            val maxY = this.size
            val deltaX = x / maxX
            val deltaY = y / maxY
            val originalRisk = this[y % maxY][x % maxX]
            val newRisk = (originalRisk + deltaX + deltaY)
            
            if(newRisk < 10) {
                newRisk
            } else {
                newRisk - 9
            }
        }
    }

    private data class Vertex(val position: Point, val risk: Int) : Comparable<Vertex> {

        override fun compareTo(other: Vertex): Int {
            return risk.compareTo(other.risk)
        }
    }
}
    
