package masterclass.conditionls

/**
 * @author Martin Trollip
 * @since 2019/02/17 16:52
 */
fun main(args: Array<String>) {
    val age = 6

    when(age) {
        in 10..19 -> println("Teenager")
        else -> println("Not teenager")
    }

    //or alternative writing which is cleaner
    when {
        age < 10 -> println("Less than 10")
        age in 10..19 -> println("Teenager")
        else -> println("Not teenager")
    }
}