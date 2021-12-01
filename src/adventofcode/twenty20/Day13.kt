package adventofcode.twenty20

import java.io.File
import kotlin.math.max
import kotlin.math.min

/**
 * @author Martin Trollip
 * @since 2020/12/13 22:30
 */
private const val DAY13_INPUT = "src/res/twenty20/day13_input"

fun main(args: Array<String>) {
    val day13 = Day13(DAY13_INPUT)
    println("${day13.part1()}")
    println("${day13.part2()}")
}

class Day13(val input: String) {

    private fun read(fileName: String): Pair<Int, List<Pair<Int, Long>>> {
        var time = 0
        val ids = mutableListOf<Pair<Int, Long>>()
        File(fileName).readLines().forEachIndexed { index, line ->
            when (index) {
                0 -> time = line.toInt()
                1 -> {
                    line.split(",").forEachIndexed { timeOffset, id ->
                        if (id.toIntOrNull() != null) {
                            ids.add(Pair(id.toInt(), timeOffset.toLong()))
                        }
                    }
                }
            }
        }
        return Pair(time, ids)
    }

    fun part1(): Int {
        val (time, ids) = read(input)

        var minTime = Integer.MAX_VALUE
        var minId = -1

        //TODO can we simplify using streams
        ids.map { it.first }.forEach { id ->
            val departTime = ((time / id) + 1) * id
            if (departTime < minTime) {
                minTime = departTime
                minId = id
            }
        }

        return (minTime - time) * minId
    }

    fun part2(): Long {
        val (_, ids) = read(input)

        val minId = ids.minByOrNull  { it.second }!!

        var currentTime = 0L
        while (true) {
            var flag = true
            for (id in ids) {
                if (((currentTime + id.second) % id.first) != 0L) {
                    flag = false
                    break
                }
            }

            if (flag) {
                return currentTime
            }
            currentTime += minId.first + minId.second //Step with the smallest id
        }
    }

    //https://en.wikipedia.org/wiki/Chinese_remainder_theorem
    //https://www.youtube.com/watch?v=ru7mWZJlRQg
    //https://www.youtube.com/watch?v=fz1vxq5ts5I
    //https://www.geeksforgeeks.org/chinese-remainder-theorem-set-1-introduction/
    //https://rosettacode.org/wiki/Chinese_remainder_theorem#Kotlin
    fun part2Attempt2(): Long {
        val (_, ids) = read(input)
        //First check the GCD if it's not 1 for all inputs we can't use the Chinese Remainder Theorem
        assert(checkGreatestCommonDenominator(ids.map { it.first })) { "Unable to perform Chinese Remainder" }
        return chineseRemainder(ids.map { it.first.toLong() }, ids.map { it.second  + 1L})
    }

    fun multInv(a: Long, b: Long): Long {
        if (b == 1L) return 1
        var aa = a
        var bb = b
        var x0 = 0L
        var x1 = 1L
        while (aa > 1L) {
            val q = aa / bb
            var t = bb
            bb = aa % bb
            aa = t
            t = x0
            x0 = x1 - q * x0
            x1 = t
        }
        if (x1 < 0L) x1 += b
        return x1
    }

    fun chineseRemainder(n: List<Long>, a: List<Long>): Long {
        val prod = n.fold(1L) { acc, i -> acc * i }
        var sum = 0L
        for (i in n.indices) {
            val p :Long = prod / n[i]
            sum += a[i] * multInv(p, n[i]) * p
        }
        return (sum % prod)  - (prod + 1) /*TODO figure out why the second term is required, found this by trail and error*/
    }

    fun checkGreatestCommonDenominator(check: List<Int>): Boolean {
        check.forEach { a ->
            check.forEach { b ->
                if (a != b && greatestCommonDenominator(a, b) != 1) {
                    return false
                }
            }
        }
        return true
    }

    fun greatestCommonDenominator(a: Int, b: Int): Int {
        var x = max(a, b)
        var y = min(a, b)
        while (x != y) {
            if (x > y) {
                x -= y
            } else {
                y -= x
            }
        }
        return x
    }
}