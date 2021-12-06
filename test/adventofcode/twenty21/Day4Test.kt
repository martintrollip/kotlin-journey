package adventofcode.twenty21

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2021/12/04 16:38
 */
class Day4Test {
    private val DAY4_INPUT_EXAMPLE = "src/res/twenty21/day4_input_example"
    private val DAY4_INPUT = "src/res/twenty21/day4_input"


    /**
     * At this point, the third board wins because it has at least one complete row or column of marked numbers (in this case, the entire top row is marked: 14 21 17 24 4).
     *
     * The score of the winning board can now be calculated. Start by finding the sum of all unmarked numbers on that board;
     * in this case, the sum is 188. Then, multiply that sum by the number that was just called when the board won, 24, to get the final score, 188 * 24 = 4512.
     */
    @Test
    fun day4_calculateWinningBoardScore_givesCorrectAnswer() {
        val day4 = Day4(DAY4_INPUT_EXAMPLE)
        assertEquals(4512, day4.calculateWinningBoardScore())
    }

    @Test
    fun day4_calculateWinningBoardScore_givesCorrectAnswer_withInput() {
        val day4 = Day4(DAY4_INPUT)
        assertEquals(83799, day4.calculateWinningBoardScore())
    }

    /**
     * In the above example, the second board is the last to win, which happens after 13 is eventually called and its middle
     * column is completely marked. If you were to keep playing until this point, the second board would have a sum of
     * unmarked numbers equal to 148 for a final score of 148 * 13 = 1924.
     */
    @Test
    fun day4_calculateLastWinningBoard_givesCorrectAnswer() {
        val day4 = Day4(DAY4_INPUT_EXAMPLE)
        assertEquals(1924, day4.calculateLastWinningBoardScore())
    }


    @Test
    fun day4_calculateLastWinningBoard_givesCorrectAnswer_withInput() {
        val day4 = Day4(DAY4_INPUT)
        assertEquals(17435, day4.calculateLastWinningBoardScore())
    }
}
