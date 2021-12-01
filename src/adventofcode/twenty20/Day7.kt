package adventofcode.twenty20

import java.io.File

/**
 * @author Martin Trollip
 * @since 2020/12/07 07:17
 */
private const val DAY7_INPUT = "src/res/twenty20/day7_input"

fun main(args: Array<String>) {
    val day7 = Day7(DAY7_INPUT)
    println("Bags that contains at least one shiny gold bag? ${day7.part1()}")
    println("${day7.part2()}")
}

class Day7(input: String) {
    private val SHINY_GOLD = "shiny gold"
    private val BAGS_REGEX = "([0-9])?[ ]?([A-Za-z ]+) bag[s]?".toRegex() //https://regex101.com/r/5ue8pm/2/
    private val rules: HashMap<String, List<Pair<String, Int>>>

    init {
        rules = readInput(input)
    }

    private fun readInput(fileName: String): HashMap<String, List<Pair<String, Int>>> {
        val bags = hashMapOf<String, List<Pair<String, Int>>>()
        File(fileName).readLines().forEach {
            val rules = it.split("contain")
            val (_, bigBag) = BAGS_REGEX.find(rules[0])!!.destructured
            val smallContents = BAGS_REGEX.findAll(rules[1])

            val contents = mutableListOf<Pair<String, Int>>()
            for (smallContent in smallContents) {
                val (number, content) = smallContent.destructured
                contents.add(Pair(content, number.toIntOrZero()))
            }
            bags[bigBag] = contents
        }
        return bags
    }

    private fun String.toIntOrZero(): Int {
        return if (this.toIntOrNull() != null) {
            this.toInt()
        } else {
            0
        }
    }

    fun part1(): Int {
        var count = 0

        for (bag in rules) {
            if (canContainShinyGold(bag.value)) {
                count++
            }
        }

        return count
    }

    private fun canContainShinyGold(contents: List<Pair<String, Int>>): Boolean {
        var flag = false
        if (contents.any { it.first == SHINY_GOLD }) {
            flag = true
        } else {
            for (content in contents) {
                if (rules.contains(content.first) && canContainShinyGold(rules[content.first]!!)) {
                    flag = true
                    break
                }
            }
        }

        return flag
    }

    fun part2(): Int {
        val shinyGold = rules[SHINY_GOLD]
        return countBags(shinyGold!!) -1
    }

    private fun countBags(contents: List<Pair<String, Int>>) : Int {
        var count = 1
        for (content in contents) {
            if (rules.contains(content.first)) {
                count += content.second * countBags(rules[content.first]!!)
            }
        }
        return count
    }
}