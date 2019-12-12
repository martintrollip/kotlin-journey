package adventofcode.twenty19

import java.awt.Point
import java.io.File

/**
 * @author Martin Trollip
 * @since 2019/12/11 16:04
 */
private const val DAY11_INPUT = "src/res/twenty19/day11_input"

fun main(args: Array<String>) {
    val day11 = Day11()

    println("How many panels does the robot paint at least once? ${day11.part1()}\n") //1564
    println("What registration identifier does it paint? ${day11.part2()}") //RFEPCFEB

}

class Day11 {
    var panels = mutableMapOf<Point, Int>()

    fun part1(): Int {
        val robot = Robot()
        return robot.run(panels).size - 1
    }

    fun part2(): Int {
        panels = mutableMapOf()
        panels[Point(0, 0)] = 1
        val robot = Robot()
        return robot.run(panels).size - 1
    }

    class Robot(var x: Int = 0, var y: Int = 0, var xDir: Int = 0, var yDir: Int = -1) {

        val computer: IntcodeComputer = IntcodeComputer()
        val program: Array<Long>

        init {
            program = computer.increaseMemorySize(File(DAY11_INPUT).readLines()[0]
                    .split(",")
                    .map { it.toLong() }
                    .toTypedArray())
        }

        fun run(panels: MutableMap<Point, Int>): Map<Point, Int> {
            while (computer.running) {
                val (paintColour, turn) = computer.execute(program, scanPanel(panels).toLong(), 2)
                paintPanel(panels, paintColour.toInt())
                turn(turn.toInt())
                move()
            }

            render(panels)
            return panels
        }

        fun render(panels: MutableMap<Point, Int>) {
            val maxX = panels.maxBy { it.key.x }!!.key.x
            val minX = panels.minBy { it.key.x }!!.key.x
            val maxY = panels.maxBy { it.key.y }!!.key.y
            val minY = panels.minBy { it.key.y }!!.key.y

            for (row in minY..maxY) {
                for (col in minX..maxX) {
                    if (panels[Point(col, row)] == 1) {
                        print("▓")
                    } else {
                        print("░")
                    }
                }
                println()
            }
        }

        fun paintPanel(panels: MutableMap<Point, Int>, paintColour: Int) {
            panels[Point(x, y)] = paintColour
        }

        /**
         * 0: means it should turn left 90 degrees,
         * 1: means it should turn right 90 degrees.
         */
        fun turn(direction: Int) {
            if (xDir == 0 && yDir == -1) {
                //Up
                yDir = 0
                xDir = if (direction == 0) {
                    -1
                } else {
                    1
                }
            } else if (xDir == 0 && yDir == 1) {
                //Down
                yDir = 0
                xDir = if (direction == 0) {
                    1
                } else {
                    -1
                }
            } else if (xDir == -1 && yDir == 0) {
                //Left
                xDir = 0
                yDir = if (direction == 0) {
                    1
                } else {
                    -1
                }
            } else if (xDir == 1 && yDir == 0) {
                //Right
                xDir = 0
                yDir = if (direction == 0) {
                    -1
                } else {
                    1
                }
            }
        }

        fun move() {
            x += xDir
            y += yDir
        }

        /**
         * 0: black
         * 1: white
         */
        fun scanPanel(panels: Map<Point, Int>): Int {
            return if (panels.keys.any { it.x == x && it.y == y }) {
                panels[Point(x, y)]!!
            } else {
                0
            }
        }
    }
}