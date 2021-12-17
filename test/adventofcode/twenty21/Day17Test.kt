package adventofcode.twenty21

import adventofcode.twenty18.readInput
import org.junit.Assert
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2021/12/17 06:46
 */
class Day17Test {
    private val DAY17_INPUT_EXAMPLE = "src/res/twenty21/day17_input_example"
    private val DAY17_INPUT = "src/res/twenty21/day17_input"


    /**
     * In the above example, using an initial velocity of 6,9 is the best you can do, causing the probe to reach a maximum y position of 45. 
     * (Any higher initial y velocity causes the probe to overshoot the target area entirely.)
     * 
     * Find the initial velocity that causes the probe to reach the highest y position and still eventually be within the 
     * target area after any step. What is the highest y position it reaches on this trajectory?
     */
    @Test
    fun day17_part1_example() {
        val day17 = Day17()
        Assert.assertEquals(45, day17.part1(day17.readInput(DAY17_INPUT_EXAMPLE)))
    }    
    
    @Test
    fun day17_part1_withInput() {
        val day17 = Day17()
        Assert.assertEquals(8646, day17.part1(day17.readInput(DAY17_INPUT)))
    }  
    
    @Test
    fun day17_part2_example() {
        val day17 = Day17()
        Assert.assertEquals(112, day17.part2(day17.readInput(DAY17_INPUT_EXAMPLE)))
    }

    @Test
    fun day17_part2_withInput() {
        val day17 = Day17()
        Assert.assertEquals(2224, day17.part2(day17.readInput(DAY17_INPUT)))
    }
}
