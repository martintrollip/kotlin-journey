package adventofcode.twenty21

import java.awt.Point
import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.sign

/**
 * @author Martin Trollip
 * @since 2021/12/06 09:27
 */
private const val DAY5_INPUT = "src/res/twenty21/day5_input"

fun main() {
    val day5 = Day5(DAY5_INPUT)
    println(
        "At how many points do at least two lines overlap (Horizontal and Vertical)? ${
            day5.calculateHorizontalAndVerticalVents()
        }"
    )
    println(
        "At how many points do at least two lines overlap? (all lines) ${
            day5.calculateAllVents()
        }"
    )
}

typealias HydrothermalVents = Pair<Point, Point>

class Day5(inputFile: String) {
    private val VENT_REGEX = "([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)".toRegex() //https://regex101.com/r/7kCSb2/1

    private val input = readInput(inputFile)

    fun readInput(fileName: String): List<HydrothermalVents> {
        return File(fileName).readLines().map {
            if (VENT_REGEX.matches(it)) {
                val (x1, y1, x2, y2) = VENT_REGEX.find(it)!!.destructured
                Pair(Point(x1.toInt(), y1.toInt()), Point(x2.toInt(), y2.toInt()))
            } else {
                throw IllegalArgumentException("Invalid instruction: $it")
            }
        }
    }

    fun calculateHorizontalAndVerticalVents(): Int {
        return numberOfOverlappingVents { it.horizontalOrVertical() }
    }

    fun calculateAllVents(): Int {
        return numberOfOverlappingVents { true }
    }

    private fun HydrothermalVents.horizontalOrVertical(): Boolean {
        return this.first.x == this.second.x || this.first.y == this.second.y
    }

    private fun numberOfOverlappingVents(filter: (HydrothermalVents) -> Boolean): Int {
        val overlappingPoints = mutableMapOf<Point, Int>()
        input.filter { filter(it) }.forEach { segment ->
            segment.points().forEach { point ->
                overlappingPoints[point] = overlappingPoints.getOrDefault(point, 0) + 1
            }
        }

        return overlappingPoints.filter { it.value > 1 }.size
    }

    private fun HydrothermalVents.equation(): Line {
        val (p1, p2) = this
        val slope = (p2.y - p1.y).toDouble() / (p2.x - p1.x).toDouble()
        val c = p1.y - slope * p1.x
        return Line(slope, c)
    }

    data class Line(val slope: Double, val c: Double)

    fun HydrothermalVents.points(): List<Point> {
        val linePoints = mutableListOf<Point>()

        val deltaX = this.second.x - this.first.x
        val deltaY = this.second.y - this.first.y

        val length = max(deltaX.absoluteValue, deltaY.absoluteValue)

        for (i in 0..length) {
            linePoints.add(Point(this.first.x + i * deltaX.sign, this.first.y + i * deltaY.sign))
        }

        return linePoints
    }
}

