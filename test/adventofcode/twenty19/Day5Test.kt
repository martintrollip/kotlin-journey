package adventofcode.twenty19

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2019/12/05 06:53
 */
class Day5Test {

    private val computer = IntcodeComputer()

    /**
     * Opcode 3 takes a single integer as input and saves it to the address given by its only parameter.
     * Opcode 4 outputs the value of its only parameter.
     * Opcode 5 is jump-if-true: if the first parameter is non-zero, it sets the instruction pointer to the value from the second parameter. Otherwise, it does nothing.
     * Opcode 6 is jump-if-false: if the first parameter is zero, it sets the instruction pointer to the value from the second parameter. Otherwise, it does nothing.
     * Opcode 7 is less than: if the first parameter is less than the second parameter, it stores 1 in the position given by the third parameter. Otherwise, it stores 0.
     * Opcode 8 is equals: if the first parameter is equal to the second parameter, it stores 1 in the position given by the third parameter. Otherwise, it stores 0.
     *
     */
    @Test
    fun testOpcodes() {
        Assert.assertEquals(IntcodeComputer.Opcode.STORE, computer.getOpcode(3))
        Assert.assertEquals(IntcodeComputer.Opcode.OUTPUT, computer.getOpcode(4))
        Assert.assertEquals(IntcodeComputer.Opcode.JUMP_TRUE, computer.getOpcode(5))
        Assert.assertEquals(IntcodeComputer.Opcode.JUMP_FALSE, computer.getOpcode(6))
        Assert.assertEquals(IntcodeComputer.Opcode.LESS_THAN, computer.getOpcode(7))
        Assert.assertEquals(IntcodeComputer.Opcode.EQUALS, computer.getOpcode(8))
    }

