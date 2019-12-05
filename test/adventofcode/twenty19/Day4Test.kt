package adventofcode.twenty19

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2019/12/04 16:07
 */
class Day4Test {

    private var day4: Day4 = Day4()

    /**
     * It is a six-digit number.
     */
    @Test
    fun testSixDigits() {
        assertTrue(day4.sixDigits(123456))
    }

    /**
     * The value is within the range given in your puzzle input.
     */
    @Test
    fun testRange() {
        assertTrue(day4.inRange(123456))
    }

    /**
     * Two adjacent digits are the same (like 22 in 122345).7u
     *
     * See https://regex101.com/r/WOCjyK/1
     */
    @Test
    fun testAdjacentDigits() {
        assertFalse(day4.adjacentDigits(12345))
        assertFalse(day4.adjacentDigits(12))
        assertTrue(day4.adjacentDigits(22))
        assertTrue(day4.adjacentDigits(122345))
        assertTrue(day4.adjacentDigits(123455))
        assertTrue(day4.adjacentDigits(1223455))
        assertTrue(day4.adjacentDigits(11122111))
    }

    /**
     * Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).
     */
    @Test
    fun testIncreasingDigits() {
        assertFalse(day4.increasingDigits(1111023))
        assertFalse(day4.increasingDigits(987654321))
        assertFalse(day4.increasingDigits(1234567890))
        assertTrue(day4.increasingDigits(135679))
        assertTrue(day4.increasingDigits(1111135679))
        assertTrue(day4.increasingDigits(22))
        assertTrue(day4.increasingDigits(22))
    }

    /**
     * 111111 meets these criteria (double 11, never decreases).
     * 223450 does not meet these criteria (decreasing pair of digits 50).
     * 123789 does not meet these criteria (no double).
     */
    @Test
    fun testMatch() {
        assertFalse(day4.matchPart1(1111023))
        assertFalse(day4.matchPart1(987654321))
        assertFalse(day4.matchPart1(223450))
        assertFalse(day4.matchPart1(123789))
        assertTrue(day4.matchPart1(111111))
    }

    /**
     * The two adjacent matching digits are not part of a larger group of matching digits.
     *
     * See https://regex101.com/r/WOCjyK/4
     */
    @Test
    fun testAdjacentAlone() {
        assertTrue(day4.adjacentAloneDigits(11))
        assertFalse(day4.adjacentAloneDigits(111))
        assertFalse(day4.adjacentAloneDigits(1111))
        assertFalse(day4.adjacentAloneDigits(11111))
        assertFalse(day4.adjacentAloneDigits(111111))
        assertTrue(day4.adjacentAloneDigits(111122))
        assertTrue(day4.adjacentAloneDigits(112233))
        assertFalse(day4.adjacentAloneDigits(123444))
    }

    /**
     *  112233 meets these criteria because the digits never decrease and all repeated digits are exactly two digits long.
     *  123444 no longer meets the criteria (the repeated 44 is part of a larger group of 444).
     *  111122 meets the criteria (even though 1 is repeated more than twice, it still contains a double 22).
     *
     */
    @Test
    fun testMatchPart2() {
        assertTrue(day4.matchPart2(112233))
        assertFalse(day4.matchPart2(123444))
        assertTrue(day4.matchPart2(111122))
        assertTrue(day4.matchPart2(1111122))
        assertFalse(day4.matchPart2(111111))
        assertFalse(day4.matchPart2(11122111))
        assertTrue(day4.matchPart2(111223))
        assertTrue(day4.matchPart2(223333))
        assertTrue(day4.matchPart2(224444))
        assertFalse(day4.matchPart2(222444))
        assertFalse(day4.matchPart2(333456))
        assertFalse(day4.matchPart2(123444))
    }
}





