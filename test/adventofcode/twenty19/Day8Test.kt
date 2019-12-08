package adventofcode.twenty19

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2019/12/08 09:14
 */
class Day8Test {

    val day8 = Day8()

    /**
     * For example, given an image 3 pixels wide and 2 pixels tall,
     * the image data 123456789012 corresponds to the following image layers:
     */
    @Test
    fun testLayers() {
        val input = "123456789012"
        val image = day8.buildImage(input, 3, 2)

        assertEquals(2, image.layers.size)

        //Layer 1
        assertEquals("123", image.layers[0].rows[0])
        assertEquals("456", image.layers[0].rows[1])

        //Layer 2
        assertEquals("789", image.layers[1].rows[0])
        assertEquals("012", image.layers[1].rows[1])

        //Test fewest 0 digits
        val fewestZeros = day8.findFewestZeroLayer(image)
        assertEquals(2, fewestZeros.rows.size)
        assertEquals("123", fewestZeros.rows[0])
        assertEquals("456", fewestZeros.rows[1])
        assertEquals(1, day8.multiplyOneAndTwos(fewestZeros))
    }

    /**
     * 0222112222120000
     */
    @Test
    fun testRender() {
        val input = "0222112222120000"
        val image = day8.buildImage(input, 2, 2)

        //Layer 1
        assertEquals("02", image.layers[0].rows[0])
        assertEquals("22", image.layers[0].rows[1])

        //Layer 2
        assertEquals("11", image.layers[1].rows[0])
        assertEquals("22", image.layers[1].rows[1])

        //Layer 3
        assertEquals("22", image.layers[2].rows[0])
        assertEquals("12", image.layers[2].rows[1])

        //Layer 4
        assertEquals("00", image.layers[3].rows[0])
        assertEquals("00", image.layers[3].rows[1])

        //Rendered
        val render = day8.render(image, 2, 2)
        println(render)
        assertEquals('0', render[0][0])
        assertEquals('1', render[0][1])
        assertEquals('1', render[1][0])
        assertEquals('0', render[1][1])
    }

}