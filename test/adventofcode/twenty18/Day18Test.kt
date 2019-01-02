package adventofcode.twenty18

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2018/12/26 09:06
 */
class Day18Test {
    var DAY18_INPUT_SMALL = "src/res/day18_input_small"
    var yard = listOf<List<Acre>>()

    @Before
    fun setup() {
        yard = readForestInput(DAY18_INPUT_SMALL)
        assertNotNull(yard)
    }

    @Test
    fun testInput() {
        //Test row 0
        assertEquals(YardType.GROUND, yard[0][0].type)
        assertEquals(YardType.LUMBER, yard[0][1].type)
        assertEquals(YardType.GROUND, yard[0][2].type)
        assertEquals(YardType.LUMBER, yard[0][3].type)
        assertEquals(YardType.GROUND, yard[0][4].type)
        assertEquals(YardType.GROUND, yard[0][5].type)
        assertEquals(YardType.GROUND, yard[0][6].type)
        assertEquals(YardType.TREE, yard[0][7].type)
        assertEquals(YardType.LUMBER, yard[0][8].type)
        assertEquals(YardType.GROUND, yard[0][9].type)

        //Test row 9
        assertEquals(YardType.GROUND, yard[9][0].type)
        assertEquals(YardType.GROUND, yard[9][1].type)
        assertEquals(YardType.GROUND, yard[9][2].type)
        assertEquals(YardType.LUMBER, yard[9][3].type)
        assertEquals(YardType.GROUND, yard[9][4].type)
        assertEquals(YardType.TREE, yard[9][5].type)
        assertEquals(YardType.GROUND, yard[9][6].type)
        assertEquals(YardType.GROUND, yard[9][7].type)
        assertEquals(YardType.TREE, yard[9][8].type)
        assertEquals(YardType.GROUND, yard[9][9].type)

        //Test index
        assertEquals(0, yard[0][0].row)
        assertEquals(0, yard[0][0].col)
        assertEquals(0, yard[0][4].row)
        assertEquals(4, yard[0][4].col)
        assertEquals(9, yard[0][9].col)
        assertEquals(5, yard[5][5].row)
        assertEquals(5, yard[5][5].col)
    }

    @Test
    fun testGetAdjacentAcres() {
        val topLeftCorner = getAdjacentAcres(yard, yard[0][0])
        val topRightCorner = getAdjacentAcres(yard, yard[0][9])
        val bottomLeftCorner = getAdjacentAcres(yard, yard[9][0])
        val bottomRightCorner = getAdjacentAcres(yard, yard[9][9])

        val topCenter = getAdjacentAcres(yard, yard[0][5])
        val leftCenter = getAdjacentAcres(yard, yard[5][0])
        val rightCenter = getAdjacentAcres(yard, yard[5][9])
        val bottomCenter = getAdjacentAcres(yard, yard[9][5])

        val top = getAdjacentAcres(yard, yard[1][5])
        val left = getAdjacentAcres(yard, yard[1][1])
        val right = getAdjacentAcres(yard, yard[1][8])
        val bottom = getAdjacentAcres(yard, yard[8][5])
        val center = getAdjacentAcres(yard, yard[5][5])

        //Test counts
        assertEquals(3, topLeftCorner.size)
        assertEquals(3, topRightCorner.size)
        assertEquals(3, bottomLeftCorner.size)
        assertEquals(3, bottomRightCorner.size)
        assertEquals(5, topCenter.size)
        assertEquals(5, rightCenter.size)
        assertEquals(5, leftCenter.size)
        assertEquals(5, bottomCenter.size)
        assertEquals(8, top.size)
        assertEquals(8, left.size)
        assertEquals(8, right.size)
        assertEquals(8, bottom.size)
        assertEquals(8, center.size)

        //Test surrounding index
        assertEquals(0, topLeftCorner[0].row)
        assertEquals(1, topLeftCorner[0].col)
        assertEquals(1, topLeftCorner[1].row)
        assertEquals(0, topLeftCorner[1].col)
        assertEquals(1, topLeftCorner[2].row)
        assertEquals(1, topLeftCorner[2].col)
        assertEquals(4, center[0].row)
        assertEquals(4, center[0].col)
        assertEquals(4, center[1].row)
        assertEquals(5, center[1].col)
        assertEquals(4, center[2].row)
        assertEquals(6, center[2].col)
        assertEquals(5, center[3].row)
        assertEquals(4, center[3].col)
        assertEquals(5, center[4].row)
        assertEquals(6, center[4].col)
        assertEquals(6, center[5].row)
        assertEquals(4, center[5].col)
        assertEquals(6, center[6].row)
        assertEquals(5, center[6].col)
        assertEquals(6, center[7].row)
        assertEquals(6, center[7].col)
        assertEquals(7, bottom[0].row)
        assertEquals(4, bottom[0].col)
        assertEquals(7, bottom[1].row)
        assertEquals(5, bottom[1].col)
        assertEquals(7, bottom[2].row)
        assertEquals(6, bottom[2].col)
        assertEquals(8, bottom[3].row)
        assertEquals(4, bottom[3].col)
        assertEquals(8, bottom[4].row)
        assertEquals(6, bottom[4].col)
    }

