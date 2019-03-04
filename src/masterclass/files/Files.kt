package masterclass.files

import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception

/**
 * @author Martin Trollip
 * @since 2019/03/04 19:57
 */
fun main(args: Array<String>) {
    writeToFile("whooo!")
    readFromFile()
}

private const val FILE_NAME = "new-file.txt"

fun writeToFile(message: String) {
    try {
        val writer = FileWriter(FILE_NAME, true) //Boolean parameter to indicate that we wish to append to an existing file
        writer.write(message)
        writer.close()
    } catch (ex: Exception) {
        println("Booo!")
    }
}

fun readFromFile() {
    val reader = FileReader(FILE_NAME)

    try {
        reader.readLines().forEach{ println(it)}
    } catch (ex: Exception) {
        println("Booo!")
    }
}
