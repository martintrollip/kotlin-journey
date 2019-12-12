package adventofcode.twenty19

import org.junit.Assert.assertEquals
import org.junit.Test
import java.awt.Point

/**
 * @author Martin Trollip
 * @since 2019/12/11 16:05
 */

class Day11Test {

    /*
    *   +-> +x
    *   |
    *   V
    *   +y
    *
    * */


    @Test
    fun testRobotScanner() {
        val panels = mutableMapOf<Point, Int>()
        assertEquals(0, Day11.Robot().scanPanel(panels))
        panels[Point(0, 0)] = 1
        assertEquals(1, Day11.Robot().scanPanel(panels))
    }

    @Test
    fun testPaintPanels() {
        val panels = mutableMapOf<Point, Int>()
        val robot = Day11.Robot()

        assertEquals(0, panels.getOrDefault(Point(0, 0), 0))
        robot.paintPanel(panels, 1)
        assertEquals(1, panels.getOrDefault(Point(0, 0), 0))
        robot.paintPanel(panels, 0)
        assertEquals(0, panels.getOrDefault(Point(0, 0), 0))
    }

    /**
     * 0: means it should turn left 90 degrees,
     * 1: means it should turn right 90 degrees.
     */
    @Test
    fun testTurn() {
        val robot = Day11.Robot()
        assertFacingUp(robot)

        //Facing Up
        val robotUp1 = robotFacingUp()
        robotUp1.turn(0)
        assertFacingLeft(robotUp1)

        val robotUp2 = robotFacingUp()
        robotUp2.turn(1)
        assertFacingRight(robotUp2)

        //Facing Left
        val robotLeft1 = robotFacingLeft()
        robotLeft1.turn(0)
        assertFacingDown(robotLeft1)

        val robotLeft2 = robotFacingLeft()
        robotLeft2.turn(1)
        assertFacingUp(robotLeft2)

        //Facing Right
        val robotRight1 = robotFacingRight()
        robotRight1.turn(0)
        assertFacingUp(robotRight1)

        val robotRight2 = robotFacingRight()
        robotRight2.turn(1)
        assertFacingDown(robotRight2)

        //Facing Down
        val robotDown1 = robotFacingDown()
        robotDown1.turn(0)
        assertFacingRight(robotDown1)

        val robotDown2 = robotFacingDown()
        robotDown2.turn(1)
        assertFacingLeft(robotDown2)
    }

    private fun robotFacingUp(): Day11.Robot {
        return Day11.Robot(xDir = 0, yDir = -1)
    }

    private fun assertFacingUp(robot: Day11.Robot) {
        assertEquals(0, robot.xDir)
        assertEquals(-1, robot.yDir)
    }

    private fun robotFacingLeft(): Day11.Robot {
        return Day11.Robot(xDir = -1, yDir = 0)
    }

    private fun assertFacingLeft(robot: Day11.Robot) {
        assertEquals(-1, robot.xDir)
        assertEquals(0, robot.yDir)
    }

    private fun robotFacingRight(): Day11.Robot {
        return Day11.Robot(xDir = 1, yDir = 0)
    }

    private fun assertFacingRight(robot: Day11.Robot) {
        assertEquals(1, robot.xDir)
        assertEquals(0, robot.yDir)
    }

    private fun robotFacingDown(): Day11.Robot {
        return Day11.Robot(xDir = 0, yDir = 1)
    }

    private fun assertFacingDown(robot: Day11.Robot) {
        assertEquals(0, robot.xDir)
        assertEquals(1, robot.yDir)
    }

    @Test
    fun testRunRobot() {
        var panels = mutableMapOf<Point, Int>()
        assertEquals(1564, Day11.Robot().run(panels).size - 1)

        println()
        panels = mutableMapOf()
        panels[Point(0, 0)] = 1
        val robot2 = Day11.Robot()
         robot2.run(panels).size - 1 //RFEPCFEB
    }
}