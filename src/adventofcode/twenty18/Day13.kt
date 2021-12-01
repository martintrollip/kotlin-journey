package adventofcode.twenty18

import java.io.File
import java.rmi.UnexpectedException
import java.util.*

/**
 * @author Martin Trollip
 * @since 2018/12/15 18:10
 */
const val DAY13_INPUT = "src/res/day13_input"

const val HORIZONTAL = '-'
const val VERTICAL = '|'
const val CORNER_A = '/'
const val CORNER_B = '\\'
const val INTERSECTION = '+'
const val CART_X_POSITIVE = '>'
const val CART_X_NEGATIVE = '<'
const val CART_Y_POSITIVE = 'v'
const val CART_Y_NEGATIVE = '^'

fun main(args: Array<String>) {
    val tracks = LinkedHashMap<TrackCoordinate, Track>()
    val carts = ArrayList<Cart>()

    var y = 0
    File(DAY13_INPUT).readLines().forEach {
        var x = 0
        for (char in it) {
            val coordinate = TrackCoordinate(x++, y)
            when (char) {
                CORNER_A, CORNER_B, HORIZONTAL, VERTICAL, INTERSECTION -> {
                    val track = buildTrack(char)
                    tracks.put(coordinate, track)
                }
                CART_X_POSITIVE, CART_X_NEGATIVE, CART_Y_POSITIVE, CART_Y_NEGATIVE -> {
                    val (cart, track) = buildCartAndTrack(coordinate, char)
                    tracks.put(coordinate, track)
                    carts.add(cart)
                }
            }
        }
        y++
    }

    tracks.print(carts)
    println("Starting carts: $carts \n")
    while (carts.size > 1) {
        carts.move(tracks)
//        tracks.print(carts)
    }
    tracks.print(carts)
    println("Position of final cart ${carts.first()}")
}

fun buildTrack(type: Char): Track {
    when (type) {
        HORIZONTAL -> {
            return HorizontalTrack()
        }
        VERTICAL -> {
            return VerticalTrack()
        }
        CORNER_A -> {
            return CornerForwardSlashTrack()
        }
        CORNER_B -> {
            return CornerBackslashTrack()
        }
        INTERSECTION -> {
            return IntersectionTrack()
        }
    }
    throw UnexpectedException("Don't know what to do with a track of type=$type")
}

fun buildCartAndTrack(coordinate: TrackCoordinate, cartChar: Char): CartOnTrack {
    val track = when (cartChar) {
        CART_X_POSITIVE, CART_X_NEGATIVE -> {
            HorizontalTrack()
        }
        CART_Y_POSITIVE, CART_Y_NEGATIVE -> {
            VerticalTrack()
        }
        else -> throw UnexpectedException("Assume that the cart won't start on an intersection or corner type=$cartChar, on your initial map, the track under each cart is a straight path matching the direction the cart is facing.")
    }

    val cart = when (cartChar) {
        CART_X_POSITIVE -> {
            Cart(coordinate = TrackCoordinate(coordinate.x, coordinate.y), direction = Direction.X_POSITIVE)
        }
        CART_X_NEGATIVE -> {
            Cart(coordinate = TrackCoordinate(coordinate.x, coordinate.y), direction = Direction.X_NEGATIVE)
        }
        CART_Y_POSITIVE -> {
            Cart(coordinate = TrackCoordinate(coordinate.x, coordinate.y), direction = Direction.Y_POSITIVE)
        }
        CART_Y_NEGATIVE -> {
            Cart(coordinate = TrackCoordinate(coordinate.x, coordinate.y), direction = Direction.Y_NEGATIVE)
        }
        else -> throw UnexpectedException("Unexpected cart type=$cartChar")
    }
    return CartOnTrack(cart, track)
}

fun LinkedHashMap<TrackCoordinate, Track>.print(carts: ArrayList<Cart>) {
    val padding = 1

    val minX = minByOrNull  { it.key.x }?.key?.x!!
    val maxX = maxByOrNull  { it.key.x }?.key?.x!!

    val minY = minByOrNull  { it.key.y }?.key?.y!!
    val maxY = maxByOrNull  { it.key.y }?.key?.y!!

    var print = ""
    for (y in minY..maxY) {
        print += "$y".padStart(3)
        for (x in minX..maxX) {
            val coordinate = TrackCoordinate(x, y)
            print +=
                    when {
                        carts.getCartAt(coordinate).size > 1 -> "X".padStart(padding)
                        carts.getCartAt(coordinate).size == 1 -> carts.getCartAt(coordinate).first().getSymbol().padStart(padding)
                        containsKey(coordinate) -> get(coordinate).toString().padStart(padding)
                        else -> " ".padStart(padding)
                    }
        }
        print += "\n"
    }
    print(print)
}

fun ArrayList<Cart>.getCartAt(coordinate: TrackCoordinate): List<Cart> {
    val carts = ArrayList<Cart>()

    for (cart in this) {
        if (cart.coordinate == coordinate) {
            carts.add(cart)
        }
    }
    return carts
}

fun ArrayList<Cart>.move(tracks: LinkedHashMap<TrackCoordinate, Track>) {
    val toRemove = ArrayList<Cart>()
    val sorted = this.sortedWith(compareBy({ it.coordinate.y }, { it.coordinate.x }))

    for (cart in sorted) {
        cart.move(tracks)
        val carts = getCartAt(cart.coordinate)
        if (carts.size > 1) {
            toRemove.addAll(carts)
            println("Colision at ${carts.first().coordinate}")
        }
    }
    removeAll(toRemove)
}

