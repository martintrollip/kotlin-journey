package adventofcode.twenty21

import java.awt.Point
import java.io.File
import kotlin.math.sign

/**
 * @author Martin Trollip
 * @since 2021/12/17 06:49
 */
private const val DAY17_INPUT = "src/res/twenty21/day17_input"

fun main() {
    val day17 = Day17()
    println(
        "? ${
            day17.part1(day17.readInput(DAY17_INPUT))
        }"
    )
    println(
        "How many distinct initial velocity values cause the probe to be within the target area after any step? ${
            day17.part2(day17.readInput(DAY17_INPUT))
        }"
    )
}

class Day17 {

    private val TARGET_REGEX = "target area: x=([0-9]+)..([0-9]+), y=([-0-9]+)..([-0-9]+)".toRegex() //https://regex101.com/r/8mwTMW/1

    fun readInput(fileName: String): TargetArea {
        return File(fileName).readLines().take(1).map {
            if (TARGET_REGEX.matches(it)) {
                val (x1, x2, y1, y2) = TARGET_REGEX.find(it)!!.destructured
                TargetArea(IntRange(x1.toInt(), x2.toInt()), IntRange(y1.toInt(), y2.toInt()))
            } else {
                throw IllegalArgumentException("Invalid instruction: $it")
            }
        }.first()
    }

    fun part1(input: TargetArea): Int {
        return calculateAcceptableInitialConditions(input).maxHeight
    }

    fun part2(input: TargetArea): Int {
        return calculateAcceptableInitialConditions(input).size
    }

    private fun calculateAcceptableInitialConditions(input: TargetArea): Day17Result {
        val initialConditions = mutableListOf<Point>()
        var maxHeight = 0
        //Choose arbitrary large bounds for initial vx and vy
        for (vx in -100..1000) {
            for (vy in -1000..1000) {
                val probe = Probe(Point(0, 0), vx, vy)

                while (!isInTargetArea(probe, input) && !exceededTargetArea(probe, input)) {
                    probe.step()
                }

                if (isInTargetArea(probe, input)) {
                    initialConditions.add(Point(vx, vy))
                    if (probe.maxHeight > maxHeight) {
                        maxHeight = probe.maxHeight
                    }
                }
            }
        }

        return Day17Result(initialConditions.size, maxHeight)
    }

    private fun isInTargetArea(probe: Probe, targetArea: TargetArea): Boolean {
        return targetArea.xRange.contains(probe.currentPosition.x) && targetArea.yRange.contains(probe.currentPosition.y)
    }

    private fun exceededTargetArea(probe: Probe, targetArea: TargetArea): Boolean {
        return (probe.vx > 0 && (probe.currentPosition.x > targetArea.xRange.last) || 
                probe.vy < 0 && (probe.currentPosition.y < targetArea.yRange.first)) 
    }

    /**
     * The probe's x,y position starts at 0,0. Then, it will follow some trajectory by moving in steps. On each step, these changes occur in the following order:
     *
     * The probe's x position increases by its x velocity.
     * The probe's y position increases by its y velocity.
     * Due to drag, the probe's x velocity changes by 1 toward the value 0; that is, it decreases by 1 if it is greater than 0, increases by 1 if it is less than 0, or does not change if it is already 0.
     * Due to gravity, the probe's y velocity decreases by 1.
     */
    private fun Probe.step() {
        //The probe's x position increases by its x velocity
        currentPosition.x += vx

        //The probe's y position increases by its y velocity.
        currentPosition.y += vy

        //Due to drag, the probe's x velocity changes by 1 toward the value 0; 
        // that is, it decreases by 1 if it is greater than 0, 
        // increases by 1 if it is less than 0, 
        // or does not change if it is already 0.
        vx = vx.sign.let {
            when {
                it > 0 -> vx - 1
                it < 0 -> vx + 1
                else -> vx
            }
        }

        //Due to gravity, the probe's y velocity decreases by 1.
        vy -= 1

        //Set max height
        if (currentPosition.y > maxHeight) {
            maxHeight = currentPosition.y
        }
    }

    data class TargetArea(val xRange: IntRange, val yRange: IntRange)

    data class Probe(val currentPosition: Point, var vx: Int, var vy: Int, var maxHeight: Int = 0)

    data class Day17Result(val size: Int, val maxHeight: Int)
}
    
