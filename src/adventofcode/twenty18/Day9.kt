package adventofcode.twenty18

import java.io.File

/**
 * @author Martin Trollip
 * @since 2018/12/09 06:58
 *
 *
 * 1114593875 is too small for part 2
 */
val SCORES_REGEX = "([0-9]+) players; last marble is worth ([0-9]+) points".toRegex()

const val DAY9_INPUT = "src/res/day9_input"

fun main(args: Array<String>) {
    var playerCount = 0
    var lastScore = 0

    File(DAY9_INPUT).readLines().map {
        val matchResult = SCORES_REGEX.find(it)
        val (players, worth) = matchResult!!.destructured
        playerCount = players.toInt()
        lastScore = worth.toInt()
    }

    playGame(playerCount, lastScore)
}

fun playGame(playerCount: Int, lastScore: Int) {
    val scores = LinkedHashMap<Int, Long>()

    var round = 1
    var currentPlayer = -1
    var nextMarble = Marble(0)

    var currentNode = CircularNode(item = nextMarble)

    while (nextMarble.number < lastScore * 100) { //Times 100 for part 2
        currentPlayer = currentPlayer.nextPlayer(playerCount)
        nextMarble = Marble(round, currentPlayer)

        if (!nextMarble.number.multipleOf23()) {
            currentNode = currentNode.insert(nextMarble)
        } else {
            var playerScore = scores.getOrDefault(currentPlayer, 0)
            playerScore += nextMarble.number
            currentNode = currentNode.popPositionCounterClockwise()
            playerScore += currentNode.item.number
            currentNode = currentNode.next

            scores.put(currentPlayer, playerScore)
        }
        round++
//        currentNode.print()
    }

    val maxScore = scores.maxBy { it.value }
    println("The winner is $maxScore")
}

fun Int.multipleOf23(): Boolean {
    return this != 0 && rem(23) == 0
}

fun Int.nextPlayer(playerCount: Int): Int {
    return (this + 1).rem(playerCount)
}

data class Marble(var number: Int, var player: Int = -1)

class CircularNode {

    var previous: CircularNode
    var next: CircularNode
    var item: Marble

    constructor(item: Marble) {
        this.item = item
        this.previous = this
        this.next = this
    }

    fun insert(marble: Marble): CircularNode {
        val insert = CircularNode(marble)

        val pos1 = next
        val pos2 = next.next

        pos1.next = insert
        insert.previous = pos1

        pos2.previous = insert
        insert.next = pos2

        return insert
    }

    fun popPositionCounterClockwise(): CircularNode {
        val node = previous.previous.previous.previous.previous.previous.previous
        return pop(node)
    }

    private fun pop(node: CircularNode): CircularNode {
        node.previous.next = node.next
        node.next.previous = node.previous

        return node
    }
}

fun CircularNode.print() {
    var print = this.item.number.toString()

    var nextNode = this.next
    while (nextNode != this) {
        print += "-" + nextNode.item.number
        nextNode = nextNode.next
    }
    println(print)
}