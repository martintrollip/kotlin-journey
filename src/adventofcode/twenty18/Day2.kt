package adventofcode.twenty18

import java.io.File

/**
 * @author Martin Trollip
 * @since 2018/12/03 14:22
 */
fun main(args: Array<String>) {
    val lines = File("src/res/day2_input").readLines()

    println("The checksum is " + checksum(lines))
    similarBoxes(lines)
}

fun checksum(lines: List<String>): Int {

    var twoTimesCount = 0
    var threeTimesCount = 0

    lines.map {
        val chars = LinkedHashMap<Char, Int>()

        it.map {
            if (chars.containsKey(it)) {
                chars[it]?.plus(1)?.let { it1 -> chars.put(it, it1) }
            } else {
                chars.put(it, 1)
            }
        }

        val filterTwoTimes = chars.filter {
            it.value == 2
        }

        val filterThreeTimes = chars.filter {
            it.value == 3
        }

        if (!filterTwoTimes.isEmpty()) {
            twoTimesCount++
        }

        if (!filterThreeTimes.isEmpty()) {
            threeTimesCount++
        }
    }

    return twoTimesCount * threeTimesCount
}

fun similarBoxes(lines: List<String>) {
    lines.forEach {
        lines.similarCodes(it)?.let {
            println("The similar codes are $it")
        }
    }
}

fun List<String>.similarCodes(another: String): String? {
    this.forEach {
        if (it.differences(another) == 1) {
            return it.corrected(another)
        }
    }

    return null
}

fun String.differences(another: String): Int {
    var differences = 0
    this.forEachIndexed { index, char ->
        if (char != another[index]) {
            differences++
        }
    }
    return differences
}

fun String.corrected(another: String): String {
    var correctedId = ""
    this.forEachIndexed { index, char ->
        if (char == another[index]) {
            correctedId += char
        }
    }
    return correctedId
}