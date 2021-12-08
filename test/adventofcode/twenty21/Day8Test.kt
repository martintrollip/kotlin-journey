package adventofcode.twenty21

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2021/12/07 10:32
 */
class Day8Test {
    private val DAY8_INPUT_EXAMPLE = "src/res/twenty21/day8_input_example"
    private val DAY8_INPUT_EXAMPLE_LARGER = "src/res/twenty21/day8_input_example_larger"
    private val DAY8_INPUT = "src/res/twenty21/day8_input"
    
    @Test
    fun day8_countEasyDigits_example() {
        val day8 = Day8()
        assertEquals(0, day8.part1(day8.readInput(DAY8_INPUT_EXAMPLE)))
    }
    
    @Test
    fun day8_countEasyDigits_example_larger() {
        val day8 = Day8()
        assertEquals(26, day8.part1(day8.readInput(DAY8_INPUT_EXAMPLE_LARGER)))
    }

    @Test
    fun day8_countEasyDigits_withInput() {
        val day8 = Day8()
        assertEquals(416, day8.part1(day8.readInput(DAY8_INPUT)))
    }

    @Test
    fun day8_outputValue_example() {
        val day8 = Day8()
        assertEquals(5353, day8.part2(day8.readInput(DAY8_INPUT_EXAMPLE)))
    }

    @Test
    fun day8_outputValue_example_larger() {
        val day8 = Day8()
        assertEquals(61229, day8.part2(day8.readInput(DAY8_INPUT_EXAMPLE_LARGER)))
    }

    @Test
    fun day8_outputValue_withInput() {
        val day8 = Day8()
        assertEquals(61229, day8.part2(day8.readInput(DAY8_INPUT)))
    }
}
