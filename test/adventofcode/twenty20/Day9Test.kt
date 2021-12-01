package adventofcode.twenty20

import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/12/09 06:55
 */
class Day9Test {
    private val DAY9_INPUT_SMALL = "src/res/twenty20/day9_input_small"
    private val DAY9_INPUT = "src/res/twenty20/day9_input"

    /**
     * In this example, after the 5-number preamble, almost every number is the sum of two of the previous 5 numbers;
     *
     * the only number that does not follow this rule is 127.
     */
    @Test
    fun day9_part1_small() {
        val day9 = Day9(DAY9_INPUT_SMALL)
        assertEquals(127.toBigInteger(), day9.part1(5))
    }

    @Test
    fun day9_part1() {
        val day9 = Day9(DAY9_INPUT)
        assertEquals(177777905.toBigInteger(), day9.part1(25))
    }

    /**
     * To find the encryption weakness, add together the smallest and largest number in this contiguous range; in this example,
     * these are 15 and 47, producing 62.
     */
    @Test
    fun day9_part2_small() {
        val day9 = Day9(DAY9_INPUT_SMALL)
        assertEquals(62.toBigInteger(), day9.part2(5))
    }

    @Test
    fun day9_part2() {
        val day9 = Day9(DAY9_INPUT)
        assertEquals(23463012.toBigInteger(), day9.part2(25))
    }
}
