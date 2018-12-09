package adventofcode.twenty18

import java.io.File
import kotlin.math.absoluteValue

/**
 * @author Martin Trollip
 * @since 2018/12/09 06:58
 *
 *
 * 1114593875 is too small for part 2
 */
val SCORES_REGEX = "([0-9]+) players; last marble is worth ([0-9]+) points".toRegex()

const val DAY9_INPUT = "src/res/day9_input_small"

fun main(args: Array<String>) {
    var playerCount = 0
    var lastScore = 0

    File(DAY9_INPUT).readLines().map {
        val matchResult = SCORES_REGEX.find(it)
        val (players, worth) = matchResult!!.destructured
        playerCount = players.toInt()
        lastScore = worth.toInt()
    }

    var board = ArrayList<Marble>()
    playGame(board, playerCount, lastScore)
}

fun playGame(board: MutableList<Marble>, playerCount: Int, lastScore: Int) {
    var scores = LinkedHashMap<Int, Int>()
    var round = 1
    var currentPlayer = -1
    var currentPosition = 0
    var nextMarble = Marble(0)
    board.add(0, nextMarble)
    printRound(nextMarble.player, board)

    while (nextMarble.number < lastScore) {
        currentPlayer = currentPlayer.nextPlayer(playerCount)
        nextMarble = Marble(round, currentPlayer)

        if (!nextMarble.number.multipleOf23()) {
            currentPosition = nextPosition(currentPosition, board.size)
            board.add(currentPosition, nextMarble)
        } else {
            var playerScore = scores.getOrDefault(currentPlayer, 0)
            playerScore += nextMarble.number
            val removedPosition = positionCounterClockwise(currentPosition, board.size)
            playerScore += board.pop(removedPosition).number
            currentPosition = removedPosition
            scores.put(currentPlayer, playerScore)
        }

        printRound(nextMarble.player, board)
        round++
    }

    val maxScore = scores.maxBy { it.value }
    println("The winner is $maxScore")
}

fun nextPosition(currentPosition: Int, currentSize: Int): Int {
    if (currentSize == 0 || currentSize == 1) {
        return currentSize
    }

    val pos = (currentPosition + 2).rem(currentSize)
    return if (pos == 0) {
        //place at end rather
        currentSize
    } else {
        pos
    }
}

fun positionCounterClockwise(currentPosition: Int, currentSize: Int): Int {
    val pos: Int
    if (currentPosition - 7 < 0) {
        pos = currentSize - (currentPosition - 7).absoluteValue
    } else {
        pos = (currentPosition - 7).rem(currentSize)
    }

    return if (pos == 0) {
        //place at end rather
        currentSize
    } else {
        pos
    }
}

fun MutableList<Marble>.pop(index: Int): Marble {
    val marble = get(index)
    removeAt(index)
    return marble
}

fun Int.multipleOf23(): Boolean {
    return this != 0 && rem(23) == 0
}

fun Int.nextPlayer(playerCount: Int): Int {
    return (this + 1).rem(playerCount)
}

private fun printRound(player: Int, board: MutableList<Marble>) {
    var print = "[$player]".padEnd(4)
    for (marble in board) {
        print += "(${marble.number})".padStart(5)
    }
    println(print)
}

data class Marble(var number: Int, var player: Int = -1)