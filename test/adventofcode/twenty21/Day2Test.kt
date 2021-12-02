package adventofcode.twenty21

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

/**
 * @author Martin Trollip
 * @since 2021/12/02 07:35
 */
class Day2Test {
    private val DAY2_INPUT_EXAMPLE = "src/res/twenty21/day2_input_example"
    private val DAY2_INPUT = "src/res/twenty21/day2_input"

    /**
     * Your horizontal position and depth both start at 0. The steps above would then modify them as follows:
     *
     *      forward 5 adds 5 to your horizontal position, a total of 5.
     *      down 5 adds 5 to your depth, resulting in a value of 5.
     *      forward 8 adds 8 to your horizontal position, a total of 13.
     *      up 3 decreases your depth by 3, resulting in a value of 2.
     *      down 8 adds 8 to your depth, resulting in a value of 10.
     *      forward 2 adds 2 to your horizontal position, a total of 15.
     *
     *  After following these instructions, you would have a horizontal position of 15 and a depth of 10. (Multiplying these together produces 150.)
     */
    @Test
    fun day2_calculatePosition_givesCorrectAnswer() {
        val day2 = Day2();
        val input = day2.readInput(DAY2_INPUT_EXAMPLE)
        val result = day2.getCoordinatesMutiplied(input)
        assertEquals(150, result)
    }

    @Test
    fun day2_calculatePosition_givesCorrectAnswer_withInput() {
        val day2 = Day2();
        val input = day2.readInput(DAY2_INPUT)
        val result = day2.getCoordinatesMutiplied(input)
        assertEquals(1893605, result)
    }
}