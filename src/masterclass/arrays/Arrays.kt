package masterclass.arrays

/**
 * @author Martin Trollip
 * @since 2019/03/04 19:33
 */
fun main(args: Array<String>) {
    val array = Array(5){2}//fixed size at 5 with 2 elements

    array.forEach {
        println(it)
    }

    val array2 = listOf("Name 1", "Name 2", "Name 3", 67)
//    array2.add()//This is not possible since listOf is Immutable

    array2.forEach {
        println(it)
    }

    val array3 = mutableListOf("Name 4", "Name 5", "Name 6")
    array3.add("Name 7")

}
