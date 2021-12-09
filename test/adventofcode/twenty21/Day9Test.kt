package adventofcode.twenty21

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2021/12/08 14:30
 */
class Day9Test {
    private val DAY9_INPUT_EXAMPLE = "src/res/twenty21/day9_input_example"
    private val DAY9_INPUT = "src/res/twenty21/day9_input"

    /**
     * Your first goal is to find the low points - the locations that are lower than any of its adjacent locations. Most locations have four adjacent locations (up, down, left, and right); locations on the edge or corner of the map have three or two adjacent locations, respectively. (Diagonal locations do not count as adjacent.)
     *
     * In the above example, there are four low points, all highlighted: two are in the first row (a 1 and a 0), one is in the third row (a 5), and one is in the bottom row (also a 5). All other locations on the heightmap have some lower adjacent location, and so are not low points.
     *
     * The risk level of a low point is 1 plus its height. In the above example, the risk levels of the low points are 2, 1, 6, and 6. The sum of the risk levels of all low points in the heightmap is therefore 15.
     */
    @Test
    fun day9_calculateLowPoints_example() {
        val day9 = Day9()
        val result = day9.part1(day9.readInput(DAY9_INPUT_EXAMPLE))
        assertEquals(15, result)
    }
    
    @Test
    fun day9_calculateLowPoints_withInput() {
        val day9 = Day9()
        val result = day9.part1(day9.readInput(DAY9_INPUT))
        assertEquals(436, result)
    }

    @Test
    fun day9_calculateBasins_example() {
        val day9 = Day9()
        val result = day9.part2(day9.readInput(DAY9_INPUT_EXAMPLE))
        assertEquals(1134, result)
    }

    @Test
    fun day9_calculateBasins_withInput() {
        val day9 = Day9()
        val result = day9.part2(day9.readInput(DAY9_INPUT))
        assertEquals(436, result)
    }
}
