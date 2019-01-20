package adventofcode.twenty18

/**
 * @author Martin Trollip
 * @since 2019/01/03 14:03
 */

const val DAY21_INPUT = "src/res/day21_input"
var registers21 = listOf(Register("0", 0), Register("1"), Register("2"), Register("3"), Register("4"), Register("5"))

fun main(args: Array<String>) {
    val instructions = readDay19Input(DAY21_INPUT)
    val pointer = readDay19Pointer(DAY21_INPUT)
    resetRegisters(0)
    execute(pointer, instructions)
}

private fun resetRegisters(i: Int) {
    registers21[0].value = i
    registers21[1].value = 0
    registers21[2].value = 0
    registers21[3].value = 0
    registers21[4].value = 0
    registers21[5].value = 0
}