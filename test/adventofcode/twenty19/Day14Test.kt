package adventofcode.twenty19

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Ignore
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2019/12/15 07:08
 */
class Day14Test {

    @Test
    fun testInput() {
        val day14 = Day14()
        val input = listOf(
                "10 ORE => 10 A",
                "1 ORE => 1 B",
                "7 A, 1 B => 1 C",
                "7 A, 1 C => 1 D",
                "7 A, 1 D => 1 E",
                "7 A, 1 E => 1 FUEL")

        val readInput = day14.readInput(input)
        val aList = readInput["A"]
        val dList = readInput["D"]
        val fuelList = readInput["FUEL"]

        assertEquals(6, readInput.size)

        //10 ORE => 10 A
        assertNotNull(aList)
        assertEquals(1, aList!!.ingredients.size)
        assertEquals("ORE", aList.ingredients[0].name)
        assertEquals(10, aList.ingredients[0].amount)
        assertEquals("A", aList.product.name)
        assertEquals(10, aList.product.amount)

        //7 A, 1 C => 1 D
        assertEquals(2, dList!!.ingredients.size)
        assertEquals("A", dList.ingredients[0].name)
        assertEquals(7, dList.ingredients[0].amount)
        assertEquals("C", dList.ingredients[1].name)
        assertEquals(1, dList.ingredients[1].amount)
        assertEquals("D", dList.product.name)
        assertEquals(1, dList.product.amount)

        //7 A, 1 E => 1 FUEL
        assertEquals(2, fuelList!!.ingredients.size)
        assertEquals("A", fuelList.ingredients[0].name)
        assertEquals(7, fuelList.ingredients[0].amount)
        assertEquals("E", fuelList.ingredients[1].name)
        assertEquals(1, fuelList.ingredients[1].amount)
        assertEquals("FUEL", fuelList.product.name)
        assertEquals(1, fuelList.product.amount)
    }


    /**
     * The first two reactions use only ORE as inputs; they indicate that you can produce as much of chemical A as you want
     * (in increments of 10 units, each 10 costing 10 ORE) and as much of chemical B as you want (each costing 1 ORE).
     *
     * To produce 1 FUEL, a total of 31 ORE is required: 1 ORE to produce 1 B,
     * then 30 more ORE to produce the 7 + 7 + 7 + 7 = 28 A (with 2 extra A wasted) required in the reactions to convert
     * the B into C, C into D, D into E, and finally E into FUEL  (30 A is produced because its reaction requires that it is created in increments of 10.)
     */
    @Test
    fun testSimpleExample() {
        val day14 = Day14()
        val input = listOf(
                "10 ORE => 10 A",
                "1 ORE => 1 B",
                "7 A, 1 B => 1 C",
                "7 A, 1 C => 1 D",
                "7 A, 1 D => 1 E",
                "7 A, 1 E => 1 FUEL")

        assertEquals(31, day14.oreRequired(day14.readInput(input)))
    }

    /**
     * 13312 ORE for 1 FUEL:
     */
    @Test
    fun testBigExample() {
        val day14 = Day14()
        val input = listOf(
                "157 ORE => 5 NZVS",
                "165 ORE => 6 DCFZ",
                "44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL",
                "12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ",
                "179 ORE => 7 PSHF",
                "177 ORE => 5 HKGWZ",
                "7 DCFZ, 7 PSHF => 2 XJWVT",
                "165 ORE => 2 GPVTF",
                "3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT")

        assertEquals(13312 , day14.oreRequired(day14.readInput(input)))
    }

    /**
     * After collecting ORE for a while, you check your cargo hold: 1 trillion (1000000000000) units of ORE.
     *
     * The 13312 ORE-per-FUEL example could produce 82892753 FUEL.
     *
     * @Ignore takes a minute
     */
    @Test
    @Ignore
    fun testBigExamplePart2() {
        val day14 = Day14()
        val input = listOf(
                "157 ORE => 5 NZVS",
                "165 ORE => 6 DCFZ",
                "44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL",
                "12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ",
                "179 ORE => 7 PSHF",
                "177 ORE => 5 HKGWZ",
                "7 DCFZ, 7 PSHF => 2 XJWVT",
                "165 ORE => 2 GPVTF",
                "3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT")

        assertEquals(82892753 , day14.maxFuelPossible(day14.readInput(input)))
    }
}