package adventofcode.twenty20

import java.io.File

/**
 * @author Martin Trollip
 * @since 2020/12/17 12:04
 */
private const val DAY16_INPUT = "src/res/twenty20/day16_input"

fun main(args: Array<String>) {
    val day16 = Day16(DAY16_INPUT)
    println("${day16.part1()}")
    println("${day16.part2()}")
}

class Day16(val input: String) {

    private val FIELDS_REGEX =
        "([a-z]+\\s?[a-z]+):\\s+([0-9]+)-([0-9]+)\\s+or\\s+([0-9]+)-([0-9]+)".toRegex() //https://regex101.com/r/D47dW1/1

    private val fields = mutableMapOf<String, List<IntRange>>()
    private var myTicket = listOf<Int>()
    private val nearbyTickets = mutableListOf<List<Int>>()

    init {
        var checkingMyTicket = false
        var checkingNearbyTickets = false
        File(input).readLines().forEach { line ->
            when {
                line.matches(FIELDS_REGEX) -> {
                    val (fieldName, firstGroupStart, firstGroupEnd, secondGroupStart, secondGroupEnd) = FIELDS_REGEX.find(line)!!.destructured
                    fields[fieldName] = listOf(
                        IntRange(firstGroupStart.toInt(), firstGroupEnd.toInt()),
                        IntRange(secondGroupStart.toInt(), secondGroupEnd.toInt())
                    )
                }
                checkingMyTicket -> {
                    myTicket = line.split(",").map { it.toInt() }
                    checkingMyTicket = false
                }
                checkingNearbyTickets -> {
                    val ticket = line.split(",").map { it.toInt() }
                    nearbyTickets.add(ticket)
                }
            }

            if (line.contains("your ticket:")) {
                checkingMyTicket = true
            }

            if (line.contains("nearby tickets:")) {
                checkingNearbyTickets = true
            }
        }
    }

    fun part1(): Int {
        var errors = 0
        for (nearbyTicket in nearbyTickets) {
            val invalidValue = getInvalidIfAny(nearbyTicket, fields.values.flatten())
            if (invalidValue > 0) {
                errors += invalidValue
            }
        }

        return errors
        //Another nicer way
//    https://todd.ginsberg.com/post/advent-of-code/2020/day16/
//    return nearbyTickets.sumBy { ticket ->
//        ticket.filter { field ->
//            fields.values.flatten().none { rule -> field in rule }
//        }.sum()
//    }
    }



    fun getInvalidIfAny(nearbyTicket: List<Int>, range: List<IntRange>): Int {
        for (fieldValue in nearbyTicket) { //TODO this is brute force, find a better way
            var fieldValid = false
            for (intRange in range) {
                if (intRange.contains(fieldValue)) {
                    fieldValid = true
                }
            }
            if (!fieldValid) {
                return fieldValue
            }
        }
        return -1
    }

    fun part2(): Long {
        val nearbyTicketsFiltered = nearbyTickets.filter { getInvalidIfAny(it, fields.values.flatten()) < 0 }

        val fieldIndexes = fields.keys.map { fieldName ->
            fieldName to myTicket.indices.filterIndexed { index, _ ->
                allFieldsMatch(index, fields[fieldName]!!, nearbyTicketsFiltered)
            }.toMutableList()
        }.toMap()

        while (fieldIndexes.any { it.value.size > 1}) {
            val toRemove = fieldIndexes.filter { it.value.size == 1 }.map { it.value[0] }
            fieldIndexes.filter { it.value.size > 1 }.forEach { it.value.removeAll(toRemove) }
        }

        return fieldIndexes
            .filter { it.key.startsWith("departure") }
            .map { myTicket[it.value[0]].toLong() }
            .reduce {multiply, value -> multiply * value }
    }

    private fun allFieldsMatch(checkIndex: Int, range: List<IntRange>, nearbyTickets: List<List<Int>>): Boolean {
        val matchedFields = nearbyTickets.count { ticket ->
            fieldMatch(checkIndex, range, ticket)
        }
        return matchedFields == nearbyTickets.size
    }

    private fun fieldMatch(checkIndex: Int, range: List<IntRange>, ticket: List<Int>): Boolean {
        return range.any { rule -> ticket[checkIndex] in rule }
    }
}