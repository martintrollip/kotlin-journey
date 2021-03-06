    package koans.conventions

    import java.util.*

    /**
     * @author Martin Trollip
     * @since 2018/11/18 20:51
     */
    data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int, val hour: Int = 0, val minute: Int = 0) : Comparable<MyDate> {

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

            if (hour != other.hour) {
                return hour - other.hour
            }

            if (minute != other.minute) {
                return minute - other.minute
            }

            return 0
        }
    }

    fun Long.toDate(): MyDate {
        val c = Calendar.getInstance()
        c.setTimeInMillis(this)
        return MyDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE))
    }

    fun MyDate.toMillis(): Long {
        val c = Calendar.getInstance()
        c.set(year, month, dayOfMonth, hour, minute, 0)
        c.set(Calendar.MILLISECOND, 0)
        return c.timeInMillis
    }

    operator fun MyDate.plus(timeInterval: TimeInterval) = addTimeIntervals(timeInterval, 1)
    operator fun MyDate.plus(timeInterval: TimeIntervalsMuliplier) = addTimeIntervals(timeInterval.timeInterval, timeInterval.times)

    operator fun TimeInterval.times(times: Int) = TimeIntervalsMuliplier(this, times)