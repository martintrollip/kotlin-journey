package adventofcode.twenty18

import java.io.File
import java.lang.Math.pow

/**
 * @author Martin Trollip
 * @since 2018/12/12 07:03
 */

const val DAY12_INPUT = "src/res/day12_input"

val PLANTS_REGEX = "([.#]{5}) => ([.#])".toRegex()

const val initialState = "##....#.#.#...#.#..#.#####.#.#.##.#.#.#######...#.##....#..##....#.#..##.####.#..........#..#...#"
const val initialStateSmall = "#..#.#..##......###...###"

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

    var configuration = initialState.toPots()
    configuration.print()
    for (generation in 0 until 50000000000) {
        configuration.updateConfiguration()
        configuration = nextGeneration(configuration)
//        configuration.print()
    }


    val sum = configuration.filter { it.plant }.sumBy { it.index }

    println("The sum is $sum")

}

fun nextGeneration(initialPot: Pot): Pot {
    var currentPot = initialPot
    for (pot in currentPot) {
        pot.plant = notes.getOrDefault(pot.currentConfig, false)
    }
    return initialPot
}

fun String.toBool() = this == "#"


fun String.toPots(): Pot {
    var initialPot = Pot(0, plant = get(0).toString().toBool())
    getLeftLeftPot(initialPot) //This will "add" empty pots to the left
    var currentPot = initialPot
    for (i in 1 until length) {
        var nextPot = Pot(i, plant = get(i).toString().toBool())
        nextPot.left = currentPot
        currentPot.right = nextPot
        currentPot = nextPot
    }

    return initialPot
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


class Pot(var index: Int, var currentConfig: Byte = -1, var plant: Boolean = false, var left: Pot? = null, var right: Pot? = null) : Iterable<Pot> {

    fun updateConfiguration() {
        for (pot in this) {
            if (shouldUpdate(pot)) {
                pot.currentConfig = convertToDecimal(getLeftLeftPot(pot).plant, getLeftPot(pot).plant, pot.plant, getRightPot(pot).plant, getRightRightPot(pot).plant)
            }
        }
    }

    fun shouldUpdate(pot: Pot): Boolean {
        val leftMostPot = pot.searchLeftMostPot()
        val rightMostPot = pot.searchRightMostPot()

        val checkRightEdge = rightMostPot.plant == true || rightMostPot.left?.plant == true || rightMostPot.left?.left?.plant == true || rightMostPot.left?.left?.left?.plant == true || rightMostPot.left?.left?.left?.left?.plant == true

        val inbetween = pot.index > leftMostPot.index + 2 && pot.index < rightMostPot.index - 2

        return checkRightEdge || inbetween
    }

    fun searchLeftMostPot(): Pot {
        var currentPot: Pot? = this
        while (currentPot?.left != null) {
            currentPot = currentPot.left
        }
        return currentPot!!
    }

    fun searchRightMostPot(): Pot {
        var currentPot: Pot? = this
        while (currentPot?.right != null) {
            currentPot = currentPot.right
        }
        return currentPot!!
    }

    override fun iterator(): Iterator<Pot> = PotsIterator(this)
}

fun getLeftLeftPot(currentPot: Pot): Pot {
    if (getLeftPot(currentPot).left == null) {
        getLeftPot(currentPot).left = Pot(currentPot.index - 2, right = getLeftPot(currentPot))
    }

    return getLeftPot(currentPot).left!!
}

private fun getLeftPot(currentPot: Pot): Pot {
    if (currentPot.left == null) {
        currentPot.left = Pot(currentPot.index - 1, right = currentPot)
    }
    return currentPot.left!!
}

private fun getRightPot(currentPot: Pot): Pot {
    if (currentPot.right == null) {
        currentPot.right = Pot(currentPot.index + 1, left = currentPot)
    }

    return currentPot.right!!
}

private fun getRightRightPot(currentPot: Pot): Pot {
    if (getRightPot(currentPot).right == null) {
        getRightPot(currentPot).right = Pot(currentPot.index + 2, left = getRightPot(currentPot))
    }
    return getRightPot(currentPot).right!!
}

fun Pot.print() {
    var print = ""
    for (pot in this) {
        print += if (pot.plant) {
            "#"
        } else {
            "."
        }
    }

    println(print)
}

class PotsIterator(start: Pot) : Iterator<Pot> {
    private var current: Pot? = start.searchLeftMostPot()

    override fun hasNext(): Boolean = current != null

    override fun next(): Pot {
        val result = current

        if (current != null) {
            current = current!!.right
        }

        return result!!
    }
}