package adventofcode.twenty21

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

/**
 * @author Martin Trollip
 * @since 2021/12/01 08:21
 */
class Day1Test {
    private val DAY1_INPUT_EXAMPLE = "src/res/twenty21/day1_input_example"
    private val DAY1_INPUT = "src/res/twenty21/day1_input"

    /**
     * For example, suppose you had the following report:
     *
     * 199
     * 200
     * 208
     * 210
     * 200
     * 207
     * 240
     * 269
     * 260
     * 263
     *
     * In this example, there are 7 measurements that are larger than the previous measurement.
     */
    @Test
    fun day1_numberOfIncreasingElements_giveCorrectAnswer() {
        val day1 = Day1()
        val exampleInput = day1.readInput(DAY1_INPUT_EXAMPLE);
        assertEquals(7, day1.numberOfIncreasingElementsInList(exampleInput))
    }

    @Test
    fun day1_numberOfIncreasingElements_giveCorrectAnswer_input() {
        val day1 = Day1()
        val input = day1.readInput(DAY1_INPUT);
        assertEquals(1692, day1.numberOfIncreasingElementsInList(input))
    }

    /**
     * Your goal now is to count the number of times the sum of measurements in this sliding window increases from the previous sum.
     * So, compare A with B, then compare B with C, then C with D, and so on. Stop when there aren't enough measurements left to create a new three-measurement sum.
     */
    @Test
    fun day1_windowedSum_giveCorrectAnswer() {
        val day1 = Day1()
        val exampleInput = day1.readInput(DAY1_INPUT_EXAMPLE);
        assertEquals(5, day1.numberOfIncreasingSumOfWindowedElementsInList(exampleInput))
    }

    @Test
    fun day1_windowedSum_giveCorrectAnswer_input() {
        val day1 = Day1()
        val input = day1.readInput(DAY1_INPUT);
        assertEquals(1724, day1.numberOfIncreasingSumOfWindowedElementsInList(input))
    }
}