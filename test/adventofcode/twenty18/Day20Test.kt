package adventofcode.twenty18

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

/**
 * @author Martin Trollip
 * @since 2019/01/03 12:08
 */
class Day20Test {

    var DAY20_INPUT_SIMPLE = "^WNE\$"
    var DAY20_INPUT_EXAMPLE_1 = "^ENWWW(NEEE|SSE(EE|N))\$"
    var DAY20_INPUT_EXAMPLE_2 = "^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN\$"
    var DAY20_INPUT_EXAMPLE_3 = "^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))\$"
    var DAY20_INPUT_EXAMPLE_4 = "^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))\$"

    @Test
    fun testSimpleCase() {
        val parsed = parse(DAY20_INPUT_SIMPLE)
        assertEquals(3, parsed.maxByOrNull  { it.value }!!.value)
    }

    @Test
    fun testExample1() {
        val parsed = parse(DAY20_INPUT_EXAMPLE_1)
        assertEquals(10, parsed.maxByOrNull  { it.value }!!.value)
    }

    @Test
    fun testExample2() {
        val parsed = parse(DAY20_INPUT_EXAMPLE_2)
        assertEquals(18, parsed.maxByOrNull  { it.value }!!.value)
    }

    @Test
    fun testExample3() {
        val parsed = parse(DAY20_INPUT_EXAMPLE_3)
        assertEquals(23, parsed.maxByOrNull  { it.value }!!.value)
    }

    @Test
    fun testExample4() {
        val parsed = parse(DAY20_INPUT_EXAMPLE_4)
        assertEquals(31, parsed.maxByOrNull  { it.value }!!.value)
    }

    @Test
    fun testPart1AndPart2() {
        val parsed = parse(File(DAY20_INPUT).readText())
        println("The largest number of doors you would be required to pass through to reach a room? ${roomsPart1(parsed)}")
        println("How many rooms have a shortest path from your current location that pass through at least 1000 doors? ${roomsPart2(parsed)}")
        assertEquals(4214, roomsPart1(parsed))
        assertEquals(8492, roomsPart2(parsed))
    }

}