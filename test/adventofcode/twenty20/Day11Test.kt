package adventofcode.twenty20

import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/12/11 07:06
 */
class Day11Test {
    private val DAY11_INPUT_SMALL = "src/res/twenty20/day11_input_small"
    private val DAY11_INPUT = "src/res/twenty20/day11_input"

    @Test
    fun day11_part1Small() {
        val day11 = Day11(DAY11_INPUT_SMALL)
        assertEquals(37, day11.part1())
    }

    @Test
    fun day11_part1() {
        val day11 = Day11(DAY11_INPUT)
        assertEquals(2170, day11.part1())
    }

    @Test
    fun day11_part2Small() {
        val day11 = Day11(DAY11_INPUT_SMALL)
        assertEquals(26, day11.part2())
    }

    @Test
    fun day11_part2() {
        val day11 = Day11(DAY11_INPUT)
        assertEquals(2174, day11.part2())
    }
}
