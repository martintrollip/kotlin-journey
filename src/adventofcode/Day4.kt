package adventofcode

import koans.conventions.MyDate
import java.io.File

/**
 * @author Martin Trollip <martint@discovery.co.za>
 * @since 2018/12/04 06:47
 */

val SCHEDULE_REGEX = "\\[([0-9]{4})\\-([0-9]{2})\\-([0-9]{2}) ([0-9]{2}):([0-9]{2})\\] (falls asleep|wakes up|Guard #([0-9]+) begins shift)".toRegex()

const val DAY4_INPUT = "src/res/day4_input"

fun main(args: Array<String>) {
    val events = File(DAY4_INPUT).readLines().map {
        val matchResult = SCHEDULE_REGEX.find(it)
        val (year, month, day, hour, minute, action, guardId) = matchResult!!.destructured

        val date = MyDate(year.toInt(), month.toInt(), day.toInt(), hour.toInt(), minute.toInt())

        ScheduleEntry(date, guardId, action)
    }.sortAndAssignIds()
    events.print()

    var guards = LinkedHashMap<String, Sleep>()

    var asleepMinute = 0
    events.forEach {
        if (it.action == "falls asleep") {
            asleepMinute = it.date.minute
        }

        if (it.action == "wakes up") {
            guards[it.guardId] = guards.getOrDefault(it.guardId, Sleep(0, List(60) { 0 })) + (it.date.minute - asleepMinute)
            guards[it.guardId]?.markMinutes(asleepMinute, it.date.minute)
            asleepMinute = 0
        }
    }
    val maxSleep = guards.maxBy { it.value.total }
    val mostAsleepMinute = maxSleep?.value?.minutes?.indexOf(maxSleep.value.minutes.max())

    println(guards)

    println("Most sleep is Guard: ${maxSleep?.key} with ${maxSleep?.value?.total} minutes")
    println("Minute most asleep: $mostAsleepMinute")
    println("Mulitply those two: ${maxSleep?.key?.toInt()?.times(mostAsleepMinute!!)}")

    val maxSameTime = guards.maxBy { it.value.maxFrequency }
    println("Maximum same frequency is Guard ${maxSameTime?.key} during minute ${maxSameTime?.value?.maxIndex}")
    println("Mulitply those two:  ${maxSameTime?.key?.toInt()?.times(maxSameTime.value.maxIndex)}")
}

fun List<ScheduleEntry>.print() {
    for (schedule in this) {
        println("${schedule.date.year}-${schedule.date.month}-${schedule.date.dayOfMonth} ${schedule.date.hour} ${schedule.date.minute} - (${schedule.guardId}) ${schedule.action}")
    }
}

fun List<ScheduleEntry>.sortAndAssignIds(): List<ScheduleEntry> {
    var currentGuardId = ""
    for (schedule in this.sortedBy { it.date }) {
        if (!schedule.guardId.isBlank()) {
            currentGuardId = schedule.guardId
        } else {
            schedule.guardId = currentGuardId
        }
    }

    return this.sortedBy { it.date }
}

data class ScheduleEntry(var date: MyDate, var guardId: String, var action: String)

data class Sleep(var total: Int = 0, var minutes: List<Int>, var maxFrequency: Int = 0, var maxIndex:Int = -1)

operator fun Sleep.plus(minute: Int): Sleep {
    this.total += minute
    return this
}

fun Sleep.markMinutes(asleepTime: Int, awakeTime: Int) {
    var counts = this.minutes.toMutableList()
    for (i in asleepTime until awakeTime) {
        counts[i] += 1
    }
    this.maxFrequency = counts.max()!!
    this.maxIndex = counts.indexOf(counts.maxBy { it }!!)

    this.minutes = counts
}