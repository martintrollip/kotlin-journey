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

    private var DAY11_INPUT_SMALL = "src/res/day11_input_small"

    @Before
    fun setup() {
        serial = File(DAY11_INPUT_SMALL).readText().toInt()
    }

    /**
     * For example, to find the power level of the fuel cell at 3,5 in a grid with serial number 8:
     *
     * The rack ID is 3 + 10 = 13.
     * The power level starts at 13 * 5 = 65.
     * Adding the serial number produces 65 + 8 = 73.
     * Multiplying by the rack ID produces 73 * 13 = 949.
     * The hundreds digit of 949 is 9.
     * Subtracting 5 produces 9 - 5 = 4.
     */
    @Test
    fun testDay11() {
        val cell = FuelCell(33, 45)

        assertEquals(43, rackId(cell))
        assertEquals(1935, initialPower(cell))
        assertEquals(1953, increasePower(initialPower(cell)))
        assertEquals(83979, multiply(increasePower(initialPower(cell)), rackId(cell)))
        assertEquals(9, hundreds(multiply(increasePower(initialPower(cell)), rackId(cell))))
        assertEquals(4, subtract(hundreds(multiply(increasePower(initialPower(cell)), rackId(cell)))))
    }

    @Test
    fun testDay11_GridPower() {
        val cell = FuelCell(33, 45)
        assertEquals(29, gridPower(cell, gridSize = 3))
    }

    @Test
    fun testDay11_GridPower_Bounds() {
        var cell = FuelCell(33, 297)
        assertEquals(3, gridPower(cell, gridSize = 3))

        cell = FuelCell(33, 298)
        assertEquals(-1, gridPower(cell, gridSize = 3))

        cell = FuelCell(297, 33)
        assertEquals(5, gridPower(cell, gridSize = 3))

        cell = FuelCell(298, 33)
        assertEquals(-1, gridPower(cell, gridSize = 3))

        cell = FuelCell(297, 297)
        assertEquals(0, gridPower(cell, gridSize = 3))

        cell = FuelCell(298, 298)
        assertEquals(-1, gridPower(cell, gridSize = 3))
    }
}