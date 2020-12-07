package adventofcode.twenty20

import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/12/05 07:37
 */
class Day5Test {
    private val DAY5_INPUT_SMALL = "src/res/twenty20/day5_input_small"
    private val DAY5_INPUT = "src/res/twenty20/day5_input"

    /**
     * ROW
     * For example, consider just the first seven characters of FBFBBFFRLR:
     *
     * Start by considering the whole range, rows 0 through 127.
     * F means to take the lower half, keeping rows 0 through 63.
     * B means to take the upper half, keeping rows 32 through 63.
     * F means to take the lower half, keeping rows 32 through 47.
     * B means to take the upper half, keeping rows 40 through 47.
     * B keeps rows 44 through 47.
     * F keeps rows 44 through 45.
     *
     * The final F keeps the lower of the two, row 44.
     *
     * COLUMN
     * For example, consider just the last 3 characters of FBFBBFFRLR:
     *
     * Start by considering the whole range, columns 0 through 7.
     * R means to take the upper half, keeping columns 4 through 7.
     * L means to take the lower half, keeping columns 4 through 5.
     *
     * The final R keeps the upper of the two, column 5.
     *
     * Every seat also has a unique seat ID: multiply the row by 8, then add the column. In this example, the seat has ID 44 * 8 + 5 = 357.
     */
    @Test
    fun day5_part1_OneLine() {
        val day5 = Day5(DAY5_INPUT_SMALL)
        val (row, col) = day5.calculateRowCol("FBFBBFFRLR")
        assertEquals(44, row)
        assertEquals(5, col)
        assertEquals(357, day5.calculateRowID(row, col))
    }

    /**
     * Here are some other boarding passes:
     *
     * BFFFBBFRRR: row 70, column 7, seat ID 567.
     * FFFBBBFRRR: row 14, column 7, seat ID 119.
     * BBFFBBFRLL: row 102, column 4, seat ID 820.
     */
    @Test
    fun day5_part1_More_OneLine() {
        val day5 = Day5(DAY5_INPUT_SMALL)

        val (row, col) = day5.calculateRowCol("BFFFBBFRRR")
        assertEquals(70, row)
        assertEquals(7, col)
        assertEquals(567, day5.calculateRowID(row, col))

        val (row1, col1) = day5.calculateRowCol("FFFBBBFRRR")
        assertEquals(14, row1)
        assertEquals(7, col1)
        assertEquals(119, day5.calculateRowID(row1, col1))

        val (row2, col2) = day5.calculateRowCol("BBFFBBFRLL")
        assertEquals(102, row2)
        assertEquals(4, col2)
        assertEquals(820, day5.calculateRowID(row2, col2))
    }

    @Test
    fun day5_part1_extPopRange() {
        assertEquals("Hello World!", "aHello World!".removeFirst())
    }

    @Test
    fun day5_part1() {
        val day5 = Day5(DAY5_INPUT)
        assertEquals(908, day5.part1())
    }
    @Test
    fun day5_part2() {
        val day5 = Day5(DAY5_INPUT)
        assertEquals(617, day5.part2())
    }

    fun String.removeFirst(): String {
        return this.removeRange(0, 1)
    }
}
