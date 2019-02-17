package masterclass.operations

/**
 * @author Martin Trollip
 * @since 2019/02/17 16:28
 */
fun main(args: Array<String>) {
    division()
    remainder()
}

fun division() {
    //For division, be careful of how Kotlin will convert the types
    val a = 1
    val b = 5
    println("1/5 = ${a / b}")

    //Need to convert to float by using c =5f or toFloat()
    val c = 5f
    println("1/5 = ${a / c.toFloat()}")
}

fun remainder() {
    //What remains after dividing 2 numbers
    //For division, be careful of how Kotlin will convert the types
    val a = 8
    val b = 3
    println("8%3 = ${a % b}")
}