    /**
     * Opcode 3 takes a single integer as input and saves it to the address given by its only parameter.
     * For example, the instruction 3,50 would take an input value and store it at address 50.
     */
    @Test
    fun testStore() {
        val memory = Array(100) { it.toLong() }
        memory[0] = computer.store(1L)
        memory[50] = computer.store(1234L)
        memory[99] = computer.store(9999L)

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
     * Opcode 5 is jump-if-true: if the first parameter is non-zero, it sets the instruction pointer to the value from the second parameter.
     * Otherwise, it does nothing.
     */
    @Test
    fun testJumpIfTrue() {
        assertEquals(2, computer.jumptrue(1, 2, 3))
        assertEquals(9999, computer.jumptrue(1, 9999, 3))
        assertEquals(-1, computer.jumptrue(1, -1, 3))
        assertEquals(5, computer.jumptrue(-1, 5, 3))

        assertEquals(6, computer.jumptrue(0, 5, 3))
    }

    /**
     *  Opcode 6 is jump-if-false: if the first parameter is zero, it sets the instruction pointer to the value from the second parameter.
     *  Otherwise, it does nothing.
     */
    @Test
    fun testJumpIfFalse() {
        assertEquals(2, computer.jumpfalse(0, 2, 3))
        assertEquals(9999, computer.jumpfalse(0, 9999, 3))
        assertEquals(-1, computer.jumpfalse(0, -1, 3))
        assertEquals(5, computer.jumpfalse(0, 5, 3))

        //6 it moved to the next pointer
        assertEquals(6, computer.jumpfalse(9999, 5, 3))
        assertEquals(6, computer.jumpfalse(-9999, 5, 3))
    }

    /**
     *  Opcode 7 is less than: if the first parameter is less than the second parameter, it stores 1 in the position given by the third parameter.
     *  Otherwise, it stores 0
     */
    @Test
    fun testLessThan() {
        assertEquals(1, computer.lessthan(1, 2))
        assertEquals(1, computer.lessthan(-1, 2))
        assertEquals(1, computer.lessthan(1, 1000))
        assertEquals(1, computer.lessthan(-5, -1))

        assertEquals(0, computer.lessthan(1, 1))
        assertEquals(0, computer.lessthan(1, -1))
        assertEquals(0, computer.lessthan(1000, 1))
        assertEquals(0, computer.lessthan(-1, -5))
    }

    /**
     * Opcode 8 is equals: if the first parameter is equal to the second parameter, it stores 1 in the position given by the third parameter. Otherwise, it stores 0.
     */
    @Test
    fun testEquals() {
        assertEquals(1, computer.equals(1, 1))
        assertEquals(1, computer.equals(-1, -1))
        assertEquals(1, computer.equals(1000, 1000))
        assertEquals(1, computer.equals(-5, -5))

        assertEquals(0, computer.equals(1, 10))
        assertEquals(0, computer.equals(1, 2))
        assertEquals(0, computer.equals(1000, 1))
        assertEquals(0, computer.equals(-1, -5))
    }

    /**
     * The program 3,0,4,0,99 outputs whatever it gets as input, then halts.
     */
    @Test
    fun testExecute() {
        val memory = arrayOf(3L, 0, 4, 0, 99)
        assertEquals(100, computer.execute(memory, 100)[0]) //println 100
    }

    /**
     * 3,9,8,9,10,9,4,9,99,-1,8
     * Using position mode, consider whether the input is equal to 8; output 1 (if it is) or 0 (if it is not).
     */
    @Test
    fun testExecute2() {
        //Store input in pos 9
        //Equals pos 9 and pos 10
        val memory = arrayOf(3L, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8)
        assertEquals(1L, computer.execute(memory, 8)[0]) //println 1
        assertEquals(0L, computer.execute(memory, -1)[0]) //println 0
        assertEquals(0L, computer.execute(memory, 100)[0]) //println 0
    }

    /**
     *  3,9,7,9,10,9,4,9,99,-1,8
     *  Using position mode, consider whether the input is less than 8; output 1 (if it is) or 0 (if it is not).
     */
    @Test
    fun testExecute3() {
        val memory = arrayOf(3L, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8)
        assertEquals(1, computer.execute(memory, 7)[0]) //println 1
        assertEquals(1, computer.execute(memory, -1)[0]) //println 1
        assertEquals(0, computer.execute(memory, 8)[0]) //println 0
        assertEquals(0, computer.execute(memory, 100)[0]) //println 0
    }

    /**
     * 3,3,1108,-1,8,3,4,3,99
     * Using immediate mode, consider whether the input is equal to 8; output 1 (if it is) or 0 (if it is not).
     */
    @Test
    fun testExecute4() {
        val memory = arrayOf(3L, 3, 1108, -1, 8, 3, 4, 3, 99)
        assertEquals(1, computer.execute(memory, 8)[0]) //println 1
        assertEquals(0, computer.execute(memory, -1)[0]) //println 0
        assertEquals(0, computer.execute(memory, 100)[0]) //println 0
    }

    /**
     *  3,3,1107,-1,8,3,4,3,99
     *  Using immediate mode, consider whether the input is less than 8; output 1 (if it is) or 0 (if it is not).
     */
    @Test
    fun testExecute5() {
        val memory = arrayOf(3L, 3, 1107, -1, 8, 3, 4, 3, 99)
        assertEquals(1, computer.execute(memory, 7)[0]) //println 1
        assertEquals(1, computer.execute(memory, -1)[0]) //println 1
        assertEquals(0, computer.execute(memory, 8)[0]) //println 0
        assertEquals(0, computer.execute(memory, 100)[0]) //println 0
    }

    /**
     * 3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9 (using position mode)
     * take an input, then output 0 if the input was zero or 1 if the input was non-zero:
     */
    @Test
    fun testExecute6() {
        val memory = arrayOf(3L, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9)
        assertEquals(1, computer.execute(memory.copyOf(), 7)[0])//println 1
        assertEquals(1, computer.execute(memory.copyOf(), -1)[0]) //println 1
        assertEquals(0, computer.execute(memory.copyOf(), 0)[0]) //println 0
    }

    /**
     * 3,3,1105,-1,9,1101,0,0,12,4,12,99,1 (using immediate  mode)
     * take an input, then output 0 if the input was zero or 1 if the input was non-zero:
     */
    @Test
    fun testExecute7() {
        val memory = arrayOf(3L, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1)
        assertEquals(0, computer.execute(memory.copyOf(), 0)[0]) //println 0
        assertEquals(1, computer.execute(memory.copyOf(), 1101)[0]) //println 1
        assertEquals(1, computer.execute(memory.copyOf(), 7)[0]) //println 1
        assertEquals(1, computer.execute(memory.copyOf(), -1)[0]) //println 1
        assertEquals(0, computer.execute(memory.copyOf(), 0)[0]) //println 0
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
        Assert.assertEquals(IntcodeComputer.Opcode.JUMP_TRUE, computer.getOpcode(1205))
        Assert.assertEquals(IntcodeComputer.Opcode.JUMP_FALSE, computer.getOpcode(1206))
        Assert.assertEquals(IntcodeComputer.Opcode.LESS_THAN, computer.getOpcode(1207))
        Assert.assertEquals(IntcodeComputer.Opcode.EQUALS, computer.getOpcode(1208))
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
        val memory = arrayOf(1002L, 4, 3, 4, 33)
        computer.execute(memory)//Inspsect to see memory
    }
}





