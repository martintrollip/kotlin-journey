package adventofcode.twenty19

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/**
 * @author Martin Trollip
 * @since 2019/12/09 06:40
 */

class Day9Test {

    /**
     * Parameters in mode 2, relative mode, behave very similarly to parameters in position mode: the parameter is interpreted as a position.
     * Like position mode, parameters in relative mode can be read from or written to.
     */
    @Test
    fun testGetModes() {
        val computer = IntcodeComputer()

        Assert.assertEquals(IntcodeComputer.Mode.IMMEDIATE, computer.getMode(11101, 0))
        Assert.assertEquals(IntcodeComputer.Mode.POSITION, computer.getMode(1001, 0))
        Assert.assertEquals(IntcodeComputer.Mode.RELATIVE, computer.getMode(1201, 0))
    }

    /**
     * For example, given a relative base of 50, a relative mode parameter of -7 refers to memory address 50 + -7 = 43.
     */
    @Test
    fun testRelativeBaseValue() {
        val computer = IntcodeComputer()

        val memory = arrayOf(0L, 100, 200, 3, 4, 5, 6, 400)
        assertEquals(100, computer.getValue(IntcodeComputer.Mode.POSITION, 1, memory)) //At index 1 = 100
        assertEquals(200, computer.getValue(IntcodeComputer.Mode.RELATIVE, 2, memory)) //At index (0 + 2) = 1
        assertEquals(300, computer.getValue(IntcodeComputer.Mode.IMMEDIATE, 300, memory)) //Value 102

        computer.updateRelative(5)
        assertEquals(400, computer.getValue(IntcodeComputer.Mode.RELATIVE, 2, memory)) //At index (5 + 1) = 400
    }


    /**
     * The relative base is modified with the relative base offset instruction:
     *
     * Opcode 9 adjusts the relative base by the value of its only parameter. The relative base increases (or decreases, if the value is negative) by the value of the parameter.
     *
     * For example, if the relative base is 2000, then after the instruction 109,19, the relative base would be 2019.
     * If the next instruction were 204,-34, then the value at address 1985 would be output.
     */
    @Test
    fun testRelativeBase() {
        val computer = IntcodeComputer()
        val memory = Array(2000) { it.toLong() }
        memory[0] = 204
        memory[1] = -34

        assertEquals(2000, computer.updateRelative(2000))
        assertEquals(2019, computer.updateRelative(19))
        assertEquals(IntcodeComputer.Mode.RELATIVE, computer.getMode(204, 0))

        val instruction = computer.getInstructionAt(memory, 0)
        assertEquals(IntcodeComputer.Opcode.OUTPUT, instruction.type)
        assertEquals(IntcodeComputer.Mode.RELATIVE, computer.getMode(instruction.mode, 0)) //TODO Martin surely the mode can be state?
        assertEquals(-34, instruction.result)

        assertEquals(1985, computer.getValue(computer.getMode(instruction.mode, 0), instruction.result, memory))
    }

    /**
     * Test opcode 9
     */
    @Test
    fun testOpcode9() {
        val computer = IntcodeComputer()

        assertEquals(IntcodeComputer.Opcode.RELATIVE_BASE, computer.getOpcode(9))
        assertEquals(IntcodeComputer.Mode.POSITION, computer.getMode(9, 0))

        assertEquals(IntcodeComputer.Opcode.RELATIVE_BASE, computer.getOpcode(109))
        assertEquals(IntcodeComputer.Mode.IMMEDIATE, computer.getMode(109, 0))

        assertEquals(IntcodeComputer.Opcode.RELATIVE_BASE, computer.getOpcode(209))
        assertEquals(IntcodeComputer.Mode.RELATIVE, computer.getMode(209, 0))

        assertEquals(IntcodeComputer.Opcode.OUTPUT, computer.getOpcode(4))
        assertEquals(IntcodeComputer.Mode.POSITION, computer.getMode(4, 0))

        assertEquals(IntcodeComputer.Opcode.STORE, computer.getOpcode(203))
        assertEquals(IntcodeComputer.Mode.RELATIVE, computer.getMode(203, 0))

        assertEquals(IntcodeComputer.Opcode.EQUALS, computer.getOpcode(21108))
        assertEquals(IntcodeComputer.Mode.IMMEDIATE, computer.getMode(21108, 0))
        assertEquals(IntcodeComputer.Mode.IMMEDIATE, computer.getMode(21108, 1))
        assertEquals(IntcodeComputer.Mode.RELATIVE, computer.getMode(21108, 2))

        assertEquals(IntcodeComputer.Opcode.RELATIVE_BASE, computer.getOpcode(109))
        assertEquals(IntcodeComputer.Opcode.OUTPUT, computer.getOpcode(204))
        assertEquals(IntcodeComputer.Opcode.ADD, computer.getOpcode(1001))
        assertEquals(IntcodeComputer.Opcode.EQUALS, computer.getOpcode(1008))
        assertEquals(IntcodeComputer.Opcode.JUMP_FALSE, computer.getOpcode(1006))
        assertEquals(IntcodeComputer.Opcode.TERMINATE, computer.getOpcode(1199))
    }

