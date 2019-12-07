package adventofcode.twenty19

import java.io.File

/**
 * @author Martin Trollip
 * @since 2019/12/06 07:34
 */
private const val DAY6_INPUT = "src/res/twenty19/day6_input"

fun main(args: Array<String>) {
    val day6 = Day6()

    println("What is the total number of direct and indirect orbits in your map data?? ${day6.part1()}\n") //186597
    println("What is the minimum number of orbital transfers required between You and Santa? ${day6.part2()}")//412
}

class Day6 {

    fun part1(): Int {
        return countIndirectOrbits(File(DAY6_INPUT).readLines())
    }

    val ORBIT_REGEX = "(.+)\\)(.+)".toRegex()
    fun countIndirectOrbits(lines: List<String>): Int {
        val directOrbits = getAllOrbits(lines)
        return cost(directOrbits, directOrbits["COM"]!!, 0)
    }

    private fun getAllOrbits(lines: List<String>): MutableMap<String, MutableList<String>> {
        val directOrbits = mutableMapOf<String, MutableList<String>>()

        lines.forEach {
            val (center, orbit) = ORBIT_REGEX.find(it)!!.destructured

            if (directOrbits[center] == null) {
                directOrbits[center] = mutableListOf(orbit)
            } else {
                directOrbits[center]!!.add(orbit)
            }
        }
        return directOrbits
    }

    fun cost(directOrbits: Map<String, List<String>>, orbits: List<String>, costToHere: Int): Int {
        var cost = costToHere
        for (orbit in orbits) {
            cost += if (directOrbits[orbit] != null /*This is on an end node*/) {
                cost(directOrbits, directOrbits[orbit]!!, costToHere + 1)
            } else {
                costToHere + 1
            }
        }
        return cost

    }

    fun part2(): Int {
        return countJumpsBetween(File(DAY6_INPUT).readLines())
    }

    fun countJumpsBetween(lines: List<String>): Int {
        val directOrbits = getAllOrbits(lines)
        val myPath = pathToCom(directOrbits, "YOU")
        val santaPath = pathToCom(directOrbits, "SAN")

        return myPath.minus(santaPath.keys).size + santaPath.minus(myPath.keys).size
    }

    private fun pathToCom(directOrbits: Map<String, List<String>>, orbit: String): Map<String, List<String>> {
        var you = orbit

        val path = mutableMapOf<String, List<String>>()
        do {
            you = directOrbits.filter { it.value.contains(you) }.keys.first()
            path[you] = directOrbits[you]!!
        } while (you != "COM")

        return path
    }
}