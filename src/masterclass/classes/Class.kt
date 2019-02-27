package masterclass.classes

/**
 * @author Martin Trollip
 * @since 2019/02/20 06:56
 */
fun main(args: Array<String>) {
    val house = House("appartment", 100000.00, 1994, "Paulo")
    println(house)
    println("The house is an ${house.type} built in ${house.yearBuilt}")

    val lion = Lion("Simba")
    println("${lion.name()} the ${lion.type} has ${lion.numberLegs} legs")

    println("Test".trimFirstAndLastChar())

    val martin = Person()//Without companion object, have to instantiate
    martin.printName()
    Person.printCompanionName()

    operatorOverloading()
}

fun operatorOverloading() {
    val x = 9
    val y = 1

    println(x + y)
    println(x.plus(y))

    val pointX = Point(9,9)
    val pointY = Point(1, 1)

    val sum = pointX + pointY
    println("(${sum.x},${sum.y})")
    val diff = pointX - pointY
    println("(${diff.x},${diff.y})")
}

data class House(val type: String, val price: Double, val yearBuilt: Int, var owner: String) {
    init {
        println("This is called when object is instantiated: [$this]")
    }
}

/**
 * All classes are final by default in Kotlin, add open if we want to extend them
 */
open class Animal(protected var name: String, var type: String, var numberLegs: Int)

class Lion(name: String) : Animal(name, "Lion", 4) {
    fun name() : String =  name
}

/**
 * Extension functions allows for easy implementations. This can be done on any object
 */
fun String.trimFirstAndLastChar() : String = this.substring(1, this.length - 1)


class Person {
    val name = "Martin"
    fun printName() = println("My name is $name")

    companion object {
        val name = "Companion name"
        fun printCompanionName() = println("My name is $name")
    }
}

class Point(var x: Int, var y: Int) {
    operator fun plus(point: Point): Point {
        return Point(x + point.x, y + point.y)
    }

    operator fun minus(point: Point): Point {
        return Point(x - point.x, y - point.y)
    }
}