enum class Direction {
    X_POSITIVE, X_NEGATIVE, Y_POSITIVE, Y_NEGATIVE;

    fun rotateLeft(): Direction {
        return when (this) {
            X_POSITIVE -> {
                Y_NEGATIVE
            }
            X_NEGATIVE -> {
                Y_POSITIVE
            }
            Y_POSITIVE -> {
                X_POSITIVE
            }
            Y_NEGATIVE -> {
                X_NEGATIVE
            }
        }
    }

    fun rotateRight(): Direction {
        return when (this) {
            X_POSITIVE -> {
                Y_POSITIVE
            }
            X_NEGATIVE -> {
                Y_NEGATIVE
            }
            Y_POSITIVE -> {
                X_NEGATIVE
            }
            Y_NEGATIVE -> {
                X_POSITIVE
            }
        }
    }
}

class Cart(var coordinate: TrackCoordinate, var direction: Direction) {
    private var intersectionCount = 0

    enum class Moves(i: kotlin.Int) {
        LEFT(0), STRAIGHT(1), RIGHT(2)
    }

    fun move(tracks: LinkedHashMap<TrackCoordinate, Track>) {
        when (direction) {
            Direction.X_POSITIVE -> {
                coordinate.x += 1
            }
            Direction.X_NEGATIVE -> {
                coordinate.x -= 1
            }
            Direction.Y_POSITIVE -> {
                coordinate.y += 1
            }
            Direction.Y_NEGATIVE -> {
                coordinate.y -= 1
            }
        }

        updateDirection(tracks[(TrackCoordinate(coordinate.x, coordinate.y))]!!)
    }

    private fun updateDirection(newTrack: Track) {

        if (shouldTurnUp(newTrack)) {
            direction = Direction.Y_NEGATIVE
        } else if (shouldTurnDown(newTrack)) {
            direction = Direction.Y_POSITIVE
        } else if (shouldTurnLeft(newTrack)) {
            direction = Direction.X_NEGATIVE
        } else if (shouldTurnRight(newTrack)) {
            direction = Direction.X_POSITIVE
        } else if (newTrack is IntersectionTrack) {
            updateDirectionAtIntersection()
        }
        //Horizontal and Vertical don't change direction
    }

    private fun updateDirectionAtIntersection() {
        intersectionCount++
        val move = Moves.values()[(intersectionCount - 1).rem(Moves.values().size)]

        when (move) {
            Moves.LEFT -> {
                direction = direction.rotateLeft()
            }
            Moves.RIGHT -> {
                direction = direction.rotateRight()
            }
        //Straight don't change direction
        }
    }

    /**
     *      ^       ^
     *      ^       ^
     *      ^       ^
     *  >>>>/       \<<<<
     */
    private fun shouldTurnUp(newTrack: Track) =
            (direction == Direction.X_POSITIVE && newTrack is CornerForwardSlashTrack) || (direction == Direction.X_NEGATIVE && newTrack is CornerBackslashTrack)

    /**
     *  >>>>\       /<<<<
     *      v       v
     *      v       v
     *      v       v
     */
    private fun shouldTurnDown(newTrack: Track) =
            (direction == Direction.X_POSITIVE && newTrack is CornerBackslashTrack) || (direction == Direction.X_NEGATIVE && newTrack is CornerForwardSlashTrack)

    /**
     *  <<<<\       v
     *      ^       v
     *      ^       v
     *      ^   <<<</
     */
    private fun shouldTurnLeft(newTrack: Track) =
            (direction == Direction.Y_NEGATIVE && newTrack is CornerBackslashTrack) || (direction == Direction.Y_POSITIVE && newTrack is CornerForwardSlashTrack)

    /**
     *      />>>>   v
     *      ^       v
     *      ^       v
     *      ^       \>>>>
     */
    private fun shouldTurnRight(newTrack: Track) =
            (direction == Direction.Y_NEGATIVE && newTrack is CornerForwardSlashTrack) || (direction == Direction.Y_POSITIVE && newTrack is CornerBackslashTrack)


    public fun getSymbol(): String {
        when (direction) {
            Direction.X_POSITIVE -> {
                return CART_X_POSITIVE.toString()
            }
            Direction.X_NEGATIVE -> {
                return CART_X_NEGATIVE.toString()
            }
            Direction.Y_POSITIVE -> {
                return CART_Y_POSITIVE.toString()
            }
            Direction.Y_NEGATIVE -> {
                return CART_Y_NEGATIVE.toString()
            }
        }
    }

    override fun toString(): String {
        return "$coordinate ${getSymbol()}"
    }
}

abstract class Track {
    abstract fun getSymbol(): String

    override fun toString(): String {
        return getSymbol()
    }
}

class HorizontalTrack : Track() {
    override fun getSymbol(): String {
        return HORIZONTAL.toString()
    }
}

class VerticalTrack : Track() {
    override fun getSymbol(): String {
        return VERTICAL.toString()
    }
}

class CornerForwardSlashTrack() : Track() {
    override fun getSymbol(): String {
        return CORNER_A.toString()
    }
}

class CornerBackslashTrack() : Track() {
    override fun getSymbol(): String {
        return CORNER_B.toString()
    }
}

class IntersectionTrack : Track() {
    override fun getSymbol(): String {
        return INTERSECTION.toString()
    }
}

data class CartOnTrack(var cart: Cart, var track: Track)

class TrackCoordinate(var x: Int, var y: Int) {

    override fun equals(other: Any?): Boolean {
        if (other is TrackCoordinate) {
            return x == other.x && y == other.y
        }
        return false
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }

    override fun toString(): String {
        return "($x,$y)"
    }
}