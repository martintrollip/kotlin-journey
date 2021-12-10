package adventofcode.twenty21

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
: @since 2021/12/10 08:31
 */
class Day11Test {
    private val DAY11_INPUT_EXAMPLE = "src/res/twenty21/day11_input_example"
    private val DAY11_INPUT = "src/res/twenty21/day11_input"

   
    @Test
    fun day11_calculateScore_example() {
        val day11 = Day11()
        val result = day11.part1(day11.readInput(DAY11_INPUT_EXAMPLE))
        assertEquals(26397, result)
    }
}
