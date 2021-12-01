package adventofcode.twenty20

import junit.framework.Assert.*
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/12/15 14:16
 */
class Day14Test {
    private val DAY14_INPUT_SMALL = "src/res/twenty20/day14_input_small"
    private val DAY14_INPUT_SMALL2 = "src/res/twenty20/day14_input_small2"
    private val DAY14_INPUT = "src/res/twenty20/day14_input"

    @Test
    fun day14_toBinaryString() {
        assertEquals("000000000000000000000000000000001011", 11.convertToBinary())
        assertEquals("000000000000000000000000000001100101", 101.convertToBinary())
        assertEquals("000000000000000000000000000000000000", 0.convertToBinary())
        assertEquals("000000000000000000000000000001001001", 73.convertToBinary())
        assertEquals("000000000000000000000000000001000000", 64.convertToBinary())
    }

    @Test
    fun day14_toDecimal() {
        assertEquals(11, "000000000000000000000000000000001011".convertToDecimal())
        assertEquals(101, "000000000000000000000000000001100101".convertToDecimal())
        assertEquals(0, "000000000000000000000000000000000000".convertToDecimal())
        assertEquals(73, "000000000000000000000000000001001001".convertToDecimal())
        assertEquals(64, "000000000000000000000000000001000000".convertToDecimal())
        assertEquals(22019146806, "010100100000011100011000010000110110".convertToDecimal())
    }

    @Test
    fun day14_applyMask() {
        val day14 = Day14(DAY14_INPUT_SMALL)
        assertEquals("000000000000000000000000000001001001", day14.applyMask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X".toCharArray(), "000000000000000000000000000000001011".toCharArray()))
        assertEquals("000000000000000000000000000001100101", day14.applyMask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X".toCharArray(), "000000000000000000000000000001100101".toCharArray()))
        assertEquals("000000000000000000000000000001000000", day14.applyMask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X".toCharArray(), "000000000000000000000000000000000000".toCharArray()))
    }

    @Test
    fun day14_generateNewMask() {
        val day14 = Day14(DAY14_INPUT_SMALL)
        assertEquals("000000000000000000000000000000X1101X", day14.applyMaskV2("000000000000000000000000000000X1001X".toCharArray(), "000000000000000000000000000000101010".toCharArray()))
        assertEquals("00000000000000000000000000000001X0XX", day14.applyMaskV2("00000000000000000000000000000000X0XX".toCharArray(), "000000000000000000000000000000011010".toCharArray()))
    }

    @Test
    fun day14_getCombos() {
        val day14 = Day14(DAY14_INPUT_SMALL)
        val memoryAddresses = day14.getMemoryAddresses("000000000000000000000000000000X1101X")
        assertEquals(4, memoryAddresses.size)
        assertTrue(memoryAddresses.contains("000000000000000000000000000000011010"))
        assertTrue(memoryAddresses.contains("000000000000000000000000000000011011"))
        assertTrue(memoryAddresses.contains("000000000000000000000000000000111010"))
        assertTrue(memoryAddresses.contains("000000000000000000000000000000111011"))

        val memoryAddresses2 = day14.getMemoryAddresses("00000000000000000000000000000001X0XX")
        assertEquals(8, memoryAddresses2.size)
        assertTrue(memoryAddresses2.contains("000000000000000000000000000000010000"))
        //Some check omitted
        assertTrue(memoryAddresses2.contains("000000000000000000000000000000011011"))
    }

    @Test
    fun day14_part1Small() {
        val day14 = Day14(DAY14_INPUT_SMALL)
        assertEquals(165, day14.part1())
    }

    @Test
    fun day14_part1() {
        val day14 = Day14(DAY14_INPUT)
        assertEquals(5055782549997, day14.part1())
    }

    @Test
    fun day14_part2Small() {
        val day14 = Day14(DAY14_INPUT_SMALL2)
        assertEquals(208, day14.part2())
    }

    @Test
    fun day14_part2() {
        val day14 = Day14(DAY14_INPUT)
        assertEquals(4795970362286, day14.part2())
    }
}
