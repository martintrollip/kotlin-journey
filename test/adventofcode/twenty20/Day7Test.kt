package adventofcode.twenty20

import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/12/07 07:15
 */
class Day7Test {
    private val DAY7_INPUT_SMALL = "src/res/twenty20/day7_input_small"
    private val DAY7_INPUT_SMALL2 = "src/res/twenty20/day7_input_small2"
    private val DAY7_INPUT = "src/res/twenty20/day7_input"

    /**
     * So, in this example, the number of bag colors that can eventually contain at least one shiny gold bag is 4.
     */
    @Test
    fun day7_part1_small() {
        val day7 = Day7(DAY7_INPUT_SMALL)
        assertEquals(4, day7.part1())
    }

    @Test
    fun day7_part1() {
        val day7 = Day7(DAY7_INPUT)
        assertEquals(233, day7.part1())
    }

    /**
     * So, a single shiny gold bag must contain 1 dark olive bag (and the 7 bags within it) plus 2 vibrant plum bags
     * (and the 11 bags within each of those): 1 + 1*7 + 2 + 2*11 = 32 bags!
     */
    @Test
    fun day7_part2_small() {
        val day7 = Day7(DAY7_INPUT_SMALL)
        assertEquals(32, day7.part2())
    }

    @Test
    fun day7_part2_small2() {
        val day7 = Day7(DAY7_INPUT_SMALL2)
        assertEquals(126, day7.part2())
    }

    @Test
    fun day7_part2() {
        val day7 = Day7(DAY7_INPUT)
        assertEquals(233, day7.part2())
    }

}
