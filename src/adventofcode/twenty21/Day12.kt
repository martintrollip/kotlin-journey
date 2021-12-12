package adventofcode.twenty21

import java.io.File
import java.util.*

/**
 * @author Martin Trollip
 * @since 2021/12/11 18:03
 */
private const val DAY12_INPUT = "src/res/twenty21/day12_input"

fun main() {
    val day12 = Day12()
    println(
        "How many paths through this cave system are there? ${
            day12.part1(day12.readInput(DAY12_INPUT))
        }"
    )
    println(
        "Given new rules, how many paths through this cave system are there? ${
            day12.part2(day12.readInput(DAY12_INPUT))
        }"
    )
}

class Day12 {

    fun readInput(fileName: String): List<Node> {
        val nodes = listOf<Node>().toMutableList()

        File(fileName).readLines().forEach { line ->
            val (nodeAName, nodeBName) = line.split("-")

            var nodeA = Node(nodeAName)
            var nodeB = Node(nodeBName)

            if (!nodes.contains(nodeA)) {
                nodes.add(nodeA)
            } else {
                nodeA = nodes.find { it.value == nodeAName }!!
            }

            if (!nodes.contains(nodeB)) {
                nodes.add(nodeB)
            } else {
                nodeB = nodes.find { it.value == nodeBName }!!
            }

            nodeA.children.add(nodeB)
            nodeB.parents.add(nodeA)
        }
        return nodes
    }

    fun part1(input: List<Node>): Int {
        val find = input.find { it.value == "start" }!!
        val allPaths = mutableListOf(mutableListOf<Node>())
        find.traverse(find, mutableListOf(), allPaths)
        return allPaths.filter { it.size > 1 }.size
    }

    fun part2(input: List<Node>): Int {
        val find = input.find { it.value == "start" }!!
        val allPaths = mutableListOf(mutableListOf<Node>())

        input.filter { it.value != "start" && it.value != "end" && it.value.isLowerCase() }.forEach {
            find.traverse2(find, mutableListOf(), it, allPaths)
        }

        return allPaths.filter { it.size > 1 }.distinct().size
    }

    class Node(
        val value: String,
        val parents: MutableList<Node> = mutableListOf(),
        val children: MutableList<Node> = mutableListOf(),
    ) {

        /**
         * Find list of possible paths
         */
        fun traverse(node: Node, path: MutableList<Node>, allPaths: MutableList<MutableList<Node>>) {
            path.add(node)

            val possibleSteps =
                (node.children + node.parents).filter { (it.value.isLowerCase() && !path.contains(it)) || !it.value.isLowerCase() }

            if (possibleSteps.isEmpty() || node.value == "end") {
                if (node.value == "end") {
                    allPaths.add(path)
//                    println(path.joinToString("->"))
                }
            } else {
                possibleSteps.forEach {
                    traverse(it, path.toMutableList(), allPaths)
                }
            }
        }

        fun traverse2(node: Node, path: MutableList<Node>, nodeAllowedTwoVisits: Node, allPaths: MutableList<MutableList<Node>>) {
            path.add(node)

            val possibleSteps = (node.children + node.parents).filter {
                (it.value.isLowerCase() && visitAllowed(
                    it,
                    path,
                    nodeAllowedTwoVisits
                )) || !it.value.isLowerCase()
            }

            if (possibleSteps.isEmpty() || node.value == "end") {
                if (node.value == "end") {
                    allPaths.add(path)
//                    println(path.joinToString("->"))
                }
            } else {
                possibleSteps.forEach {
                    traverse2(it, path.toMutableList(), nodeAllowedTwoVisits, allPaths)
                }
            }
        }

        private fun visitAllowed(node: Node, path: MutableList<Node>, nodeAllowedTwoVisits: Node): Boolean {
            return if (node == nodeAllowedTwoVisits) {
                path.count { it == node } < 2
            } else {
                !path.contains(node)
            }
        }

        override fun equals(other: Any?): Boolean {
            if (other is Node) {
                return other.value == value
            }
            return false
        }

        override fun hashCode(): Int {
            return value.hashCode()
        }

        override fun toString(): String {
            return value
        }

        fun String.isLowerCase() = this.lowercase(Locale.getDefault()) == this
    }

    fun String.isLowerCase() = this.lowercase(Locale.getDefault()) == this
}

