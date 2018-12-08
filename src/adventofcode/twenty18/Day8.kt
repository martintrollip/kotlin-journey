package adventofcode.twenty18

import java.io.File

/**
 * @author Martin Trollip
 * @since 2018/12/08 06:53
 */
val TREE_REGEX = "([0-9]+)".toRegex()

const val DAY8_INPUT = "src/res/day8_input"

fun main(args: Array<String>) {

    val license = ArrayList<Int>()
    File(DAY8_INPUT).readLines().map {
        val matchResults = TREE_REGEX.findAll(it)

        for (matchResult in matchResults) {
            license.add(matchResult.groups.get(0)?.value?.toInt()!!)
        }
    }

    val tree = build(license)
    println(tree)
    println("The sum is ${tree.sum()}")
    println("The value is ${tree.value()}")
}

fun build(license: MutableList<Int>): Node {
    val node = license.getNodeHeader()

    for (children in 0 until node.childrenCount) {
        node.children.add(build(license))
    }

    node.metaData = license.getMetaData(node.metaDataLength)
    return node
}

fun MutableList<Int>.getNodeHeader(): Node {
    val node = Node(get(0), get(1))

    removeAt(0)
    removeAt(0)

    return node
}

fun MutableList<Int>.getMetaData(length: Int): List<Int> {
    val metadata = ArrayList<Int>()

    for (i in 0 until length) {
        metadata.add(get(0))
        removeAt(0)
    }

    return metadata
}

fun Node.sum(): Int {
    var sum = metaData.sum()

    for (child in children) {
        sum += child.sum()
    }

    return sum
}

fun Node.value(): Int {
    if (childrenCount == 0) {
        return metaData.sum()
    }

    var sum = 0
    for (metaDatum in metaData) {
        val index = metaDatum - 1
        if (index >= 0  && index < children.size) {
            sum += children[index].value()
        }
    }
    return sum
}

data class Node(var childrenCount: Int, var metaDataLength: Int, val children: MutableList<Node> = ArrayList(), var metaData: List<Int> = ArrayList())