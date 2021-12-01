package adventofcode.twenty18

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sqrt

/**
 * @author Martin Trollip
 * @since 2018/12/10 06:51
 */
val POINT_OF_LIGHT_REGEX = "position=<\\s?(\\-?[0-9]+), \\s?(\\-?[0-9]+)> velocity=<\\s?(\\-?[0-9]+), \\s?(\\-?[0-9]+)>".toRegex()

const val DAY10_INPUT = "src/res/day10_input"

fun main(args: Array<String>) {
    var count = 0

    val lightPoints = File(DAY10_INPUT).readLines().map {
        val matchResult = POINT_OF_LIGHT_REGEX.find(it)
        val (x, y, dx, dy) = matchResult!!.destructured
        LightPoint(count++, x.toLong(), y.trim().toLong(), dx.toLong(), dy.toLong())
    }.sortedWith(compareBy({ it.x }, { it.y }))

    simulate(lightPoints)
}

fun simulate(points: List<LightPoint>) {
    var textDetected = false
    var round = -1

    while (!textDetected) {
        round++
        val currentHeight = points.height()
        points.move()
        val newHeight = points.height()
        textDetected = singularityDetector(currentHeight, newHeight)
    }

    points.reverse() //we missed it
    buildArray(points).print()
    println("That took $round seconds")
}


fun singularityDetector(currentHeight: Long, newHeight: Long): Boolean {
    return newHeight > currentHeight
}

fun List<LightPoint>.move() {
    for (lightPoint in this) {
        lightPoint.x += lightPoint.dx
        lightPoint.y += lightPoint.dy
    }
}

fun List<LightPoint>.reverse() {
    for (lightPoint in this) {
        lightPoint.x -= lightPoint.dx
        lightPoint.y -= lightPoint.dy
    }
}

fun List<LightPoint>.height(): Long {
    val yMin = minByOrNull  { it.y }?.y!!
    val yMax = maxByOrNull  { it.y }?.y!!

    return (yMax - yMin).absoluteValue
}

fun List<LightPoint>.normalise() {
    val xOffset = minByOrNull  { it.x }?.x
    val yOffset = minByOrNull  { it.y }?.y

    forEach {
        it.x -= xOffset?.absoluteValue!!
        it.y -= yOffset?.absoluteValue!!
    }
}

fun buildArray(points: List<LightPoint>): Array<Array<LightPoint>> {
    points.normalise()
    val sorted = points.sortedWith(compareBy({ it.x }, { it.y }))

    val xMax = sorted.maxByOrNull  { it.x }?.x!!
    val xMin = sorted.minByOrNull  { it.x }?.x!!
    val yMax = sorted.maxByOrNull  { it.y }?.y!!
    val yMin = sorted.minByOrNull  { it.y }?.y!!
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

private fun distance(a: Long, b: Long) = sqrt(((a * a) + (b * b)).toDouble()).toInt() + 1

fun Array<Array<LightPoint>>.print() {
    var print = ""
    for (row in 0 until this.size) {
        var line = ""
        for (column in 0 until this[row].size) {
            line += if (this[row][column].id >= 0) {
                "#"
            } else {
                " "
            }
        }
        print += "$line\n"
    }

    println(print)
}

data class LightPoint(var id: Int = -1, var x: Long = -1, var y: Long = -1, var dx: Long = -1, var dy: Long = -1)