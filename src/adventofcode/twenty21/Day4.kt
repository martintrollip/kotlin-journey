package adventofcode.twenty21

import java.io.File

/**
 * @author Martin Trollip
 * @since 2021/12/08 08:21
 */
private const val DAY4_INPUT = "src/res/twenty21/day4_input"

fun main() {
    val day4 = Day4(DAY4_INPUT)
    println(
        "What will your final score be if you choose that board? ${
            day4.calculateWinningBoardScore()
        }"
    )
    println(
        "Once it wins, what would its final score be? ${
            day4.calculateLastWinningBoardScore()
        }"
    )
}

typealias Board = List<List<Int>>

class Day4(inputFile: String) {

    private val winningNumbers: List<Int> = readInput(inputFile).first().split(',').map { it.toInt() }
    private val boards: MutableSet<Board> = buildBoards(readInput(inputFile))

    fun readInput(fileName: String): List<String> {
        val file = File(fileName)
        return file.readLines()
    }

    private fun buildBoards(input: List<String>): MutableSet<Board> {
        return input
            .asSequence()
            .drop(1)
            .filter { it.isNotEmpty() }
            .chunked(5)
            .map { buildBoard(it) }
            .toMutableSet()
    }

    private fun buildBoard(line: List<String>): Board {
        return line
            .map {
                it.split(" ")
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }
            }
    }

    fun calculateWinningBoardScore(): Int {
        val drawnNumbers = emptySet<Int>().toMutableSet()
        val winningScore = winningNumbers.firstNotNullOf { draw ->
            drawnNumbers += draw
            boards.firstOrNull { it.isWinner(drawnNumbers) }?.let { winner ->
                draw * winner.nonWinningSum(drawnNumbers)
            }
        }
        return winningScore
    }

    fun calculateLastWinningBoardScore(): Int {
        val drawnNumbers = emptySet<Int>().toMutableSet()
        var lastDraw = -1
        var lastBoard: Board? = null

        while (boards.size != 0) {
            val winningBoard = winningNumbers.firstNotNullOf { draw ->
                lastDraw = draw
                drawnNumbers += draw
                boards.firstOrNull { it.isWinner(drawnNumbers) }
            }
            boards.remove(winningBoard)
            lastBoard = winningBoard
        }

        return lastBoard?.let { it.nonWinningSum(drawnNumbers) * lastDraw }
            ?: throw IllegalStateException("No winning board found")
    }

    private fun Board.isWinner(winningNumbers: Set<Int>): Boolean {
        return this.any { row -> row.all { number -> number in winningNumbers } } ||
                (0..4).any { column -> this.all { row -> row[column] in winningNumbers } }
    }

    private fun Board.nonWinningSum(winningNumbers: Set<Int>): Int {
        return this.flatten().filter { number -> number !in winningNumbers }.sum()
    }
}

