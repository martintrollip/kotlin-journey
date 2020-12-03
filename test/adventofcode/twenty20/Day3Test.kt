package adventofcode.twenty20

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/12/03 06:57
 */
class Day3Test {
    private val DAY3_INPUT_SMALL = "src/res/twenty20/day3_input_small"
    private val DAY3_INPUT = "src/res/twenty20/day3_input"

    /**
     * In this example, traversing the map using this slope would cause you to encounter 7 trees.
     */
    @Test
    fun day3_part1Small() {
        val day3 = Day3(DAY3_INPUT_SMALL)
        assertEquals(7, day3.part1())
    }

    @Test
    fun day3_part1() {
        val day3 = Day3(DAY3_INPUT)
        assertEquals(254, day3.part1())
    }


    @Test
    fun day3_part2small_1Valid() {
        val day3 = Day3(DAY3_INPUT_SMALL)
        assertEquals(336, day3.part2())
    }

    @Test
    fun day3_part2() {
        val day3 = Day3(DAY3_INPUT)
        assertEquals(1666768320, day3.part2())
    }

}