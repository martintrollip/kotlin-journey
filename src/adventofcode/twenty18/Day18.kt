package adventofcode.twenty18

import java.io.File

/**
 * @author Martin Trollip
 * @since 2018/12/26 09:04
 */
const val DAY18_INPUT = "src/res/day18_input"

fun main(args: Array<String>) {
    println("The answer is " + answer(run(readForestInput(DAY18_INPUT), 10 )))
}

fun answer(yard: List<List<Acre>>) : Int {
    var treeCount = 0
    var lumberCount = 0

    for (row in yard) {
        for (col in row) {
            if(col.type == YardType.TREE) {
                treeCount++
            }
            if(col.type == YardType.LUMBER) {
                lumberCount++
            }
        }
    }
    return treeCount * lumberCount
}

fun run(initialYard: List<List<Acre>>, time: Int): List<List<Acre>> {
    var yard = initialYard
    for (i in 0 until time) {
        yard = transformAll(yard)
    }
    return yard
}

fun transformAll(initialYard: List<List<Acre>>): List<ArrayList<Acre>> {
    return initialYard.map {
        var row = arrayListOf<Acre>()
        for (acre in it) {
            row.add(transform(initialYard, acre))
        }
        row
    }
}

fun transform(initialYard: List<List<Acre>>, current: Acre): Acre {
    val adjacent = getAdjacentAcres(initialYard, current)
    val newAcre = Acre(current.row, current.col, current.type)

    when (current.type) {
        YardType.GROUND -> {
            if (adjacent.filter { it.type == YardType.TREE }.size >= 3) {
                newAcre.type = YardType.TREE
            }
        }
        YardType.TREE -> {
            if (adjacent.filter { it.type == YardType.LUMBER }.size >= 3) {
                newAcre.type = YardType.LUMBER
            }
        }
        YardType.LUMBER -> {
            val treeFilter = adjacent.filter { it.type == YardType.TREE }
            val lumberFilter = adjacent.filter { it.type == YardType.LUMBER }
            if (treeFilter.isEmpty() || lumberFilter.isEmpty()) {
                newAcre.type = YardType.GROUND
            }
        }
    }
    return newAcre
}

fun getAdjacentAcres(yard: List<List<Acre>>, current: Acre): List<Acre> {
    val rowStart = Math.max(current.row - 1, 0)
    val rowEnd = Math.min(current.row + 1, yard.size - 1)
    val colStart = Math.max(current.col - 1, 0)
    val colEnd = Math.min(current.col + 1, yard[0].size - 1)

    var adjacent = mutableListOf<Acre>()
    for (row in rowStart..rowEnd) {
        for (col in colStart..colEnd) {
            if (!(row == current.row && col == current.col)) {
                adjacent.add(yard[row][col])
            }
        }
    }

    return adjacent
}

fun readForestInput(file: String): List<List<Acre>> {
    var rowCount = 0
    var colCount = 0
    val lumberYard = File(file).readLines().map { row ->
        var yardRow = mutableListOf<Acre>()
        for (i in 0 until row.length) {
            yardRow.add(Acre(rowCount, colCount, when (row[i]) {
                '|' -> {
                    YardType.TREE
                }
                '#' -> {
                    YardType.LUMBER
                }
                else -> {
                    YardType.GROUND
                }
            }))
            colCount++
        }
        rowCount++
        colCount = 0
        yardRow
    }

    return lumberYard
}

data class Acre(var row: Int, var col: Int, var type: YardType = YardType.GROUND)

enum class YardType {
    GROUND, TREE, LUMBER
}
