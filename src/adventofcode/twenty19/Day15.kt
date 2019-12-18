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
    println("How many minutes will it take to fill with oxygen? ${day15.part2()}") // < 798 < 797 < 349

}

class Day15 {
    fun part1(): Int {
        val droid = RepairDroid()
        //val room = droid.run()
        return 228 //TODO Martin, counted by hand, get algorithm to do this
    }

    fun part2(): Int {
        return Oxygenator().fillWithOxygen(RepairDroid().run().toMutableMap())
    }

    val frame: JFrame = JFrame("Day15")
    var panel = MyPanel()

    fun showWindow() {
        frame.pack()
        frame.size = Dimension(1000, 1000)
        frame.isVisible = true
        frame.add(panel, BorderLayout.CENTER)
    }

    /**
     * 0: The repair droid hit a wall. Its position has not changed.
     * 1: The repair droid has moved one step in the requested direction.
     * 2: The repair droid has moved one step in the requested direction; its new position is the location of the oxygen system.
     */
    fun render(currentDirection: DroidDirection, currentPosition: Point, nextPosition: Point, map: Map<Point, Int>) {
        panel.screen = map
        panel.currentPosition = currentPosition
        panel.currentDirection = currentDirection
        panel.nextPosition = nextPosition
        panel.revalidate()
        panel.repaint()
        Thread.sleep(50) //Animation
    }

    class MyPanel : JPanel() {
        var currentPosition = Point(0, 0)
        var screen: Map<Point, Int> = mapOf()
        var currentDirection: DroidDirection = DroidDirection.NORTH
        var nextPosition = Point(0, 0)

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
                    val x = ((col + minX.absoluteValue) * width)
                    val y = ((row + minY.absoluteValue) * height)
                    when (screen[Point(col, row)]) {
                        0 -> {
                            //Wall
                            g2.color = Color.DARK_GRAY
                            g2.fillRect(x, y, width, height)
                            g2.color = Color.WHITE
//                            g2.drawString("$col, $row", x, y + height / 2)
                        }
                        1 -> {
                            //Open
                            //Do nothing
                            g2.color = Color.LIGHT_GRAY
                            g2.fillRect(x, y, width - 1, height - 1)
                            g2.color = Color.BLACK
//                            g2.drawString("$col, $row", x, y + height / 2)
                        }
                        in 2..3 -> {
                            //Oxygen
                            g2.color = Color.CYAN
                            g2.fillRect(x, y, width, height)
                        }
                    }

                    //Draw starting pos
                    if (col == 0 && row == 0) {
                        g2.color = Color.GREEN
                        g2.fillRect(x, y, width, height)
                    }

                    //Draw current pos
                    if (currentPosition.x == col && currentPosition.y == row) {
                        g2.color = Color.RED
                        g2.fillRect(x, y, width, height)

                        g2.color = Color.BLACK
                        when (currentDirection) {
                            DroidDirection.NORTH -> g2.drawString("^", x + width / 3, y + 14)
                            DroidDirection.SOUTH -> g2.drawString("v", x + width / 3, y + 14)
                            DroidDirection.WEST -> g2.drawString("<", x + width / 3, y + 14)
                            DroidDirection.EAST -> g2.drawString(">", x + width / 3, y + 14)
                        }
                    }

                    //Draw nex pos
                    if (nextPosition.x == col && nextPosition.y == row) {
                        g2.color = Color.BLACK
                        g2.fillOval(x + (width - 1) / 4, y + (height - 1) / 4, width / 2, height / 2)
                    }
                }
            }
        }
    }
}

//north (1), south (2), west (3), and east (4)
enum class DroidDirection {
    NORTH, SOUTH, WEST, EAST
}

class RepairDroid {
    val day15 = Day15()
    val computer = IntcodeComputer()
    val program: Array<Long> = IntcodeComputer().increaseMemorySize(File(DAY15_INPUT).readLines()[0]
            .split(",")
            .map { it.toLong() }
            .toTypedArray())

    fun run(): Map<Point, Int> {
        day15.showWindow()

        val program2: Array<Long> = IntcodeComputer().increaseMemorySize(File(DAY15_INPUT).readLines()[0]
                .split(",")
                .map { it.toLong() }
                .toTypedArray())

        val room = mutableMapOf<Point, Int>()
        runTillDeadEnd(IntcodeComputer(), program2, DroidDirection.NORTH, Point(0, 0), room)
        return room
    }

