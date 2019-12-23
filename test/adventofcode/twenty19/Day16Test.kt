package adventofcode.twenty19

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2019/12/120 15:28
 */
class Day16Test {

    /**
     * We can simplify the calculations by splitting the input into a chunked list where each chunk represents items which will be
     * multiplied with 1, 0, -1 and 0 respectively.
     */
    @Test
    fun testSplitIntoChunks() {
        val day16 = Day16()
        val input = readShortInput()

        input.forEachIndexed { index, it ->
            val chunked = day16.splitIntoChunksWithSameMultiplier(input, index)

            when (index) {
                in 0..3 -> assertEquals(index + 1, chunked[0].size) //Any larger indexes wont have enough data to make a "full" chunk
                1 -> {
                    assertEquals(1, chunked[0])
                    assertEquals(2, chunked[1])
                    assertEquals(3, chunked[2])
                    //and etc
                }
                2 -> {
                    assertEquals(1, chunked[0][0])
                    assertEquals(2, chunked[0][1])
                    assertEquals(3, chunked[1][0])
                    assertEquals(4, chunked[1][1])
                    //and etc
                }
                3 -> {
                    assertEquals(1, chunked[0][0])
                    assertEquals(2, chunked[0][1])
                    assertEquals(3, chunked[0][2])
                    assertEquals(4, chunked[1][0])
                    assertEquals(5, chunked[1][1])
                    assertEquals(6, chunked[1][2])
                    //and etc
                }
            }
        }
    }

    /**
     * We filter all chunks where the item would have been multiplied with 0 since it makes no difference
     */
    @Test
    fun testFilterZeros() {
        val day16 = Day16()
        val input = readShortInput()

        val chunked0 = day16.splitIntoChunksWithSameMultiplier(input, 0)
        val dropZeros0 = day16.dropZeros(chunked0)

        assertEquals(4, dropZeros0.size)
        assertEquals(1, dropZeros0[0][0])
        assertEquals(3, dropZeros0[1][0])
        assertEquals(5, dropZeros0[2][0])
        assertEquals(7, dropZeros0[3][0])

        val chunked2 = day16.splitIntoChunksWithSameMultiplier(input, 2)
        val dropZeros2 = day16.dropZeros(chunked2)

        assertEquals(1, dropZeros2.size)
        assertEquals(3, dropZeros2[0][0])
        assertEquals(4, dropZeros2[0][1])
        assertEquals(5, dropZeros2[0][2])

        val chunked3 = day16.splitIntoChunksWithSameMultiplier(input, 3)
        val dropZeros3 = day16.dropZeros(chunked3)

        assertEquals(1, dropZeros3.size)
        assertEquals(4, dropZeros3[0][0])
        assertEquals(5, dropZeros3[0][1])
        assertEquals(6, dropZeros3[0][2])
        assertEquals(7, dropZeros3[0][3])

        val chunked7 = day16.splitIntoChunksWithSameMultiplier(input, 7)
        val dropZeros7 = day16.dropZeros(chunked7)

        assertEquals(1, dropZeros7.size)
        assertEquals(8, dropZeros7[0][0])
    }

    /**
     * Given the input signal 12345678, below are four phases of FFT
     *
     * After 1 phase: 48226158
     * After 2 phases: 34040438
     * After 3 phases: 03415518
     * After 4 phases: 01029498
     */
    @Test
    fun testSmallExample() {
        val day16 = Day16()

        //phase 1
        var fft = day16.fft("12345678")
        assertEquals("[4, 8, 2, 2, 6, 1, 5, 8]", fft.toString())

        //phase 2
        fft = day16.fft(fft)
        assertEquals("[3, 4, 0, 4, 0, 4, 3, 8]", fft.toString())

        //phase 3
        fft = day16.fft(fft)
        assertEquals("[0, 3, 4, 1, 5, 5, 1, 8]", fft.toString())

        //phase 4
        fft = day16.fft(fft)
        assertEquals("[0, 1, 0, 2, 9, 4, 9, 8]", fft.toString())
    }

    /**
     * 80871224585914546619083218645595 becomes 24176176
     * 19617804207202209144916044189917 becomes 73745418
     * 69317163492948606335995924319873 becomes 52432133
     */
    @Test
    fun testHundredPhases() {
        val day16 = Day16()
        assertEquals("[2, 4, 1, 7, 6, 1, 7, 6]", day16.fft("80871224585914546619083218645595", 100).toString())
        assertEquals("[7, 3, 7, 4, 5, 4, 1, 8]", day16.fft("19617804207202209144916044189917", 100).toString())
        assertEquals("[5, 2, 4, 3, 2, 1, 3, 3]", day16.fft("69317163492948606335995924319873", 100).toString())
    }

    /**
     * The message offset given in each input has been highlighted.
     *
     * Note that the inputs given below are repeated 10000 times to find the actual starting input lists.
     *
     * <b>0303673</b>2577212944063491565474664 becomes 84462026
     * <b>0293510<b>9699940807407585447034323 becomes 78725270
     * <b>0308177<b>0884921959731165446850517 becomes 53553731
     */
    @Test
    fun testTenThousandTimesWithOffset() {
        val day16 = Day16()
        assertEquals("[8, 4, 4, 6, 2, 0, 2, 6]", day16.fft2("03036732577212944063491565474664", 100).toString())
//        assertEquals("[7, 8, 7, 2, 5, 2, 7, 0]", day16.fft2("02935109699940807407585447034323", 100).toString())
//        assertEquals("[5, 3, 5, 5, 3, 7, 3, 1]", day16.fft2("03081770884921959731165446850517", 100).toString())
    }

    private fun readShortInput(): List<Int> {
        val input = "12345678".map { Integer.valueOf(it.toString()) }
        return input
    }
}