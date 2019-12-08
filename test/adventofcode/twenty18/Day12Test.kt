package adventofcode.twenty18

import org.junit.Assert.*
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2019/12/08 12:58
 */
class Day12Test {

    var DAY12_INPUT_SMALL = "src/res/day12_input_small"

    var DAY12_INITIAL_CONDITIONS = "#..#.#..##......###...###"

    val day12 = Day12()

    @Test
    fun testSum() {
        var initial = "#..##...."
        assertEquals(7, initial.sumPlants(0))

        initial = ".#....##....#####...#######....#.#..##."
        assertEquals(325, initial.sumPlants(3))
    }

    @Test
    fun testInitialConditions() {
        assertFalse(day12.hasEnoughLeadingPots(DAY12_INITIAL_CONDITIONS))
        assertFalse(day12.hasEnoughTrailingPots(DAY12_INITIAL_CONDITIONS))

        assertTrue(day12.hasEnoughLeadingPots(".....$DAY12_INITIAL_CONDITIONS"))
        assertTrue(day12.hasEnoughTrailingPots("$DAY12_INITIAL_CONDITIONS....."))

        assertTrue(day12.hasEnoughLeadingPots(".....$DAY12_INITIAL_CONDITIONS....."))
        assertTrue(day12.hasEnoughTrailingPots(".....$DAY12_INITIAL_CONDITIONS....."))
    }

    @Test
    fun testReadNotes() {
        val readInput = day12.readInput(DAY12_INPUT_SMALL)

        assertEquals(14, readInput.size)
        assertEquals("...##", readInput[0])
        assertEquals(".####", readInput[6])
        assertEquals("####.", readInput[13])
    }

    /**
     *
     */
    @Test
    fun testExample() {
        val notes = day12.readInput(DAY12_INPUT_SMALL)
        val currentState = DAY12_INITIAL_CONDITIONS

        val step1 = day12.nextGeneration(".....$currentState....",notes)
        val step2 = day12.nextGeneration(".....$step1....",notes)
        val step3 = day12.nextGeneration(".....$step2....",notes)
        val step4 = day12.nextGeneration(".....$step3....",notes)

        assertTrue(step1.contains("#...#....#.....#..#..#..#"))
        assertTrue(step2.contains("##..##...##....#..#..#..##"))
        assertTrue(step3.contains("#.#...#..#.#....#..#..#...#"))
        assertTrue(step4.contains("#.#..#...#.#...#..#..##..##"))
    }

    @Test
    fun testNextGeneration() {
        assertEquals(325, day12.generate(day12.readInput(DAY12_INPUT_SMALL), DAY12_INITIAL_CONDITIONS, 20))
    }

    @Test
    fun testSubstring() {
        val test1 = ".....#....#...#......"

        assertEquals("#....#...#", day12.trim(test1))
    }
}