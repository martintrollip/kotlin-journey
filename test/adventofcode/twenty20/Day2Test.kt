package adventofcode.twenty20

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/12/02 06:37
 */
class Day2Test {
    private val DAY2_INPUT_SMALL = "src/res/twenty20/day2_input_small"
    private val DAY2_INPUT = "src/res/twenty20/day2_input"

    /**
     * For example, suppose you have the following list:
     *
     * 1-3 a: abcde
     * 1-3 b: cdefg
     * 2-9 c: ccccccccc
     *
     * 2 passwords are valid. The middle password, cdefg, is not; it contains no instances of b, but needs at least 1.
     * The first and third passwords are valid: they contain one a or nine c, both within the limits of their respective policies.
     */
    @Test
    fun day2_part1Small_2Valid() {
        val day2 = Day2(DAY2_INPUT_SMALL)
        assertEquals(2, day2.part1())
    }

    @Test
    fun day2_part1() {
        val day2 = Day2(DAY2_INPUT)
        assertEquals(546, day2.part1())
    }

    /**
     * Given the same example list from above:
     *
     * 1-3 a: abcde is valid: position 1 contains a and position 3 does not.
     * 1-3 b: cdefg is invalid: neither position 1 nor position 3 contains b.
     * 2-9 c: ccccccccc is invalid: both position 2 and position 9 contain c.
     */
    @Test
    fun day2_part2small_1Valid() {
        val day2 = Day2(DAY2_INPUT_SMALL)
        assertEquals(1, day2.part2())
    }

    @Test
    fun day2_part2() {
        val day2 = Day2(DAY2_INPUT)
        assertEquals(275, day2.part2())
    }

}