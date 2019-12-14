package adventofcode.twenty19

import java.io.File
import java.lang.RuntimeException
import javax.swing.JFrame
import java.awt.*
import java.awt.geom.Rectangle2D
import javax.swing.JPanel


/**
 * @author Martin Trollip
 * @since 2019/12/14 09:43
 */
private const val DAY13_INPUT = "src/res/twenty19/day13_input"

fun main(args: Array<String>) {
    val day13 = Day13()

    println("How many block tiles are on the screen when the game exits? ${day13.part1()}\n")//280
    println("Final score: ${day13.part2()}")

}

class Day13 {
    fun part1(): Int {
        val arcade = Arcade()
        val run = arcade.run()
        return run.filter { it.value == 2 }.size
    }

    fun part2(): Int {
        val arcade = Arcade()
        return arcade.step()
    }
}

class Arcade {
    val computer: IntcodeComputer = IntcodeComputer()
    var program: Array<Long>
    val player: Player
    val frame: JFrame = JFrame("Day13")
    var panel = MyPanel()

    class MyPanel : JPanel() {
        var score: Int = 0
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
            g2.fillRect(0, 0, 45 * 20, 25 * 20)
            g2.color = Color.BLACK
            g2.drawString(score.toString(), 780, 50)
            for (row in minY..maxY) {
                var count = 0
                for (col in minX..maxX) {
                    val x = (col * 20)
                    val y = (row * 20)
                    when (screen[Point(col, row)]) {
                        0 -> {
                            //Empty
                            count++
                        }
                        1 -> {
                            //Wall
                            g2.color = Color.BLACK
                            g2.fillRect(x, y, width - 1, height - 1)
                        }
                        2 -> {
                            //Block
                            g2.color = if (row % 2 == 0) {
                                Color.RED
                            } else {
                                Color.ORANGE
                            }

                            g2.fillRect(x, y, width - 1, height - 1)
                        }
                        3 -> {
                            //Paddle
                            g2.color = Color.BLACK
                            g2.fillRect(x, y, width - 1, height / 3)
                        }
                        4 -> {
                            //Ball
                            g2.color = Color.BLACK
                            g2.fillOval(x, y, width, height)
                        }
                    }
                }
                if (count > 0) {
                    println()
                }
            }
        }
    }

    init {
        player = Player()
        program = computer.increaseMemorySize(File(DAY13_INPUT).readLines()[0]
                .split(",")
                .map { it.toLong() }
                .toTypedArray())
    }

    fun run(): Map<Point, Int> {
        val screen = mutableMapOf<Point, Int>()
        while (computer.running) {
            val (x, y, tileId) = computer.execute(program, returnAfter = 3)
            screen[Point(x.toInt(), y.toInt())] = tileId.toInt()
        }
        //render(screen)
        return screen
    }

    fun step(): Int {
        showWindow()
        var score = 0
        val screen = mutableMapOf<Point, Int>()

        do {
            program[0] = 2
            while (computer.running) {
                val (x, y, tileId) = computer.executeBlocking(program, returnAfter = 3, player = player, manual = false)

                if (x.toInt() == -1 && y.toInt() == 0 && tileId.toInt() != 0) {
                    score = tileId.toInt()
                }

                if (tileId.toInt() == 4) {
                    player.ball = Point(x.toInt(), y.toInt())
                }

                if (tileId.toInt() == 3) {
                    player.paddle = Point(x.toInt(), y.toInt())
                }

                screen[Point(x.toInt(), y.toInt())] = tileId.toInt()
                render(screen, score)
            }

        } while (screen.filter { it.value == 2 }.isNotEmpty() && computer.running)

        return score
    }

    fun showWindow() {
        frame.pack()
        frame.size = Dimension(45 * 20, 25 * 20) //37 * 19 is max
        frame.isVisible = true
        frame.add(panel, BorderLayout.CENTER)
    }


    /**
     * 0 is an empty tile. No game object appears in this tile.
     * 1 is a wall tile. Walls are indestructible barriers.
     * 2 is a block tile. Blocks can be broken by the ball.
     * 3 is a horizontal paddle tile. The paddle is indestructible.
     * 4 is a ball tile. The ball moves diagonally and bounces off objects.
     */
    fun render(screen: MutableMap<Point, Int>, score: Int) {
        panel.screen = screen
        panel.score = score
        panel.revalidate()
        panel.repaint()
        Thread.sleep(10)

        //For console output
      /*  print("\u001b[H\u001b[2J")
        val maxX = screen.maxBy { it.key.x }!!.key.x
        val minX = screen.minBy { it.key.x }!!.key.x
        val maxY = screen.maxBy { it.key.y }!!.key.y
        val minY = screen.minBy { it.key.y }!!.key.y

        println(score)
        for (row in minY..maxY) {
            var count = 0
            for (col in minX..maxX) {
                when (screen[Point(col, row)]) {
                    0 -> {
                        print(" "); count++
                    }
                    1 -> {
                        print("█"); count++
                    }
                    2 -> {
                        print("▒"); count++
                    }
                    3 -> {
                        print("-"); count++
                    }
                    4 -> {
                        print("•"); count++
                    }
                }
            }
            if (count > 0) {
                println()
            }
        }*/
    }

    class Player {

        var count = 0
        //        var previousMove = 1
        var ball: Point? = null
        var paddle: Point? = null

        fun play(): Int {

            if (count < 1) {
                count++
                return 1
            }

            print("Playing: ball=${ball!!.x} paddle=${paddle!!.x} ")
            if (ball == null || paddle == null) {
                return 0
            }

            if (ball!!.x == paddle!!.x) {
                return 0
            }
            val move = ball!!.x.compareTo(paddle!!.x)

            when (move) {
                -1 -> println("Left")
                1 -> println("Right")
                0 -> println("None")
                else -> throw RuntimeException("Unknown move")
            }
            return move
        }
    }
}