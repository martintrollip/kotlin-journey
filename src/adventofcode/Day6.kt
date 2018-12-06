package adventofcode

import java.io.File
import kotlin.math.absoluteValue

//5013 is wrong
/**
 * @author Martin Trollip <martint@discovery.co.za>
 * @since 2018/12/06 07:02
 */
val COORDINATE_REGEX = "([0-9]+), ([0-9]+)".toRegex()

const val DAY6_INPUT = "src/res/day6_input_small"

val EMPTY_SPACE = Coordinate()
val EMPTY_DISTANCE = listOf(ManhattanDistance())

val PADDING = 1

fun main(args: Array<String>) {
    var count = 0
    val symbols = CharArray(50) { (it + 97).toChar() }.joinToString("")

    val coordinates = File(DAY6_INPUT).readLines().map {
        val matchResult = COORDINATE_REGEX.find(it)
        val (x, y) = matchResult!!.destructured
        Coordinate(symbols[count++].toString(), x.toInt(), y.toInt())
    }

    val minX = coordinates.minBy { it.x }?.x!! - PADDING
    val maxX = coordinates.maxBy { it.x }?.x!! + PADDING

    val minY = coordinates.minBy { it.y }?.y!! - PADDING
    val maxY = coordinates.maxBy { it.y }?.y!!+ PADDING

    val bounds = Boundaries(minX, maxX, minY, maxY)
    val map = Array(maxX , { Array(maxY) { EMPTY_SPACE } })

    val manhattans = map.calculateManhattan(coordinates, bounds)
    var counts = LinkedHashMap<Coordinate, Int>()

    for (x: Int in minX until maxX) {
        for (y: Int in minY until maxY) {
            val minBy = manhattans[x][y].minBy { it.distance }

            var minDistance = 0
            if(minBy != null) {
                minDistance = minBy.distance
            }

            for (entry in manhattans[x][y]) {

                if (!isItemOnBoundary(entry.coordinate, bounds)) {
                    if(entry.distance == minDistance) {
                        if(manhattans[x][y].filter { it.distance == minDistance }.size == 1) {
                            var counter = counts.getOrDefault(entry.coordinate, 0)
                            counts.put(entry.coordinate, counter + 1)
                        }
                    }
                }
            }
        }
    }

    manhattans.print(bounds)
    println("Boundaries: x=$minX,$maxX and y=$minY,$maxY")
    println(counts)
    println(counts.maxBy { it.value })
}

fun Array<Array<List<ManhattanDistance>>>.print(bounds:Boundaries) {
    for (x: Int in bounds.xMin until bounds.xMax) {
        for (y: Int in bounds.yMin until bounds.yMax) {
            var print = ""
            val minBy = this[x][y].minBy { it.distance }

            var minDistance = 0
            if(minBy != null) {
                minDistance = minBy.distance
            }

            for (entry in this[x][y]) {
                //if (!isItemOnBoundary(entry.coordinate, bounds)) {
                    if(entry.distance == minDistance) {
                        if(x == entry.coordinate.x && y == entry.coordinate.y) {
                            print += entry.coordinate.symbol.toUpperCase()
                        } else {
                            print += entry.coordinate.symbol
                        }
                        print += ""//"(${entry.distance})"
                    }
                //}
            }
            if(print == "") {
                print = "."
            }
            print(print.padStart(4))
        }
        println("")
    }
}

fun Array<Array<Coordinate>>.calculateManhattan(coordinates: List<Coordinate>, boundaries: Boundaries): Array<Array<List<ManhattanDistance>>> {
    val manhattans = Array(boundaries.xMax, { Array(boundaries.yMax) { EMPTY_DISTANCE } })

    for (coordinate in coordinates) {
        for (x: Int in boundaries.xMin until boundaries.xMax) {
            for (y: Int in boundaries.yMin until boundaries.yMax) {
                val distance = manhattanDistance(Coordinate(".", x, y), coordinate)
                manhattans[x][y] = manhattans[x][y].add(distance, coordinate)
            }
        }
    }

    return manhattans
}

fun List<ManhattanDistance>.add(distance: Int, coordinate: Coordinate): List<ManhattanDistance> {
    val toMutableList = this.toMutableList()
    toMutableList.add(ManhattanDistance(coordinate, distance))
    return toMutableList
}

fun manhattanDistance(a: Coordinate, b: Coordinate): Int {
    return (a.x - b.x).absoluteValue + (a.y - b.y).absoluteValue
}

fun isItemOnBoundary(coordinate: Coordinate, boundaries: Boundaries): Boolean {
    //Items on the boundary will have infinite counts,
    return coordinate.x <= boundaries.xMin + PADDING || coordinate.x >= boundaries.xMax - PADDING || coordinate.y <= boundaries.yMin + PADDING || coordinate.y >= boundaries.yMax - PADDING
}

data class Coordinate(var symbol: String = ".", var x: Int = -1, var y: Int = -1)

data class ManhattanDistance(var coordinate: Coordinate = Coordinate(), var distance: Int = Integer.MAX_VALUE)

data class Boundaries(var xMin: Int, var xMax: Int, var yMin: Int, var yMax: Int)