package adventofcode.twenty19

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2019/12/01 06:16
 */
class Day1Test {
    private val DAY1_INPUT_SMALL = "src/res/twenty19/day1_input_small"

    /**
     * For a mass of 12, divide by 3 and round down to get 4, then subtract 2 to get 2.
     * For a mass of 14, dividing by 3 and rounding down still yields 4, so the fuel required is also 2.
     * For a mass of 1969, the fuel required is 654.
     * For a mass of 100756, the fuel required is 33583.
     */
    @Test
    fun testModuleWeight() {
        val day1 = Day1(DAY1_INPUT_SMALL)
        assertEquals(2, day1.fuelRequiredPerMass(12))
        assertEquals(2, day1.fuelRequiredPerMass(14))
        assertEquals(654, day1.fuelRequiredPerMass(1969))
        assertEquals(33583, day1.fuelRequiredPerMass(100756))
    }

    @Test
    fun testTotalModuleWeight() {
        val day1 = Day1(DAY1_INPUT_SMALL)
        assertEquals(2 + 2 + 654 + 33583, day1.totalFuelRequiredPerModule())
    }

    /**
     * A module of mass 14 requires 2 fuel. This fuel requires no further fuel (2 divided by 3 and rounded down is 0, which would call for a negative fuel), so the total fuel required is still just 2.
     * At first, a module of mass 1969 requires 654 fuel. Then, this fuel requires 216 more fuel (654 / 3 - 2). 216 then requires 70 more fuel, which requires 21 fuel, which requires 5 fuel, which requires no further fuel. So, the total fuel required for a module of mass 1969 is 654 + 216 + 70 + 21 + 5 = 966.
     * The fuel required by a module of mass 100756 and its fuel is: 33583 + 11192 + 3728 + 1240 + 411 + 135 + 43 + 12 + 2 = 50346.
     */
    @Test
    fun testModuleAndFuelWeight() {
        val day1 = Day1(DAY1_INPUT_SMALL)

        assertEquals(2, day1.fuelRequiredPerModuleAndFuel(12))
        assertEquals(2, day1.fuelRequiredPerModuleAndFuel(14))
        assertEquals(966, day1.fuelRequiredPerModuleAndFuel(1969))
        assertEquals(50346, day1.fuelRequiredPerModuleAndFuel(100756))
    }

    @Test
    fun testTotalModuleAndFuelWeight() {
        val day1 = Day1(DAY1_INPUT_SMALL)
        assertEquals(2 + 2 + 966 + 50346, day1.totalFuelRequiredPerModuleAndForFuel())
    }
}