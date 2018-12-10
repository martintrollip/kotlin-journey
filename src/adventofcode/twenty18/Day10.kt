package adventofcode.twenty18

import java.io.File
import java.lang.Math.pow
import kotlin.math.max
import kotlin.math.sqrt

/**
 * @author Martin Trollip
 * @since 2018/12/10 06:51
 */
val POINT_OF_LIGHT_REGEX = "position=<\\s?(\\-?[0-9]+), \\s?(\\-?[0-9]+)> velocity=<\\s?(\\-?[0-9]+), \\s?(\\-?[0-9]+)>".toRegex()
val MAX_RUNS = 10000000
var LETTER_HEIGHT = 8//Assuming the letters has the same height as example

const val DAY10_INPUT = "src/res/day10_input_small"

fun main(args: Array<String>) {
    var count = 0

    val lightPoints = File(DAY10_INPUT).readLines().map {
        val matchResult = POINT_OF_LIGHT_REGEX.find(it)
        val (x, y, dx, dy) = matchResult!!.destructured
        LightPoint(count++, x.toInt(), y.trim().toInt(), dx.toInt(), dy.toInt())
    }

    simulate(lightPoints)
}

fun simulate(points: List<LightPoint>) {
    var detected = false
    var count = -1
    while (!detected && count < MAX_RUNS) {
        count++
        points.move()
        detected = letterDetector(points)
    }

    println("Possible detection at ${count + 1}")
//    points.normalise()
    val sorted = points.sortedWith(compareBy({ it.x }, { it.y }))
    buildArray(sorted).print()
}

fun letterDetector(points: List<LightPoint>): Boolean {
    val yMax = points.maxBy { it.y }?.y
    val yMin = points.minBy { it.y }?.y

    return sqrt(pow(yMax?.toDouble()!!, 2.0) + pow(yMin?.toDouble()!!, 2.0)) <= LETTER_HEIGHT //Assuming 1 line
}

fun List<LightPoint>.move() {
    for (lightPoint in this) {
        lightPoint.x += lightPoint.dx
        lightPoint.y += lightPoint.dy
    }
}

fun List<LightPoint>.normalise() {
    val xOffset = minBy { it.x }?.x
    val yOffset = minBy { it.y }?.y

    forEach {
        it.x -= xOffset!!
        it.y -= yOffset!!
    }
}

fun buildArray(points: List<LightPoint>): Array<Array<LightPoint>> {
    val height = points.maxBy { it.y }?.y!! + 10
    val width = points.maxBy { it.x }?.x!! + 10
    val size = max(height, width)

    val array = Array(size, { Array(size, { LightPoint() }) })
    for (point in points) {
        if (point.x >= 0 && point.y >= 0 && point.x < size && point.y < size) {
            array[point.x][point.y] = point
        }
    }

    return array
}

fun Array<Array<LightPoint>>.print() {
    var print = ""
    for (row in 0 until this.size) {
        var line = ""
        for (column in 0 until this[row].size) {
            line += if (this[column][row].id >= 0) {
                "#"
            } else {
                "."
            }
        }
        print += "$line\n"
    }
    println(print)
//    File("$iteration.txt").writeText(print)
}

data class LightPoint(var id: Int = -1, var x: Int = -1, var y: Int = -1, var dx: Int = -1, var dy: Int = -1)