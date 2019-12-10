package adventofcode.twenty19

import java.io.File

/**
 * @author Martin Trollip
 * @since 2019/12/07 10:50
 */
private const val DAY7_INPUT = "src/res/twenty19/day7_input"

fun main(args: Array<String>) {
    val day7 = Day7()

    println("What is the highest signal that can be sent to the thrusters? ${day7.part1()}\n") //199988
    println("What is the highest signal that can be sent to the thrusters using the new phase settings and feedback loop arrangement? ${day7.part2()}") //17519904

}

class Day7 {

    val computerA = IntcodeComputer()
    val computerB = IntcodeComputer()
    val computerC = IntcodeComputer()
    val computerD = IntcodeComputer()
    val computerE = IntcodeComputer()

    private fun readInput(input: String): Array<Long> {
        return File(input).readLines()[0].split(",").map { it.toLong() }.toTypedArray()
    }

    fun part1(): Long {
        val memory = readInput(DAY7_INPUT)

        val permutations = permutations(listOf(0L, 1L, 2L, 3L, 4L))
        var max = 0L
        for (phase in permutations) {
            val resultA = computerA.executeList(memory.copyOf(), arrayOf(phase[0], 0))
            val resultB = computerB.executeList(memory.copyOf(), arrayOf(phase[1], resultA[0]))
            val resultC = computerC.executeList(memory.copyOf(), arrayOf(phase[2], resultB[0]))
            val resultD = computerD.executeList(memory.copyOf(), arrayOf(phase[3], resultC[0]))
            val resultE = computerE.executeList(memory.copyOf(), arrayOf(phase[4], resultD[0]))
            if (resultE[0] > max) {
                max = resultE[0]
            }
        }

        return max
    }

    fun part2(): Long {
        val memory = readInput(DAY7_INPUT)

        val permutations = permutations(listOf(5L, 6L, 7L, 8L, 9L))
        var max = 0L
        for (phase in permutations) {
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

            var resultA: Long
            var resultB: Long
            var resultC: Long
            var resultD: Long
            var resultE = 0L

            var finalOutput = 0L
            var loop = 0
            do {
                if (loop == 0) {
                    //Initial conditions
                    resultA = computerA.executeToOutput(memoryA, arrayOf(phase[0], resultE))
                    resultB = computerB.executeToOutput(memoryB, arrayOf(phase[1], resultA))
                    resultC = computerC.executeToOutput(memoryC, arrayOf(phase[2], resultB))
                    resultD = computerD.executeToOutput(memoryD, arrayOf(phase[3], resultC))
                    resultE = computerE.executeToOutput(memoryE, arrayOf(phase[4], resultD))
                } else {
                    resultA = computerA.executeToOutput(memoryA, arrayOf(resultE))
                    resultB = computerB.executeToOutput(memoryB, arrayOf(resultA))
                    resultC = computerC.executeToOutput(memoryC, arrayOf(resultB))
                    resultD = computerD.executeToOutput(memoryD, arrayOf(resultC))
                    resultE = computerE.executeToOutput(memoryE, arrayOf(resultD))
                }

                if (resultE != -9999L) {
                    finalOutput = resultE
                }

                loop++
            } while (resultE != -9999L)

            if (finalOutput > max) {
                max = finalOutput
            }
        }

        return max
    }


    fun permutations(input: List<Long>): List<List<Long>> {
        if (input.size == 1) return listOf(input)
        val perms = mutableListOf<List<Long>>()
        val toInsert = input[0]
        for (perm in permutations(input.drop(1))) {
            for (i in 0..perm.size) {
                val newPerm = perm.toMutableList()
                newPerm.add(i, toInsert)
                perms.add(newPerm)
            }
        }
        return perms
    }
}

