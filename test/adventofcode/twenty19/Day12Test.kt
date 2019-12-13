package adventofcode.twenty19

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2019/12/11 16:48
 */

class Day12Test {


    @Test
    fun testCreateMoons() {
        val day12 = Day12()
        val input = listOf(
                "<x=-1, y=0, z=2>",
                "<x=2, y=-10, z=-7>",
                "<x=4, y=-8, z=8>",
                "<x=3, y=5, z=-1>")

        val bodies = day12.readBodies(input)

        assertEquals(4, bodies.size)

        assertEquals(-1, bodies[0].x)
        assertEquals(2, bodies[1].x)
        assertEquals(4, bodies[2].x)
        assertEquals(3, bodies[3].x)

        assertEquals(0, bodies[0].y)
        assertEquals(-10, bodies[1].y)
        assertEquals(-8, bodies[2].y)
        assertEquals(5, bodies[3].y)

        assertEquals(2, bodies[0].z)
        assertEquals(-7, bodies[1].z)
        assertEquals(8, bodies[2].z)
        assertEquals(-1, bodies[3].z)

    }

    /**
     * On each axis (x, y, and z), the velocity of each moon changes by exactly +1 or -1 to pull the moons together.
     *
     * For example, if Ganymede has an x position of 3, and Callisto has a x position of 5,
     * then Ganymede's x velocity changes by +1 (because 5 > 3) and Callisto's x velocity changes by -1 (because 3 < 5).
     *
     * However, if the positions on a given axis are the same, the velocity on that axis does not change for that pair of moons.
     */
    @Test
    fun testApplyGravityAndVelocity() {
        val ganymede = Body(x = 3, y = 10, z = -1)
        val callisto = Body(x = 5, y = 5, z = -1)

        ganymede.applyGravitationalPull(callisto)
        callisto.applyGravitationalPull(ganymede)

        assertEquals(1, ganymede.dx)
        assertEquals(-1, ganymede.dy)
        assertEquals(0, ganymede.dz)

        assertEquals(-1, callisto.dx)
        assertEquals(1, callisto.dy)
        assertEquals(0, callisto.dz)

        ganymede.applyVelocity()
        callisto.applyVelocity()

        assertEquals(4, ganymede.x)
        assertEquals(9, ganymede.y)
        assertEquals(-1, ganymede.z)

        assertEquals(4, callisto.x)
        assertEquals(6, callisto.y)
        assertEquals(-1, callisto.z)
    }

    @Test
    fun testSimulation() {
        val day12 = Day12()
        val input = listOf(
                "<x=-1, y=0, z=2>",
                "<x=2, y=-10, z=-7>",
                "<x=4, y=-8, z=8>",
                "<x=3, y=5, z=-1>")

        val simulate = day12.simulate(day12.readBodies(input))

        assertEquals(2, simulate[0].x)
        assertEquals(-1, simulate[0].y)
        assertEquals(1, simulate[0].z)
        assertEquals(3, simulate[0].dx)
        assertEquals(-1, simulate[0].dy)
        assertEquals(-1, simulate[0].dz)

        assertEquals(3, simulate[1].x)
        assertEquals(-7, simulate[1].y)
        assertEquals(-4, simulate[1].z)
        assertEquals(1, simulate[1].dx)
        assertEquals(3, simulate[1].dy)
        assertEquals(3, simulate[1].dz)

        assertEquals(1, simulate[2].x)
        assertEquals(-7, simulate[2].y)
        assertEquals(5, simulate[2].z)
        assertEquals(-3, simulate[2].dx)
        assertEquals(1, simulate[2].dy)
        assertEquals(-3, simulate[2].dz)

        assertEquals(2, simulate[3].x)
        assertEquals(2, simulate[3].y)
        assertEquals(0, simulate[3].z)
        assertEquals(-1, simulate[3].dx)
        assertEquals(-3, simulate[3].dy)
        assertEquals(1, simulate[3].dz)
    }

    @Test
    fun testEnergyOfSystem() {

        val day12 = Day12()
        val input = listOf(
                "<x=-1, y=0, z=2>",
                "<x=2, y=-10, z=-7>",
                "<x=4, y=-8, z=8>",
                "<x=3, y=5, z=-1>")

        val bodies = day12.readBodies(input)

        for (i in 0 until 10) {
            day12.simulate(bodies)
        }

        assertEquals(179, bodies.totalEnery())
    }


}