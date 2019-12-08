package adventofcode.twenty19

import java.io.File

/**
 * @author Martin Trollip
 * @since 2019/12/08 09:14
 */
private const val DAY8_INPUT = "src/res/twenty19/day8_input"

fun main(args: Array<String>) {
    val day8 = Day8()

    println("What is the number of 1 digits multiplied by the number of 2 digits? ${day8.part1()}\n")  //2125
    println("part 2?\n${day8.part2()}")

}

class Day8 {

    fun part1(): Int {
        val image2 = buildImage(File(DAY8_INPUT).readLines()[0], 25, 6)
        return multiplyOneAndTwos(findFewestZeroLayer(image2))
    }

    fun findFewestZeroLayer(image: SpaceImageFormat): SpaceImageFormatLayer {
        var fewestZeros = SpaceImageFormatLayer()
        var fewestZerosCount = Integer.MAX_VALUE
        var i = 0
        image.layers.forEach { layer ->
            var count = 0
            layer.rows.forEach { row ->
                count += row.count { it == '0' }
            }

            if (count < fewestZerosCount) {
                fewestZeros = layer
                fewestZerosCount = count
            }
        }

        return fewestZeros
    }

    fun multiplyOneAndTwos(layer: SpaceImageFormatLayer): Int {
        var ones = 0
        var twos = 0
        layer.rows.forEach { row ->
            ones += row.count { it == '1' }
            twos += row.count { it == '2' }
        }
        return ones * twos
    }

    fun buildImage(data: String, width: Int, height: Int): SpaceImageFormat {
        var stringData = data
        val spaceImageFormat = SpaceImageFormat()
        do {
            val layer = SpaceImageFormatLayer()
            for (i in 0 until height) {
                layer.rows.add(stringData.substring(0, width))
                stringData = stringData.substring(width, stringData.length)
            }
            spaceImageFormat.layers.add(layer)
        } while (!stringData.isEmpty())

        return spaceImageFormat
    }

    fun part2(): String {
        val render = render(buildImage(File(DAY8_INPUT).readLines()[0], 25, 6), 25, 6)

        var result = ""
        render.forEach { row ->
            row.forEach {
                if(it == '1') {
                    result += '▓'
                } else {
                    result += '░'
                }
            }
            result += "\n"
        }
        return result
    }

    fun render(image: SpaceImageFormat, width: Int, height: Int): Array<Array<Char>> {
        val result = Array(height) { Array(width) { '2' } }

        image.layers.forEach { layer ->
            var rowCount = 0
            layer.rows.forEach { row ->
                var charCount = 0
                row.forEach { char ->
                    if (result[rowCount][charCount] == '2') {
                        result[rowCount][charCount] = char
                    }
                    charCount++
                }
                rowCount++
            }
        }

        return result
    }

}

data class SpaceImageFormat(val layers: MutableList<SpaceImageFormatLayer> = mutableListOf())
data class SpaceImageFormatLayer(val rows: MutableList<String> = mutableListOf())

