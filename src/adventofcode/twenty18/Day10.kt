package adventofcode.twenty18

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sqrt

/**
 * @author Martin Trollip
 * @since 2018/12/10 06:51
 */
val POINT_OF_LIGHT_REGEX = "position=<\\s?(\\-?[0-9]+), \\s?(\\-?[0-9]+)> velocity=<\\s?(\\-?[0-9]+), \\s?(\\-?[0-9]+)>".toRegex()
val MAX_RUNS = 1000000000
var LETTER_HEIGHT = 8//Assuming the letters has the same height as example

const val DAY10_INPUT = "src/res/day10_input_small"

//303710075

fun main(args: Array<String>) {
    var count = 0

    val lightPoints = File(DAY10_INPUT).readLines().map {
        val matchResult = POINT_OF_LIGHT_REGEX.find(it)
        val (x, y, dx, dy) = matchResult!!.destructured
        LightPoint(count++, x.toLong(), y.trim().toLong(), dx.toLong(), dy.toLong())
    }



    simulate(lightPoints)
}

fun simulate(points: List<LightPoint>) {
    var textDetected = false
    var count = -1

    while (!textDetected && count < MAX_RUNS) {
        count++
        points.move()
        textDetected = letterDetector(points)
    }

    println("Possible detection at $count")
    println(buildArray(points).print())
}

fun letterDetector(points: List<LightPoint>): Boolean {
    val yMin = points.minBy { it.y }?.y!!
    val yMax = points.maxBy { it.y }?.y!!
    val height = distance(yMax, yMin)

    return height <= LETTER_HEIGHT //Assuming 1 line
}

private fun distance(a: Long, b: Long) = sqrt(((a * a) + (b * b)).toDouble()).toInt() + 1

fun List<LightPoint>.move() {
    for (lightPoint in this) {
        lightPoint.x += lightPoint.dx
        lightPoint.y += lightPoint.dy
    }
}

fun List<LightPoint>.move(steps: Int) {
    for (lightPoint in this) {
        lightPoint.x += steps * lightPoint.dx
        lightPoint.y += steps * lightPoint.dy
    }
}

fun List<LightPoint>.normalise() {
    val xOffset = minBy { it.x }?.x
    val yOffset = minBy { it.y }?.y

    forEach {
        it.x -= xOffset?.absoluteValue!!
        it.y -= yOffset?.absoluteValue!!
    }
}

fun buildArray(points: List<LightPoint>): Array<Array<LightPoint>> {
    points.normalise()
    val sorted = points.sortedWith(compareBy({ it.x }, { it.y }))

    val xMax = sorted.maxBy { it.x }?.x!!
    val xMin = sorted.minBy { it.x }?.x!!
    val yMax = sorted.maxBy { it.y }?.y!!
    val yMin = sorted.minBy { it.y }?.y!!
    val width = distance(xMin, xMax)
    val height = distance(yMin, yMax)

    val array = Array(height, { Array(width, { LightPoint() }) })
    for (point in sorted) {
        if (point.x >= 0 && point.y >= 0 && point.x <= width && point.y <= height) {
            array[point.y.toInt()][point.x.toInt()] = point // :/
        }
    }

    return array
}

fun Array<Array<LightPoint>>.print(): String {
    var print = ""
    for (row in 0 until this.size) {
        var line = ""
        for (column in 0 until this[row].size) {
            line += if (this[row][column].id >= 0) {
                "#"
            } else {
                "."
            }
        }
        print += "$line\n"
    }
    return print
//    println(print)
//    File("$iteration.txt").writeText(print)
}

data class LightPoint(var id: Int = -1, var x: Long = -1, var y: Long = -1, var dx: Long = -1, var dy: Long = -1)