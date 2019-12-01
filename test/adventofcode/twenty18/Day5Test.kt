package adventofcode.twenty18

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File

/**
 * @author Martin Trollip
 * @since 2019/12/01 07:33
 */
class Day5Test {

    var DAY5_INPUT_SMALL = "src/res/day5_input_small"

    var sequence : String? = null

    @Before
    fun setup() {
        sequence = File(DAY5_INPUT_SMALL).readText()
    }

    @Test
    fun testDay5Part1() {
        val remainingUnits = day5Part1(sequence!!)
        println("How many units remain after fully reacting the polymer you scanned? $remainingUnits")
        assertEquals(10, remainingUnits)
    }

    @Test
    fun testDay5Part2() {
        val remainingUnits = day5Part2(sequence!!)
        println("What is the length of the shortest polymer you can produce? $remainingUnits")
        assertEquals("c=4", remainingUnits.toString())
    }
}