    /**
     * An open acre will become filled with trees if three or more adjacent acres contained trees. Otherwise, nothing happens.
     */
    @Test
    fun testOpenAcre() {
        //Initial acre is ground then new acre is tree with same coordinates
        var initialAcre = yard[8][1]
        var newAcre = transform(yard, initialAcre)
        assertEquals(YardType.GROUND, initialAcre.type)
        assertEquals(8, initialAcre.row)
        assertEquals(1, initialAcre.col)
        assertEquals(YardType.TREE, newAcre.type)
        assertEquals(8, newAcre.row)
        assertEquals(1, newAcre.col)

        //Initial acre is ground then new acre is still ground with same coordinates
        initialAcre = yard[9][0]
        newAcre = transform(yard, initialAcre)
        assertEquals(YardType.GROUND, initialAcre.type)
        assertEquals(9, initialAcre.row)
        assertEquals(0, initialAcre.col)
        assertEquals(YardType.GROUND, newAcre.type)
        assertEquals(9, newAcre.row)
        assertEquals(0, newAcre.col)
    }

    /**
     * An acre filled with trees will become a lumberyard if three or more adjacent acres were lumberyards. Otherwise, nothing happens.
     */
    @Test
    fun testTreeAcre() {
        //Initial acre is tree then new acre is lumber yard with same coordinates
        var initialAcre = yard[0][7]
        var newAcre = transform(yard, initialAcre)
        assertEquals(YardType.TREE, initialAcre.type)
        assertEquals(0, initialAcre.row)
        assertEquals(7, initialAcre.col)
        assertEquals(YardType.LUMBER, newAcre.type)
        assertEquals(0, newAcre.row)
        assertEquals(7, newAcre.col)

        //Initial acre is tree then new acre is still tree with same coordinates
        initialAcre = yard[2][1]
        newAcre = transform(yard, initialAcre)
        assertEquals(YardType.TREE, initialAcre.type)
        assertEquals(2, initialAcre.row)
        assertEquals(1, initialAcre.col)
        assertEquals(YardType.TREE, newAcre.type)
        assertEquals(2, newAcre.row)
        assertEquals(1, newAcre.col)
    }

    /**
     * An acre containing a lumberyard will remain a lumberyard if it was adjacent to at least one other lumberyard and at least one acre containing trees. Otherwise, it becomes open.
     */
    @Test
    fun testLumberAcre() {
        //Initial acre is lumberyard then new acre stays lumberyard with same coordinates
        var initialAcre = yard[2][8]
        var newAcre = transform(yard, initialAcre)
        assertEquals(YardType.LUMBER, initialAcre.type)
        assertEquals(2, initialAcre.row)
        assertEquals(8, initialAcre.col)
        assertEquals(YardType.LUMBER, newAcre.type)
        assertEquals(2, newAcre.row)
        assertEquals(8, newAcre.col)

        //Initial acre is lumberyard then new acre is open ground with same coordinates
        initialAcre = yard[4][0]
        newAcre = transform(yard, initialAcre)
        assertEquals(YardType.LUMBER, initialAcre.type)
        assertEquals(4, initialAcre.row)
        assertEquals(0, initialAcre.col)
        assertEquals(YardType.GROUND, newAcre.type)
        assertEquals(4, newAcre.row)
        assertEquals(0, newAcre.col)
    }

    /**
     * These changes happen across all acres simultaneously, each of them using the state of all acres at the beginning of the minute and changing to their new form by the end of that same minute. Changes that happen during the minute don't affect each other.
     */
    @Test
    fun testSimultaneousTransformation() {
        var transformAll = transformAll(yard)

        //After 1 minute
        //.......##.
        //......|###
        //.|..|...#.
        //..|#||...#
        //..##||.|#|
        //...#||||..
        //||...|||..
        //|||||.||.|
        //||||||||||
        //....||..|.

        //Assert row 0
        assertEquals(YardType.GROUND, transformAll[0][0].type)
        assertEquals(YardType.GROUND, transformAll[0][1].type)
        assertEquals(YardType.GROUND, transformAll[0][2].type)
        assertEquals(YardType.GROUND, transformAll[0][3].type)
        assertEquals(YardType.GROUND, transformAll[0][4].type)
        assertEquals(YardType.GROUND, transformAll[0][5].type)
        assertEquals(YardType.GROUND, transformAll[0][6].type)
        assertEquals(YardType.LUMBER, transformAll[0][7].type)
        assertEquals(YardType.LUMBER, transformAll[0][8].type)
        assertEquals(YardType.GROUND, transformAll[0][9].type)

        //Assert row 1
        assertEquals(YardType.GROUND, transformAll[1][0].type)
        assertEquals(YardType.GROUND, transformAll[1][1].type)
        assertEquals(YardType.GROUND, transformAll[1][2].type)
        assertEquals(YardType.GROUND, transformAll[1][3].type)
        assertEquals(YardType.GROUND, transformAll[1][4].type)
        assertEquals(YardType.GROUND, transformAll[1][5].type)
        assertEquals(YardType.TREE, transformAll[1][6].type)
        assertEquals(YardType.LUMBER, transformAll[1][7].type)
        assertEquals(YardType.LUMBER, transformAll[1][8].type)
        assertEquals(YardType.LUMBER, transformAll[1][9].type)

        //Assert row 9
        assertEquals(YardType.GROUND, transformAll[9][0].type)
        assertEquals(YardType.GROUND, transformAll[9][1].type)
        assertEquals(YardType.GROUND, transformAll[9][2].type)
        assertEquals(YardType.GROUND, transformAll[9][3].type)
        assertEquals(YardType.TREE, transformAll[9][4].type)
        assertEquals(YardType.TREE, transformAll[9][5].type)
        assertEquals(YardType.GROUND, transformAll[9][6].type)
        assertEquals(YardType.GROUND, transformAll[9][7].type)
        assertEquals(YardType.TREE, transformAll[9][8].type)
        assertEquals(YardType.GROUND, transformAll[9][9].type)

        transformAll = transformAll(transformAll)//2
        transformAll = transformAll(transformAll)//3
        transformAll = transformAll(transformAll)//4
        transformAll = transformAll(transformAll)//5
        transformAll = transformAll(transformAll)//6
        transformAll = transformAll(transformAll)//7
        transformAll = transformAll(transformAll)//8
        transformAll = transformAll(transformAll)//9
        transformAll = transformAll(transformAll)//10

        assertSample10Minutes(transformAll)
    }

