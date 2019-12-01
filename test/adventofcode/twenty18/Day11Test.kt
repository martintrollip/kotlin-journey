package adventofcode.twenty18

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File

/**
 * @author Martin Trollip
 * @since 2019/12/01 08:34
 */
class Day11Test {

    var DAY11_INPUT_SMALL = "src/res/day11_input_small"

    @Before
    fun setup() {
        serial = File(DAY11_INPUT_SMALL).readText().toInt()
    }

//TODO Martin
//    @Test
//    fun testDay11() {
//        assertEquals("FuelCell(x=33, y=45, power=29) with grid 18", day11())
//    }
}