package koans.conventions

/**
 * @author Martin Trollip
 * @since 2018/11/18 21:16
 */
class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {

    override operator fun contains(contains: MyDate): Boolean {
        return contains > start && contains <= endInclusive
    }

    override fun iterator(): Iterator<MyDate> = DateIterator(this)
}

/**
 * Range to
 * Implement the function <code>MyDate.rangeTo()</code>. This allows you to create a range of dates using the following syntax:
 *
 * <code>
 * MyDate(2015, 5, 11)..MyDate(2015, 5, 12)
 * </code>
 *
 * Note that now the class <code>DateRange</code> implements the standard <code>ClosedRange</code> interface and inherits contains method implementation.
 *
 */
operator fun MyDate.rangeTo(other: MyDate) = DateRange(this, other)
