package introduction

/**
 * @author Martin Trollip <martint@discovery.co.za>
 * @since 2018/11/12 22:09
 *
 * Read about <a href="http://kotlinlang.org/docs/reference/extensions.html">extension functions</a>. Then implement extension functions Int.r() and Pair.r() and make them convert Int and Pair to RationalNumber.
 */
data class RationalNumber(val numerator: Int, val denominator: Int)

fun Int.r(): RationalNumber = RationalNumber(this, 1)
fun Pair<Int, Int>.r(): RationalNumber = RationalNumber(first, second)