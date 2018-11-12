package introduction

/**
 * @author Martin Trollip ***REMOVED***
 * @since 2018/11/12 20:38
 */
fun main(args: Array<String>) {
    //Hello world!
    System.out.println(start())

    //Name arguments
    System.out.println(namedArguements(mutableListOf("a", "b", "c")))

    //Default arguments
    System.out.println(foo("a"))
    System.out.println(foo("b", 1))
    System.out.println(foo("c", toUpperCase = true))
    System.out.println(foo("d", 2, true))

    //Lamdas
    System.out.println("containsEven: " + containsEven(mutableListOf(1, 3, 5, 7)))
    System.out.println("containsEven: " + containsEven(mutableListOf(1, 2, 5, 7)))

    //Strings
    System.out.println("date match: 13 APR 1992: " + "13 APR 1992".matches(getPattern().toRegex()))
    System.out.println("date match: 12 MAT 1992: " + "12 MAT 1992".matches(getPattern().toRegex()))
    System.out.println("date match: 14 DEC 2018: " + "14 DEC 2018".matches(getPattern().toRegex()))
    System.out.println("date match: 14 DEC 18: " + "14 DEC 18".matches(getPattern().toRegex()))

    //Classes
    getPeople().forEach { System.out.println("${it.name} ${it.age}") }

    //Nullable types
    val info = PersonalInfo(email = "martin@email.com")
    val client = Client(info)
    sendMessageToClient(client, "message", Mailer())

    //Smart casts
    System.out.println(eval(Num(1)))
    System.out.println(eval(Sum(Num(1), Num(1))))

    //Extension functions
    System.out.print(1.r())
    System.out.print(Pair(1, 2).r())
}

/**
 * Take a look at <a href="http://kotlinlang.org/docs/reference/basic-syntax.html#defining-functions">function syntax</a> and make the function start return the string "OK".
 */
fun start(): String = "OK"

/**
 * <a href="kotlinlang.org/docs/reference/functions.html#default-arguments">Default and named arguments</a> help to minimize the number of overloads and improve the readability of the function invocation. The library function joinToString is declared with default values for parameters:
 *
 * <code>
 *      fun joinToString(
 *          separator: String = ", ",
 *          prefix: String = "",
 *          postfix: String = "",
 *          /* ... */
 *      ): String
 * </code>
 *
 * It can be called on a collection of Strings. Specifying only two arguments make the function joinOptions() return the list in a JSON format (e.g., "[a, b, c]")
 */
fun namedArguements(options: Collection<String>): String {
    return options.joinToString(prefix = "[", postfix = "]")
}

/**
 * Default arguments
 *
 * There are several overloads of 'foo()' in Java:
 *
 * <code>
 *      public String foo(String name, int number, boolean toUpperCase) {
 *          return (toUpperCase ? name.toUpperCase() : name) + number;
 *      }
 *
 *      public String foo(String name, int number) {
 *          return foo(name, number, false);
 *      }
 *
 *       public String foo(String name, boolean toUpperCase) {
 *          return foo(name, 42, toUpperCase);
 *      }
 *
 *      public String foo(String name) {
 *          return foo(name, 42);
 *      }
 * </code>
 *
 * All these Java overloads can be replaced with one function in Kotlin. Change the declaration of the function foo in a way that makes the code using foo compile.
 * Use <a href="http://kotlinlang.org/docs/reference/functions.html#default-arguments">default and named arguments</a>.
 */
fun foo(name: String, number: Int = 42, toUpperCase: Boolean = false) =
        (if (toUpperCase) name.toUpperCase() else name) + number

/**
 * Kotlin supports a functional style of programming. Read about <a href="http://kotlinlang.org/docs/reference/lambdas.html">higher-order functions and function literals (lambdas)</a> in Kotlin.
 * Pass a lambda to any function to check if the collection contains an even number. The function any gets a predicate as an argument and returns true if there is at least one element satisfying the predicate.
 */
fun containsEven(collection: Collection<Int>): Boolean {
    return collection.any({
        it % 2 == 0
    })
}

/**
 * Read about <a href="http://kotlinlang.org/docs/reference/basic-types.html#string-literals">different string literals and string templates</a> in Kotlin.
 *
 * Raw strings are useful for writing regex patterns, you don't need to escape a backslash by a backslash. Below there is a pattern that matches a date in format 13.06.1992 (two digits, a dot, two digits, a dot, four digits):
 *
 * <code>
 *    fun getPattern() = """\d{2}\.\d{2}\.\d{4}"""
 * </code>
 *
 * Using month variable rewrite this pattern in such a way that it matches the date in format 13 JUN 1992 (two digits, a whitespace, a month abbreviation, a whitespace, four digits).
 */
var month = "(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)"

fun getPattern(): String = """\d{2} $month \d{4}"""

/**
 * Data classes
 * Rewrite the following Java code to Kotlin:
 *
 * <code>
 *      public class Person {
 *      private final String name;
 *      private final int age;
​
 *      public Person(String name, int age) {
 *          this.name = name;
 *          this.age = age;
 *      }
 * ​
 *       public String getName() {
 *          return name;
 *      }
 *
 *      public int getAge() {
 *          return age;
 *      }
 * }
 * </code>
 *
 * Then add a modifier data to the resulting class. This annotation means the compiler will generate a bunch of useful methods in this class: equals/hashCode, toString and some others. The getPeople function should start to compile.
 *
 * Read about <a href="http://kotlinlang.org/docs/reference/classes.html">classes</a>, <a href="http://kotlinlang.org/docs/reference/properties.html">properties</a> and <a href="https://kotlinlang.org/docs/reference/data-classes.html">data classes</a>.
 */
fun getPeople(): List<Person> {
    return listOf(Person("Alice", 29), Person("Bob", 31))
}

/**
 * Read about <a href="http://kotlinlang.org/docs/reference/null-safety.html">null safety and safe calls</a> in Kotlin and rewrite the following Java code using only one if expression:
 *
 * <code>
 *      public void sendMessageToClient(@Nullable Client client, @Nullable String message, @NotNull Mailer mailer) {
 *          if (client == null || message == null) return;​
 *
 *          PersonalInfo personalInfo = client.getPersonalInfo();
 *          if (personalInfo == null) return;
 *
 *          String email = personalInfo.getEmail();
 *          if (email == null) return;
 *​
 *          mailer.sendMessage(email, message);
 *     }
 * </code>
 */
fun sendMessageToClient(client: Client?, message: String?, mailer: Mailer) {
    val email = client?.personalInfo?.email
    if (email != null && message != null) {
        mailer.sendMessage(email, message)
    }
}

/**
 * Rewrite the following Java code using smart casts and when expression:
 *
 * <code>
 *      public int eval(Expr expr) {
 *          if (expr instanceof Num) {
 *              return ((Num) expr).getValue();
 *          }
 *          if (expr instanceof Sum) {
 *              Sum sum = (Sum) expr;
 *              return eval(sum.getLeft()) + eval(sum.getRight());
 *          }
 *          throw new IllegalArgumentException("Unknown expression");
 *      }
 * </code>
 */
fun eval(expr: Expr): Int =
        when (expr) {
            is Num -> expr.value
            is Sum -> eval(expr.left) + eval(expr.right)
            else -> throw IllegalArgumentException("Unknown expression")
        }