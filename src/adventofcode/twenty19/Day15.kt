package adventofcode.twenty19

import java.awt.*
import java.io.File
import java.lang.RuntimeException
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.absoluteValue

/**
 * @author Martin Trollip ***REMOVED***
 * @since 2019/12/17 15:24
 */

private const val DAY15_INPUT = "src/res/twenty19/day15_input"

fun main(args: Array<String>) {
    val day15 = Day15()

    println("What is the fewest number of movement commands? ${day15.part1()}\n") //228
    println("How many minutes will it take to fill with oxygen? ${day15.part2()}")

}

class Day15 {
    fun part1(): Int {
        val droid = RepairDroid()

        val room = droid.run()

        return 1
    }

    fun part2(): Int {
        return 2
    }
}

//north (1), south (2), west (3), and east (4)
enum class DroidDirection {
    NORTH, SOUTH, WEST, EAST
}

class RepairDroid {
    val computer: IntcodeComputer = IntcodeComputer()
    var program: Array<Long>

    init {
        program = computer.increaseMemorySize(File(DAY15_INPUT).readLines()[0]
                .split(",")
                .map { it.toLong() }
                .toTypedArray())
    }

    fun run(): Map<Point, Int> {
        showWindow()
        val room = mutableMapOf<Point, Int>()

        var direction = DroidDirection.NORTH
        var currentPosition = Point(0, 0)

        while (computer.running) {
            direction = getBestDirection(direction, currentPosition, room)
            val state = computer.execute(program, getDirectionCommand(direction).toLong(), returnAfter = 1).first().toInt()
            val nextPosition = move(direction, currentPosition)
            room[nextPosition] = state

            when (state) {
                0 -> {
                    //The place droid wanted to move was a wall, position not changed

                }
                1 -> {
                    //Moved in the direction requested
                    currentPosition = nextPosition
                }
                2 -> {
                    //Found the oxygen, moved onto it
                    currentPosition = nextPosition
//                    computer.running = false
                }
                else -> {
                    throw RuntimeException("It broke")
                }
            }
            render(currentPosition, room)
        }
        return room
    }

    fun getDirectionCommand(direction: DroidDirection): Int {
        return direction.ordinal + 1
    }

    fun getClockwiseDirection(direction: DroidDirection): DroidDirection {
        return when (direction) {
            DroidDirection.NORTH -> DroidDirection.EAST
            DroidDirection.SOUTH -> DroidDirection.WEST
            DroidDirection.EAST -> DroidDirection.SOUTH
            DroidDirection.WEST -> DroidDirection.NORTH
        }
    }

    fun getBestDirection(direction: DroidDirection, currentPosition: Point, room: Map<Point, Int>): DroidDirection {
        //Move in same direction
        var currentDirection = direction

        //Prefer unknown area
        for (i in 0 until 3) {
            val newLocation = move(currentDirection, currentPosition)
            if (!room.contains(newLocation)) {
                //New area
                return currentDirection
            } else if (room[newLocation] == 0) {
                //Not wall
                currentDirection = getClockwiseDirection(currentDirection)
            } else {
                currentDirection = getClockwiseDirection(currentDirection)
            }
        }

        //Otherwise one without a wall
        for (i in 0 until 3) {
            val newLocation = move(currentDirection, currentPosition)
            if (room[newLocation] != 0) {
                return currentDirection
            } else {
                currentDirection = getClockwiseDirection(currentDirection)
            }
        }

        //Otherwise dead?
        return DroidDirection.values().toList().filter { it != direction }.shuffled().first()
//        return DroidDirection.NORTH
    }

    fun move(direction: DroidDirection, currentPosition: Point): Point {
        return when (direction) {
            DroidDirection.NORTH -> Point(currentPosition.x, currentPosition.y - 1)
            DroidDirection.SOUTH -> Point(currentPosition.x, currentPosition.y + 1)
            DroidDirection.EAST -> Point(currentPosition.x + 1, currentPosition.y)
            DroidDirection.WEST -> Point(currentPosition.x - 1, currentPosition.y)
        }
    }

    /**
     * 0: The repair droid hit a wall. Its position has not changed.
     * 1: The repair droid has moved one step in the requested direction.
     * 2: The repair droid has moved one step in the requested direction; its new position is the location of the oxygen system.
     */
    fun render(currentPosition: Point, map: Map<Point, Int>) {
        panel.screen = map
        panel.currentPosition = currentPosition
        panel.revalidate()
        panel.repaint()
        Thread.sleep(2)
    }

    val frame: JFrame = JFrame("Day15")
    var panel = MyPanel()

    fun showWindow() {
        frame.pack()
        frame.size = Dimension(1000, 1000)
        frame.isVisible = true
        frame.add(panel, BorderLayout.CENTER)
    }

    class MyPanel : JPanel() {
        var currentPosition = Point(0, 0)
        var screen: Map<Point, Int> = mapOf()

        override fun paintComponent(g: Graphics) {
            val maxX = screen.maxBy { it.key.x }!!.key.x
            val minX = screen.minBy { it.key.x }!!.key.x
            val maxY = screen.maxBy { it.key.y }!!.key.y
            val minY = screen.minBy { it.key.y }!!.key.y

            val width = 20
            val height = 20
            val g2 = g as Graphics2D
            g2.color = Color.WHITE
            g2.fillRect(0, 0, 1000, 1000)
            for (row in minY..maxY) {
                for (col in minX..maxX) {
                    val x = ((col + minX.absoluteValue) * 20)
                    val y = ((row + minY.absoluteValue) * 20)
                    when (screen[Point(col, row)]) {
                        0 -> {
                            //Wall
                            g2.color = Color.BLACK
                            g2.fillRect(x, y, width - 1, height - 1)
                        }
                        1 -> {
                            //Open
                            //Do nothing
                            g2.color = Color.GRAY
                            g2.fillRect(x, y, width - 1, height - 1)
                        }
                        2 -> {
                            //Oxygen
                            g2.color = Color.CYAN
                            g2.fillRect(x, y, width - 1, height - 1)
                        }
                    }

                    //Draw starting pos
                    if (col == 0 && row == 0) {
                        g2.color = Color.GREEN
                        g2.fillRect(x, y, width - 1, height - 1)
                    }

                    //Draw current pos
                    if (currentPosition.x == col && currentPosition.y == row) {
                        g2.color = Color.RED
                        g2.fillRect(x, y, width - 1, height - 1)
                    }
                }
            }
        }
    }
}