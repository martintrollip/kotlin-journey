package koans.conventions

/**
 * @author Martin Trollip
 * @since 2018/11/19 19:27
 */
class Invoker {
    var numberOfInvocations: Int = 0
        private set

    operator fun invoke(): Invoker {
        numberOfInvocations++
        println("Invoked $numberOfInvocations")
        return this
    }
}