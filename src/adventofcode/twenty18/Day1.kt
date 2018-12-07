package adventofcode.twenty18

import java.io.File

/**
 * @author Martin Trollip
 * @since 2018/12/03 12:29
 */
fun main(args: Array<String>) {
    calibrate("src/res/day1_input")
}

fun calibrate(filename: String) {
    var frequencies = LinkedHashMap<Int, Int>()
    val lines = File(filename).readLines()

    println("Calibrated this round: " + calibrate(lines))
    println("First duplicate: " + duplications(lines, frequencies, 0))
}

fun calibrate(instructions: List<String>): Int {
    var calibration = 0
    instructions.map { calibration += it.toInt() }
    return calibration
}

fun duplications(instructions: List<String>, frequencies: LinkedHashMap<Int, Int>, startCalibration: Int): Int {
    var calibration = startCalibration

    instructions.map {
        calibration += it.toInt()

        if (frequencies.containsKey(calibration)) {
            return calibration
        } else {
            frequencies.put(calibration, 1)
        }
    }
    return duplications(instructions, frequencies, calibration)
}


