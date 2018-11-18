package conventions

/**
 * @author Martin Trollip
 * @since 2018/11/18 20:51
 */
data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        if (year != other.year) {
            return year - other.year
        }

        if (month != other.month) {
            return month - other.month
        }

        if (dayOfMonth != other.dayOfMonth) {
            return dayOfMonth - other.dayOfMonth
        }

        return 0
    }
}