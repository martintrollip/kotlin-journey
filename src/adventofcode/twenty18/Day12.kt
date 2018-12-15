package adventofcode.twenty18

import java.io.File
import java.lang.Math.pow

/**
 * @author Martin Trollip
 * @since 2018/12/12 07:03
 */

const val initialState = "##....#.#.#...#.#..#.#####.#.#.##.#.#.#######...#.##....#..##....#.#..##.####.#..........#..#...#"
const val initialStateSmall = "#..#.#..##......###...###"
const val DAY12_INPUT = "src/res/day12_input"
val PLANTS_REGEX = "([.#]{5}) => ([.#])".toRegex()

const val RUNS = 50000000000L

val notes = LinkedHashMap<Byte, Boolean>()

fun main(args: Array<String>) {
    File(DAY12_INPUT).readLines().map {
        val matchResult = PLANTS_REGEX.find(it)
        val (configuration, result) = matchResult!!.destructured

        val booleanArray = BooleanArray(5)
        for (i in 0 until configuration.length) {
            booleanArray.set(i, configuration.get(i).equals('#'))
        }

        notes.put(convertToDecimal(booleanArray[0], booleanArray[1], booleanArray[2], booleanArray[3], booleanArray[4]), result.toBool())
    }

    var (pot) = initialState.toPots()
    pot.print(-1)
    for (generation in 0 until RUNS) {
        pot.updateConfiguration()
        val nextGeneration = pot.nextGeneration()
        val expand = pot.expand(nextGeneration.minPlant, nextGeneration.maxPlant)
        pot = expand.minPlant
//        pot.print(generation)
    }
    pot.print(RUNS)

    val sum = pot.filter { it.plant }.sumBy { it.index }

    println("The sum is $sum")
}

fun Pot.nextGeneration(): PotBounds {
    var minPot = this
    var minPlant: Pot? = null
    var maxPlant: Pot? = null

    for (pot in this) {
        pot.plant = notes.getOrDefault(pot.currentConfig, false)

        if (pot.plant) {
            if (minPlant == null || pot.index < minPlant.index) {
                minPlant = pot
            } else if (maxPlant == null || pot.index > maxPlant.index) {
                maxPlant = pot
            }
        }
    }

    return PotBounds(minPot, minPlant!!, maxPlant!!)
}

fun Pot.expand(min: Pot, max: Pot): PotBounds {
    var leftLeftPot = min
    var rightRightPot = max

    val expandLeftEdge = min.plant == true || min.right?.plant == true || min.right?.right?.plant == true || min.right?.right?.right?.plant == true || min.right?.right?.right?.right?.plant == true
    val expandRightEdge = max.plant == true || max.left?.plant == true || max.left?.left?.plant == true || max.left?.left?.left?.plant == true || max.left?.left?.left?.left?.plant == true

    if (expandLeftEdge) {
        leftLeftPot = updateLeftLeftPot(updateLeftLeftPot(min))
    }

    if (expandRightEdge) {
        rightRightPot = updateRightRightPot(updateRightPot(max))
    }

    return PotBounds(this, leftLeftPot, rightRightPot)
}

fun String.toBool() = this == "#"

fun String.toPots(): PotBounds {
    val initialPot = Pot(0, plant = get(0).toString().toBool())
    val leftLeftPot = updateLeftLeftPot(initialPot) //This will "add" empty pots to the left\
    var currentPot = initialPot

    var minPot = leftLeftPot
    var minPotPlant = initialPot
    var maxPotPlant = initialPot

    for (i in 1 until length) {
        val nextPot = Pot(i, plant = get(i).toString().toBool())
        nextPot.left = currentPot
        currentPot.right = nextPot
        currentPot = nextPot

        if (currentPot.plant && nextPot.index < currentPot.index) {
            minPotPlant = currentPot
        }

        if (currentPot.plant) {
            maxPotPlant = currentPot
        }
    }

    return PotBounds(minPot, minPotPlant, maxPotPlant)
}

fun convertToDecimal(a: Boolean, b: Boolean, c: Boolean, d: Boolean, e: Boolean): Byte {
    var sum = 0

    if (a) {
        sum += pow(2.0, 4.0).toInt()
    }

    if (b) {
        sum += pow(2.0, 3.0).toInt()
    }

    if (c) {
        sum += pow(2.0, 2.0).toInt()
    }

    if (d) {
        sum += pow(2.0, 1.0).toInt()
    }

    if (e) {
        sum += pow(2.0, 0.0).toInt()
    }

    return sum.toByte()
}

fun updateLeftLeftPot(currentPot: Pot): Pot {
    if (updateLeftPot(currentPot).left == null) {
        updateLeftPot(currentPot).left = Pot(currentPot.index - 2, right = updateLeftPot(currentPot))
    }
    return updateLeftPot(currentPot).left!!
}

private fun updateLeftPot(currentPot: Pot): Pot {
    if (currentPot.left == null) {
        currentPot.left = Pot(currentPot.index - 1, right = currentPot)
    }
    return currentPot.left!!
}

private fun updateRightPot(currentPot: Pot): Pot {
    if (currentPot.right == null) {
        currentPot.right = Pot(currentPot.index + 1, left = currentPot)
    }

    return currentPot.right!!
}

private fun updateRightRightPot(currentPot: Pot): Pot {
    if (updateRightPot(currentPot).right == null) {
        updateRightPot(currentPot).right = Pot(currentPot.index + 2, left = updateRightPot(currentPot))
    }
    return updateRightPot(currentPot).right!!
}

class Pot(var index: Int, var currentConfig: Byte = -1, var plant: Boolean = false, var left: Pot? = null, var right: Pot? = null) : Iterable<Pot> {

    fun updateConfiguration() {
        for (pot in this) {
            pot.currentConfig = convertToDecimal(pot.left?.left?.plant == true, pot.left?.plant == true, pot.plant, pot.right?.plant == true, pot.right?.right?.plant == true)
        }
    }

    override fun iterator(): Iterator<Pot> = PotsIterator(this)
}

fun Pot.print(round: Long) {
    var print = ""

    val offset = 50

    for (i in 0 until offset + this.index) {
        print += " "
    }

    for (pot in this) {
        print += if (pot.plant) {
            "#" /*+ pot.index + " "*/
        } else {
            "."/* + pot.index + " "*/
        }
    }

    println("$round: ".padStart(5) + print)
}

class PotsIterator(start: Pot) : Iterator<Pot> {
    private var current: Pot? = start

    override fun hasNext(): Boolean = current != null

    override fun next(): Pot {
        val result = current

        if (current != null) {
            current = current!!.right
        }

        return result!!
    }
}

data class PotBounds(var min: Pot, var minPlant: Pot, var maxPlant: Pot)