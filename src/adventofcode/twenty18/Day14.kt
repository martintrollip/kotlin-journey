package adventofcode.twenty18

/**
 * @author Martin Trollip
 * @since 2018/12/17 08:45
 */
const val PART1_INPUT = "37"
const val STEPS = 939601L

fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    val scores = getInput(PART1_INPUT)
    val elfA = Elf(scores[0])
    val elfB = Elf(scores[1])
    for (i in 0..STEPS + 10) {
        scores.combineRecipes(elfA, elfB)
    }
    println(scores.subList(STEPS.toInt(), STEPS.toInt() + 10))
}


fun MutableList<RecipeScore>.combineRecipes(elfA: Elf, elfB: Elf): MutableList<RecipeScore> {
    val sum = elfA.currentRecipe.score + elfB.currentRecipe.score
    val recipeA = RecipeScore(this.size, tens(sum))
    val recipeB = RecipeScore(this.size, ones(sum))

    if (recipeA.score > 0) {
        this.add(recipeA)
        recipeB.index += 1
    }

    this.add(recipeB)

    elfA.currentRecipe = this[(1 + elfA.currentRecipe.index + elfA.currentRecipe.score).rem(this.size)]
    elfB.currentRecipe = this[(1 + elfB.currentRecipe.index + elfB.currentRecipe.score).rem(this.size)]

    return this
}

fun ones(nummber: Int): Int {
    return (nummber).rem(10)
}

fun tens(nummber: Int): Int {
    return (nummber / 10).rem(10)
}

const val PART2_INPUT = "1012451589" //We know this to be true from part 1
const val PART2_SEQUENCE = "939601"

private fun part2() {
    var currentSequence = getInput(PART2_INPUT)
    currentSequence.forEach { it.index += 4 }
    val sequenceToMatch = PART2_SEQUENCE.toRecipeScores()

    val elfA = Elf(currentSequence[0])
    val elfB = Elf(currentSequence[4])

    currentSequence = nextSequence(currentSequence, elfA, elfB)

    val containsSequence = currentSequence.containsSequence(sequenceToMatch)

    println("$PART2_SEQUENCE first appears after $containsSequence recipes")
}

fun nextSequence(currentSequence: List<RecipeScore>, elfA: Elf, elfB: Elf): MutableList<RecipeScore> {
    val sum = elfA.currentRecipe.score + elfB.currentRecipe.score
    val recipeA = RecipeScore(score = tens(sum))
    val recipeB = RecipeScore(score = ones(sum))

    val newElements = if (recipeA.score > 0) 2 else 1

    val currentMaxIndex = currentSequence[9].index
    val newList = currentSequence.subList(newElements, 9).toMutableList()
    if (newElements == 2) {
        recipeA.index = currentMaxIndex + 1
        recipeB.index = recipeA.index + 1
        newList.add(recipeA)
        newList.add(recipeB)
    } else {
        recipeB.index = currentMaxIndex + 1
        newList.add(recipeB)
    }

    return newList
}

fun MutableList<RecipeScore>.containsSequence(sequence: MutableList<RecipeScore>): Int {
    if (sequence.size > this.size) {
        return -1
    }

    val startIndex = size - sequence.size
    for (i in 0 until sequence.size) {
        if (sequence[i] != this[startIndex + i]) {
            return -1
        }
    }
    return startIndex
}

private fun getInput(input: String): MutableList<RecipeScore> {
    var count = 0
    return input.map {
        RecipeScore(count++, it.toString().toInt())
    }.toMutableList()
}

private fun String.toRecipeScores(): MutableList<RecipeScore> {
    val sequence = this.map { RecipeScore(score = it.toString().toInt()) }.toMutableList()
    return sequence
}

data class RecipeScore(var index: Int = -1, var score: Int) {
    override fun toString(): String {
        return score.toString()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is RecipeScore) {
            score == other.score
        } else {
            false
        }
    }
}

data class Elf(var currentRecipe: RecipeScore)


