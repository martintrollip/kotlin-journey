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

class Day10(val input: String) {

    private fun read(fileName: String): List<Int> {
        return File(fileName).readLines().map {
            it.toInt()
        }
    }

    fun part1(): Int {
        val adapters = read(input).sorted().toMutableList()
        val myDeviceJolts = adapters.max()!! + 3
        var currentJolts = 0
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

        return adapterOrder.count { it.second == 1 } * adapterOrder.count { it.second == 3 }
    }


    fun part2(): Long {
        val adapters = read(input).toMutableList()
        val myDeviceJolts = adapters.max()!! + 3
        adapters.add(myDeviceJolts)
        adapters.sort()

//        return checkAdapter(path = "0", currentJolts = 0, requiredJolts = myDeviceJolts, adapters = adapters)
        return checkAdapter2(adapters)
    }

    fun checkAdapter(path: String, currentJolts: Int, requiredJolts: Int, adapters: List<Int>): Int {
        var validCombo = 0
        //TODO keep list of valid adapters, but take into account that a valid adapter might have many valid paths after it too
        if (currentJolts < requiredJolts - 3 || currentJolts > requiredJolts) {
            val compatibleAdapters = adapters.filter { it in currentJolts..currentJolts + 3 }

            if (compatibleAdapters.isNotEmpty()) {
                for (remainingAdapter in compatibleAdapters) {
                    validCombo += checkAdapter("$path -> $remainingAdapter", remainingAdapter, requiredJolts, adapters.filter { it > remainingAdapter })
                }
            }
        }

        if (currentJolts >= requiredJolts - 3 && currentJolts <= requiredJolts) {
            validCombo++
        }

        return validCombo
    }

    fun checkAdapter2(adapters: List<Int>): Long {
        val validAdapters: MutableMap<Int, Long> = mutableMapOf()

        for (adapter in adapters) {
            if (validAdapters.isEmpty()) {
                validAdapters[0] = 1
            }
            var countPaths = 0L
            for (compatibleAdapter in adapter - 1 downTo adapter - 3) {
                countPaths += validAdapters.getOrDefault(compatibleAdapter, 0)
            }
            validAdapters[adapter] = countPaths
        }

        return validAdapters.maxBy { it.value }!!.value
    }
}