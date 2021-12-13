package adventofcode.twenty21

import org.junit.Assert
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2021/12/12 12:07
 */
class Day13Test {
    private val DAY13_INPUT_EXAMPLE = "src/res/twenty21/day13_input_example"
    private val DAY13_INPUT = "src/res/twenty21/day13_input"
    
    /**
     * The transparent paper is pretty big, so for now, focus on just completing the first fold. After the first fold in the example above, 17 dots are visible - dots that end up overlapping after the fold is completed count as a single dot.
     */
    @Test
    fun day11_foldFirst_example() {
        val day13 = Day13()
        val result = day13.part1(day13.readInput(DAY13_INPUT_EXAMPLE))
        Assert.assertEquals(17, result)
    }
    
    @Test
    fun day11_foldFirst_withInput() {
        val day13 = Day13()
        val result = day13.part1(day13.readInput(DAY13_INPUT))
        Assert.assertEquals(720, result)
    }
    
    @Test
    fun day11_foldAll_example() {
        val day13 = Day13()
        val result = day13.part2(day13.readInput(DAY13_INPUT_EXAMPLE))
        Assert.assertEquals(2, result)
    }
    
    @Test
    fun day11_foldAll_withInput() {
        val day13 = Day13()
        val result = day13.part2(day13.readInput(DAY13_INPUT))
        Assert.assertEquals(2, result)
    }
    
}
