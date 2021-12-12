package adventofcode.twenty21

import org.junit.Assert
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2021/12/11 18:05
 */
class Day12Test {
    private val DAY12_INPUT_EXAMPLE = "src/res/twenty21/day12_input_example"
    private val DAY12_INPUT_EXAMPLE_2 = "src/res/twenty21/day12_input_example_2"
    private val DAY12_INPUT_EXAMPLE_3 = "src/res/twenty21/day12_input_example_3"
    private val DAY12_INPUT = "src/res/twenty21/day12_input"

    /**
     * Your goal is to find the number of distinct paths that start at start, end at end, and don't visit small caves more than once. There are two types of caves: big caves (written in uppercase, like A) and small caves (written in lowercase, like b). It would be a waste of time to visit any small cave more than once, but big caves are large enough that it might be worth visiting them multiple times. So, all paths you find should visit small caves at most once, and can visit big caves any number of times.
     *
     * Given these rules, there are 10 paths through this example cave system:
     */
    @Test 
    fun day12_distinct_paths_example() {
        val day12 = Day12()
        val result = day12.part1(day12.readInput(DAY12_INPUT_EXAMPLE))
        Assert.assertEquals(10, result)
    }
    
    @Test
    fun day12_distinct_paths_example_2() {
        val day12 = Day12()
        val result = day12.part1(day12.readInput(DAY12_INPUT_EXAMPLE_2))
        Assert.assertEquals(19, result)
    }
    
    @Test
    fun day12_distinct_paths_example_3() {
        val day12 = Day12()
        val result = day12.part1(day12.readInput(DAY12_INPUT_EXAMPLE_3))
        Assert.assertEquals(226, result)
    }
    
    @Test
    fun day12_distinct_paths_withInput() {
        val day12 = Day12()
        val result = day12.part1(day12.readInput(DAY12_INPUT))
        Assert.assertEquals(5333, result)
    }

    @Test
    fun day12_distinct_paths2_example() {
        val day12 = Day12()
        val result = day12.part2(day12.readInput(DAY12_INPUT_EXAMPLE))
        Assert.assertEquals(36  , result)
    }

    @Test
    fun day12_distinct_paths2_example_2() {
        val day12 = Day12()
        val result = day12.part2(day12.readInput(DAY12_INPUT_EXAMPLE_2))
        Assert.assertEquals(103, result)
    }

    @Test
    fun day12_distinct_paths2_example_3() {
        val day12 = Day12()
        val result = day12.part2(day12.readInput(DAY12_INPUT_EXAMPLE_3))
        Assert.assertEquals(3509, result)
    }

    @Test
    fun day12_distinct_paths2_withInput() {
        val day12 = Day12()
        val result = day12.part2(day12.readInput(DAY12_INPUT))
        Assert.assertEquals(146553, result)
    }
}
