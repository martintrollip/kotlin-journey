package adventofcode.twenty19

import java.io.File
import kotlin.math.absoluteValue

/**
 * @author Martin Trollip
 * @since 2019/12/15 07:07
 */

private const val DAY14_INPUT = "src/res/twenty19/day14_input"

fun main(args: Array<String>) {
    val day14 = Day14()

    println("What is the minimum amount of ORE required to produce exactly 1 FUEL? ${day14.part1()}\n") //198984
    println("What is the maximum amount of FUEL you can produce with 1 trillion ORE? ${day14.part2()}") //7659732

}

class Day14 {

    fun readInput(input: List<String>): Map<String, Reaction> {
        val reactions = mutableMapOf<String, Reaction>()
        input.forEach { reaction ->
            val (ingredients, product) = reaction.split(" => ")

            val inputReaction = mutableListOf<Element>()
            ingredients.split(",").forEach {
                val (required, ingredient) = it.trim().split(" ")
                inputReaction.add(Element(ingredient, required.toInt()))
            }
            val (amount, produced) = product.split(" ")
            val fullReaction = Reaction(inputReaction, Element(produced, amount.toInt()))

            reactions[produced] = fullReaction
        }
        return reactions
    }

    fun part1(): Int {
        return oreRequired(readInput(File(DAY14_INPUT).readLines()))
    }


    fun oreRequired(reactions: Map<String, Reaction>): Int {
        return findOre("FUEL", 1, reactions, mutableMapOf())
    }

    fun findOre(material: String, amount: Int, reactions: Map<String, Reaction>, stashedMaterial: MutableMap<String, Int>): Int {
        var oreAmount = 0
        if (material == "ORE") {
            return amount
        }

        val requiredAmount = amount - stashedMaterial.getOrDefault(material, 0)

        if (requiredAmount > 0) {
            //Calculate what will be required to make this reaction
            val reaction = reactions[material]
            val reactionsRequired = Math.ceil((requiredAmount.toDouble() / reaction!!.product.amount)).toInt()
            for (ingredient in reaction.ingredients) {
                oreAmount += findOre(ingredient.name, ingredient.amount * reactionsRequired, reactions, stashedMaterial)
            }
            stashedMaterial[material] = stashedMaterial.getOrDefault(material, 0) + reaction.product.amount * reactionsRequired
        }
        stashedMaterial[material] = stashedMaterial.getOrDefault(material, 0) - (amount.absoluteValue)
        return oreAmount
    }

    fun part2(): Long {
        return maxFuelPossible(readInput(File(DAY14_INPUT).readLines()))
    }

    fun maxFuelPossible(reactions: Map<String, Reaction>): Long {
        var storage = 1000000000000L
        var maxFuel = 0L

        val stash = mutableMapOf<String, Int>()
        while (storage > 0) {
            maxFuel++
            storage -= findOre("FUEL", 1, reactions, stash)
        }

        return maxFuel - 1
    }

    data class Element(val name: String, val amount: Int)
    data class Reaction(val ingredients: List<Element>, val product: Element)
}