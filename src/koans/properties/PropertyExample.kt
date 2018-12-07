package koans.properties

/**
 * @author Martin Trollip
 * @since 2018/12/07 18:38
 *
 * Properties
 * Read about <a href="http://kotlinlang.org/docs/reference/properties.html#properties-and-fields">properties</s> in Kotlin.
 *
 * Add a custom setter to PropertyExample.propertyWithCounter so that the counter property is incremented every time propertyWithCounter is assigned to.
 */
class PropertyExample() {
    var counter = 0
    var propertyWithCounter: Int? = null
        set(value) {
            field = value
            counter++
        }
}