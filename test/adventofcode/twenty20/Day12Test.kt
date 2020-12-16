package adventofcode.twenty20

import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/12/16 11:02
 */
class Day12Test {
    private val DAY12_INPUT_SMALL = "src/res/twenty20/day12_input_small"
    private val DAY12_INPUT = "src/res/twenty20/day12_input"

    @Test
    fun day12_part1Small() {
        val day12 = Day12(DAY12_INPUT_SMALL)
        assertEquals(25, day12.part1())
    }

    @Test
    fun day12_part1() {
        val day12 = Day12(DAY12_INPUT)
        assertEquals(2280, day12.part1())
    }

    @Test
    fun day12_part2Small() {
        val day12 = Day12(DAY12_INPUT_SMALL)
        assertEquals(286, day12.part2())
    }

    @Test
    fun day12_part2() {
        val day12 = Day12(DAY12_INPUT)
        assertEquals(2174, day12.part2()) //"<53865"
    }
}
