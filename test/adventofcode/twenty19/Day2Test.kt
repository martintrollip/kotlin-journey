package adventofcode.twenty19

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2019/12/02 07:24
 */
class Day2Test {
    private val DAY2_INPUT_SMALL = "src/res/twenty19/day2_input_small"

    private var day2: Day2 = Day2(DAY2_INPUT_SMALL)
    private val computer = IntcodeComputer()

    @Before
    fun before() {
        day2 = Day2(DAY2_INPUT_SMALL)
    }

    @Test
    fun testReading() {
        assertEquals(12, day2.readInput().size)
    }

    /**
     * An Intcode program is a list of integers separated by commas (like 1,0,0,3,99). To run one, start by looking at the first integer (called position 0).
     * Here, you will find an opcode - either 1, 2, or 99. The opcode indicates what to do; for example, 99 means that the program is finished and should
     * immediately halt. Encountering an unknown opcode means something went wrong.
     */
    @Test
    fun testOpcodes() {
        assertEquals(IntcodeComputer.Opcode.ADD, computer.getOpcode(1))
        assertEquals(IntcodeComputer.Opcode.MULTIPLY, computer.getOpcode(2))
        assertEquals(IntcodeComputer.Opcode.TERMINATE, computer.getOpcode(99))
        assertEquals(IntcodeComputer.Opcode.OUTPUT, computer.getOpcode(1204))
        assertEquals(IntcodeComputer.Opcode.JUMP_TRUE, computer.getOpcode(5))
    }

    /**
     * Opcode 1 adds together numbers read from two positions and stores the result in a third position. The three integers immediately
     * after the opcode tell you these three positions - the first two indicate the positions from which you should read the input values,
     * and the third indicates the position at which the output should be stored.
     */
    @Test
    fun testAdd() {
        assertEquals(10, computer.add(6, 4))
    }

    /**
    Opcode 2 works exactly like opcode 1, except it multiplies the two inputs instead of adding them. Again, the three integers after the
    opcode indicate where the inputs and outputs are, not their values.
     */
    @Test
    fun testMultiply() {
        assertEquals(10, computer.multiply(5, 2))
    }

    /**
     * 1,9,10,3,
     * 2,3,11,0,
     * 99,30,40,50
     */
    @Test
    fun testBuildInstructions() {
        var instructionAt = computer.getInstructionAt(day2.readInput(), 0)

        assertEquals(IntcodeComputer.Opcode.ADD, instructionAt.type)
        assertEquals(9, instructionAt.a)
        assertEquals(10, instructionAt.b)
        assertEquals(3, instructionAt.result)

        instructionAt = computer.getInstructionAt(day2.readInput(), 4)
        assertEquals(IntcodeComputer.Opcode.MULTIPLY, instructionAt.type)
        assertEquals(3, instructionAt.a)
        assertEquals(11, instructionAt.b)
        assertEquals(0, instructionAt.result)

        instructionAt = computer.getInstructionAt(day2.readInput(), 8)
        assertEquals(IntcodeComputer.Opcode.TERMINATE, instructionAt.type)
        assertEquals(-1, instructionAt.a)
        assertEquals(-1, instructionAt.b)
        assertEquals(-1, instructionAt.result)
    }

    /**
     * For example, suppose you have the following program:
     * 1,9,10,3,2,3,11,0,99,30,40,50
     *
     * It will produce 3500 stored at position 0:
     */
    @Test
    fun testExecution() {
        assertEquals(3500, computer.execute(day2.readInput())[0])
    }

    /**
     *     1,0,0,0,99 becomes 2,0,0,0,99 (1 + 1 = 2).
     *     2,3,0,3,99 becomes 2,3,0,6,99 (3 * 2 = 6).
     *     2,4,4,5,99,0 becomes 2,4,4,5,99,9801 (99 * 99 = 9801).
     *     1,1,1,4,99,5,6,0,99 becomes 30,1,1,4,2,5,6,0,99.
     */
    @Test
    fun testMoreExecutions() {
        assertEquals(2, IntcodeComputer().execute(arrayOf(1, 0, 0, 0, 99))[0])
        assertEquals(6, IntcodeComputer().execute(arrayOf(2, 3, 0, 3, 99))[3])
        assertEquals(9801, IntcodeComputer().execute(arrayOf(2, 4, 4, 5, 99, 0))[5])
        assertEquals(30, IntcodeComputer().execute(arrayOf(1, 1, 1, 4, 99, 5, 6, 0, 99))[0])
    }
}