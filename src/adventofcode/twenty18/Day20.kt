package adventofcode.twenty18

import java.io.File
import java.util.*

/**
 * @author Martin Trollip
 * @since 2019/01/03 11:55
 */
const val DAY20_INPUT = "src/res/day20_input"

fun main(args: Array<String>) {
    val parsed = parse(File(DAY20_INPUT).readText())
    println("The largest number of doors you would be required to pass through to reach a room? ${roomsPart1(parsed)}")
    println("How many rooms have a shortest path from your current location that pass through at least 1000 doors? ${roomsPart2(parsed)}")
}

fun roomsPart1(parsed: Map<Room, Int>): Int {
    return parsed.maxByOrNull  { it.value }!!.value
}

fun roomsPart2(parsed: Map<Room, Int>): Int {
    return parsed.filter { it.value >= 1000 }.count()
}

fun parse(string: String): Map<Room, Int> {
    var currentRoom = Room(0, 0)
    val building = mutableMapOf(currentRoom to 0)

    val parenthesis = ArrayDeque<Room>(10)
    string.forEach {
        when (it) {
            'N', 'E', 'S', 'W' -> {
                currentRoom.direction = it
                val distanceFromStart = building.getOrDefault(currentRoom, 0) + 1
                currentRoom = currentRoom.move(it) //Create a new Room with new Coordinates

                if (building.containsKey(currentRoom)) {
                    building[currentRoom] = Math.min(building[currentRoom]!!, distanceFromStart)
                } else {
                    building[currentRoom] = distanceFromStart
                }
            }
            '(' -> {
                parenthesis.push(currentRoom)
            }
            '|' -> {
                currentRoom = parenthesis.peek()
            }
            ')' -> {
                parenthesis.pop()
            }
            else -> {/*Unknown characters ignored*/
            }
        }
    }

    return building
}

data class Room(val x: Int, val y: Int, var direction: Char = '?') {

    /**
     * This will return a new room with new Coordinates if it's a valid direction to move in
     */
    fun move(direction: Char): Room {
        when (direction) {
            'N' -> {
                return Room(x, y - 1, direction)
            }
            'E' -> {
                return Room(x + 1, y, direction)
            }
            'S' -> {
                return Room(x, y + 1, direction)
            }
            'W' -> {
                return Room(x - 1, y, direction)
            }
            else -> {
                return this
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other is Room) {
            return x == other.x && y == other.y
        }
        return false
    }

    override fun hashCode(): Int {
        return x.hashCode() + (y.hashCode() * y.hashCode())
    }
}


