package adventofcode.twenty20

import junit.framework.Assert.*
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/12/15 14:16
 */
class Day15Test {

    @Test
    fun day15_part1_small() {
        val day15 = Day15("0,3,6")
        assertEquals(436, day15.part1())
    }

    @Test
    fun day15_part1() {
        val day15 = Day15("0,1,4,13,15,12,16")
        assertEquals(1665, day15.part1())
    }

    @Test
    fun day15_part2() {
        val day15 = Day15("0,1,4,13,15,12,16")
        assertEquals(436, day15.part2())
    }
}
