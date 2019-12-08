package adventofcode.twenty19

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2019/12/07 10:52
 */
class Day7Test {

    /**
     * Start the copy of the amplifier controller software that will run on amplifier A.
     * At its first input instruction, provide it the amplifier's phase setting
     * At its second input instruction, provide it the input signal
     * After some calculations, it will use an output instruction to indicate the amplifier's output signal.
     *
     * Max thruster signal 43210 (from phase setting sequence 4,3,2,1,0):
     * 3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0
     *
     */
    @Test
    fun TestController1() {
        val memory = arrayOf(3, 15, 3, 16, 1002, 16, 10, 16, 1, 16, 15, 15, 4, 15, 99, 0, 0)
        val sequence = arrayOf(4, 3, 2, 1, 0)

        val computerA = IntcodeComputer()
        val computerB = IntcodeComputer()
        val computerC = IntcodeComputer()
        val computerD = IntcodeComputer()
        val computerE = IntcodeComputer()

        val resultA = computerA.executeList(memory.copyOf(), arrayOf(4, 0))
        val resultB = computerB.executeList(memory.copyOf(), arrayOf(3, resultA[0]))
        val resultC = computerC.executeList(memory.copyOf(), arrayOf(2, resultB[0]))
        val resultD = computerD.executeList(memory.copyOf(), arrayOf(1, resultC[0]))
        val resultE = computerE.executeList(memory.copyOf(), arrayOf(0, resultD[0]))

        assertEquals(43210, resultE[0])
    }

    /**
     * Max thruster signal 54321 (from phase setting sequence 0,1,2,3,4):
     * 3,23,3,24,1002,24,10,24,1002,23,-1,23,
     * 101,5,23,23,1,24,23,23,4,23,99,0,0
     */
    @Test
    fun TestController2() {
        val memory = arrayOf(3, 23, 3, 24, 1002, 24, 10, 24, 1002, 23, -1, 23, 101, 5, 23, 23, 1, 24, 23, 23, 4, 23, 99, 0, 0)

        val computerA = IntcodeComputer()
        val computerB = IntcodeComputer()
        val computerC = IntcodeComputer()
        val computerD = IntcodeComputer()
        val computerE = IntcodeComputer()

        val resultA = computerA.executeList(memory.copyOf(), arrayOf(0, 0))
        val resultB = computerB.executeList(memory.copyOf(), arrayOf(1, resultA[0]))
        val resultC = computerC.executeList(memory.copyOf(), arrayOf(2, resultB[0]))
        val resultD = computerD.executeList(memory.copyOf(), arrayOf(3, resultC[0]))
        val resultE = computerE.executeList(memory.copyOf(), arrayOf(4, resultD[0]))

        assertEquals(54321, resultE[0])
    }

    /**
     * Max thruster signal 65210 (from phase setting sequence 1,0,4,3,2):
     * 3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0
     */
    @Test
    fun TestController3() {
        val memory = arrayOf(3, 31, 3, 32, 1002, 32, 10, 32, 1001, 31, -2, 31, 1007, 31, 0, 33, 1002, 33, 7, 33, 1, 33, 31, 31, 1, 32, 31, 31, 4, 31, 99, 0, 0, 0)

        val computerA = IntcodeComputer()
        val computerB = IntcodeComputer()
        val computerC = IntcodeComputer()
        val computerD = IntcodeComputer()
        val computerE = IntcodeComputer()

        val resultA = computerA.executeList(memory.copyOf(), arrayOf(1, 0))
        val resultB = computerB.executeList(memory.copyOf(), arrayOf(0, resultA[0]))
        val resultC = computerC.executeList(memory.copyOf(), arrayOf(4, resultB[0]))
        val resultD = computerD.executeList(memory.copyOf(), arrayOf(3, resultC[0]))
        val resultE = computerE.executeList(memory.copyOf(), arrayOf(2, resultD[0]))

        assertEquals(65210, resultE[0])
    }

