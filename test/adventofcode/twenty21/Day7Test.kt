package adventofcode.twenty21

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2021/12/07 09:07
 */
class Day7Test {
    private val DAY7_INPUT_EXAMPLE = "src/res/twenty21/day7_input_example"
    private val DAY7_INPUT = "src/res/twenty21/day7_input"

    /**
     * For example, consider the following horizontal positions:
     *
     * 16,1,2,0,4,2,7,1,2,14
     * This means there's a crab with horizontal position 16, a crab with horizontal position 1, and so on.
     *
     * Each change of 1 step in horizontal position of a single crab costs 1 fuel. You could choose any horizontal position to align them all on, but the one that costs the least fuel is horizontal position 2:
     *
     * Move from 16 to 2: 14 fuel
     * Move from 1 to 2: 1 fuel
     * Move from 2 to 2: 0 fuel
     * Move from 0 to 2: 2 fuel
     * Move from 4 to 2: 2 fuel
     * Move from 2 to 2: 0 fuel
     * Move from 7 to 2: 5 fuel
     * Move from 1 to 2: 1 fuel
     * Move from 2 to 2: 0 fuel
     * Move from 14 to 2: 12 fuel
     * This costs a total of 37 fuel. This is the cheapest possible outcome; more expensive outcomes include aligning at position 1 (41 fuel), position 3 (39 fuel), or position 10 (71 fuel).
     *
     * Determine the horizontal position that the crabs can align to using the least fuel possible. How much fuel must they spend to align to that position?
     */
    @Test
    fun day7_calculateMinimalFuelCost() {
        val day7 = Day7()
        assertEquals(37, day7.part1(day7.readInput(DAY7_INPUT_EXAMPLE)))
    }

    @Test
    fun day7_calculateMinimumFuelCost_withInput() {
        val day7 = Day7()
        assertEquals(364898, day7.part1(day7.readInput(DAY7_INPUT)))
    }

    /**
     * As it turns out, crab submarine engines don't burn fuel at a constant rate. Instead, each change of 1 step in horizontal position costs 1 more unit of fuel than the last: the first step costs 1, the second step costs 2, the third step costs 3, and so on.
     *
     *    As each crab moves, moving further becomes more expensive. This changes the best horizontal position to align them all on; in the example above, this becomes 5:
     *
     *    Move from 16 to 5: 66 fuel
     *    Move from 1 to 5: 10 fuel
     *    Move from 2 to 5: 6 fuel
     *    Move from 0 to 5: 15 fuel
     *    Move from 4 to 5: 1 fuel
     *    Move from 2 to 5: 6 fuel
     *    Move from 7 to 5: 3 fuel
     *    Move from 1 to 5: 10 fuel
     *    Move from 2 to 5: 6 fuel
     *    Move from 14 to 5: 45 fuel
     *    This costs a total of 168 fuel. This is the new cheapest possible outcome; the old alignment position (2) now costs 206 fuel instead.
     */
    @Test
    fun day7_calculateMinimalFuelCost_2() {
        val day7 = Day7()
        assertEquals(168, day7.part2(day7.readInput(DAY7_INPUT_EXAMPLE)))
    }
    
    @Test
    fun day7_calculateMinimalFuelCost_2_withInput() {
        val day7 = Day7()
        assertEquals(104149091, day7.part2(day7.readInput(DAY7_INPUT)))
    }
}
