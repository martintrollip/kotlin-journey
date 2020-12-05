package adventofcode.twenty20

import java.io.File

/**
 * @author Martin Trollip
 * @since 2020/12/04 06:45
 */
private const val DAY4_INPUT = "src/res/twenty20/day4_input"

fun main(args: Array<String>) {
    val day4 = Day4(DAY4_INPUT)
    println("Count the number of valid passports: ${day4.part1()}")
    println("Count the number of valid passports and valid fields: ${day4.part2()}")
}

data class Passport(var byr: String?, var iyr: String?, var eyr: String?, var hgt: String?, var hcl: String?, var ecl: String?, var pid: String?, var cid: String = "")

class Day4(input: String) {

    //TODO this can have an easier soultion.
    private var passports = mutableListOf<Passport>()

//    private var passports

    init {
        readInput(input)
    }

    private fun readInput(fileName: String) {

        var tempPassport = Passport(null, null, null, null, null, null, null)
        File(fileName).readLines().forEach {
            if (!it.isBlank()) {
                it.split(" ").forEach {
                    if (it.contains("byr")) {
                        tempPassport.byr = it.split(":")[1]
                    }
                    if (it.contains("iyr")) {
                        tempPassport.iyr = it.split(":")[1]
                    }
                    if (it.contains("eyr")) {
                        tempPassport.eyr = it.split(":")[1]
                    }
                    if (it.contains("hgt")) {
                        tempPassport.hgt = it.split(":")[1]
                    }
                    if (it.contains("hcl")) {
                        tempPassport.hcl = it.split(":")[1]
                    }
                    if (it.contains("ecl")) {
                        tempPassport.ecl = it.split(":")[1]
                    }
                    if (it.contains("pid")) {
                        tempPassport.pid = it.split(":")[1]
                    }
                    if (it.contains("cid")) {
                        tempPassport.cid = it.split(":")[1]
                    }
                }
            } else {
                passports.add(tempPassport)
                tempPassport = Passport(null, null, null, null, null, null, null)
            }
        }
        passports.add(tempPassport)
        println(passports.size)
    }

    fun part1(): Int {
        return passports.count { it.byr != null && it.iyr != null && it.eyr != null && it.hgt != null && it.hcl != null && it.ecl != null && it.pid != null }
    }

    fun part2(): Int {
        return passports.count {
            it.byr != null && it.byr!!.toIntOrNull() != null && it.byr!!.toInt() >= 1920 && it.byr!!.toInt() <= 2002 &&
            it.iyr != null && it.iyr!!.toIntOrNull() != null && it.iyr!!.toInt() >= 2010 && it.iyr!!.toInt() <= 2020 &&
            it.eyr != null && it.eyr!!.toIntOrNull() != null && it.eyr!!.toInt() >= 2020 && it.eyr!!.toInt() <= 2030 &&
            it.hgt != null && ((it.hgt!!.endsWith("cm") && it.hgt!!.replace("cm", "").toInt() >= 150 && it.hgt!!.replace("cm", "").toInt() <= 193) || (it.hgt!!.endsWith("in") && it.hgt!!.replace("in", "").toInt() >= 59 && it.hgt!!.replace("in", "").toInt() <= 76)) &&
            it.hcl != null && it.hcl!!.startsWith('#') && it.hcl!!.length == 7 &&
            it.ecl != null && (it.ecl == "amb" || it.ecl == "blu" || it.ecl == "brn" || it.ecl == "gry" || it.ecl == "grn" || it.ecl == "hzl" || it.ecl == "oth") &&
            it.pid != null && it.pid!!.toIntOrNull() != null && it.pid!!.length == 9
        }
    }
}

