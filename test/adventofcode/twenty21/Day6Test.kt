package adventofcode.twenty21

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2021/12/06 16:50
 */
class Day6Test {
    private val DAY6_INPUT_EXAMPLE = "src/res/twenty21/day6_input_example"
    private val DAY6_INPUT = "src/res/twenty21/day6_input"

    /**
     * Each day, a 0 becomes a 6 and adds a new 8 to the end of the list, while each other number decreases by 1 if it was present at the start of the day.

    In this example, after 18 days, there are a total of 26 fish. After 80 days, there would be a total of 5934.
     */
    @Test
    fun day6_runFor80Days_givesCorrectAnswer() {
        val day6 = Day6()
        assertEquals(5934, day6.run(day6.readInput(DAY6_INPUT_EXAMPLE),80))
    }
}
