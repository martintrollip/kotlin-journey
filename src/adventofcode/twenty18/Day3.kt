package adventofcode.twenty18

import java.io.File

/**
 * @author Martin Trollip
 * @since 2018/12/03 19:53
 */

const val EMPTY_MATERIAL = "."
const val CONFLICTING_MATERIAL = "X"

val INSTRUCTION_REGEX = "\\#([0-9]+) \\@ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)".toRegex()

const val TOTAL_SIZE = 1000
const val DAY3_INPUT = "src/res/day3_input"

fun main(args: Array<String>) {

    val instructions = File(DAY3_INPUT).readLines().map {
        val matchResult = INSTRUCTION_REGEX.find(it)
        val (id, left, top, width, height) = matchResult!!.destructured
        Cut(id, left.toInt(), top.toInt(), width.toInt(), height.toInt())
    }

    val ids = instructions.map { it.id }.toMutableList()

    val material = Array(TOTAL_SIZE, { Array(TOTAL_SIZE) { EMPTY_MATERIAL } })

    instructions.forEach {
        material.cut(it, ids)
    }

    material.print()
    println("Conflicting count: " + material.conflicting())
    println("Non-conflicting id: $ids")
}

fun Array<Array<String>>.print() {
    for (row in this) {
        for (column in row) {
            print(column.padStart(5))
        }
        println("")
    }
}

fun Array<Array<String>>.cut(instruction: Cut, ids: MutableList<String>) {
    val (id, left, top, width, height) = instruction

    for (row in top until top + height) {
        for (column in left until left + width) {
            if (this[row][column] == EMPTY_MATERIAL) {
                this[row][column] = id
            } else {
                ids.remove(id)
                ids.remove(this[row][column])
                this[row][column] = CONFLICTING_MATERIAL
            }
        }
    }
}

fun Array<Array<String>>.conflicting(): Int {
    var count = 0

    for (row in this) {
        for (cell in row) {
            if (cell == CONFLICTING_MATERIAL) {
                count++
            }
        }
    }

    return count
}

data class Cut(val id: String, val left: Int, val top: Int, val width: Int, val height: Int)


