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
    fun day10_part1Small_deviceVoltage() {
        val day10 = Day10(DAY10_INPUT_SMALL)
        assertEquals(22, day10.myDeviceJolts)
    }

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
}
