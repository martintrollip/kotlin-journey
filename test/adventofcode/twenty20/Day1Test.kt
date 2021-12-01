package adventofcode.twenty20

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/12/01 11:41
 */
class Day1Test {
    private val DAY1_INPUT_SMALL = "src/res/twenty20/day1_input_small"
    private val DAY1_INPUT = "src/res/twenty20/day1_input"

    /**
     * Suppose your expense report contained the following:
     *
     * 1721
     * 979
     * 366
     * 299
     * 675
     * 1456
     *
     * In this list, the two entries that sum to 2020 are 1721 and 299.
     *
     * Multiplying them together produces 1721 * 299 = 514579, so the correct answer is 514579.
     */
    @Test
    fun day1_sumTwoExpensesToTotal2020Small_giveCorrectAnswer() {
        val day1 = Day1(DAY1_INPUT_SMALL)
        assertEquals(514579, day1.productOfExpensesSummingToValue(2020))
    }

    @Test
    fun day1_sumTwoExpensesToTotal2020_giveCorrectAnswer() {
        val day1 = Day1(DAY1_INPUT)
        assertEquals(437931, day1.productOfExpensesSummingToValue(2020))
    }

    /**
     * Using the above example again, the three entries that sum to 2020 are 979, 366, and 675.
     *
     * Multiplying them together produces the answer, 241861950.
     */
    @Test
    fun day1_sumThreeExpensesToTotal2020Small_giveCorrectAnswer() {
        val day1 = Day1(DAY1_INPUT_SMALL)
        assertEquals(241861950, day1.productOfThreeExpensesSummingTo2020())
    }

    @Test
    fun day1_sumThreeExpensesToTotal2020_giveCorrectAnswer() {
        val day1 = Day1(DAY1_INPUT)
        assertEquals(157667328, day1.productOfThreeExpensesSummingTo2020())
    }

}