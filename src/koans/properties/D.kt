package koans.properties

import koans.conventions.MyDate
import koans.conventions.toDate
import koans.conventions.toMillis
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author Martin Trollip ***REMOVED***
 * @since 2018/12/07 19:02
 *
 * Delegates
 * You may declare your own <a href="https://kotlinlang.org/docs/reference/delegated-properties.html#property-delegate-requirements">delegates</a>. Implement the methods of the class 'EffectiveDate' so it can be delegated to. Store only the time in milliseconds in 'timeInMillis' property.
 *
 * Use the extension functions MyDate.toMillis() and Long.toDate(), defined at MyDate.kt
 *
 */

class D {
    var date: MyDate by EffectiveDate()
}

class EffectiveDate<R> : ReadWriteProperty<R, MyDate> {

    var timeInMillis: Long? = null

    override fun getValue(thisRef: R, property: KProperty<*>): MyDate {
        return timeInMillis!!.toDate()
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: MyDate) {
        timeInMillis = value.toMillis()
    }
}