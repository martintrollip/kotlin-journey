package adventofcode.twenty18

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * @author Martin Trollip ***REMOVED***
 * @since 2019/01/02 11:04
 */
class Day19Test {

    var DAY19_INPUT_SMALL = "src/res/day19_input_small"

    var pointer: Pointer? = null
    var sample: ArrayList<NamedInstruction>? = null
    var newRegister = listOf(Register("0"), Register("1"), Register("2"), Register("3"), Register("4"), Register("5"))

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
        assertEquals("seti", (sample?.get(0) as NamedInstruction).opCodeName)
        assertEquals(5, (sample?.get(0) as NamedInstruction).a)
        assertEquals(0, (sample?.get(0) as NamedInstruction).b)
        assertEquals(1, (sample?.get(0) as NamedInstruction).c)

        assertTrue(sample?.get(4) is NamedInstruction)
        assertEquals("addr", (sample?.get(3) as NamedInstruction).opCodeName)
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
}