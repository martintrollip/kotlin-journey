package conventions

/**
 * @author Martin Trollip
 * @since 2018/11/18 20:50
 */
fun main(args: Array<String>) {
    val currentDay = MyDate(2018, 11, 18)
    val futureDay = MyDate(2018, 12, 18)
    val pastDay = MyDate(2018, 10, 18)
    val leapDay = MyDate(2020, 2, 29)

    //Comparison
    println("Comparing:")
    println(compare(futureDay, currentDay))

    //In range
    println("Check in range $pastDay to $futureDay, $currentDay:")
    println(checkInRange(pastDay, futureDay, currentDay))

    //Range to
    println("Range to $pastDay to $futureDay")
    println(pastDay..futureDay)

    //For loop
    println("Iterate/For loop $pastDay to $futureDay")
    iterateOverDateRange(pastDay, futureDay)

    //Operators overloading
    println("Plus operator $currentDay (plus year, plus week")
    println(task1(currentDay))
    println("Plus operator $currentDay (plus 2 years, plus 3 weeks, plus 5 days")
    println(task2(currentDay))

    //Destructuring declarations
    println("29 February of a leap year $leapDay")
    println(isLeapDay(leapDay))

    //Invoke
    println("Invoke twice")
    invokeTwice(Invoker())
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
fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate) {
    for (date in firstDate..secondDate) {
        println("${date.year} ${date.month} ${date.dayOfMonth}")
    }
}

/**
 * Operators overloading
 * Implement a kind of date arithmetic. Support adding years, weeks and days to a date. You could be able to write the code like this: date + YEAR * 2 + WEEK * 3 + DAY * 15.
 *
 * At first, add an extension function 'plus()' to MyDate, taking a TimeInterval as an argument. Use an utility function MyDate.addTimeIntervals() declared in DateUtil.kt
 *
 * Then, try to support adding several time intervals to a date. You may need an extra class.
 */
fun task1(today: MyDate): MyDate {
    return today + TimeInterval.YEAR + TimeInterval.WEEK
}

fun task2(today: MyDate): MyDate {
   return today + TimeInterval.YEAR * 2 + TimeInterval.WEEK * 3 + TimeInterval.DAY * 5
}

/**
 * Destructuring declarations
 * Read about <a href="http://kotlinlang.org/docs/reference/multi-declarations.html">destructuring declarations</a> and make the following code compile by adding one word.
 *
 * Example: Returning Two Values from a Function! :)
 */
fun isLeapDay(date: MyDate): Boolean {

    val (year, month, dayOfMonth) = date

    // 29 February of a leap year
    return year % 4 == 0 && month == 2 && dayOfMonth == 29
}

/**
 * Invoke
 * Objects with invoke() method can be invoked as a function.
 *
 * You can add invoke extension for any class, but it's better not to overuse it:
 *
 * <code>
 * fun Int.invoke() { println(this) }
​ *
 * 1() //huh?..
 * </code>
 *
 * Implement the function Invokable.invoke() so it would count a number of invocations.
 *
 */
fun invokeTwice(invokable: Invoker) = invokable()()