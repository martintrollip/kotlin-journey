package adventofcode.twenty21

import org.junit.Assert
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2021/12/15 07:28
 */
class Day15Test {
    private val DAY15_INPUT_EXAMPLE = "src/res/twenty21/day15_input_example"
    private val DAY15_INPUT = "src/res/twenty21/day15_input"

    /**
     * The total risk of this path is 40 (the starting position is never entered, so its risk is not counted).
     */
    @Test
    fun day15_lowestRiskPath_example() {
        val day15 = Day15()
        Assert.assertEquals(40, day15.part1(day15.readInput(DAY15_INPUT_EXAMPLE)))
    }

    @Test
    fun day15_lowestRiskPath_input() {
        val day15 = Day15()
        Assert.assertEquals(769, day15.part1(day15.readInput(DAY15_INPUT)))
    }

    /**
     * The total risk of this path is 315 (the starting position is still never entered, so its risk is not counted).
     */
    @Test
    fun day15_lowestRiskPathFull_example() {
        val day15 = Day15()
        Assert.assertEquals(315, day15.part2(day15.readInput(DAY15_INPUT_EXAMPLE)))
    }

    @Test
    fun day15_lowestRiskPathFull_input() {
        val day15 = Day15()
        Assert.assertEquals(2963, day15.part2(day15.readInput(DAY15_INPUT)))
    }
}
