package adventofcode.twenty19

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

/**
 *
 *
 * @author Martin Trollip
 * @since 2019/12/10 16:09
 */
private const val DAY10_INPUT = "src/res/twenty19/day10_input"
private const val DELTA = 0.00005

class Day10Test {

    @Test
    fun testBuildField() {
        val day10 = Day10()

        val field = listOf(
                "#####",
                "#...#",
                "#####",
                ".#..#",
                "...##",
                ".....")

        val asteroids = day10.buildList(field)

        assertEquals(16, asteroids.size)
        assertEquals(0.0, asteroids[0].x, DELTA)
        assertEquals(0.0, asteroids[0].y, DELTA)

        assertEquals(1.0, asteroids[1].x, DELTA)
        assertEquals(0.0, asteroids[1].y, DELTA)

        assertEquals(4.0, asteroids[15].x, DELTA)
        assertEquals(4.0, asteroids[15].y, DELTA)
    }

    /**
     *
     * .#..#
     * .....
     * #####
     * ....#
     * ...##
     *
     */
    @Test
    fun testAsteroid0() {
        val day10 = Day10()

        val field = listOf(
                ".#..#",
                ".....",
                "#####",
                "....#",
                "...##")

        val visibility = day10.getMaxVisibility(field)
        assertEquals(8, visibility.visible)
        assertEquals(3.0, visibility.asteroid.x, DELTA)
        assertEquals(4.0, visibility.asteroid.y, DELTA)
    }


    /**
     * Best is 5,8 with 33 other asteroids detected:
     *
     *    ......#.#.
     *    #..#.#....
     *    ..#######.
     *    .#.#.###..
     *    .#..#.....
     *    ..#....#.#
     *    #..#....#.
     *    .##.#..###
     *    ##...#..#.
     *    .#....####
     */
    @Test
    fun testAsteroid1() {
        val day10 = Day10()

        val field = listOf(
                "......#.#.",
                "#..#.#....",
                "..#######.",
                ".#.#.###..",
                ".#..#.....",
                "..#....#.#",
                "#..#....#.",
                ".##.#..###",
                "##...#..#.",
                ".#....####")

        val visibility = day10.getMaxVisibility(field)
        assertEquals(33, visibility.visible)
        assertEquals(5.0, visibility.asteroid.x, DELTA)
        assertEquals(8.0, visibility.asteroid.y, DELTA)
    }

    @Test
    fun testPart1() {
        val day10 = Day10()

        val maxVisibility = day10.getMaxVisibility(File(DAY10_INPUT).readLines())
        assertEquals(292, maxVisibility.visible)
    }

    @Test
    fun testLaserVaporising() {
        val day10 = Day10()

        val field = listOf(
                ".#..##.###...#######", //0
                "##.############..##.", //1
                ".#.######.########.#", //2
                ".###.#######.####.#.", //3
                "#####.##.#.##.###.##", //4
                "..#####..#.#########", //5
                "####################", //6
                "#.####....###.#.#.##", //7
                "##.#################", //8
                "#####.##.###..####..", //9
                "..######..##.#######", //10
                "####.##.####...##..#", //11
                ".#####..#.######.###", //12
                "##...#.##########...", //13
                "#.##########.#######", //14
                ".####.#.###.###.#.##", //15
                "....##.##.###..#####", //16
                ".#.#.###########.###", //17
                "#.#.#.#####.####.###", //18
                "###.##.####.##.#..##") //19

        val visibility = day10.getMaxVisibility(field)
        assertEquals(210, visibility.visible)
        assertEquals(11.0, visibility.asteroid.x, DELTA)
        assertEquals(13.0, visibility.asteroid.y, DELTA)

        val asteroid200 = day10.vaporiseAsteroids(field)
        assertEquals(8.0, asteroid200.x, DELTA)
        assertEquals(2.0, asteroid200.y, DELTA)
        assertEquals(802, ((asteroid200.x * 100) + asteroid200.y).toInt())
    }
}