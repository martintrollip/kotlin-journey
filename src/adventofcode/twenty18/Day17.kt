package adventofcode.twenty18

import java.io.File

/**
 * @author Martin Trollip
 * @since 2018/12/20 19:27
 *
 * TODO:
 * - Segment this, only keep important part of the cave and keep a seperate count
 * - Tests
 */
const val DAY17_INPUT = "src/res/day17_input_small"

val CAVE_REGEX = "([xy])=([0-9]+), ([xy])=([0-9]+)..([0-9]+)".toRegex()

fun main(args: Array<String>) {
    val cave = generateCaveFromInput()
    flow(cave)

    var count = 0
    for (row in cave) {
        for (col in row) {
            if (col.wet) {
                count++
            }
        }
    }

//    cave.print()
    println("The total number of tiles the water can reach is ${count - 1}")

}

private fun flow(cave: Array<Array<Ground>>) {
    val waterSources = HashSet<Ground>()
    (0 until cave.size).flatMapTo(waterSources) { row ->
        (0 until cave[row].size).filter { col ->
            cave[row][col] is WaterSource
        }.map { col ->
            cave[row][col]
        }
    }

    for (i in 0..80) {
        var oldSources = ArrayList<WaterSource>()
        var newSources = ArrayList<WaterSource>()
        for (source in waterSources) {
            if (source is WaterSource && source.y < cave.size - 1) {
                if (canMoveDown(cave, source)) {
                    cave[source.y][source.x] = Ground(source.x, source.y, true)
                    val waterSource = WaterSource(x = source.x, y = source.y + 1)
                    cave[source.y + 1][source.x] = waterSource
                    newSources.add(waterSource)
                    oldSources.add(source)
                } else if (canMoveSideways(cave, source) || canMoveUp(cave, source)) {
                    newSources.addAll(fillCavity(cave, source))
                    oldSources.add(source)
                } else {
                    oldSources.add(source)
                }
            }
            println(i)
            cave.print(waterSources)
        }
        waterSources.addAll(newSources)
        removeAll(cave, waterSources, oldSources)

    }
}

private fun canMoveDown(cave: Array<Array<Ground>>, source: Ground): Boolean {
    val below = cave[source.y + 1][source.x]
    return isOpenSpace(below)
}

private fun canMoveUp(cave: Array<Array<Ground>>, source: Ground): Boolean {
    val above = cave[source.y - 1][source.x]
    return isOpenSpace(above)
}

private fun canMoveSideways(cave: Array<Array<Ground>>, source: Ground): Boolean {
    val left = cave[source.y][source.x - 1]
    val right = cave[source.y][source.x + 1]
    return isOpenSpace(left) || isOpenSpace(right)
}


fun fillCavity(cave: Array<Array<Ground>>, source: WaterSource): ArrayList<WaterSource> {
    var leftX = source.x - 1
    var rightX = source.x + 1
    val y = source.y

    while (isOpenSpace(cave[y][leftX]) && (!canMoveDown(cave, cave[y][leftX]) || cave[y + 1][leftX] is WaterSource)) {//TODO this logic creates double waterfalls. Also below
        if (cave[y][leftX] !is WaterSource) {
            cave[y][leftX] = Ground(leftX, y, true)
        }
        leftX--
    }

    while (isOpenSpace(cave[y][rightX]) && (!canMoveDown(cave, cave[y][rightX]) || cave[y + 1][rightX] is WaterSource)) {
        if (cave[y][rightX] !is WaterSource) {
            cave[y][rightX] = Ground(rightX, y, true)
        }
        rightX++
    }

    val newSources = ArrayList<WaterSource>()
    if (isWalled(cave, y, leftX, rightX)) {
        for (x in (leftX + 1) until rightX) {
            if (cave[y][x] !is WaterSource) {
                cave[y][x] = Water(x, y)
            }
        }
        newSources.add(WaterSource(source.x, source.y - 1))
    }

    if (canMoveDown(cave, cave[y][leftX])) {
        newSources.add(WaterSource(leftX, y))
    }

    if (canMoveDown(cave, cave[y][rightX])) {
        newSources.add(WaterSource(rightX, y))
    }

    if (newSources.isEmpty() && canMoveUp(cave, source)) {
        return arrayListOf(WaterSource(source.x, source.y - 1))
    }

    return newSources
}

