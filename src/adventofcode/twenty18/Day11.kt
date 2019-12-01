package adventofcode.twenty18

import java.io.File

/**
 * @author Martin Trollip
 * @since 2018/12/11 17:51
 */
const val DAY111_INPUT = "src/res/day11_input"
const val SIZE = 300

var serial = -1

fun main(args: Array<String>) {
    serial = File(DAY111_INPUT).readText().toInt()
    println(day11())
}

fun day11(): String {
    val powerBank = Array(SIZE) { row -> Array(SIZE) { FuelCell(it + 1, row + 1) } }

    var bestCell = FuelCell()
    var maxPower = -1
    var gridSize = -1
    for (grid in 0 until SIZE) {
        for (x in 0 until SIZE) {
            for (y in 0 until SIZE) {
                val power = powerBank.calculateAreaPower(powerBank[x][y], grid)
                if (power > maxPower) {
                    maxPower = power
                    bestCell = powerBank[x][y]
                    gridSize = grid
                }
            }
        }
    }

    return "$bestCell with grid $gridSize"
}

//Step 1: Find the fuel cell's rack ID, which is its X coordinate plus 10.
fun rackId(cell: FuelCell): Int {
    return cell.x + 10
}

//Step 2: Begin with a power level of the rack ID times the Y coordinate
fun initialPower(cell: FuelCell): Int {
    return rackId(cell) * cell.y
}

//Step 3:Increase the power level by the value of the grid serial number
fun increasePower(power: Int): Int {
    return power + serial
}

//Step 4: Set the power level to itself multiplied by the rack ID.
fun multiply(power: Int, rackId: Int): Int {
    return power * rackId
}

//Step 5: Keep only the hundreds digit of the power level (so 12345 becomes 3; numbers with no hundreds digit become 0).
fun hundreds(power: Int): Int {
    return (power / 100).rem(10)
}

//Step 6: Keep only the hundreds digit of the power level (so 12345 becomes 3; numbers with no hundreds digit become 0)
fun substract(power: Int): Int {
    return power - 5
}

//Total power
fun totalPower(cell: FuelCell): Int {
    return substract(hundreds(multiply(increasePower(initialPower(cell)), rackId(cell))))
}

fun Array<Array<FuelCell>>.calculateAreaPower(referenceCell: FuelCell, areaSize: Int): Int {
    var power = 0

    for (x in referenceCell.x - 1 until referenceCell.x - 1 + areaSize) {
        for (y in referenceCell.y - 1 until referenceCell.y - 1 + areaSize) {
            if (x in 1..(size - 1) && y in 1..(size - 1)) {
                power += this[x][y].power
            } else {
                power -= 1000
            }
        }
    }

    return power
}

data class FuelCell(var x: Int = -1, var y: Int = -1, var power: Int = -1) {
    init {
        power = totalPower(this)
    }
}

