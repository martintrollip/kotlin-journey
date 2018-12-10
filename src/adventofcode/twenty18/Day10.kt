package adventofcode.twenty18

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.max

/**
 * @author Martin Trollip
 * @since 2018/12/10 06:51
 */
val POINT_OF_LIGHT_REGEX = "position=<\\s?(\\-?[0-9]+), \\s?(\\-?[0-9]+)> velocity=<\\s?(\\-?[0-9]+), \\s?(\\-?[0-9]+)>".toRegex()

const val DAY10_INPUT = "src/res/day10_input_small"

fun main(args: Array<String>) {
    var count = 0

    val lightPoints = File(DAY10_INPUT).readLines().map {
        val matchResult = POINT_OF_LIGHT_REGEX.find(it)
        val (x, y, dx, dy) = matchResult!!.destructured
        LightPoint(count++, x.toInt(), y.trim().toInt(), dx.toInt(), dy.toInt())
    }

    //TODO move to function and have multiple return values
    val xMin = lightPoints.minBy { it.x }?.x
    val xMax = lightPoints.maxBy { it.x }?.x
    val xSize = (xMax?.absoluteValue!! + xMin?.absoluteValue!!) * 2
    val yMin = lightPoints.minBy { it.y }?.y
    val yMax = lightPoints.maxBy { it.y }?.y
    val ySize = (yMin?.absoluteValue!! + yMax?.absoluteValue!!) * 2
    val size = max(xSize, ySize)

    lightPoints.translateAll(xMin, yMin)
    val skyMap = Array(size, { Array(size, { LightPoint() }) })
    simulate(skyMap, lightPoints)
}

fun simulate(skyMap: Array<Array<LightPoint>>, points: List<LightPoint>) {
    var map = skyMap.toMutableList()
//    points.move(3)
    for (i in 0 until 10) {
        for (point in points) {
            if (point.x >= 0 && point.x < map.size && point.y >= 0 && point.y < map.size) {
                map[point.x][point.y] = point
            }
        }
        map.print()
        map = MutableList(skyMap.size, { Array(skyMap.size, { LightPoint() }) })
        points.move()
    }
}


fun List<LightPoint>.move(step: Int) {
    for (lightPoint in this) {
        lightPoint.x += lightPoint.dx * step
        lightPoint.y += lightPoint.dy * step
    }
}

fun List<LightPoint>.move() {
    for (lightPoint in this) {
        lightPoint.x += lightPoint.dx
        lightPoint.y += lightPoint.dy
    }
}

fun List<LightPoint>.translateAll(xMin: Int, yMin: Int) {
    for (lightPoint in this) {
        lightPoint.x += xMin.absoluteValue
        lightPoint.y += yMin.absoluteValue
    }
}

fun MutableList<Array<LightPoint>>.print() {
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