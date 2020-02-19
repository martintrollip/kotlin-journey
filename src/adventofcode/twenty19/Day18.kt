package adventofcode.twenty19

import java.awt.Point
import java.io.File

/**
 * @author Martin Trollip
 * @since 2019/12/29 10:56
 */
private const val DAY18_INPUT = "src/res/twenty19/day18_input"

fun main(args: Array<String>) {
    val day18 = Day18()
    val input = day18.readInput(File(DAY18_INPUT).readLines())
    println("How many steps is the shortest path that collects all of the keys? ${day18.part1(input)}\n") //4408
    println("How much dust does the vacuum robot report it has collected? ${day18.part2(input)}")//862452
}

class Day18 {
    private final val EMPTY = '.'
    private final val DOOR = '@'
    private final val WALL = '#'


    fun readInput(lines: List<String>): Map<Point, Char> {
        val map = mutableMapOf<Point, Char>()
        var y = 0

        lines.forEach { line ->
            var x = 0
            line.forEach { char ->
                if (char != WALL) {
                    map[Point(x, y)] = char
                }
                x++
            }
            y++
        }

        return map
    }

    fun part1(input: Map<Point, Char>): Int {
        return calculateSteps(input.toMutableMap())
    }

    private enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private fun calculateSteps(input: MutableMap<Point, Char>): Int {
        var steps = 0

        val startingPosition = input.filter { it.value == DOOR }.keys.first()
        input[startingPosition] = EMPTY

        getPossibleDirections(startingPosition, input).forEach {
            val nextPoint = when (it) {
                Direction.UP -> Point()
                Direction.DOWN -> Point()
                Direction.LEFT -> Point()
                Direction.RIGHT -> Point()

            }
            steps +=
        }

        if (input.filterValues { it != EMPTY }.isEmpty()) {
            return steps
        }
        return -1;
    }

    private fun getPossibleDirections(currentPosition: Point, input: Map<Point, Char>): MutableList<Direction> {
        val possibleDirections = mutableListOf<Direction>()

        //UP
        if(movable(input, Point(currentPosition.x, currentPosition.y - 1))) {
            possibleDirections.add(Direction.UP);
        }

        //DOWN
        if(movable(input, Point(currentPosition.x, currentPosition.y + 1))) {
            possibleDirections.add(Direction.DOWN)
        }

        //LEFT
        if(movable(input, Point(currentPosition.x - 1, currentPosition.y))) {
            possibleDirections.add(Direction.LEFT)
        }

        //RIGHT
        if(movable(input, Point(currentPosition.x + 1, currentPosition.y))) {
            possibleDirections.add(Direction.RIGHT)
        }

        return possibleDirections
    }

    private fun movable(input: Map<Point, Char>, point: Point) : Boolean {
        val pointValue = input[point]
        return pointValue != null && (isEmpty(pointValue) || isKey(pointValue))
    }

    private fun isEmpty(input: Char) : Boolean {
        return input == EMPTY
    }

    private fun isKey(input: Char) : Boolean {
        return input.isLowerCase()
    }

    private fun getUp(currentPosition: Point): Point {
        return Point(currentPosition.x, currentPosition.y - 1)
    }

    private fun getDown(currentPosition: Point): Point {
        return Point(currentPosition.x, currentPosition.y + 1)
    }

    private fun getLeft(currentPosition: Point): Point {
        return Point(currentPosition.x - 1, currentPosition.y)
    }

    private fun getRight(currentPosition: Point): Point {
        return Point(currentPosition.x + 1, currentPosition.y)
    }

    fun part2(input: Map<Point, Char>): Int {
        return 2
    }
}
