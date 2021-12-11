package adventofcode.twenty21

import java.awt.Point
import java.io.File
import java.util.*

/**
 * @author Martin Trollip
 * @since 2021/12/10 08:32
 */
private const val DAY11_INPUT = "src/res/twenty21/day11_input"

fun main() {
    val day11 = Day11()
    println(
        "How many total flashes are there after 100 steps? ${
            day11.part1(day11.readInput(DAY11_INPUT).toMutableMap(), 100)
        }"
    )
    println(
        "What is the first step during which all octopuses flash? ${
            day11.part2(day11.readInput(DAY11_INPUT).toMutableMap())
        }"
    )
}

class Day11 {

    fun readInput(fileName: String): Map<Point, Int> {
        var row = 0
        var col = 0
        val map = mutableMapOf<Point, Int>()

        File(fileName).readLines().forEach {
            it.forEach {
                map[Point(col, row)] = it.toString().toInt()
                col++
            }
            row++
            col = 0
        }
        return map
    }

    fun part1(whales: MutableMap<Point, Int>, repeat: Int): Int {
        var counts = 0

        repeat(repeat) {
            val flashes = emptyList<Point>().toMutableList()
            
            //First increase energy level of all whales by 1
            whales.forEach {
                whales[it.key] = it.value + 1
            }

            var delta = -1
            while (delta != 0) {
                val newFlashes = emptyList<Point>().toMutableList()
                
                //Let whales > 9 flash and go to 0
                whales.forEach {
                    if (it.value > 9 && !flashes.contains(it.key)) {
                        newFlashes += it.key
                    }
                }

                //Increase energy level of neighbors
                val allNeighbours = allNeighbours(newFlashes)
                allNeighbours.forEach {
                    if (whales[it] != null) {
                        whales[it] = whales[it]!! + 1
                    }
                }                
                
                delta = newFlashes.size
                flashes.addAll(newFlashes)
                newFlashes.clear()
            }

            //Then start from the top
            
            whales.filter { flashes.contains(it.key) }.forEach { t, u -> 
                whales[t] = 0
            }
            
            counts += flashes.size
            flashes.clear()
        }

        return counts
    }
    
    private fun allNeighbours(points: List<Point>) = points.flatMap {
        listOf(
            Point(it.x - 1, it.y - 1),
            Point(it.x, it.y - 1),
            Point(it.x + 1, it.y - 1),
            Point(it.x - 1, it.y),
            Point(it.x + 1, it.y),
            Point(it.x - 1, it.y + 1),
            Point(it.x, it.y + 1),
            Point(it.x + 1, it.y + 1)
        )
    }

    fun part2(whales: MutableMap<Point, Int>): Int {
        var steps = 0
        var flashed = 0
        
        while (flashed < 100) {
            val flashes = emptyList<Point>().toMutableList()

            //First increase energy level of all whales by 1
            whales.forEach {
                whales[it.key] = it.value + 1
            }

            var delta = -1
            while (delta != 0) {
                val newFlashes = emptyList<Point>().toMutableList()

                //Let whales > 9 flash and go to 0
                whales.forEach {
                    if (it.value > 9 && !flashes.contains(it.key)) {
                        newFlashes += it.key
                    }
                }

                //Increase energy level of neighbors
                val allNeighbours = allNeighbours(newFlashes)
                allNeighbours.forEach {
                    if (whales[it] != null) {
                        whales[it] = whales[it]!! + 1
                    }
                }

                delta = newFlashes.size
                flashes.addAll(newFlashes)
                newFlashes.clear()
            }

            //Then start from the top

            whales.filter { flashes.contains(it.key) }.forEach { t, u ->
                whales[t] = 0
            }

            steps++
            flashed = flashes.size
            flashes.clear()
        }

        return steps
    }

}

