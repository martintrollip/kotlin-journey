package adventofcode.twenty20

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/12/23 08:52
 */
class Day17Test {
    private val DAY17_INPUT_SMALL = "src/res/twenty20/day17_input_small"
    private val DAY17_INPUT = "src/res/twenty20/day17_input"

    @Test
    fun day17_part1_small() {
        val day17 = Day17(DAY17_INPUT_SMALL)
        assertEquals(71, day17.part1())
    }

    @Test
    fun day17_part1() {
        val day17 = Day17(DAY17_INPUT)
        assertEquals(19060, day17.part1())
    }

    @Test
    fun day17_part2() {
        val day17 = Day17(DAY17_INPUT)
        assertEquals(953713095011L, day17.part2())
    }
}
