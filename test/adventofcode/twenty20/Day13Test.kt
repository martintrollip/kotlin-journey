package adventofcode.twenty20

import junit.framework.Assert.*
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/12/13 22:14
 */
class Day13Test {
    private val DAY13_INPUT_SMALL = "src/res/twenty20/day13_input_small"
    private val DAY13_INPUT = "src/res/twenty20/day13_input"

    @Test
    fun day13_part1Small() {
        val day13 = Day13(DAY13_INPUT_SMALL)
        assertEquals(295, day13.part1())
    }

    @Test
    fun day13_part1() {
        val day13 = Day13(DAY13_INPUT)
        assertEquals(3997, day13.part1())
    }

    @Test
    fun day13_part2_small() {
        val day13 = Day13(DAY13_INPUT_SMALL)
        assertEquals(1068781, day13.part2())
    }

    @Test
    fun day13_part2_small_attempt2() {
        val day13 = Day13(DAY13_INPUT_SMALL)
        assertEquals(1068781, day13.part2Attempt2())
    }

    @Test
    fun day13_part2() {
        val day13 = Day13(DAY13_INPUT)
        assertEquals(295, day13.part2Attempt2()) // < 1409240223158943
    }

    @Test
    fun day13_part2_gcd() {
        val day13 = Day13(DAY13_INPUT)

        assertEquals(1, day13.greatestCommonDenominator(7, 13))
        assertEquals(1, day13.greatestCommonDenominator(7, 59))
        assertEquals(1, day13.greatestCommonDenominator(7, 31))
        assertEquals(1, day13.greatestCommonDenominator(7, 19))
        assertEquals(1, day13.greatestCommonDenominator(13, 59))
        assertEquals(1, day13.greatestCommonDenominator(13, 31))
        assertEquals(1, day13.greatestCommonDenominator(13, 19))
        assertEquals(1, day13.greatestCommonDenominator(59, 31))
        assertEquals(1, day13.greatestCommonDenominator(59, 19))
        assertEquals(1, day13.greatestCommonDenominator(31, 19))

        assertEquals(5, day13.greatestCommonDenominator(5, 10))
        assertEquals(35, day13.greatestCommonDenominator(70, 105))
        assertEquals(35, day13.greatestCommonDenominator(105, 70))
    }

    @Test
    fun day13_part2_checkGcd() {
        val day13 = Day13(DAY13_INPUT)
        assertTrue(day13.checkGreatestCommonDenominator(listOf(7, 13, 59, 31, 19)))
        assertFalse(day13.checkGreatestCommonDenominator(listOf(7, 14, 59, 31, 19)))
    }

    @Test
    fun day13_part2_chineseRemainder() {
        val day13 = Day13(DAY13_INPUT)
//        assertEquals(26, day13.chineseRemainder(listOf(3, 4, 5) /* (mod x)*/, listOf(2, 2, 1) /*remainders*/)) //TODO the second term in the return statement is specific to the problem in AOC and not general.  Split the two to make this better.
    }

}