private fun removeAll(cave: Array<Array<Ground>>, waterSources: HashSet<Ground>, oldSources: ArrayList<WaterSource>) {
    for (oldSource in oldSources) {
        cave[oldSource.y][oldSource.x].wet = true
    }
    waterSources.removeAll(oldSources)
}


private fun isWalled(cave: Array<Array<Ground>>, y: Int, leftX: Int, rightX: Int) =
        (cave[y][leftX] is Clay || cave[y][leftX] is WaterSource) && (cave[y][rightX] is Clay || cave[y][rightX] is WaterSource)

private fun isOpenSpace(ground: Ground) =
        ground !is Clay && ground !is Water && ground !is WaterSource

var offset = 2
private fun generateCaveFromInput(): Array<Array<Ground>> {
    val clay = ArrayList<Ground>()
    clay.add(WaterSource())
    File(DAY17_INPUT).readLines().map {
        val matchResult = CAVE_REGEX.find(it)
        val (firstDirection, firstValue, secondDirection, secondValueFrom, secondValueTo) = matchResult!!.destructured

        val from = secondValueFrom.toInt()
        val to = secondValueTo.toInt()
        if (firstDirection == "x") {
            val x = firstValue.toInt()
            for (y in from..to) {
                clay.add(Clay(x, y))
            }
        } else if (firstDirection == "y") {
            val y = firstValue.toInt()
            for (x in from..to) {
                clay.add(Clay(x, y))
            }
        }
    }

    val (xMin, xMax, yMin, yMax) = clay.getBounds()
    clay.normalise()
    val cave = Array((yMax - yMin + 1) + offset, { y -> Array((xMax - xMin + 1) + offset * 2, { x -> Ground(x, y, false) }) })
    for (piece in clay) {
        cave[piece.y][piece.x] = piece
    }

    return cave
}

fun List<Ground>.getBounds(): CaveBounds {
    val xMin = minBy { it.x }?.x!!
    val xMax = maxBy { it.x }?.x!!
    val yMin = minBy { it.y }?.y!!
    val yMax = maxBy { it.y }?.y!!
    return CaveBounds(xMin, xMax, yMin, yMax)
}

fun List<Ground>.normalise() {
    val (xMin, xMax, yMin, yMax) = getBounds()

    forEach {
        it.x -= xMin - offset
        it.y -= yMin - offset
    }
}

//fun Array<Array<Ground>>.print() {
fun Array<Array<Ground>>.print(waterSources: HashSet<Ground>) {
    val padding = 2
    var rowCount = 0
    for (row in this) {
        var print = "$rowCount ".padEnd(4)
        for (column in row) {
            var contains = false
            for (waterSource in waterSources) {
                if (waterSource.x == column.x && waterSource.y == column.y) {
                    contains = true
                }
            }

            if (!contains) {
                print +=
                        when (column) {
                            is WaterSource -> {
                                "+".padStart(padding)
                            }
                            is Water -> {
                                "~".padStart(padding)
                            }
                            is Clay -> {
                                "#".padStart(padding)
                            }
                            else -> {
                                if (column.wet) {
                                    "|".padStart(padding)
                                } else {
                                    " ".padStart(padding)
                                }
                            }
                        }

            } else {
                print += "0".padStart(padding)
            }
        }
        println(print)
        rowCount++
    }
    println("-    0 1 2 3 4 5 6 7 8 9 1 1 1 1 1 1 1")
    println("                         0 1 2 3 4 5 6")
    println("\r\n")
}

open class Ground(var x: Int, var y: Int, var wet: Boolean)

class Clay(x: Int, y: Int) : Ground(x, y, false)

class Water(x: Int, y: Int) : Ground(x, y, true)

class WaterSource(x: Int = 500, y: Int = 0) : Ground(x, y, true)

data class CaveBounds(var xMin: Int, var xMax: Int, var yMin: Int, var yMax: Int)

