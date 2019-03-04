package masterclass.generics

/**
 * @author Martin Trollip
 * @since 2019/03/04 19:20
 */
//Generics
class Login<T>(name: T, password: T) {
    init {
        println("The name is $name and the password is ####")
    }
}

//enum class
enum class Suits {
    HEARTS,
    DIAMONDS,
    CLUBS,
    SPADES
}


fun main(args: Array<String>) {
    var login = Login("martin", "password") //Dont have to explicitly specify the type
    var login2 = Login(1, 12345)
}
