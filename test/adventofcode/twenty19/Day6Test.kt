package adventofcode.twenty19

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File


/**
 * @author Martin Trollip
 * @since 2019/12/06 07:38
 */
class Day6Test {
    private val DAY6_INPUT = "src/res/twenty19/day6_input_small"

    private val day6 = Day6()

    @Test
    fun testIndirectOrbits() {
        assertEquals(54, day6.countIndirectOrbits(File(DAY6_INPUT).readLines()))
    }

    /**
     * In this example, YOU are in orbit around K, and SAN is in orbit around I.
     * To move from K to I, a minimum of 4 orbital transfers are required:
     *
     *                            YOU
     *                          /
     *        G - H        J - K - L
     *        /           /
     * COM - B - C - D - E - F
     *               \
     *                I - SAN
     *
     */
    @Test
    fun testJumpsBetweenPaths() {
        assertEquals(4, day6.countJumpsBetween(File(DAY6_INPUT).readLines()))
    }

}





