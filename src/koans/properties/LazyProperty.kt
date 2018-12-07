package koans.properties

/**
 * @author Martin Trollip ***REMOVED***
 * @since 2018/12/07 18:44
 *
 * Lazy property
 * Add a custom getter to make the 'lazy' val really lazy. It should be initialized by the invocation of 'initializer()' at the moment of the first access.
 *
 * You can add as many additional properties as you need.
 *
 * Do not use delegated properties!
 *
 * ----------------------------------------------------------------------------------------------------------------------------------------------------------
 *
 * Delegates example
 * Read about <a href="http://kotlinlang.org/docs/reference/delegated-properties.html">delegated properties</a> and make the property lazy by using delegates
 *
 */
class LazyProperty(val initializer: () -> Int) {
    var property: Int? = null

    val lazy: Int
        get() {
            if (property == null) {
                property = initializer()
            }
            return property!!
        }

    val lazy2 by lazy(initializer)
}

