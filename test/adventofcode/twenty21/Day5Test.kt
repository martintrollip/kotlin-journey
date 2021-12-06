package adventofcode.twenty21

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2021/12/06 09:27
 */
class Day5Test {
    private val DAY5_INPUT_EXAMPLE = "src/res/twenty21/day5_input_example"
    private val DAY5_INPUT = "src/res/twenty21/day5_input"


    /**
     * In this diagram, the top left corner is 0,0 and the bottom right corner is 9,9. Each position is shown as the number
     * of lines which cover that point or . if no line covers that point. The top-left pair of 1s, for example, comes
     * from 2,2 -> 2,1; the very bottom row is formed by the overlapping lines 0,9 -> 5,9 and 0,9 -> 2,9.
     *
     * To avoid the most dangerous areas, you need to determine the number of points where at least two lines overlap.
     * In the above example, this is anywhere in the diagram with a 2 or larger - a total of 5 points.
     *
     * Consider only horizontal and vertical lines. At how many points do at least two lines overlap?
     */
    @Test
    fun day5_calculatePointWhereMoreThan1InterceptHorizontal() {
        val day5 = Day5(DAY5_INPUT_EXAMPLE)
        assertEquals(3, day5.calculateHorizontalAndVerticalVents())
    }

    @Test
    fun day5_calculatePointWhereMoreThan1InterceptHorizontal_withInput() {
        val day5 = Day5(DAY5_INPUT)
        assertEquals(1089, day5.calculateHorizontalAndVerticalVents())
    }

    /**
     * You still need to determine the number of points where at least two lines overlap. In the above example,
     * this is still anywhere in the diagram with a 2 or larger - now a total of 12 points.
     *
     * Consider all of the lines. At how many points do at least two lines overlap?
     */
    @Test
    fun day5_calculatePointWhereMoreThan1Intercept() {
        val day5 = Day5(DAY5_INPUT_EXAMPLE)
        assertEquals(12, day5.calculateAllVents())
    }

    @Test
    fun day5_calculatePointWhereMoreThan1Intercept_withInput() {
        val day5 = Day5(DAY5_INPUT)
        assertEquals(21514, day5.calculateAllVents())
    }
}