    /**
     * Your Intcode computer will also need a few other capabilities:
     *
     * The computer's available memory should be much larger than the initial program. Memory beyond the initial program starts with the value 0
     * and can be read or written like any other memory. (It is invalid to try to access memory at a negative address, though.)
     *
     */
    @Test
    fun testLargerMemory() {
        val computer = IntcodeComputer()
        val memory = arrayOf(0L, 1L, 2L)
        val increasedMemory = computer.increaseMemorySize(memory)
        assertEquals(33, increasedMemory.size)
    }

    /**
     * The computer should have support for large numbers. Some instructions near the beginning of the BOOST program will verify this capability.
     */
    @Test
    fun testLargeNumberSupport() {
        val memory = arrayOf(Integer.MAX_VALUE + 1L, Integer.MAX_VALUE + 2L)
        assertTrue(IntcodeComputer().multiply(memory[0], memory[1]) > 1L)
    }

    /**
     * 109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99 takes no input and produces a copy of itself as output.
     */
    @Test
    fun testQuine() {
        val computer = IntcodeComputer()
        val memory = computer.increaseMemorySize(arrayOf(109L, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99))

        val results = computer.execute(memory)

        assertEquals(109, results[0])
        assertEquals(1, results[1])
        assertEquals(204, results[2])
        assertEquals(-1, results[3])
        assertEquals(1001, results[4])
        assertEquals(100, results[5])
        assertEquals(1, results[6])
        assertEquals(100, results[7])
        assertEquals(1008, results[8])
        assertEquals(100, results[9])
        assertEquals(16, results[10])
        assertEquals(101, results[11])
        assertEquals(1006, results[12])
        assertEquals(101, results[13])
        assertEquals(0, results[14])
        assertEquals(99, results[15])
    }

    /**
     * 1102,34915192,34915192,7,4,7,99,0 should output a 16-digit number.
     */
    @Test
    fun test16DigitNumber() {
        val computer = IntcodeComputer()
        val memory = computer.increaseMemorySize(arrayOf(1102L, 34915192, 34915192, 7, 4, 7, 99, 0))

        val results = computer.execute(memory)
        assertEquals(1219070632396864L, results[0])
    }

    /**
     * 104,1125899906842624,99 should output the large number in the middle.
     */
    @Test
    fun testLargeNumber() {
        val computer = IntcodeComputer()
        val memory = computer.increaseMemorySize(arrayOf(104, 1125899906842624, 99))

        val results = computer.execute(memory)
        assertEquals(1125899906842624L, results[0])
    }

    /**
     * The BOOST program will ask for a single input; run it in test mode by providing it the value 1.
     *
     * It will perform a series of checks on each opcode, output any opcodes (and the associated parameter modes) that seem to be
     * functioning incorrectly, and finally output a BOOST keycode.
     *
     * Once your Intcode computer is fully functional, the BOOST program should report no malfunctioning opcodes when run in test mode;
     * it should only output a single value, the BOOST keycode. What BOOST keycode does it produce?
     */
    private val DAY9_INPUT = "src/res/twenty19/day9_input"
    @Test
    fun testProgram() {
        val computer = IntcodeComputer()
        val result = computer.execute(computer.increaseMemorySize(readInput()), 1)
        assertEquals(1, result.size)
    }

    fun readInput(): Array<Long> {
        return File(DAY9_INPUT).readLines()[0].split(",").map { it.toLong() }.toTypedArray()
    }
}