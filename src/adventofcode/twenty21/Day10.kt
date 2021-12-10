package adventofcode.twenty21

import java.io.File
import java.util.*

/**
 * @author Martin Trollip
 * @since 2021/12/10 07:06
 */
private const val DAY10_INPUT = "src/res/twenty21/day10_input"

fun main() {
    val day10 = Day10()
    println(
        "What is the total syntax error score for those errors?? ${
            day10.part1(day10.readInput(DAY10_INPUT))
        }"
    )
    println(
        "What is the middle score? ${
            day10.part2(day10.readInput(DAY10_INPUT))
        }"
    )
}

class Day10 {

    fun readInput(fileName: String): List<String> {
        return File(fileName).readLines()
    }

    fun part1(input: List<String>): Int {
        return calculateSyntaxErrorScore(input)
    }

    fun part2(input: List<String>): Long {
        val completedLines = input.filter { isIncomplete(it) }.map { completeIncompleteStack(it) }
        return completedLines.map { calculateAutoCompleteScore(it) }.sorted()[completedLines.size / 2]
    }

    private fun calculateSyntaxErrorScore(input: List<String>): Int {
        return input.sumOf { calculateSyntaxErrorScore(it) }
    }

    private fun calculateSyntaxErrorScore(line: String): Int {
        val lineStack = buildStack(line)
        return when (lineStack.illegalChar()) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }
    }

    private fun calculateAutoCompleteScore(line: List<Char>): Long {
        var score = 0L

        line.forEach { char ->
            score *= 5
            when (char) {
                ')' -> score += 1
                ']' -> score += 2
                '}' -> score += 3
                '>' -> score += 4
            }
        }

        return score
    }

    private fun isIncomplete(line: String): Boolean {
        return buildStack(line).containsOnlyOpenBrackets()
    }

    private fun completeIncompleteStack(line: String): List<Char> {
        return buildStack(line).map { it.invertOpenBracket() }.reversed()
    }

    private fun buildStack(line: String): Stack<Char> {
        val stack = Stack<Char>()

        for (c in line) {
            if (!c.isBracket())
                continue

            if (stack.isNotEmpty() && c.matchesBracket(stack.peek())) {
                stack.pop()
            } else {
                stack.push(c)
            }
        }

        return stack
    }

    private fun Char.isBracket(): Boolean {
        return this == '[' || this == ']' || this == '{' || this == '}' || this == '(' || this == ')' || this == '<' || this == '>'
    }

    private fun Char.isOpenBracket(): Boolean {
        return this == '[' || this == '{' || this == '(' || this == '<'
    }

    private fun Char.matchesBracket(other: Char): Boolean {
        return this == ']' && other == '[' || this == '}' && other == '{' || this == ')' && other == '(' || this == '>' && other == '<'
    }

    private fun Char.invertOpenBracket(): Char {
        return when (this) {
            '[' -> ']'
            '{' -> '}'
            '(' -> ')'
            '<' -> '>'
            else -> throw IllegalArgumentException("$this is not a open bracket")
        }
    }

    private fun Stack<Char>.containsOnlyOpenBrackets(): Boolean {
        return this.isEmpty() || this.all { it.isOpenBracket() }
    }

    private fun Stack<Char>.illegalChar(): Char? {
        return this.firstOrNull { !it.isOpenBracket() }
    }
}

