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

    private fun readInput(input: String): Array<Int> {
        return File(input).readLines()[0].split(",").map { it.toInt() }.toTypedArray()
    }

    fun part1(): Int {
        val memory = readInput(DAY7_INPUT)

        val permutations = permutations(listOf(0, 1, 2, 3, 4))
        var max = 0
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

    fun part2(): Int {
        val memory = readInput(DAY7_INPUT)

        val permutations = permutations(listOf(5, 6, 7, 8, 9))
        var max = 0
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

                if (resultE != -9999) {
                    finalOutput = resultE
                }

                loop++
            } while (resultE != -9999)

            if (finalOutput > max) {
                max = finalOutput
            }
        }

        return max
    }


    fun permutations(input: List<Int>): List<List<Int>> {
        if (input.size == 1) return listOf(input)
        val perms = mutableListOf<List<Int>>()
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

