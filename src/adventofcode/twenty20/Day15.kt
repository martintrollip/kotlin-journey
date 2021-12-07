package adventofcode.twenty20

/**
 * @author Martin Trollip
 * @since 2020/12/17 10:53
 */
private const val DAY15_INPUT = "src/res/twenty20/day15_input"

fun main(args: Array<String>) {
    val day15 = Day15(DAY15_INPUT)
    println("${day15.part1()}")
    println("${day15.part2()}")
}

class Day15(val input: String) {

    fun part1(): Int {
        return run(2020)
    }

    fun part2(): Int {
        return run(30000000)
    }

    private fun run(rounds: Int): Int {
        var currentRound = 0
        var lastNumberSpoken = 0
        val numbers = mutableMapOf<Int, MutableList<Int>>() //Number, List<Ages> (round numbers where this was spoken).
        input.split(",").forEach { number ->
            currentRound++
            lastNumberSpoken = number.toInt()
            numbers[lastNumberSpoken] = mutableListOf(currentRound)
        }

        while (currentRound < rounds) {
            currentRound++

            if (numbers[lastNumberSpoken]!!.size == 1) {
                //If that was the first time the number has been spoken, the current player says 0.
                lastNumberSpoken = 0
                numbers[lastNumberSpoken]!!.add(currentRound)
            } else {
                //Otherwise, the number had been spoken before; the current player announces how many turns apart the number is from when it was previously spoken.
                lastNumberSpoken = numbers[lastNumberSpoken]!![numbers[lastNumberSpoken]!!.lastIndex] - numbers[lastNumberSpoken]!![numbers[lastNumberSpoken]!!.lastIndex - 1] //TODO simplify

                if (numbers.containsKey(lastNumberSpoken)) {
                    numbers[lastNumberSpoken]!!.add(currentRound)
                } else {
                    numbers[lastNumberSpoken] = mutableListOf(currentRound)
                }
            }
        }

        return lastNumberSpoken
    }

}