package adventofcode.twenty19

import java.io.File
import kotlin.math.absoluteValue

/**
 * @author Martin Trollip
 * @since 2019/12/12 16:42
 */
private const val DAY12_INPUT = "src/res/twenty19/day12_input"
private val BODIES_REGEX = "<x=(.+), y=(.+), z=(.+)>".toRegex()


fun main(args: Array<String>) {
    val day12 = Day12()

    println("What is the total energy in the system? ${day12.part1(1000)}\n")//8625
    println("How many steps does it take to reach the first state that exactly matches a previous state? ${day12.part2()}")

}

class Day12 {
    fun part1(runs: Int): Int {
        val bodies = readBodies(File(DAY12_INPUT).readLines())
        for (i in 0 until runs) {
            simulate(bodies)
        }
        return bodies.totalEnery()
    }

    fun part2(): Int {
        val bodies = readBodies(File(DAY12_INPUT).readLines())
        return findRepeated(bodies)
    }

    fun findRepeated(bodies: List<Body>): Int {
        val states = mutableMapOf<Int, String>()
        var step = 0

        while (!states.contains(bodies.totalEnery())) {
            step++
            states[bodies.totalEnery()] = bodies.toString()
            simulate(bodies)
        }

        return step
    }

    fun readBodies(input: List<String>): List<Body> {
        val bodies = mutableListOf<Body>()
        val names = listOf("A", "B", "C", "D")

        input.forEachIndexed { index, it ->
            val (x, y, z) = BODIES_REGEX.find(it)!!.destructured
            bodies.add(Body(names[index], x.toInt(), y.toInt(), z.toInt()))
        }

        return bodies
    }

    fun simulate(bodies: List<Body>): List<Body> {
        bodies.forEach { i ->
            bodies.forEach { j ->
                if (i != j) {
                    i.applyGravitationalPull(j)
                }
            }
        }

        bodies.forEach { it.applyVelocity() }

        return bodies
    }
}

data class Body(val name: String = "", var x: Int, var y: Int, var z: Int, var dx: Int = 0, var dy: Int = 0, var dz: Int = 0) {

    fun applyGravitationalPull(other: Body) {
        dx += gravity(x, other.x)
        dy += gravity(y, other.y)
        dz += gravity(z, other.z)
    }

    private fun gravity(a: Int, b: Int): Int {
        return b.compareTo(a)
    }

    fun applyVelocity() {
        x += dx
        y += dy
        z += dz
    }
}

fun List<Body>.totalEnery(): Int {
    return this.sumBy {
        val potential = it.x.absoluteValue + it.y.absoluteValue + it.z.absoluteValue
        val kinetic = it.dx.absoluteValue + it.dy.absoluteValue + it.dz.absoluteValue
        potential * kinetic
    }
}