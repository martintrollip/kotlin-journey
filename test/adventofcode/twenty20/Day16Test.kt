package adventofcode.twenty20

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/12/17 12:04
 */
class Day16Test {
    private val DAY16_INPUT_SMALL = "src/res/twenty20/day16_input_small"
    private val DAY16_INPUT_SMALL2 = "src/res/twenty20/day16_input_small2"
    private val DAY16_INPUT = "src/res/twenty20/day16_input"

    @Test
    fun day16_part1_small() {
        val day16 = Day16(DAY16_INPUT_SMALL)
        assertEquals(71, day16.part1())
    }

    @Test
    fun day16_part1() {
        val day16 = Day16(DAY16_INPUT)
        assertEquals(19060, day16.part1())
    }

    @Test
    fun day16_part2_small() {
        val day16 = Day16(DAY16_INPUT_SMALL2)
        assertEquals(19060, day16.part2())
    }

    @Test
    fun day16_part2() {
        val day16 = Day16(DAY16_INPUT)
        assertEquals(953713095011L, day16.part2())
    }
}
