package conventions

/**
 * @author Martin Trollip <martint@discovery.co.za>
 * @since 2018/11/18 21:41
 */
class DateIterator(val dateRange:DateRange) : Iterator<MyDate> {
    var current: MyDate = dateRange.start
    override fun next(): MyDate {
        val result = current
        current = current.nextDay()
        return result
    }
    override fun hasNext(): Boolean = current <= dateRange.endInclusive
}