    @Test
    fun test10Minutes() {
        assertSample10Minutes(run(yard, 10) as List<ArrayList<Acre>>)
    }

    @Test
    fun testResult() {
        assertEquals(1147, answer(run(yard, 10) as List<ArrayList<Acre>>))
    }

    private fun assertSample10Minutes(transformAll: List<ArrayList<Acre>>) {
        //After 10 minutes
        //.||##.....
        //||###.....
        //||##......
        //|##.....##
        //|##.....##
        //|##....##|
        //||##.####|
        //||#####|||
        //||||#|||||
        //||||||||||
        //Assert row 0
        assertEquals(YardType.GROUND, transformAll[0][0].type)
        assertEquals(YardType.TREE, transformAll[0][1].type)
        assertEquals(YardType.TREE, transformAll[0][2].type)
        assertEquals(YardType.LUMBER, transformAll[0][3].type)
        assertEquals(YardType.LUMBER, transformAll[0][4].type)
        assertEquals(YardType.GROUND, transformAll[0][5].type)
        assertEquals(YardType.GROUND, transformAll[0][6].type)
        assertEquals(YardType.GROUND, transformAll[0][7].type)
        assertEquals(YardType.GROUND, transformAll[0][8].type)
        assertEquals(YardType.GROUND, transformAll[0][9].type)

        //Assert row 1
        assertEquals(YardType.TREE, transformAll[1][0].type)
        assertEquals(YardType.TREE, transformAll[1][1].type)
        assertEquals(YardType.LUMBER, transformAll[1][2].type)
        assertEquals(YardType.LUMBER, transformAll[1][3].type)
        assertEquals(YardType.LUMBER, transformAll[1][4].type)
        assertEquals(YardType.GROUND, transformAll[1][5].type)
        assertEquals(YardType.GROUND, transformAll[1][6].type)
        assertEquals(YardType.GROUND, transformAll[1][7].type)
        assertEquals(YardType.GROUND, transformAll[1][8].type)
        assertEquals(YardType.GROUND, transformAll[1][9].type)

        //Assert row 5
        assertEquals(YardType.TREE, transformAll[5][0].type)
        assertEquals(YardType.LUMBER, transformAll[5][1].type)
        assertEquals(YardType.LUMBER, transformAll[5][2].type)
        assertEquals(YardType.GROUND, transformAll[5][3].type)
        assertEquals(YardType.GROUND, transformAll[5][4].type)
        assertEquals(YardType.GROUND, transformAll[5][5].type)
        assertEquals(YardType.GROUND, transformAll[5][6].type)
        assertEquals(YardType.LUMBER, transformAll[5][7].type)
        assertEquals(YardType.LUMBER, transformAll[5][8].type)
        assertEquals(YardType.TREE, transformAll[5][9].type)

        //Assert row 9
        assertEquals(YardType.TREE, transformAll[9][0].type)
        assertEquals(YardType.TREE, transformAll[9][1].type)
        assertEquals(YardType.TREE, transformAll[9][2].type)
        assertEquals(YardType.TREE, transformAll[9][3].type)
        assertEquals(YardType.TREE, transformAll[9][4].type)
        assertEquals(YardType.TREE, transformAll[9][5].type)
        assertEquals(YardType.TREE, transformAll[9][6].type)
        assertEquals(YardType.TREE, transformAll[9][7].type)
        assertEquals(YardType.TREE, transformAll[9][8].type)
        assertEquals(YardType.TREE, transformAll[9][9].type)
    }
}