package adventofcode.twenty19

import java.io.File
import java.lang.UnsupportedOperationException
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

/**
 * @author Martin Trollip
 * @since 2019/12/03 06:38
 */
private const val DAY2_INPUT = "src/res/twenty19/day3_input"

fun main(args: Array<String>) {
    val day3 = Day3(DAY2_INPUT)

    println("What is the Manhattan distance from the central port to the closest intersection? ${day3.part1()}\n")
    println("What is the fewest combined steps the wires must take to reach an intersection? ${day3.part2()}")
}


class Day3(val input: String) {
    private val LINE_REGEX = "([UDLR])([0-9]+)".toRegex()

    fun part1(): Int {
        val input = File(input).readLines()
        val wireA = createWire(input[0])
        val wireB = createWire(input[1])
        val distances = mutableListOf<Int>()

        //TODO this for loop can be optimised by checking the perpendicular and bound of the lines 1st
        for (lineA in wireA) {
            for (lineB in wireB) {
                val intersection = lineA.intersection(lineB)
                if (intersection != null) {
                    val distance = manhattanDistance(Coordinate(0, 0), intersection)
                    distances.add(distance)
                    println("Intersection at $intersection and distance $distance")
                }
            }
        }

        return distances.minBy { it }!!
    }

    fun part2(): Int {
        val input = File(input).readLines()
        val wireA = createWire(input[0])
        val wireB = createWire(input[1])

        val steps = mutableListOf<Int>()

        wireA.forEachIndexed { aIndex, aLine ->
            run {
                wireB.forEachIndexed { bIndex, bLine ->
                    run {
                        val intersection = aLine.intersection(bLine)
                        if (intersection != null) {
                            var aCost = aLine.lengthTo(intersection)
                            var bCost = bLine.lengthTo(intersection)

                            for (a in aIndex - 1 downTo 0) {
                                aCost += wireA[a].length()
                            }
                            for (b in bIndex - 1 downTo 0) {
                                bCost += wireB[b].length()
                            }

                            steps.add(aCost + bCost)
                            println("Intersection at $intersection steps=${aCost + bCost}")
                        }
                    }
                }
            }
        }

        return steps.minBy { it }!!
    }

    fun createWire(input: String): List<Line> {
        var start = Coordinate(0, 0)

        return input.split(",").map {
            val line = createLine(it, start)
            start = line.end
            line
        }
    }

    fun createLine(input: String, currentCoordinate: Coordinate): Line {
        val line = LINE_REGEX.find(input)
        if (line != null) {
            val (strDirection, length) = line.destructured
            val direction = Direction.valueOf(strDirection)
            return Line(currentCoordinate, currentCoordinate.move(direction, length.toInt()))
        } else {
            println("Invalid entry $input")
            throw UnsupportedOperationException("Invalid line entry $input")
        }
    }

    fun manhattanDistance(a: Coordinate, b: Coordinate): Int {
        return (a.x - b.x).absoluteValue + (a.y - b.y).absoluteValue
    }
}

/**
 *
 *          -y
 *           ^
 *           |
 *           |
 * -x <------+------> +x
 *           |
 *           |
 *           v
 *           +y
 *
 */
enum class Direction {
    U, D, L, R
}

data class Coordinate(var x: Int = -1, var y: Int = -1) {

    fun move(direction: Direction, distance: Int): Coordinate {
        return when (direction) {
            Direction.U -> Coordinate(this.x, this.y - distance)
            Direction.D -> Coordinate(this.x, this.y + distance)
            Direction.L -> Coordinate(this.x - distance, this.y)
            Direction.R -> Coordinate(this.x + distance, this.y)
        }
    }
}

data class Line(val start: Coordinate, val end: Coordinate) {

    fun intersection(another: Line): Coordinate? {

        if (perpendicular(another)) { //TODO edgecase wher both start at the center and one moves left and another moves right
            if (slope(this) == Direction.U) {
                val xConst = this.start.x
                val yConst = another.start.y

                val lowerX = min(another.start.x, another.end.x)
                val upperX = max(another.start.x, another.end.x)
                val lowerY = min(this.start.y, this.end.y)
                val upperY = max(this.start.y, this.end.y)

                if ((xConst in lowerX..upperX) && (yConst in lowerY..upperY)) {
                    return Coordinate(xConst, yConst)
                }
            } else {
                val xConst = another.start.x
                val yConst = this.start.y

                val lowerX = min(this.start.x, this.end.x)
                val upperX = max(this.start.x, this.end.x)
                val lowerY = min(another.start.y, another.end.y)
                val upperY = max(another.end.y, another.end.y)

                if ((xConst in lowerX..upperX) && (yConst in lowerY..upperY)) {
                    return Coordinate(xConst, yConst)
                }
            }
        }

        return null
    }

    fun length(): Int { //TODO Martin Can use manhattan from above
        return (start.x - end.x).absoluteValue + (start.y - end.y).absoluteValue
    }

    fun lengthTo(coordinate: Coordinate): Int {
        return (start.x - coordinate.x).absoluteValue + (start.y - coordinate.y).absoluteValue
    }

    private fun slope(line: Line): Direction {
        return if (line.start.x == line.end.x) {
            Direction.U
        } else {
            Direction.L
        }
    }

    private fun perpendicular(another: Line): Boolean {
        return slope(this) != slope(another)
    }
}