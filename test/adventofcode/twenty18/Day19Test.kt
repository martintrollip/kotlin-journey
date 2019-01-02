package adventofcode.twenty18

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * @author Martin Trollip <martint@discovery.co.za>
 * @since 2019/01/02 11:04
 */
class Day19Test {

    var DAY19_INPUT_SMALL = "src/res/day19_input_small"
    var DAY19_INPUT = "src/res/day19_input"

    var pointer: Pointer? = null
    var sample: Map<Int, NamedInstruction>? = null

    @Before
    fun setup() {
        sample = readDay19Input(DAY19_INPUT_SMALL)
        pointer = readDay19Pointer(DAY19_INPUT_SMALL)

        assertNotNull(sample)
    }

    @Test
    fun testInput() {
        assertEquals(7, sample?.size)

        assertTrue(pointer is Pointer)
        assertEquals(0, pointer!!.value)

        assertTrue(sample?.get(0) is NamedInstruction)
        assertEquals("seti", (sample?.get(0) as NamedInstruction).opcode.name)
        assertEquals(5, (sample?.get(0) as NamedInstruction).a)
        assertEquals(0, (sample?.get(0) as NamedInstruction).b)
        assertEquals(1, (sample?.get(0) as NamedInstruction).c)

        assertTrue(sample?.get(4) is NamedInstruction)
        assertEquals("addr", (sample?.get(3) as NamedInstruction).opcode.name)
        assertEquals(1, (sample?.get(3) as NamedInstruction).a)
        assertEquals(2, (sample?.get(3) as NamedInstruction).b)
        assertEquals(3, (sample?.get(3) as NamedInstruction).c)
    }

    @Test
    fun testInstructions() {
        val execute = execute(pointer!!, sample!!)

        assertEquals(6, execute[0].value)
        assertEquals(5, execute[1].value)
        assertEquals(6, execute[2].value)
        assertEquals(0, execute[3].value)
        assertEquals(0, execute[4].value)
        assertEquals(9, execute[5].value)
    }

    @Test
    fun testPart1() {
        println(
                measureTimeMillis {
                    val part1 = part1(readDay19Pointer(DAY19_INPUT), readDay19Input(DAY19_INPUT))
                    assertEquals(984, part1)
                }.toString() + "ms")
        //old time 7172ms
        //new time 5714ms (removed for loop, let all operations modify the same instance of the register)
        //newest time 404ms (toString is expensive!)
        //newest time 318ms (get only once registers[pointer.address])
        //newest time 307ms (use int indexes on map)
        //newest time 292ms (move pointer register assignment out of for loop)
        //newest time 230ms (assign operations beforehand)
    }

    @Test
    fun testPart2() {
        println(
                measureTimeMillis {
                    val part1 = part2(readDay19Pointer(DAY19_INPUT), readDay19Input(DAY19_INPUT))
                    assertEquals(984, part1)
                }.toString() + "ms")
    }
}