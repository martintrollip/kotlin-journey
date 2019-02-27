package adventofcode.twenty18

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2019/01/20 16:24
 */

const val DEPTH_INPUT = 510
const val TARGET_X = 10
const val TARGET_Y = 10

class Day22Test {

    @Before
    fun setup() {
        depthInput = DEPTH_INPUT
        targetX = TARGET_X
        targetY = TARGET_Y
    }

    /**
     * The region at 0,0 (the mouth of the cave) has a geologic index of 0.
     */
    @Test
    fun testGeoLogicIndexEntrance() {
        assertEquals(0, geoLogicIndex(0, 0))
    }

    /**
     * The region at the coordinates of the target has a geologic index of 0.
     */
    @Test
    fun testGeoLogicIndexTarget() {
        assertEquals(0, geoLogicIndex(TARGET_X, TARGET_Y))
    }

    /**
     * If the region's Y coordinate is 0, the geologic index is its X coordinate times 16807.
     */
    @Test
    fun testGeoLogicIndexY0() {
        assertEquals(16807, geoLogicIndex(1, 0))
        assertEquals(33614, geoLogicIndex(2, 0))
        assertEquals(16807 * 3, geoLogicIndex(3, 0))
    }

    /**
     * If the region's X coordinate is 0, the geologic index is its Y coordinate times 48271.
     */
    @Test
    fun testGeoLogicIndexX0() {
        assertEquals(48271, geoLogicIndex(0, 1))
        assertEquals(96542, geoLogicIndex(0, 2))
        assertEquals(48271 * 3, geoLogicIndex(0, 3))
    }

    /**
     * A region's erosion level is its geologic index plus the cave system's depth, all modulo 20183
     *
     * If the erosion level modulo 3 is 0, the region's type is rocky.
     * If the erosion level modulo 3 is 1, the region's type is wet.
     * If the erosion level modulo 3 is 2, the region's type is narrow.
     *
     */
    @Test
    fun testErosionLevel() {
        //At 0,0, the geologic index is 0. The erosion level is (0 + 510) % 20183 = 510. The type is 510 % 3 = 0, rocky.
        assertEquals(510, erosionLevel(0, 0))
        assertEquals(Cave.ROCKY, Cave.from(510))

        //At 1,0, because the Y coordinate is 0, the geologic index is 1 * 16807 = 16807. The erosion level is (16807 + 510) % 20183 = 17317. The type is 17317 % 3 = 1, wet.
        assertEquals(16807, geoLogicIndex(1, 0))
        assertEquals(17317, erosionLevel(1, 0))
        assertEquals(Cave.WET, Cave.from(erosionLevel(1, 0)))

        //At 0,1, because the X coordinate is 0, the geologic index is 1 * 48271 = 48271. The erosion level is (48271 + 510) % 20183 = 8415. The type is 8415 % 3 = 0, rocky.
        assertEquals(48271, geoLogicIndex(0, 1))
        assertEquals(8415, erosionLevel(0, 1))
        assertEquals(Cave.ROCKY, Cave.from(erosionLevel(0, 1)))

        //At 1,1, neither coordinate is 0 and it is not the coordinate of the target, so the geologic index is the erosion level of 0,1 (8415) times the erosion level of 1,0 (17317), 8415 * 17317 = 145722555. The erosion level is (145722555 + 510) % 20183 = 1805. The type is 1805 % 3 = 2, narrow.
        assertEquals(145722555, geoLogicIndex(1, 1))
        assertEquals(1805, erosionLevel(1, 1))
        assertEquals(Cave.NARROW, Cave.from(erosionLevel(1, 1)))

        //At 10,10, because they are the target's coordinates, the geologic index is 0. The erosion level is (0 + 510) % 20183 = 510. The type is 510 % 3 = 0, rocky.
        assertEquals(0, geoLogicIndex(10, 10))
        assertEquals(510, erosionLevel(10, 10))
        assertEquals(Cave.ROCKY, Cave.from(erosionLevel(10, 10)))
    }

    /**
     * In the cave system above, because the mouth is at 0,0 and the target is at 10,10, adding up the risk level of all
     * regions with an X coordinate from 0 to 10 and a Y coordinate from 0 to 10, this total is 114.
     */
    @Test
    fun testRiskLevel() {
        assertEquals(114, riskLevel())
    }

    @Test
    fun testTools() {
        //In rocky regions, you can use the climbing gear or the torch. You cannot use neither (you'll likely slip and fall).
        assertTrue(Cave.ROCKY.tools.containsAll(setOf(Tool.CLIMBING, Tool.TORCH)))
        assertFalse(Cave.ROCKY.tools.containsAll(setOf(Tool.NEITHER)))

        //In wet regions, you can use the climbing gear or neither tool. You cannot use the torch (if it gets wet, you won't have a light source).
        assertTrue(Cave.WET.tools.containsAll(setOf(Tool.CLIMBING, Tool.NEITHER)))
        assertFalse(Cave.WET.tools.containsAll(setOf(Tool.TORCH)))

        //In narrow regions, you can use the torch or neither tool. You cannot use the climbing gear (it's too bulky to fit).
        assertTrue(Cave.NARROW.tools.containsAll(setOf(Tool.NEITHER, Tool.TORCH)))
        assertFalse(Cave.NARROW.tools.containsAll(setOf(Tool.CLIMBING)))
    }

    @Test
    fun testStartAndEndTools() {
        //You start at 0,0 (the mouth of the cave) with the torch equipped
        assertTrue(availableToolsAt(0, 0).contains(Tool.TORCH))
        assertFalse(availableToolsAt(0, 0).contains(Tool.NEITHER))
        assertFalse(availableToolsAt(0, 0).contains(Tool.CLIMBING))

        //Finally, once you reach the target, you need the torch equipped before you can find him in the dark.
        assertTrue(availableToolsAt(targetX, targetY).contains(Tool.TORCH))
        assertFalse(availableToolsAt(targetX, targetY).contains(Tool.NEITHER))
        assertFalse(availableToolsAt(targetY, targetY).contains(Tool.CLIMBING))
    }

    /**
     * Thanks to Todd Ginsberg for explaining the logic
     */
    @Test
    fun testMovingThroughCave() {
        assertEquals(45, rescueTarget())
    }
}