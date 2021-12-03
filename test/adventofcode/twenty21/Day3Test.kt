package adventofcode.twenty21

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2021/12/03 08:07
 */
class Day3Test {
    private val DAY3_INPUT_EXAMPLE = "src/res/twenty21/day3_input_example"
    private val DAY3_INPUT = "src/res/twenty21/day3_input"

    /**
     * Each bit in the gamma rate can be determined by finding the most common bit in the corresponding position of all numbers in the diagnostic report. For example, given the following diagnostic report:
     *
     *      00100
     *      11110
     *      10110
     *      10111
     *      10101
     *      01111
     *      00111
     *      11100
     *      10000
     *      11001
     *      00010
     *      01010
     *
     * Considering only the first bit of each number, there are five 0 bits and seven 1 bits. Since the most common bit is 1, the first bit of the gamma rate is 1.
     *
     * The most common second bit of the numbers in the diagnostic report is 0, so the second bit of the gamma rate is 0.
     *
     * The most common value of the third, fourth, and fifth bits are 1, 1, and 0, respectively, and so the final three bits of the gamma rate are 110.
     *
     * So, the gamma rate is the binary number 10110, or 22 in decimal
     *
     * The epsilon rate is calculated in a similar way; rather than use the most common bit, the least common bit from each position is used.
     * So, the epsilon rate is 01001, or 9 in decimal. Multiplying the gamma rate (22) by the epsilon rate (9) produces the power consumption, 198.
     */
    @Test
    fun day3_calculatePowerConsumption_givesCorrectAnswer() {
        val day3 = Day3()
        val input = day3.readInput(DAY3_INPUT_EXAMPLE)
        assertEquals(198, day3.calculatePowerConsumption(input))
    }

    @Test
    fun day3_calculatePowerConsumption_givesCorrectAnswer_forInput() {
        val day3 = Day3()
        val input = day3.readInput(DAY3_INPUT)
        assertEquals(1071734, day3.calculatePowerConsumption(input))
    }

    /**
     * To find oxygen generator rating, determine the most common value (0 or 1) in the current bit position, and keep
     * only numbers with that bit in that position. If 0 and 1 are equally common, keep values with a 1 in the position being considered.
     *
     * Finally, to find the life support rating, multiply the oxygen generator rating (23) by the CO2 scrubber rating (10) to get 230.
     */
    @Test
    fun day3_calculateLifeSupportRating_givesCorrectAnswer() {
        val day3 = Day3()
        val input = day3.readInput(DAY3_INPUT_EXAMPLE)
        assertEquals(230, day3.calculateLifeSupportRating(input))
    }

    @Test
    fun day3_calculateLifeSupportRating_givesCorrectAnswer_forInput() {
        val day3 = Day3()
        val input = day3.readInput(DAY3_INPUT)
        assertEquals(6124992, day3.calculateLifeSupportRating(input))
    }

}
