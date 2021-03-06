package adventofcode.twenty19

import java.io.File

/**
 * @author Martin Trollip
 * @since 2019/12/01 06:16
 */
private const val DAY1_INPUT = "src/res/twenty19/day1_input"

fun main(args: Array<String>) {
    val day1 = Day1(DAY1_INPUT)
    println("What is the sum of the fuel requirements? ${day1.totalFuelRequiredPerModule()}")
    println("What is the sum of the fuel requirements? ${day1.totalFuelRequiredPerModuleAndForFuel()}")
}

class Day1(input: String) {

    private val moduleWeights: List<Int>

    init {
        moduleWeights = readInput(input)
    }

    private fun readInput(fileName: String): List<Int> {
        return File(fileName).readLines().map { it.toInt() }
    }

    //Part 1
    fun totalFuelRequiredPerModule(): Int {
        return moduleWeights.sumBy { fuelRequiredPerMass(it) }
    }

    fun fuelRequiredPerMass(mass: Int): Int {
        return (Math.floor(mass / 3.0) - 2).toInt()
    }

    //Part 2
    fun totalFuelRequiredPerModuleAndForFuel(): Int {
        return moduleWeights.sumBy { fuelRequiredPerModuleAndFuel(it) }
    }

    fun fuelRequiredPerModuleAndFuel(mass: Int): Int {
        var totalFuel = 0
        var massToCalculateFuelRequirement = mass

        while (true) {
            val fuelRequired = fuelRequiredPerMass(massToCalculateFuelRequirement)
            if (fuelRequired <= 0) {
                return totalFuel
            }
            totalFuel += fuelRequired
            massToCalculateFuelRequirement = fuelRequired
        }
    }
}