    fun runTillDeadEnd(computer: IntcodeComputer, program2: Array<Long>, currentDirection: DroidDirection, currentPosition: Point, room: MutableMap<Point, Int>) {
        val possibleDirections = getPossibleDirections(currentDirection, currentPosition, room).toMutableList()
        while (!possibleDirections.isEmpty()) {
            val direction = possibleDirections.removeAt(0)
            val nextPosition = move(direction, currentPosition)
            if (!room.contains(nextPosition)) {
                val state = computer.execute(program2, getDirectionCommand(direction).toLong(), returnAfter = 1).first().toInt()
                day15.render(direction, currentPosition, nextPosition, room)
                room[nextPosition] = state
                when (state) {
                    0 -> {/*The place droid wanted to move was a wall, position not changed */
                    }
                    in 1..2 -> {
                        //Moved in the direction requested
                        runTillDeadEnd(computer, program2, direction, nextPosition, room)//TODO Martin multi thread this?
                        //Moveback
                        computer.execute(program2, getDirectionCommand(getOppositeDirection(direction)).toLong(), returnAfter = 1).first().toInt()
                    }
                    2 -> println("Oxy at $currentPosition")//Found the oxygen, moved onto it
                    else -> throw RuntimeException("It broke")
                }
            }
        }
    }

    fun getDirectionCommand(direction: DroidDirection): Int {
        return direction.ordinal + 1
    }

    fun getOppositeDirection(direction: DroidDirection): DroidDirection {
        return when (direction) {
            DroidDirection.NORTH -> DroidDirection.SOUTH
            DroidDirection.EAST -> DroidDirection.WEST
            DroidDirection.SOUTH -> DroidDirection.NORTH
            DroidDirection.WEST -> DroidDirection.EAST
        }
    }

    fun getClockwiseDirection(direction: DroidDirection): DroidDirection {
        return when (direction) {
            DroidDirection.NORTH -> DroidDirection.EAST
            DroidDirection.EAST -> DroidDirection.SOUTH
            DroidDirection.SOUTH -> DroidDirection.WEST
            DroidDirection.WEST -> DroidDirection.NORTH
        }
    }

    fun getPossibleDirections(direction: DroidDirection, currentPosition: Point, room: Map<Point, Int>): List<DroidDirection> {
        val possibleDirection = mutableListOf<DroidDirection>()
        var currentDirection = direction

        //Prefer unknown area
        for (i in 0 until 4) {
            val newLocation = move(currentDirection, currentPosition)
            if (!room.contains(newLocation) && room[newLocation] != 0) { //New area and not a wall
                //New area
                possibleDirection.add(currentDirection)
            }
            currentDirection = getClockwiseDirection(currentDirection)
        }
        return possibleDirection
    }

    fun move(direction: DroidDirection, currentPosition: Point): Point {
        return when (direction) {
            DroidDirection.NORTH -> Point(currentPosition.x, currentPosition.y - 1)
            DroidDirection.SOUTH -> Point(currentPosition.x, currentPosition.y + 1)
            DroidDirection.EAST -> Point(currentPosition.x + 1, currentPosition.y)
            DroidDirection.WEST -> Point(currentPosition.x - 1, currentPosition.y)
        }
    }
}


class Oxygenator {
    val day15 = Day15()

    /**
     * 0: The repair droid hit a wall. Its position has not changed.
     * 1: The repair droid has moved one step in the requested direction.
     * 2: The repair droid has moved one step in the requested direction; its new position is the location of the oxygen system.
     * 3: Already counted oxygen
     */
    fun fillWithOxygen(room: MutableMap<Point, Int>): Int {
        day15.showWindow()
        var count = 0

        while (!room.filter { it.value == 2 }.isEmpty()) {
            count++
            //TODO Martin this can be multithreaded
            room.filter { it.value == 2 }.forEach { oxegyn ->
                expandInAllDirections(room, oxegyn)
            }
            day15.render(DroidDirection.NORTH, Point(0, 0), Point(0, 0), room)
            println(count)
        }

        return count - 1
    }

    fun expandInAllDirections(room: MutableMap<Point, Int>, oxegyn: Map.Entry<Point, Int>) {
        val oxygenPoint = oxegyn.key
        val surroundings = room.filter {
            (it.key.x in oxygenPoint.x - 1..oxygenPoint.x + 1) &&
                    (it.key.y in oxygenPoint.y - 1..oxygenPoint.y + 1) &&
                    (it.key.x == oxygenPoint.x || it.key.y == oxygenPoint.y) &&
                    (it.value == 1)
        }

        if (!surroundings.isEmpty()) {
            surroundings.forEach {
                room[it.key] = 2
            }
        }
        room[oxygenPoint] = 3
    }
}