package conventions

/**
 * @author Martin Trollip
 * @since 2018/11/18 20:50
 */
fun main(args: Array<String>) {
    //Comparison
    val currentDay = MyDate(2018, 11, 18)
    val futureDay = MyDate(2018, 12, 18)
    val pastDay = MyDate(2018, 10, 18)

    println(compare(futureDay, currentDay))

    //In range
    println(checkInRange(pastDay, futureDay, currentDay))

    //Range to
    println(pastDay..futureDay)

    //For loop
    println(checkInRange(pastDay, futureDay, currentDay))
}

/**
 * Comparison
 * Read about <a href="http://kotlinlang.org/docs/reference/operator-overloading.html">operator overloading</a> to learn how different conventions for operations like ==, <, + work in Kotlin. Add the function compareTo to the class MyDate to make it comparable. After that the code below date1 < date2 will start to compile.
 *
 * In Kotlin when you override a member, the modifier override is mandatory.
 */
fun compare(date1: MyDate, date2: MyDate) = date1 > date2

/**
 * In range
 * In Kotlin <code>in</code> checks are translated to the corresponding <code>contains</code> calls:
 *
 * <code>
 * val list = listOf("a", "b")
 * "a" in list  // list.contains("a")
 * "a" !in list // !list.contains("a")
 * </code>
 *
 * Read about <a href="http://kotlinlang.org/docs/reference/ranges.html">ranges>/a>. Add a method <code>fun contains(d: MyDate)</code> to the class <code>DateRange</code> to allow in checks with a range of dates.
 */
fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
    return date in DateRange(first, last)
}

/**
 * For loop
 * Kotlin <a href="http://kotlinlang.org/docs/reference/control-flow.html#for-loops">for loop</a> iterates through anything that provides an iterator. Make the class DateRange implement Iterable<MyDate>, so that it could be iterated over. You can use the function MyDate.nextDay() defined in DateUtil.kt
 */
fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
    for (date in firstDate..secondDate) {
        handler(date)
    }
}