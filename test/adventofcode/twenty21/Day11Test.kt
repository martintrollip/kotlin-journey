package adventofcode.twenty21

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
: @since 2021/12/10 08:31
 */
class Day11Test {
    private val DAY11_INPUT_EXAMPLE = "src/res/twenty21/day11_input_example"
    private val DAY11_INPUT_EXAMPLE_SMALLER = "src/res/twenty21/day11_input_example_smaller"
    private val DAY11_INPUT = "src/res/twenty21/day11_input"

   
    @Test
    fun day11_countFlashes_example() {
        val day11 = Day11()
        val result = day11.part1(day11.readInput(DAY11_INPUT_EXAMPLE).toMutableMap(), 100)
        assertEquals(1656, result)
    }
    
    @Test
    fun day11_countFlashes_example_smaller() {
        val day11 = Day11()
        val result = day11.part1(day11.readInput(DAY11_INPUT_EXAMPLE_SMALLER).toMutableMap(), 100)
        assertEquals(256, result)
    }
    
    @Test
    fun day11_countFlashes_withInput() {
        val day11 = Day11()
        val result = day11.part1(day11.readInput(DAY11_INPUT).toMutableMap(), 100)
        assertEquals(2, result)
    }

    @Test
    fun day11_countSteps_example() {
        val day11 = Day11()
        val result = day11.part2(day11.readInput(DAY11_INPUT_EXAMPLE).toMutableMap())
        assertEquals(195, result)
    }

    @Test
    fun day11_countSteps_withInput() {
        val day11 = Day11()
        val result = day11.part2(day11.readInput(DAY11_INPUT).toMutableMap())
        assertEquals(195, result)
    }
    
}
