package adventofcode.twenty18

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * @author Martin Trollip <martint@discovery.co.za>
 * @since 2018/12/18 07:40
 */
class Day16Test {

    /**
     * Before: [3, 2, 1, 1]
     * 9 A=2 B=1 C=2
     * After:  [3, 2, 2, 1]
     */

    var DAY16_INPUT_SMALL = "src/res/day16_input_small"
    var sample: Sample? = null

    @Before
    fun setup() {
        sample = readInput(DAY16_INPUT_SMALL).first()
    }

    @Test
    fun testAddr() {
        val before = sample?.before
        val (opCodeName, a, b, c) = sample?.instruction!!

        //Register A=2 + Register B=1 -> 1 + 2 -> 3
        val after = Addr().operation(a, b, c, before!!)

        assertTrue("Expected register C to have answer", after[c].value == 3)
        assertTrue("Expected register 0 to remain unchanged", after[0].value == 3)
        assertTrue("Expected register 1 to remain unchanged", after[1].value == 2)
        assertTrue("Expected register 3 to remain unchanged", after[3].value == 1)
    }

    @Test
    fun testAddi() {
        val before = sample?.before
        val (opCodeName, a, b, c) = sample?.instruction!!

        //Register A=2 + value B=1 -> 1 + 1 -> 2
        val after = Addi().operation(a, b, c, before!!)

        assertTrue("Expected register C to have answer", after[c].value == 2)
        assertTrue("Expected register 0 to remain unchanged", after[0].value == 3)
        assertTrue("Expected register 1 to remain unchanged", after[1].value == 2)
        assertTrue("Expected register 3 to remain unchanged", after[3].value == 1)
    }

    @Test
    fun testMulr() {
        val before = sample?.before
        val (opCodeName, a, b, c) = sample?.instruction!!

        //Register A=2 * Register B=1 -> 1 * 2 -> 2
        val after = Mulr().operation(a, b, c, before!!)

        assertTrue("Expected register C to have answer", after[c].value == 2)
        assertTrue("Expected register 0 to remain unchanged", after[0].value == 3)
        assertTrue("Expected register 1 to remain unchanged", after[1].value == 2)
        assertTrue("Expected register 3 to remain unchanged", after[3].value == 1)
    }

    @Test
    fun testMuli() {
        val before = sample?.before
        val (opCodeName, a, b, c) = sample?.instruction!!

        //Register A=2 * value B=1 -> 1 * 1 -> 1
        val after = Muli().operation(a, b, c, before!!)

        assertTrue("Expected register C to have answer", after[c].value == 1)
        assertTrue("Expected register 0 to remain unchanged", after[0].value == 3)
        assertTrue("Expected register 1 to remain unchanged", after[1].value == 2)
        assertTrue("Expected register 3 to remain unchanged", after[3].value == 1)
    }

