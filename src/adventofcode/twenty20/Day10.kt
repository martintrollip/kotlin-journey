package adventofcode.twenty20

import java.io.File

/**
 * @author Martin Trollip
 * @since 2020/12/11 06:10
 */
private const val DAY10_INPUT = "src/res/twenty20/day10_input"

fun main(args: Array<String>) {
    val day10 = Day10(DAY10_INPUT)
    println("${day10.part1()}")
    println("${day10.part2()}")
}

class Day10(input: String) {

    private val socketJolts = 0
    private val adapters: MutableList<Int>
    val myDeviceJolts: Int

    init {
        adapters = read(input).sorted().toMutableList()
        myDeviceJolts = adapters.max()!! + 3
    }

    private fun read(fileName: String): List<Int> {
        return File(fileName).readLines().map {
            it.toInt()
        }
    }

    fun part1(): Int {
        var currentJolts = socketJolts
        val adapterOrder = mutableListOf<Pair<Int, Int>>()

        while (adapters.isNotEmpty()) {
            val first = adapters.firstOrNull { it in currentJolts..currentJolts + 3 }

            if (first != null) {
                adapterOrder.add(Pair(first, first - currentJolts))
                currentJolts = first
                adapters.remove(first)
            } else {
                val last = adapters.first()
                adapterOrder.add(Pair(last, last - currentJolts))
                adapters.remove(last)
            }
        }

        adapterOrder.add(Pair(myDeviceJolts, myDeviceJolts - currentJolts))

        return adapterOrder.count { it.second == 1 } * adapterOrder.count {it.second == 3}
    }


    fun part2(): Int {
        return -2
    }
}