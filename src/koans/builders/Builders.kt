package koans.builders

import java.util.HashMap

/**
 * @author Martin Trollip
 * @since 2018/12/14 11:30
 */


fun main(args: Array<String>) {
    println(task())
    println(usage())
    println(createString())
    println(createMap())
}

/**
 * Extension function literals
 *
 * Read about <a href="https://kotlinlang.org/docs/reference/lambdas.html#function-literals-with-receiver">function literals</a> with receiver.
 *
 * You can declare isEven and isOdd as values, that can be called as extension functions. Complete the declarations below.
 */
fun task(): List<Boolean> {
    val isEven: Int.() -> Boolean = { this % 2 == 0 }
    val isOdd: Int.() -> Boolean = { !this.isEven() }

    return listOf(42.isOdd(), 239.isOdd(), 294823098.isEven())
}

/**
 * String and map builders
 * Extension function literals are very useful for creating builders, e.g.:
 *
 * <code>
 * fun buildString(build: StringBuilder.() -> Unit): String {
 *      val stringBuilder = StringBuilder()
 *      stringBuilder.build()
 *      return stringBuilder.toString()
 * }
 * ​
 * val s = buildString {
 *      this.append("Numbers: ")
 *      for (i in 1..3) {
 *          // 'this' can be omitted
 *          append(i)
 *      }
 * }
​ *
 * s == "Numbers: 123"
 * </code>
 *
 * Add and implement the function 'buildMap' with one parameter (of type extension function) creating a new HashMap, building it and returning it as a result. The usage of this function is shown below.
 */
fun buildMap(buildMartin: HashMap<Int, String>.() -> Unit): Map<Int, String> {
    val map = HashMap<Int, String>()
    map.buildMartin()
    return map
}

fun usage(): Map<Int, String> {
    return buildMap {
        put(0, "0")
        for (i in 1..10) {
            put(i, "$i")
        }
    }
}

/**
 * The function apply
 * The previous examples can be rewritten using the library function apply (see examples below). Write your own implementation of this function named 'myApply'.
 */
fun <T> T.myApply(function: T.() -> Unit): T {
    function() //This was passed as parameter
    return this
}

fun createString(): String {
    return StringBuilder().myApply {
        append("Numbers: ")
        for (i in 1..10) {
            append(i)
        }
    }.toString()
}

fun createMap(): Map<Int, String> {
    return hashMapOf<Int, String>().myApply {
        put(0, "0")
        for (i in 1..10) {
            put(i, "$i")
        }
    }
}