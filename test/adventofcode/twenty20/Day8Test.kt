package adventofcode.twenty20

import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/12/08 07:01
 */
class Day8Test {
    private val DAY8_INPUT_SMALL = "src/res/twenty20/day8_input_small"
    private val DAY8_INPUT = "src/res/twenty20/day8_input"

    @Test
    fun day8_part1_small() {
        val day8 = Day8(DAY8_INPUT_SMALL)
        assertEquals(5, day8.part1())
    }

    @Test
    fun day8_part1() {
        val day8 = Day8(DAY8_INPUT)
        assertEquals(1816, day8.part1())
    }

    @Test
    fun day8_part2_small() {
        val day8 = Day8(DAY8_INPUT_SMALL)
        assertEquals(8, day8.part2())
    }

    @Test
    fun day8_part2() {
        val day8 = Day8(DAY8_INPUT)
        assertEquals(1149, day8.part2())
    }

}