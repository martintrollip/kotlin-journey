package adventofcode.twenty20

import java.io.File

/**
 * @author Martin Trollip
 * @since 2020/12/01 11:40
 */
private const val DAY1_INPUT = "src/res/twenty20/day1_input"
private const val SUM_REQUIRED = 2020

fun main(args: Array<String>) {
    val day1 = Day1(DAY1_INPUT)
    println("Find the two entries that sum to 2020; what do you get if you multiply them together? ${day1.productOfExpensesSummingToValue(SUM_REQUIRED)}")
    println("In your expense report, what is the product of the three entries that sum to 2020? ${day1.productOfThreeExpensesSummingTo2020()}")
}

class Day1(input: String) {

    private val expenseEntries: HashSet<Int>

    init {
        expenseEntries = readInput(input)
    }

    private fun readInput(fileName: String): HashSet<Int> {
        val expenses = File(fileName).readLines().map { it.toInt() }
        return HashSet(expenses)
    }

    fun productOfExpensesSummingToValue(requiredSum: Int): Int {
        for (expenseEntry in expenseEntries) {
            val requiredTerm = requiredSum - expenseEntry
            if (expenseEntries.contains(requiredTerm)) {
                return expenseEntry * requiredTerm
            }
        }
        return -1
    }

    fun productOfThreeExpensesSummingTo2020(): Int {
        for (expenseEntry in expenseEntries) {
            val requiredTerm = SUM_REQUIRED - expenseEntry

            for (secondExpenseEntry in expenseEntries) {
                val product = productOfExpensesSummingToValue(requiredTerm)

                if (product >= 0) {
                    return expenseEntry * product;
                }
            }
        }
        return -1;
    }
}

