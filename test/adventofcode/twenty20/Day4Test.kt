package adventofcode.twenty20

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/12/04 06:47
 */
class Day4Test {
    private val DAY4_INPUT_SMALL = "src/res/twenty20/day4_input_small"
    private val DAY4_INPUT = "src/res/twenty20/day4_input"

    @Test
    fun day4_part1Small() {
        val day4 = Day4(DAY4_INPUT_SMALL)
        assertEquals(10, day4.part1())
    }

    @Test
    fun day4_part1() {
        val day4 = Day4(DAY4_INPUT)
        assertEquals(204, day4.part1())
    }

    @Test
    fun day4_part2small() {
        val day4 = Day4(DAY4_INPUT_SMALL)
        assertEquals(6, day4.part2())
    }

    @Test
    fun day4_part2() {
        val day4 = Day4(DAY4_INPUT)
        assertEquals(179, day4.part2())
    }

}