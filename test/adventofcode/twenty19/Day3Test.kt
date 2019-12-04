package adventofcode.twenty19

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

/**
 * @see https://regex101.com/r/fXQWcU/3/
 *
 * @author Martin Trollip
 * @since 2019/12/03 06:37
 */
class Day3Test {
    private val DAY2_INPUT_SMALL = "src/res/twenty19/day3_input_small"

    private var day3: Day3 = Day3(DAY2_INPUT_SMALL)

    @Before
    fun before() {
        day3 = Day3(DAY2_INPUT_SMALL)
    }


    /**
     * R75,D30,R83,L1,U101
     *
     * +------> x
     * |
     * |
     * v
     * y
     *
     */
    @Test
    fun testCreateLine() {
        val start = Coordinate(0, 0)
        val line1 = day3.createLine("R75", start)
        val line2 = day3.createLine("D30", line1.end)
        val line3 = day3.createLine("R83", line2.end)
        val line4 = day3.createLine("L1", line3.end)
        val line5 = day3.createLine("U101", line4.end)

        assertEquals(0, line1.start.x)
        assertEquals(0, line1.start.y)
        assertEquals(75, line1.end.x)
        assertEquals(0, line1.end.y)

        assertEquals(75, line2.start.x)
        assertEquals(0, line2.start.y)
        assertEquals(75, line2.end.x)
        assertEquals(30, line2.end.y)

        assertEquals(75, line3.start.x)
        assertEquals(30, line3.start.y)
        assertEquals(158, line3.end.x)
        assertEquals(30, line3.end.y)

        assertEquals(158, line4.start.x)
        assertEquals(30, line4.start.y)
        assertEquals(157, line4.end.x)
        assertEquals(30, line4.end.y)

        assertEquals(157, line5.start.x)
        assertEquals(30, line5.start.y)
        assertEquals(157, line5.end.x)
        assertEquals(-71, line5.end.y)
    }

    /**
     * R75,D30,R83,L1,U101
     */
    @Test
    fun testCreateWire() {
        val wire = day3.createWire("R75,D30,R83,L1,U101")

        assertEquals(5, wire.size)

        assertEquals(0, wire[0].start.x)
        assertEquals(0, wire[0].start.y)
        assertEquals(75, wire[0].end.x)
        assertEquals(0, wire[0].end.y)

        assertEquals(157, wire[4].start.x)
        assertEquals(30, wire[4].start.y)
        assertEquals(157, wire[4].end.x)
        assertEquals(-71, wire[4].end.y)
    }

    @Test
    fun testIntersectionCalculations() {
        val line1 = Line(Coordinate(0, 10), Coordinate(5, 10))
        val line2 = Line(Coordinate(3, 5), Coordinate(3, 16))
        val line3 = Line(Coordinate(0, 15), Coordinate(5, 15))

        val intersection = line1.intersection(line2)
        val intersection2 = line2.intersection(line3)
        val intersection3 = line3.intersection(line1)

        assertEquals(3, intersection!!.x)
        assertEquals(10, intersection.y)
        assertEquals(3, intersection2!!.x)
        assertEquals(15, intersection2.y)
        assertEquals(null, intersection3)
    }

    @Test
    fun moreIntersectionTests() {
        val lineA = Line(Coordinate(8, -5), Coordinate(3, -5))
        val lineB = Line(Coordinate(6, -7), Coordinate(6, -3))

        val intersection = lineA.intersection(lineB)

        assertNotNull(intersection)
        assertEquals(6, intersection!!.x)
        assertEquals(-5, intersection.y)
    }

    @Test
    fun testManhattanDistance() {
        val start = Coordinate(0, 0)
        val coordinate1 = Coordinate(3, 3)
        val coordinate2 = Coordinate(-3, -3)
        val coordinate3 = Coordinate(3, -3)
        val coordinate4 = Coordinate(-3, 3)

        assertEquals(6, day3.manhattanDistance(start, coordinate1))
        assertEquals(6, day3.manhattanDistance(start, coordinate2))
        assertEquals(6, day3.manhattanDistance(start, coordinate3))
        assertEquals(6, day3.manhattanDistance(start, coordinate4))
    }

    /**
     * Here are a few more examples:
     *
     * R75,D30,R83,U83,L12,D49,R71,U7,L72
     * U62,R66,U55,R34,D71,R55,D58,R83 = distance 159
     *
     * */
    @Test
    fun testPart1() {
        val distance = day3.part1()
        assertEquals(159, distance)
    }

    @Test
    fun testLength() {
        val line1 = Line(Coordinate(0, 10), Coordinate(5, 10))
        val line2 = Line(Coordinate(3, 5), Coordinate(3, 16))
        val line3 = Line(Coordinate(0, 15), Coordinate(5, 15))

        assertEquals(5, line1.length())
        assertEquals(11, line2.length())
        assertEquals(5, line3.length())
    }

    /**
     * R75,D30,R83,U83,L12,D49,R71,U7,L72
     * U62,R66,U55,R34,D71,R55,D58,R83
     *
     * = 610 steps
     *
     * The simple case does work because the wire does not cross itself.
     */
    @Test
    fun testSteps() {
        val distance = day3.part2NotWorking()
        assertEquals(610, distance)
    }

    @Test
    fun testLenthTo() {
        val line1 = Line(Coordinate(0, 0), Coordinate(0, 10))

        assertEquals(0, line1.lengthTo(Coordinate(0, 0)))
        assertEquals(1, line1.lengthTo(Coordinate(0, 1)))
        assertEquals(5, line1.lengthTo(Coordinate(0, 5)))
        assertEquals(9, line1.lengthTo(Coordinate(0, 9)))
        assertEquals(10, line1.lengthTo(Coordinate(0, 10)))

        val line2 = Line(Coordinate(0, 0), Coordinate(10, 0))
        assertEquals(0, line2.lengthTo(Coordinate(0, 0)))
        assertEquals(1, line2.lengthTo(Coordinate(1, 0)))
        assertEquals(5, line2.lengthTo(Coordinate(5, 0)))
        assertEquals(9, line2.lengthTo(Coordinate(9, 0)))
        assertEquals(10, line2.lengthTo(Coordinate(10, 0)))
    }

    /**
     * R75,D30,R83,U83,L12,D49,R71,U7,L72
     * U62,R66,U55,R34,D71,R55,D58,R83
     *
     * = 610 steps
     *
     * Attempt 2 here
     */
    @Test
    fun testPart2() {
        val distance = day3.part2()
        assertEquals(610, distance)
    }
}