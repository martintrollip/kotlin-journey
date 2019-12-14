package adventofcode.twenty19

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2019/12/14 09:45
 */

class Day13Test {

    @Test
    fun testGame() {
        val arcade = Arcade()
        val run = arcade.run()
        val ans = run.filter { it.value == 2 }.size

        assertEquals(280, ans)
    }
}
