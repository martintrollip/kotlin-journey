package adventofcode.twenty21

import java.awt.Point
import java.io.File

/**
 * @author Martin Trollip
 * @since 2021/12/12 12:08
 */
private const val DAY13_INPUT = "src/res/twenty21/day13_input"

fun main() {
    val day13 = Day13()
    println(
        "How many dots are visible after completing just the first fold instruction on your transparent paper? ${
            day13.part1(day13.readInput(DAY13_INPUT))
        }"
    )
    println(
        "What code do you use to activate the infrared thermal imaging camera system? ${
            day13.part2(day13.readInput(DAY13_INPUT))
        }"
    )
}

class Day13 {

    fun readInput(fileName: String): Day13Input {
        val points = mutableListOf<Point>()
        val folds = mutableListOf<Point>()

        File(fileName).readLines().forEach { line ->
            if (line.contains(",")) {
                val (x, y) = line.split(",")
                points.add(Point(x.toInt(), y.toInt()))
            }

            if (line.contains("fold along ")) {
                val (dir, value) = line.replace("fold along ", "").split("=")

                when (dir) {
                    "x" -> folds.add(Point(value.toInt(), 0))
                    "y" -> folds.add(Point(0, value.toInt()))
                }
            }
        }

        return Day13Input(points, folds)
    }

    fun part1(input: Day13Input): Int {
        val fold = input.folds.first()
        val points = input.points.map { it.reflect(fold) }

        return points.distinct().count()
    }

    fun part2(input: Day13Input): Int {
        var points = input.points
        
        for (fold in input.folds) {
            points = points.map { it.reflect(fold) }
        }
        
        printPoints(points)
        return 2
    }

    private fun printPoints(points: List<Point>) {
        val minX = points.minByOrNull { it.x }!!.x
        val maxX = points.maxByOrNull { it.x }!!.x
        val minY = points.minByOrNull { it.y }!!.y
        val maxY = points.maxByOrNull { it.y }!!.y

        for (y in minY..maxY) {
            for (x in minX..maxX) {
                if (points.contains(Point(x, y))) {
                    print("#")
                } else {
                    print(" ")
                }
            }
            println()
        }
    }

    /**
     * Reflect a point around the given fold line
     *
     * Check if x or y?
     *
     * If above fold, don't change, if below fold, change
     *
     *  If below fold,  calculate distance from fold.
     *    New coordinate is fold - that distance
     */
    private fun Point.reflect(fold: Point): Point {
        var x = this.x
        var y = this.y

        if (fold.x == 0) {
            y = if (fold.y < this.y) fold.y - (this.y - fold.y) else this.y
        }

        if (fold.y == 0) {
            x = if (fold.x < this.x) fold.x - (this.x - fold.x) else this.x
        }

        return Point(x, y)
    }

    data class Day13Input(
        val points: List<Point>,
        val folds: List<Point>
    )
}

