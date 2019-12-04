package adventofcode.twenty19

/**
 * Input part 1 is 108457-562041
 *
 * @author Martin Trollip ***REMOVED***
 * @since 2019/12/04 16:06
 */
fun main(args: Array<String>) {
    val day4 = Day4()

    val start = 108457
    val end = 562041

    println("How many different passwords within the range? ${day4.part1(start, end)}\n")
    println("How many different passwords within the range? (adjacent alone) ${day4.part2(start, end)}")
}

class Day4 {

    fun part1(start: Int, end: Int): Int {
        var matches = 0
        for (i in start..end) {
            if (matchPart1(i)) {
                matches++
            }
        }
        return matches
    }

    fun matchPart1(number: Int): Boolean {
        return adjacentDigits(number) && increasingDigits(number)
    }

    fun sixDigits(number: Int): Boolean {
        //Assume it is always 6 digits
        return true
    }

    fun inRange(number: Int): Boolean {
        //Assume it is always in range
        return true
    }

    private val DUPLICATED_CHAR_REGEX = "([0-9])\\1".toRegex()
    fun adjacentDigits(number: Int): Boolean {
        val duplicated = DUPLICATED_CHAR_REGEX.find(number.toString())
        return duplicated != null
    }

    fun increasingDigits(number: Int): Boolean {
        var num = number
        var a = num % 10

        do {
            num /= 10
            val b = num % 10

            if (b > a) {
                return false
            } else {
                a = b
            }
        } while (num > 0)

        return true
    }

    fun part2(start: Int, end: Int): Int {
        var matches = 0
        for (i in start..end) {
            if (matchPart2(i)) {
                matches++
            }
        }
        return matches
    }

    fun matchPart2(number: Int): Boolean {
        return adjacentAloneDigits(number) && increasingDigits(number)
    }


    private val DUPLICATED_MORE_CHAR_REGEX = "([0-9])\\1{2,6}".toRegex()
    fun adjacentAloneDigits(number: Int): Boolean {
        if (adjacentDigits(number)) {
            var string = number.toString()
            string = string.replace(DUPLICATED_MORE_CHAR_REGEX, "")
            return if (!string.isBlank()) {
                adjacentDigits(string.toInt())
            } else {
                false
            }
        }
        return false
    }
}