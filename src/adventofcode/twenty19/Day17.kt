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
    println("What is the sum of the alignment parameters for the scaffold intersections? ${day17.part1()}\n") //4408
    println("How much dust does the vacuum robot report it has collected? ${day17.part2()}")//862452
}

class Day17 {
    fun part1(): Int {
        val scaffold = VacuumRobot().observeScaffold() //4408
        val intersections = findIntersections(scaffold)
        return intersections.keys.sumBy { it.x * it.y }
    }

    /**
     * Did the steps manually and condensed it to
     *
     * R 8 L 12 R 8            A
     * R 12 L 8 R 10           B
     * R 12 L 8 R 10           B
     * R 8 L 12 R 8            A
     * R 8 L 8 L 8 R 8 R 10    C
     * R 8 L 12 R 8            A
     * R 8 L 12 R 8            A
     * R 8 L 8 L 8 R 8 R 10    C
     * R 12 L 8 R 10           B
     * R 8 L 8 L 8 R 8 R 10    C
     *
     * The sudo code
     *
     * 1) Get the next 2 moves, left/right and a distance
     * 2) Repeat until the end of the scaffold
     * 3) Check if any 2 items is repeated already
     *      a) Set n = 1
     *          a) Check if next n moves after (also check before) the repeated items are also repeated
     *          b) If so move all n repeated moves to 1st item
     *          c) Repeat 3b) until there is no case where this happens anymore (increase size of items to merge)
     *          d) Increase n and start 3a) again, repeat until there is only 3 repeated items left
     */
    fun part2(): Long {
        val robot = VacuumRobot()
        robot.wakeup()

        val input = "A,B,B,A,C,A,A,C,B,C\n" +
                "R,8,L,12,R,8\n" +
                "R,12,L,8,R,10\n" +
                "R,8,L,8,L,8,R,8,R,10\n" +
                "n\n"

        return robot.run(input)
    }

    private fun findIntersections(scaffold: Map<Point, Int>): Map<Point, Int> {
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
    var program: Array<Long> = IntcodeComputer().increaseMemorySize(File(DAY17_INPUT).readLines()[0]
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
            if (output != 46L && output != 10L) {
                scaffold[Point(x, y)] = output.toInt()
            }

            x++
            if (output == 10L) {
                x = 0
                y++
            }
        }

        return scaffold
    }

    fun wakeup() {
        val mutableList = program.toMutableList()
        mutableList[0] = 2
        program = mutableList.toTypedArray()
    }

    fun run(function: String): Long {
        val input = function.map { it.toLong() }.toTypedArray()

        var output = listOf<Long>()
        while (computer.running) {
            output = computer.executeList(program, input)
        }
        return output.last()
    }
}