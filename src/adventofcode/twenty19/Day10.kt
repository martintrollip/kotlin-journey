package adventofcode.twenty19

import java.io.File
import kotlin.math.atan2

/**
 * @author Martin Trollip
 * @since 2019/12/10 16:05
 */
private const val DAY10_INPUT = "src/res/twenty19/day10_input"

fun main(args: Array<String>) {
    val day10 = Day10()

    println("How many other asteroids can be detected from that location? ${day10.part1()}\n")  //292
    println("Which will be the 200th asteroid? Multiply its X coordinate by 100 and then add its Y coordinate? ${day10.part2()}") //317

}

class Day10 {

    fun buildList(lines: List<String>): List<Asteroid> {
        val asteroids = mutableListOf<Asteroid>()
        lines.forEachIndexed { y, row ->
            row.forEachIndexed { x, col ->
                if (col == '#') {
                    asteroids.add(Asteroid(x.toDouble(), y.toDouble()))
                }
            }
        }
        return asteroids
    }

    fun part1(): Int {
        return getMaxVisibility(File(DAY10_INPUT).readLines()).visible
    }

    data class MaxAsteroid(val asteroid: Asteroid, val visible: Int)

    fun getMaxVisibility(list: List<String>): MaxAsteroid {
        val asteroids = buildList(list)

        var maxCount = 0
        var maxAsteroid = Asteroid()
        asteroids.forEach { me ->
            val size = asteroids.filter { me != it }.groupBy { me.gradient(it) }.size
            if (size > maxCount) {
                maxCount = size
                maxAsteroid = me
            }
        }

        return MaxAsteroid(maxAsteroid, maxCount)
    }

    fun part2(): Int {
        val vaporisedAsteroid = vaporiseAsteroids(File(DAY10_INPUT).readLines())
        return ((vaporisedAsteroid.x * 100) + vaporisedAsteroid.y).toInt()
    }

    fun vaporiseAsteroids(list: List<String>): Asteroid {
        val asteroids = buildList(list)
        val station = getMaxVisibility(list).asteroid
        var asteroid200 = Asteroid()

        var groups = asteroids.filter { station != it }
                .groupBy { station.gradient(it) }
                .map { mapEntry ->
                    mapEntry.key to mapEntry.value.sortedBy { it.distance(station) } //Create a Pair<Angle, List<Sored by distance>>
                }
                .sortedByDescending { it.first }

        var count = 0
        while (count < groups.size && groups.any { it.second.isNotEmpty() }) {
            val laserLineOfSight = groups[count % groups.size].second

            if (laserLineOfSight.isNotEmpty()) {
                count++
                val asteroid = laserLineOfSight.toMutableList().removeAt(0)
                println("The $count removed asteroid is at (${asteroid.x}, ${asteroid.y})")

                if (count == 200) {
                    asteroid200 = asteroid
                }
            }
        }

        return asteroid200
    }

    data class Asteroid(val x: Double = -1.0, val y: Double = -1.0) {

        fun gradient(other: Asteroid): Double {
            return atan2((other.x - x), (other.y - y)) * 180 / Math.PI
        }

        fun distance(other: Asteroid): Double {
            return Math.sqrt(Math.pow(other.x - x, 2.0) + Math.pow(other.y - y, 2.0))
        }
    }
}