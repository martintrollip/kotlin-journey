package adventofcode.twenty21

import org.junit.Assert
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2021/12/14 09:21
 */
class Day14Test {
    private val DAY14_INPUT_EXAMPLE = "src/res/twenty21/day14_input_example"
    private val DAY14_INPUT = "src/res/twenty21/day14_input"

    /**
     * This polymer grows quickly. After step 5, it has length 97; After step 10, it has length 3073. After step 10, B occurs 1749 times,
     * C occurs 298 times, H occurs 161 times, and N occurs 865 times; taking the quantity of the most common element (B, 1749)
     * and subtracting the quantity of the least common element (H, 161) produces 1749 - 161 = 1588.
     */
    @Test
    fun day14_runSteps_example() {
        val day14 = Day14()
        Assert.assertEquals(1588, day14.part1(day14.readInput(DAY14_INPUT_EXAMPLE)))
    }
    
    @Test
    fun day14_runSteps_withInput() {
        val day14 = Day14()
        Assert.assertEquals(2435, day14.part1(day14.readInput(DAY14_INPUT)))
    }
    
    @Test
    fun day14_runSteps2_example() {
        val day14 = Day14()
        Assert.assertEquals(2188189693529L, day14.part2(day14.readInput(DAY14_INPUT_EXAMPLE), 40))
    }
    
    @Test
    fun day14_runSteps2_withInput() {
        val day14 = Day14()
        Assert.assertEquals(1588, day14.part2(day14.readInput(DAY14_INPUT), 40))
    }
}
