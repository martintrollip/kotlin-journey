package adventofcode.twenty20

import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/12/07 09:26
 */
class Day6Test {
    private val DAY6_INPUT_SMALL = "src/res/twenty20/day6_input_small"
    private val DAY6_INPUT = "src/res/twenty20/day6_input"

    /**
     * In this example, the sum of these counts is 3 + 3 + 3 + 1 + 1 = 11.
     */
    @Test
    fun day6_part1_small() {
        val day6 = Day6(DAY6_INPUT_SMALL)
        assertEquals(11, day6.part1())
    }

    @Test
    fun day6_part1() {
        val day6 = Day6(DAY6_INPUT)
        assertEquals(6382, day6.part1())
    }

    /**
     * In this example, the sum of these counts is 3 + 0 + 1 + 1 + 1 = 6.
     */
    @Test
    fun day6_part2_small() {
        val day6 = Day6(DAY6_INPUT_SMALL)
        assertEquals(6, day6.part2())
    }

    @Test
    fun day6_part2() {
        val day6 = Day6(DAY6_INPUT)
        assertEquals(3197, day6.part2())
    }
}
