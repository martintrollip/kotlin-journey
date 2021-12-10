package adventofcode.twenty21

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Martin Trollip
: @since 2021/12/10 07:08
 */
class Day10Test {
    private val DAY10_INPUT_EXAMPLE = "src/res/twenty21/day10_input_example"
    private val DAY10_INPUT = "src/res/twenty21/day10_input"

    /**
     * Did you know that syntax checkers actually have contests to see who can get the high score for syntax errors in a file? It's true! To calculate the syntax error score for a line, take the first illegal character on the line and look it up in the following table:
     *
     * ): 3 points.
     * ]: 57 points.
     * }: 1197 points.
     * >: 25137 points.
     * In the above example, an illegal ) was found twice (2*3 = 6 points), an illegal ] was found once (57 points), an illegal } was found once (1197 points), and an illegal > was found once (25137 points). So, the total syntax error score for this file is 6+57+1197+25137 = 26397 points!/
     *
     */
    @Test
    fun day10_calculateScore_example() {
        val day10 = Day10()
        val result = day10.part1(day10.readInput(DAY10_INPUT_EXAMPLE))
        assertEquals(26397, result)
    }

    @Test
    fun day10_calculateScore_withInput() {
        val day10 = Day10()
        val result = day10.part1(day10.readInput(DAY10_INPUT))
        assertEquals(370407, result)
    }

    /**
     * The five lines' completion strings have total scores as follows:
     *
     *    }}]])})] - 288957 total points.
     *    )}>]}) - 5566 total points.
     *    }}>}>)))) - 1480781 total points.
     *    ]]}}]}]}> - 995444 total points.
     *    ])}> - 294 total points.
     *    
     *    Autocomplete tools are an odd bunch: the winner is found by sorting all of the scores and then taking the middle score. (There will always be an odd number of scores to consider.) In this example, the middle score is 288957 because there are the same number of scores smaller and larger than it.
     */
    @Test
    fun day10_calculateMiddleScore() {
        val day10 = Day10()
        val result = day10.part2(day10.readInput(DAY10_INPUT_EXAMPLE))
        assertEquals(288957, result)
    }
    
    @Test
    fun day10_calculateMiddleScore_withInput() {
        val day10 = Day10()
        val result = day10.part2(day10.readInput(DAY10_INPUT))
        assertEquals(3249889609L, result)
    }
}
