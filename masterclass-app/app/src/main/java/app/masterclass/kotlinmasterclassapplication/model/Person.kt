package app.masterclass.kotlinmasterclassapplication.model

import android.support.annotation.DrawableRes

/**
 * @author Martin Trollip ***REMOVED***
 * @since 2019/06/03 19:02
 */
data class Person(var name: String = "", var number: String = "", @DrawableRes var picture: Int = 0, var age: Int = 0)