    /**
     * Phase integers from 5 to 9
     * The output from amplifier E is now connected into amplifier A's input
     *
     * Max thruster signal 139629729 (from phase setting sequence 9,8,7,6,5):
     *
     * 3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5
     */
    @Test
    fun testControllerPart2_1() {
        val memory = arrayOf(3, 26, 1001, 26, -4, 26, 3, 27, 1002, 27, 2, 27, 1, 27, 26, 27, 4, 27, 1001, 28, -1, 28, 1005, 28, 6, 99, 0, 0, 5)

        val computerA = IntcodeComputer()
        val memoryA = memory.copyOf()
        val computerB = IntcodeComputer()
        val memoryB = memory.copyOf()
        val computerC = IntcodeComputer()
        val memoryC = memory.copyOf()
        val computerD = IntcodeComputer()
        val memoryD = memory.copyOf()
        val computerE = IntcodeComputer()
        val memoryE = memory.copyOf()

        var resultA: Int
        var resultB: Int
        var resultC: Int
        var resultD: Int
        var resultE = 0

        var finalOutput = 0

        var loop = 0
        do {
            if (loop == 0) {
                //Initial conditions
                resultA = computerA.executeToOutput(memoryA, arrayOf(9, resultE))
                resultB = computerB.executeToOutput(memoryB, arrayOf(8, resultA))
                resultC = computerC.executeToOutput(memoryC, arrayOf(7, resultB))
                resultD = computerD.executeToOutput(memoryD, arrayOf(6, resultC))
                resultE = computerE.executeToOutput(memoryE, arrayOf(5, resultD))
            } else {
                resultA = computerA.executeToOutput(memoryA, arrayOf(resultE))
                resultB = computerB.executeToOutput(memoryB, arrayOf(resultA))
                resultC = computerC.executeToOutput(memoryC, arrayOf(resultB))
                resultD = computerD.executeToOutput(memoryD, arrayOf(resultC))
                resultE = computerE.executeToOutput(memoryE, arrayOf(resultD))
            }

            if (resultE != -9999) {
                finalOutput = resultE
            }

            loop++
        } while (resultE != -9999)

        assertEquals(139629729, finalOutput)
    }

    /**
     * Max thruster signal 18216 (from phase setting sequence 9,7,8,5,6):
     *
     * 3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10
     */
    @Test
    fun testControllerPart2_2() {
        val memory = arrayOf(3, 52, 1001, 52, -5, 52, 3, 53, 1, 52, 56, 54, 1007, 54, 5, 55, 1005, 55, 26, 1001, 54, -5, 54, 1105, 1, 12, 1, 53, 54, 53, 1008, 54, 0, 55, 1001, 55, 1, 55, 2, 53, 55, 53, 4, 53, 1001, 56, -1, 56, 1005, 56, 6, 99, 0, 0, 0, 0, 10)

        val computerA = IntcodeComputer()
        val memoryA = memory.copyOf()
        val computerB = IntcodeComputer()
        val memoryB = memory.copyOf()
        val computerC = IntcodeComputer()
        val memoryC = memory.copyOf()
        val computerD = IntcodeComputer()
        val memoryD = memory.copyOf()
        val computerE = IntcodeComputer()
        val memoryE = memory.copyOf()

        var resultA: Int
        var resultB: Int
        var resultC: Int
        var resultD: Int
        var resultE = 0

        var finalOutput = 0

        var loop = 0
        do {
            if (loop == 0) {
                //Initial conditions
                resultA = computerA.executeToOutput(memoryA, arrayOf(9, resultE))
                resultB = computerB.executeToOutput(memoryB, arrayOf(7, resultA))
                resultC = computerC.executeToOutput(memoryC, arrayOf(8, resultB))
                resultD = computerD.executeToOutput(memoryD, arrayOf(5, resultC))
                resultE = computerE.executeToOutput(memoryE, arrayOf(6, resultD))
            } else {
                resultA = computerA.executeToOutput(memoryA, arrayOf(resultE))
                resultB = computerB.executeToOutput(memoryB, arrayOf(resultA))
                resultC = computerC.executeToOutput(memoryC, arrayOf(resultB))
                resultD = computerD.executeToOutput(memoryD, arrayOf(resultC))
                resultE = computerE.executeToOutput(memoryE, arrayOf(resultD))
            }

            if (resultE != -9999) {
                finalOutput = resultE
            }

            loop++
        } while (resultE != -9999)

        assertEquals(18216, finalOutput)
    }
}