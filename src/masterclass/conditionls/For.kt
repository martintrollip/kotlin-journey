package masterclass.conditionls

/**
 * @author Martin Trollip ***REMOVED***
 * @since 2019/02/17 17:00
 */
fun main(args: Array<String>) {
    val size = 20
    //Count from 0 - 20, inclusive
    for (i in 0..size) {
        println(i)
    }
    println("---")
    for (i in 0 until size) {
        //until is the same as; for (i in 0..size - 1) {
        println(i)
    }
}