    @Test
    fun testBanr() {
        val before = sample?.before
        val (opCodeName, a, b, c) = sample?.instruction!!

        //Register A=2 and Register B=1 -> 1 and 2 -> 0
        var after = Banr().operation(a, b, c, before!!)

        assertTrue("Expected register C to have answer", after[c].value == 0)
        assertTrue("Expected register 0 to remain unchanged", after[0].value == 3)
        assertTrue("Expected register 1 to remain unchanged", after[1].value == 2)
        assertTrue("Expected register 3 to remain unchanged", after[3].value == 1)

        //Register A=2 and Register B=1 -> 3 and 3 -> 1
        before[a].value = 4
        before[b].value = 4
        after = Banr().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 4)
    }

    @Test
    fun testBani() {
        val before = sample?.before
        var (opCodeName, a, b, c) = sample?.instruction!!

        //Register A=2 * value B=1 -> 1 and 1 -> 1
        var after = Bani().operation(a, b, c, before!!)

        assertTrue("Expected register C to have answer", after[c].value == 1)
        assertTrue("Expected register 0 to remain unchanged", after[0].value == 3)
        assertTrue("Expected register 1 to remain unchanged", after[1].value == 2)
        assertTrue("Expected register 3 to remain unchanged", after[3].value == 1)

        //Register A=2 and Register B=1 -> 3 and 3 -> 1
        before[a].value = 4
        before[b].value = 532 //Should be ignored
        b = 4
        after = Bani().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 4)
    }

    @Test
    fun testBorr() {
        val before = sample?.before
        var (opCodeName, a, b, c) = sample?.instruction!!

        //Register A=2 and Register B=1 -> 1 or 2 -> 3
        var after = Borr().operation(a, b, c, before!!)

        assertTrue("Expected register C to have answer", after[c].value == 3)
        assertTrue("Expected register 0 to remain unchanged", after[0].value == 3)
        assertTrue("Expected register 1 to remain unchanged", after[1].value == 2)
        assertTrue("Expected register 3 to remain unchanged", after[3].value == 1)

        //Register A=2 and Register B=1 -> 4 or 5 -> 5
        before[a].value = 4
        before[b].value = 5
        after = Borr().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 5)
    }

    @Test
    fun testBori() {
        val before = sample?.before
        var (opCodeName, a, b, c) = sample?.instruction!!

        //Register A=2 * value B=1 -> 1 or 1 -> 1
        var after = Bori().operation(a, b, c, before!!)

        assertTrue("Expected register C to have answer", after[c].value == 1)
        assertTrue("Expected register 0 to remain unchanged", after[0].value == 3)
        assertTrue("Expected register 1 to remain unchanged", after[1].value == 2)
        assertTrue("Expected register 3 to remain unchanged", after[3].value == 1)

        //Register A=2 and value B=5 -> 4 or 5 -> 5
        before[a].value = 4
        before[b].value = 53235
        b = 5
        after = Bori().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 5)
    }

    @Test
    fun TestSetr() {
        val before = sample?.before
        var (opCodeName, a, b, c) = sample?.instruction!!

        //Register A=2 copied to RegisterC=2 -> 3 copied to C -> 1
        var after = Setr().operation(a, b, c, before!!)

        assertTrue("Expected register C to have answer", after[c].value == 1)
        assertTrue("Expected register 0 to remain unchanged", after[0].value == 3)
        assertTrue("Expected register 1 to remain unchanged", after[1].value == 2)
        assertTrue("Expected register 3 to remain unchanged", after[3].value == 1)

        before[b].value = 3232
        after = Setr().operation(0, b, c, before!!)
        assertTrue("Expected register C to have answer", after[c].value == 3)
        assertTrue("Expected register 0 to remain unchanged", after[0].value == 3)
        assertTrue("Expected register 1 to remain unchanged", after[1].value == 3232)
        assertTrue("Expected register 3 to remain unchanged", after[3].value == 1)
    }

    @Test
    fun testSeti() {
        val before = sample?.before
        var (opCodeName, a, b, c) = sample?.instruction!!

        //value A=2 copied to RegisterC=2 -> 2 copied to C -> 2
        var after = Seti().operation(a, b, c, before!!)

        assertTrue("Expected register C to have answer", after[c].value == 2)
        assertTrue("Expected register 0 to remain unchanged", after[0].value == 3)
        assertTrue("Expected register 1 to remain unchanged", after[1].value == 2)
        assertTrue("Expected register 3 to remain unchanged", after[3].value == 1)

        //Register A=2 and value B=5 -> 4 or 5 -> 5
        before[a].value = 4242
        before[b].value = 53235
        a = 5
        b = 6
        after = Seti().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 5)
    }

    @Test
    fun testGtir() {
        val before = sample?.before
        var (opCodeName, a, b, c) = sample?.instruction!!

        //Value A=2 greater than Register B=1 -> 2 > 2 -> 0
        var after = Gtir().operation(a, b, c, before!!)

        assertTrue("Expected register C to have answer", after[c].value == 0)
        assertTrue("Expected register 0 to remain unchanged", after[0].value == 3)
        assertTrue("Expected register 1 to remain unchanged", after[1].value == 2)
        assertTrue("Expected register 3 to remain unchanged", after[3].value == 1)

        //Value A=2 greater than Register B=1 -> 100 > 2 -> 1
        a = 100
        after = Gtir().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 1)

        //Value A=2 greater than Register B=1 -> 0 > 100 -> 0
        a = 0
        before[b].value = 100
        after = Gtir().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 0)
    }

    @Test
    fun testGtri() {
        val before = sample?.before
        var (opCodeName, a, b, c) = sample?.instruction!!

        //Register A=2 greater than value B=1 -> 1 > 1 -> 0
        var after = Gtri().operation(a, b, c, before!!)

        assertTrue("Expected register C to have answer", after[c].value == 0)
        assertTrue("Expected register 0 to remain unchanged", after[0].value == 3)
        assertTrue("Expected register 1 to remain unchanged", after[1].value == 2)
        assertTrue("Expected register 3 to remain unchanged", after[3].value == 1)

        //Register A=2 greater than value B=1 -> 100 > 1 -> 1
        before[a].value = 100
        after = Gtri().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 1)

        //Register A=2 greater than value B=1 -> 0 > 1 -> 0
        before[a].value = 0
        after = Gtri().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 0)
    }

    @Test
    fun testGtrr() {
        val before = sample?.before
        var (opCodeName, a, b, c) = sample?.instruction!!

        //Register A=2 greater than Register B=1 -> 1 > 2 -> 0
        var after = Gtrr().operation(a, b, c, before!!)

        assertTrue("Expected register C to have answer", after[c].value == 0)
        assertTrue("Expected register 0 to remain unchanged", after[0].value == 3)
        assertTrue("Expected register 1 to remain unchanged", after[1].value == 2)
        assertTrue("Expected register 3 to remain unchanged", after[3].value == 1)

        //Register A=2 greater than Register B=1 -> 100 > 2 -> 1
        before[a].value = 100
        after = Gtrr().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 1)

        //Register A=2 greater than Register B=1 -> 0 > 2 -> 0
        before[a].value = 0
        after = Gtrr().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 0)
    }

    @Test
    fun testEqir() {
        val before = sample?.before
        var (opCodeName, a, b, c) = sample?.instruction!!

        //Value A=2 equals Register B=1 -> 2 == 2 -> 0
        var after = Eqir().operation(a, b, c, before!!)

        assertTrue("Expected register C to have answer", after[c].value == 1)
        assertTrue("Expected register 0 to remain unchanged", after[0].value == 3)
        assertTrue("Expected register 1 to remain unchanged", after[1].value == 2)
        assertTrue("Expected register 3 to remain unchanged", after[3].value == 1)

        //Value A=2 equals Register B=1 -> 100 == 2 -> 1
        a = 100
        after = Eqir().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 0)

        //Value A=2 equals Register B=1 -> 0 == 2 -> 0
        a = 0
        after = Eqir().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 0)

        //Value A=2 equals Register B=1 -> 2 == 2 -> 0
        a = 2
        after = Eqir().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 1)
    }

    @Test
    fun testEqri() {
        val before = sample?.before
        var (opCodeName, a, b, c) = sample?.instruction!!

        //Register A=2 equals value B=1 -> 1 == 1 -> 1
        var after = Eqri().operation(a, b, c, before!!)

        assertTrue("Expected register C to have answer", after[c].value == 1)
        assertTrue("Expected register 0 to remain unchanged", after[0].value == 3)
        assertTrue("Expected register 1 to remain unchanged", after[1].value == 2)
        assertTrue("Expected register 3 to remain unchanged", after[3].value == 1)

        //Register A=2 equals value B=1 -> 100 == 1 -> 0
        before[a].value = 100
        after = Eqri().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 0)

        //Register A=2 equals value B=1 -> 0 == 1-> 0
        before[a].value = 0
        after = Eqri().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 0)
    }



    @Test
    fun testEqrr() {
        val before = sample?.before
        var (opCodeName, a, b, c) = sample?.instruction!!

        //Register A=2 equals Register B=1 -> 1 == 2 -> 0
        var after = Eqrr().operation(a, b, c, before!!)

        assertTrue("Expected register C to have answer", after[c].value == 0)
        assertTrue("Expected register 0 to remain unchanged", after[0].value == 3)
        assertTrue("Expected register 1 to remain unchanged", after[1].value == 2)
        assertTrue("Expected register 3 to remain unchanged", after[3].value == 1)

        //Register A=2 equals Register B=1 -> 100 == 2 -> 0
        before[a].value = 100
        after = Eqrr().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 1)

        //Register A=2 equals Register B=1 -> 2 == 2 -> 1
        before[a].value = 2
        after = Eqrr().operation(a, b, c, before)

        assertTrue("Expected register C to have answer", after[c].value == 1)
    }
}