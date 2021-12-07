package adventofcode.twenty20

import adventofcode.twenty19.Coordinate
import java.awt.Point
import java.io.File
import kotlin.math.absoluteValue


/**
 * @author Martin Trollip
 * @since 2020/12/16 11:00
 */
private const val DAY12_INPUT = "src/res/twenty20/day12_input"

fun main(args: Array<String>) {
    val day12 = Day12(DAY12_INPUT)
    println("${day12.part1()}")
    println("${day12.part2()}")
}

class Day12(val input: String) {

    private val INSTRUCTION_REGEX = "([A-Z])([0-9]+)".toRegex() //https://regex101.com/r/mqqEfG/1

    enum class Cardinal {
        N,
        E,
        S,
        W
    }

    data class Location(var coordinate: Point = Point(0, 0), var direction: Cardinal = Cardinal.E)

    /**
     *               N
     *   (x, y).     |  .(x, y)
     *          W----+----E
     *   (-x, -y).   |  .(x, -y)
     *               S
     */
    fun part1(): Int {
        val currentLocation = Location()

        File(input).readLines().forEach { line ->
            val (instruction, value) = INSTRUCTION_REGEX.find(line)!!.destructured
            move(instruction, currentLocation, value.toInt())
        }

        return manhattanDistance(Coordinate(0, 0), Coordinate(currentLocation.coordinate.x, currentLocation.coordinate.y))
    }

    private fun move(instruction: String, location: Location, value: Int) {
        when (instruction) {
            "N" -> location.coordinate.y += value
            "E" -> location.coordinate.x += value
            "S" -> location.coordinate.y -= value
            "W" -> location.coordinate.x -= value
            "F" -> move(location.direction.toString(), location, value)
            "L" -> location.direction = location.direction.left(value)
            "R" -> location.direction = location.direction.right(value)
        }
    }

    fun Cardinal.left(value: Int): Cardinal {
        return Cardinal.values()[(this.ordinal - value / 90 + 4) % 4]
    }

    fun Cardinal.right(value: Int): Cardinal {
        return Cardinal.values()[(this.ordinal + value / 90) % 4]
    }

    fun part2(): Int {
        val shipLocation = Location()
        val waypointLocation = Location(Point(10, 1))

        File(input).readLines().forEach { line ->
            val (instruction, value) = INSTRUCTION_REGEX.find(line)!!.destructured
            move2(instruction, shipLocation, waypointLocation, value.toInt())
        }

        return manhattanDistance(Coordinate(0, 0), Coordinate(shipLocation.coordinate.x, shipLocation.coordinate.y))
    }

    private fun move2(instruction: String, ship: Location, waypoint: Location, value: Int) {
        when (instruction) {
            "N" -> waypoint.coordinate.y += value
            "E" -> waypoint.coordinate.x += value
            "S" -> waypoint.coordinate.y -= value
            "W" -> waypoint.coordinate.x -= value
            "F" -> moveToWaypoint(ship, waypoint, value)
            "L" -> waypoint.coordinate = waypoint.coordinate.counterClockwise(value)
            "R" -> waypoint.coordinate = waypoint.coordinate.clockwise(value)
        }
    }

    private fun moveToWaypoint(ship: Location, waypoint: Location, value: Int) {
        ship.coordinate.x += waypoint.coordinate.x * value
        ship.coordinate.y += waypoint.coordinate.y * value
    }

    //https://calcworkshop.com/transformations/rotation-rules/
    fun Point.counterClockwise(value: Int): Point {
        return when (value) {
            90 -> Point(-this.y, this.x)
            180 -> Point(-this.x, -this.y)
            270 -> this.clockwise(90)
            else -> this
        }
    }

    fun Point.clockwise(value: Int): Point {
        return when (value) {
            90 -> Point(this.y, -this.x)
            180 -> Point(-this.x, -this.y)
            270 -> this.counterClockwise(90)
            else -> this
        }
    }

    private fun manhattanDistance(a: Coordinate, b: Coordinate): Int {
        return (a.x - b.x).absoluteValue + (a.y - b.y).absoluteValue
    }
}