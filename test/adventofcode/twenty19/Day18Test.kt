package adventofcode.twenty19

import org.junit.Assert.assertEquals
import org.junit.Test
import java.awt.Point

/**
 * @author Martin Trollip
 * @since 2019/12/29 10:56
 */

class Day18Test {
    /**
     * #########
     * #b.A.@.a#
     * #########
     */
    @Test
    fun testReadInputSmall() {
        val readInput = Day18().readInput(getSmallInput())

        assertEquals(7, readInput.size)
        assertEquals('b', readInput[Point(1, 1)])
        assertEquals('.', readInput[Point(2, 1)])
        assertEquals('A', readInput[Point(3, 1)])
        assertEquals('.', readInput[Point(4, 1)])
        assertEquals('@', readInput[Point(5, 1)])
        assertEquals('.', readInput[Point(6, 1)])
        assertEquals('a', readInput[Point(7, 1)])
    }

    /**
     * ########################
     * #f.D.E.e.C.b.A.@.a.B.c.#
     * ######################.#
     * #d.....................#
     * ########################
     */
    @Test
    fun testReadMediumLarge() {
        val readInput = Day18().readInput(getMediumInput())

        assertEquals(45, readInput.size)
        assertEquals('f',readInput[Point(1,1)])
        assertEquals('c',readInput[Point(21,1)])
        assertEquals('.',readInput[Point(22,2)])
        assertEquals('d',readInput[Point(1,3)])
    }

    /**
     * For example, suppose you have the following map:
     * #########
     * #b.A.@.a#
     * #########
     *
     * Starting from the entrance (@), you can only access a large door (A) and a key (a). Moving toward the door doesn't help you, but you can move 2 steps to collect the key, unlocking A in the process:
     * #########
     * #b.....@#
     * #########
     *
     * Then, you can move 6 steps to collect the only other key, b:
     * #########
     * #@......#
     * #########
     *
     * So, collecting every key took a total of 8 steps.
     *
     */
    @Test
    fun testSmallExample() {
        val day18 = Day18()
        day18.part1(day18.readInput(getSmallInput()))
    }

    private fun getSmallInput() : List<String> {
        val input =
                "#########\n" +
                "#b.A.@.a#\n" +
                "#########";

        return input.split("\n")
    }

    private fun getMediumInput() : List<String> {
        val input =
                "########################\n" +
                "#f.D.E.e.C.b.A.@.a.B.c.#\n" +
                "######################.#\n" +
                "#d.....................#\n" +
                "########################"

        return input.split("\n")
    }
}