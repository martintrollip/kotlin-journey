package adventofcode.twenty18

import java.io.File
import kotlin.math.absoluteValue

/**
 * @author Martin Trollip ***REMOVED***
 * @since 2018/12/06 07:02
 */
val COORDINATE_REGEX = "([0-9]+), ([0-9]+)".toRegex()

const val DAY6_INPUT = "src/res/day6_input"

const val OFFSET = 10

fun main(args: Array<String>) {
    var count = 0
    val symbols = CharArray(50) { (it + 97).toChar() }.joinToString("")

    val points = File(DAY6_INPUT).readLines().map {
        val matchResult = COORDINATE_REGEX.find(it)
        val (y, x) = matchResult!!.destructured
        Coordinate(x.toInt() + OFFSET / 2, y.toInt() + OFFSET / 2, ManhattanDistance(symbols[count++].toString(), 0))
    }

    val xMin = points.minBy { it.x }?.x
    val xMax = points.maxBy { it.x }?.x

    val yMin = points.minBy { it.y }?.y
    val yMax = points.maxBy { it.y }?.y

    val map = Array(xMax!! + OFFSET, { Array(yMax!! + OFFSET, { Coordinate() }) })

    map.plot(points)
    map.calculateManhattan(points)
    map.print()

    val largestArea = map.largestArea(xMin, xMax, yMin, yMax)
    println("The largest are is $largestArea")

    val sumOfDistanceLessThan = map.sumOfDistanceLessThan(10000)
    println("Safe zone size $sumOfDistanceLessThan")

}

fun Array<Array<Coordinate>>.plot(points: List<Coordinate>) {
    for (row in 0 until this.size) {
        for (column in 0 until this[row].size) {
            this[row][column] = Coordinate(row, column)
        }
    }

    for (coordinate in points) {
        coordinate.closestDistance.symbol = coordinate.closestDistance.symbol.toUpperCase()
        this[coordinate.x][coordinate.y] = coordinate
    }
}

fun Array<Array<Coordinate>>.calculateManhattan(points: List<Coordinate>) {
    for (row in this) {
        for (column in row) {
            for (point in points) {
                val distance = manhattanDistance(column, point)
                column.setClosestDistance(point, distance)
                column.add(distance)
            }
        }
    }
}

fun Array<Array<Coordinate>>.print() {
    for (row in this) {
        for (column in row) {
            print("${column.closestDistance.symbol}".padStart(2))
        }
        println("")
    }
}

fun Array<Array<Coordinate>>.largestArea(xMin: Int?, xMax: Int?, yMin: Int?, yMax: Int?): Int? {
    var areas = LinkedHashMap<String, Int>()
    var infinites = LinkedHashMap<String, Int>()

    for (row in this) {
        for (column in row) {
            val key = column.closestDistance.symbol.toLowerCase()

            if (isItemOnBoundary(column, xMin, xMax, yMin, yMax)) {
                infinites.put(key, 0)
            } else {
                areas.put(key, areas.getOrDefault(key, 0) + 1)
            }
        }
    }

    infinites.keys.forEach {
        areas.remove(it)
    }

    println(areas)

    return areas.maxBy { it.value }?.value
}

fun Array<Array<Coordinate>>.sumOfDistanceLessThan(upperBound: Int) : Int {
    var count = 0
    for (row in this) {
        for (column in row) {
            if (column.sumOfDistance < upperBound ) {
                count++
            }
        }
    }

    return count
}

fun manhattanDistance(a: Coordinate, b: Coordinate): Int {
    return (a.x - b.x).absoluteValue + (a.y - b.y).absoluteValue
}

fun isItemOnBoundary(coordinate: Coordinate, xMin: Int?, xMax: Int?, yMin: Int?, yMax: Int?): Boolean {
    //Items on the boundary will have infinite counts,
    return coordinate.x <= xMin!! || coordinate.x >= xMax!! || coordinate.y <= yMin!! || coordinate.y >= yMax!!
}

data class Coordinate(var x: Int = -1, var y: Int = -1, var closestDistance: ManhattanDistance = ManhattanDistance(), var sumOfDistance: Int = 0) {
    fun setClosestDistance(point: Coordinate, distance: Int) {
        this.closestDistance.distance

        if (this.closestDistance.distance != 0) {
            if (this.closestDistance.distance == distance) {
                this.closestDistance.symbol = "."
            } else {
                if (distance < this.closestDistance.distance) {
                    this.closestDistance.distance = distance
                    this.closestDistance.symbol = point.closestDistance.symbol.toLowerCase()
                }
            }
        }
    }

    fun add(distance: Int) {
        this.sumOfDistance += distance

//        if(this.sumOfDistance >= 32) {
//            this.closestDistance.symbol = ""
//        }
    }
}

data class ManhattanDistance(var symbol: String = ".", var distance: Int = Integer.MAX_VALUE)