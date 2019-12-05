package adventofcode.twenty19

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2019/12/05 06:53
 */
class Day5Test {

    private val DAY5_INPUT_SMALL = "src/res/twenty19/day5_input_small"

    private val computer = IntcodeComputer()

    /**
     * Opcode 3 takes a single integer as input and saves it to the address given by its only parameter.
     *
     * Opcode 4 outputs the value of its only parameter.
     *
     */
    @Test
    fun testOpcodes() {
        Assert.assertEquals(IntcodeComputer.Opcode.STORE, computer.getOpcode(3))
        Assert.assertEquals(IntcodeComputer.Opcode.OUTPUT, computer.getOpcode(4))
    }

    /**
     * Opcode 3 takes a single integer as input and saves it to the address given by its only parameter.
     * For example, the instruction 3,50 would take an input value and store it at address 50.
     */
    @Test
    fun testStore() {
        val memory = Array(100) { it }
        memory[0] = computer.store(1)
        memory[50] = computer.store(1234)
        memory[99] = computer.store(9999)

        assertEquals(1, memory[0])
        assertEquals(1234, memory[50])
        assertEquals(9999, memory[99])
    }

    /**
     *  Opcode 4 outputs the value of its only parameter.
     *  For example, the instruction 4,50 would output the value at address 50.
     */
    @Test
    fun testOutput() {
        assertEquals(50, computer.output(50))
        assertEquals(0, computer.output(0))
        assertEquals(99, computer.output(99))
    }

    /**
     * The program 3,0,4,0,99 outputs whatever it gets as input, then halts.
     */
    @Test
    fun testExecute() {
        val memory = arrayOf(3, 0, 4, 0, 99)
        computer.execute(memory, 100)
    }


    /**
     * the opcode is the rightmost two digits of the first value in an instruction.
     * Parameter modes are single digits, one per parameter, read right-to-left from the opcode:
     *
     */
    @Test
    fun testOpcodesWithParameters() {
        Assert.assertEquals(IntcodeComputer.Opcode.ADD, computer.getOpcode(1))
        Assert.assertEquals(IntcodeComputer.Opcode.MULTIPLY, computer.getOpcode(2))
        Assert.assertEquals(IntcodeComputer.Opcode.STORE, computer.getOpcode(3))
        Assert.assertEquals(IntcodeComputer.Opcode.OUTPUT, computer.getOpcode(4))
        Assert.assertEquals(IntcodeComputer.Opcode.TERMINATE, computer.getOpcode(99))

        Assert.assertEquals(IntcodeComputer.Opcode.ADD, computer.getOpcode(1201))
        Assert.assertEquals(IntcodeComputer.Opcode.MULTIPLY, computer.getOpcode(1202))
        Assert.assertEquals(IntcodeComputer.Opcode.STORE, computer.getOpcode(1203))
        Assert.assertEquals(IntcodeComputer.Opcode.OUTPUT, computer.getOpcode(1204))
        Assert.assertEquals(IntcodeComputer.Opcode.TERMINATE, computer.getOpcode(1299))
    }

    /**
     * 0 is position mode
     * 1 is immediate mode
     *
     * the first parameter's mode is in the hundreds digit,
     * the second parameter's mode is in the thousands digit,
     * the third parameter's mode is in the ten-thousands digit, and so on.
     *
     * Any missing modes are 0.
     *
     * For example, consider the program 1002,4,3,4,33.
     *
     * The first instruction, 1002,4,3,4, is a multiply instruction - the rightmost two digits of the first value, 02, indicate opcode 2, multiplication.
     * Then, going right to left, the parameter modes are 0 (hundreds digit), 1 (thousands digit), and 0 (ten-thousands digit, not present and therefore zero):
     */
    @Test
    fun testGetModes() {
        Assert.assertEquals(IntcodeComputer.Mode.IMMEDIATE, computer.getMode(11101, 0))
        Assert.assertEquals(IntcodeComputer.Mode.IMMEDIATE, computer.getMode(11101, 1))
        Assert.assertEquals(IntcodeComputer.Mode.IMMEDIATE, computer.getMode(11101, 2))

        Assert.assertEquals(IntcodeComputer.Mode.POSITION, computer.getMode(1001, 0))
        Assert.assertEquals(IntcodeComputer.Mode.IMMEDIATE, computer.getMode(1001, 1))
        Assert.assertEquals(IntcodeComputer.Mode.POSITION, computer.getMode(1001, 2))

        Assert.assertEquals(IntcodeComputer.Mode.IMMEDIATE, computer.getMode(101, 0))
        Assert.assertEquals(IntcodeComputer.Mode.POSITION, computer.getMode(101, 1))
        Assert.assertEquals(IntcodeComputer.Mode.POSITION, computer.getMode(101, 2))

        Assert.assertEquals(IntcodeComputer.Mode.POSITION, computer.getMode(1, 0))
        Assert.assertEquals(IntcodeComputer.Mode.POSITION, computer.getMode(1, 1))
        Assert.assertEquals(IntcodeComputer.Mode.POSITION, computer.getMode(1, 2))

        Assert.assertEquals(IntcodeComputer.Mode.POSITION, computer.getMode(99, 0))
        Assert.assertEquals(IntcodeComputer.Mode.POSITION, computer.getMode(99, 1))
        Assert.assertEquals(IntcodeComputer.Mode.POSITION, computer.getMode(99, 2))
    }

    /**
     * ABCDE
     *  1002
     *
     * DE - two-digit opcode,      02 == opcode 2
     * C - mode of 1st parameter,  0 == position mode
     * B - mode of 2nd parameter,  1 == immediate mode
     * A - mode of 3rd parameter,  0 == position mode,
     *
     */
    @Test
    fun testOpcodeWithMode() {
        Assert.assertEquals(IntcodeComputer.Opcode.MULTIPLY, computer.getOpcode(1002))
        Assert.assertEquals(IntcodeComputer.Mode.POSITION, computer.getMode(1002, 0))
        Assert.assertEquals(IntcodeComputer.Mode.IMMEDIATE, computer.getMode(1002, 1))
        Assert.assertEquals(IntcodeComputer.Mode.POSITION, computer.getMode(1002, 2))
    }

    /**
     * 1002,4,3,4,33
     *
     * This instruction multiplies its first two parameters.
     *
     * The first parameter, 4 in position mode, works like it did before - its value is the value stored at address 4 (33).
     * The second parameter, 3 in immediate mode, simply has value 3.
     *
     * The result of this operation, 33 * 3 = 99, is written according to the third parameter, 4 in position mode,
     * which also works like it did before - 99 is written to address 4.
     */
    @Test
    fun testOpcodeWithModesExecution() {
        val memory = arrayOf(1002, 4, 3, 4, 33)
        assertEquals(99, computer.execute(memory)[4])
    }
}





