package adventofcode.twenty20

import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/12/11 06:11
 */
class Day10Test {
    private val DAY10_INPUT_SMALL = "src/res/twenty20/day10_input_small"
    private val DAY10_INPUT_SMALL2 = "src/res/twenty20/day10_input_small2"
    private val DAY10_INPUT = "src/res/twenty20/day10_input"

    @Test
    fun day10_part1Small_useAdapters() {
        val day10 = Day10(DAY10_INPUT_SMALL)
        assertEquals(7 * 5, day10.part1())
    }

    @Test
    fun day10_part1Small_useAdapters2() {
        val day10 = Day10(DAY10_INPUT_SMALL2)
        assertEquals(22 * 10, day10.part1())
    }

    @Test
    fun day10_part1() {
        val day10 = Day10(DAY10_INPUT)
        assertEquals(2170, day10.part1())
    }

    @Test
    fun day10_part2Small_useAdapters() {
        val day10 = Day10(DAY10_INPUT_SMALL)
        assertEquals(8, day10.part2())
    }

    @Test
    fun day10_part2Small_useAdapters2() {
        val day10 = Day10(DAY10_INPUT_SMALL2)
        assertEquals(19208, day10.part2())
    }

    @Test
    fun day10_part2() {
        val day10 = Day10(DAY10_INPUT)
//        assertEquals(24803586664192L, day10.part2())
        println(939/7)
        println(939/13)
        println(939/59)
        println(939/31)
        println(939/19)

        println()
        println(7*135)
        println(13*73)
        println(59*16)
        println(31*31)
        println(19*50)
    }
}
