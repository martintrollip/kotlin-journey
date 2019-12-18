package adventofcode.twenty19

import org.junit.Assert.*
import org.junit.Test
import java.awt.Point

/**
 * @author Martin Trollip
 * @since 2019/12/17 15:48
 */
class Day15Test {

    /**
     * Only four movement commands are understood: north (1), south (2), west (3), and east (4). Any other command is invalid.
     */
    @Test
    fun testOrdinalDirection() {
        val repairDroid = RepairDroid()

        assertEquals(1, repairDroid.getDirectionCommand(DroidDirection.NORTH))
        assertEquals(2, repairDroid.getDirectionCommand(DroidDirection.SOUTH))
        assertEquals(3, repairDroid.getDirectionCommand(DroidDirection.WEST))
        assertEquals(4, repairDroid.getDirectionCommand(DroidDirection.EAST))
    }

    @Test
    fun testNextDirection() {
        val repairDroid = RepairDroid()

        assertEquals(DroidDirection.EAST, repairDroid.getClockwiseDirection(DroidDirection.NORTH))
        assertEquals(DroidDirection.WEST, repairDroid.getClockwiseDirection(DroidDirection.SOUTH))
        assertEquals(DroidDirection.NORTH, repairDroid.getClockwiseDirection(DroidDirection.WEST))
        assertEquals(DroidDirection.SOUTH, repairDroid.getClockwiseDirection(DroidDirection.EAST))
    }

    @Test
    fun testMove() {
        val repairDroid = RepairDroid()
        val point = Point(0, 0)

        assertEquals(Point(0, -1), repairDroid.move(DroidDirection.NORTH, point))
        assertEquals(Point(0, 1), repairDroid.move(DroidDirection.SOUTH, point))
        assertEquals(Point(-1, 0), repairDroid.move(DroidDirection.WEST, point))
        assertEquals(Point(1, 0), repairDroid.move(DroidDirection.EAST, point))
    }

    @Test
    fun testPossibleDirections() {
        val repairDroid = RepairDroid()
        val point = Point(0, 0)

        assertEquals(4, repairDroid.getPossibleDirections(DroidDirection.NORTH, point, mapOf()).size)
    }
}