package adventofcode.twenty18

import java.awt.Point
import java.util.*

/**
 * @author Martin Trollip
 * @since 2019/01/20 16:22
 */

var depthInput = 11991
var targetX = 6
var targetY = 797

const val MOD = 20183

const val X_ZERO_MULTIPLIER = 48271
const val Y_ZERO_MULTIPLIER = 16807

fun main(args: Array<String>) {
    println("Part 1: Risk level is ${riskLevel()}")
    println("Part 2: Minimum time to rescue target ${rescueTarget()}")
}

val cave = mutableMapOf<Point, Int>()

fun riskLevel(): Int =
        (0..targetY).flatMap { y ->
            (0..targetX).map { x ->
                Cave.from(erosionLevel(x, y)).ordinal
            }
        }.sum()

fun rescueTarget(): Int {
    val minimumCosts: MutableMap<Pair<Point, Tool>, Int> = mutableMapOf(Point(0, 0) to Tool.TORCH to 0)
    val possiblePaths = PriorityQueue<Path>()
    possiblePaths.add(Path(0, 0, Tool.TORCH)) //Can also use .apply { ... }

    while (!possiblePaths.isEmpty()) {
        val currentPath = possiblePaths.poll()

        if(isTargetAndTool(currentPath.xEnd, currentPath.yEnd, currentPath.tool)) {
            //cheapest cost.add this
        }

        val next = mutableListOf<Path>()
        possibleMoves(currentPath.xEnd, currentPath.yEnd).forEach {it ->
            if(currentPath.tool in availableToolsAt(it.x, it.y)) {
                next
            }
        }
    }
}

fun isTargetAndTool(x:Int, y:Int, tool:Tool) = x == targetX && y == targetY && tool == Tool.TORCH

fun erosionLevel(x: Int, y: Int): Int {
    val location = Point(x, y)
    return if (location in cave) {
        cave[location]!!
    } else {
        val erosionLevel = (geoLogicIndex(location.x, location.y) + depthInput) % MOD
        cave[location] = erosionLevel
        erosionLevel
    }
}

fun geoLogicIndex(x: Int, y: Int): Int {
    if ((x == 0 && y == 0) || (x == targetX && y == targetY)) {
        return 0
    }

    if (y == 0) {
        return x * Y_ZERO_MULTIPLIER
    }

    if (x == 0) {
        return y * X_ZERO_MULTIPLIER
    }

    return erosionLevel(x - 1, y) * erosionLevel(x, y - 1)
}

fun availableToolsAt(x: Int, y: Int): Set<Tool> {
    return if ((x == 0 && y == 0) || (x == targetX && y == targetY)) {
        setOf(Tool.TORCH)
    } else {
        Cave.from(erosionLevel(x, y)).tools
    }
}

fun possibleMoves(x: Int, y: Int): List<Point> =
        listOf(
                Point(x, y - 1),
                Point(x - 1, y),
                Point(x + 1, y),
                Point(x, y + 1)
        ).filter { it.x >= 0 && it.y >= 0 }

enum class Tool {
    TORCH, CLIMBING, NEITHER
}

enum class Cave(val tools: Set<Tool>) {
    ROCKY(setOf(Tool.CLIMBING, Tool.TORCH)),
    WET(setOf(Tool.CLIMBING, Tool.NEITHER)),
    NARROW(setOf(Tool.TORCH, Tool.NEITHER));

    companion object {
        fun from(erosionLevel: Int): Cave {
            return values()[erosionLevel % values().size]
        }
    }
}

data class Path(val xEnd: Int, val yEnd: Int, val tool: Tool, val totalCost: Int = 0) : Comparable<Path> {
    override fun compareTo(other: Path): Int {
        return this.totalCost.compareTo(other.totalCost)
    }
}