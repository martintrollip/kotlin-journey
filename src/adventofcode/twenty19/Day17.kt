package adventofcode.twenty19

import java.awt.Point
import java.io.File

/**
 * @author Martin Trollip
 * @since 2019/12/23 21:15
 */

private const val DAY17_INPUT = "src/res/twenty19/day17_input"

fun main(args: Array<String>) {
    val day17 = Day17()
    println("What is the sum of the alignment parameters for the scaffold intersections? ${day17.part1()}\n")
    println("part2 ${day17.part2()}")
}

class Day17 {
    fun part1(): Int {
        val scaffold = VacuumRobot().observeScaffold()
        val intersections = findIntersections(scaffold)
        return intersections.keys.sumBy { it.x * it.y }
    }

    fun part2(): Int {
        return 2
    }

    fun findIntersections(scaffold: Map<Point, Int>): Map<Point, Int> {
        return scaffold.filter {
            it.value == 35
        }.filter {
            scaffold.containsKey(Point(it.key.x - 1, it.key.y)) && //point to the left
            scaffold.containsKey(Point(it.key.x + 1, it.key.y)) && //point to the right
            scaffold.containsKey(Point(it.key.x, it.key.y - 1)) && // point above
            scaffold.containsKey(Point(it.key.x, it.key.y + 1))    //point below
        }
    }
}

class VacuumRobot {
    val computer = IntcodeComputer()
    val program: Array<Long> = IntcodeComputer().increaseMemorySize(File(DAY17_INPUT).readLines()[0]
            .split(",")
            .map { it.toLong() }
            .toTypedArray())

    fun observeScaffold(): MutableMap<Point, Int> {
        val scaffold = mutableMapOf<Point, Int>()
        var x = 0
        var y = 0
        while (computer.running) {
            val output = computer.execute(program, returnAfter = 1)[0]
            print(output.toChar())

            if(output != 46L && output != 10L) {
                scaffold[Point(x, y)] = output.toInt()
            }

            x++
            if(output == 10L) {
                x = 0
                y++
            }
        }

        return scaffold
    }
}