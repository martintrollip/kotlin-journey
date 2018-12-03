package koans.collections

data class Shop(val name: String, val customers: List<Customer>)

data class Customer(val name: String, val city: City, val orders: List<Order>) {
    override fun toString() = "$name from ${city.name}"
}

data class Order(val products: List<Product>, val isDelivered: Boolean)

data class Product(val name: String, val price: Double) {
    override fun toString() = "'$name' for $price"
}

data class City(val name: String) {
    override fun toString() = name
}

/**
 * Introduction
 * This part was inspired by <a href="https://github.com/goldmansachs/gs-collections-kata">GS Collections Kata</a>.
 *
 * Default collections in Kotlin are Java collections, but there are lots of useful extension functions for them. For example, operations that transform a collection to another one, starting with 'to': toSet or toList.
 *
 * Implement an extension function Shop.getSetOfCustomers(). The class Shop and all related classes can be found at Shop.kt.
 */
fun Shop.getSetOfCustomers() = shop.customers.toSet()


/**
 * Filter; map
 * Implement extension functions Shop.getCitiesCustomersAreFrom() and Shop.getCustomersFrom() using functions map and filter.
 *
 * <code>
 * val numbers = listOf(1, -1, 2)
 * numbers.filter { it > 0 } == listOf(1, 2)
 * numbers.map { it * it } == listOf(1, 1, 4)
 * </code>
 *
 */
// Return the set of cities the customers are from
fun Shop.getCitiesCustomersAreFrom(): Set<City> = getSetOfCustomers().map { it.city }.toSet()

// Return a list of the customers who live in the given city
fun Shop.getCustomersFrom(city: City): List<Customer> = getSetOfCustomers().filter { it.city == city }

/**
 * All, Any and other predicates
 * Implement all the functions below using all, any, count, find.
 *
 * <code>
 * val numbers = listOf(-1, 0, 2)
 * val isZero: (Int) -> Boolean = { it == 0 }
 * numbers.any(isZero) == true
 * numbers.all(isZero) == false
 * numbers.count(isZero) == 1
 * numbers.find { it > 0 } == 2
 * </code>
 *
 */

// Return true if all customers are from the given city
fun Shop.checkAllCustomersAreFrom(city: City): Boolean = customers.all { it.city == city }

//
//// Return true if there is at least one customer from the given city
fun Shop.hasCustomerFrom(city: City): Boolean = customers.any { it.city == city }

//
//// Return the number of customers from the given city
fun Shop.countCustomersFrom(city: City): Int = customers.count { it.city == city }

//
// Return a customer who lives in the given city, or null if there is none
fun Shop.findAnyCustomerFrom(city: City): Customer? = customers.find { it.city == city }

/**
 * FlatMap
 * Implement Customer.getOrderedProducts() and Shop.getAllOrderedProducts() using flatMap.
 *
 * <code>
 * val result = listOf("abc", "12").flatMap { it.toCharList() }
 * result == listOf('a', 'b', 'c', '1', '2')
 * </code>
 */

// Return all products this customer has ordered
val Customer.orderedProducts: Set<Product>
    get() {
        return orders.flatMap { it.products }.toSet()
    }

// Return all products that were ordered by at least one customer
val Shop.allOrderedProducts: Set<Product>
    get() {
        return customers.flatMap { it.orderedProducts }.toSet()
    }

/**
 * Max; min
 * Implement Shop.getCustomerWithMaximumNumberOfOrders() and Customer.getMostExpensiveOrderedProduct() using max, min, maxBy, or minBy.
 *
 * <code>
 * listOf(1, 42, 4).max() == 42
 * listOf("a", "ab").minBy { it.length } == "a"
 * </code>
 */

// Return a customer whose order count is the highest among all customers
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = customers.maxBy { it.orderedProducts.size }

// Return the most expensive product which has been ordered
fun Customer.getMostExpensiveOrderedProduct(): Product? = orders.flatMap { it.products }.maxBy { it.price }

/**
 * Sort
 * Implement Shop.getCustomersSortedByNumberOfOrders() using sorted or sortedBy.
 *
 * <code>
 * listOf("bbb", "a", "cc").sorted() == listOf("a", "bbb", "cc")
 * listOf("bbb", "a", "cc").sortedBy { it.length } == listOf("a", "cc", "bbb")
 * </code>
 */

// Return a list of customers, sorted by the ascending number of orders they made
fun Shop.getCustomersSortedByNumberOfOrders(): List<Customer> = customers.sortedBy { it.orderedProducts.size }

/**
 * Sum
 * Implement Customer.getTotalOrderPrice() using sum or sumBy.
 *
 * <code>
 * listOf(1, 5, 3).sum() == 9
 * listOf("a", "b", "cc").sumBy { it.length() } == 4
 * </code>
 *
 * If you want to sum the double values, use sumByDouble.
 */

// Return the sum of prices of all products that a customer has ordered.
// Note: the customer may order the same product for several times.
fun Customer.getTotalOrderPrice(): Double = orders.flatMap { it.products }.sumByDouble { it.price }

/**
 * Group By
 * Implement Shop.groupCustomersByCity() using groupBy.
 *
 * <code>
 * val result = listOf("a", "b", "ba", "ccc", "ad").groupBy { it.length() }
 * result == mapOf(1 to listOf("a", "b"), 2 to listOf("ba", "ad"), 3 to listOf("ccc"))
 * </code>
 */
// Return a map of the customers living in each city
fun Shop.groupCustomersByCity(): Map<City, List<Customer>> = customers.groupBy { it.city }

/**
 * Partition
 * Implement Shop.getCustomersWithMoreUndeliveredOrdersThanDelivered() using partition.
 *
 * <code>
 * val numbers = listOf(1, 3, -4, 2, -11)
 * val (positive, negative) = numbers.partition { it > 0 }
 * positive == listOf(1, 3, 2)
 * negative == listOf(-4, -11)
 * Note that destructuring declaration syntax is used in this example.
 * </code>
 *
 * partition returns a pair with the positive and negatives
 */
// Return customers who have more undelivered orders than delivered
fun Shop.getCustomersWithMoreUndeliveredOrdersThanDelivered(): Set<Customer> = customers.filter {
    val (delivered, undelivered) = it.orders.partition { it.isDelivered }
    undelivered.size > delivered.size
}.toSet()

/**
 * Fold
 * Implement Shop.getSetOfProductsOrderedByEveryCustomer() using fold.
 *
 * <code>
 * listOf(1, 2, 3, 4).fold(1, {
 * partProduct, element ->
 * element * partProduct
 * }) == 24
 *  </code>
 *
 *  //TODO map vs flatMap
 *  //TODO read up on flat map
 */
// Return the set of products that were ordered by every customer
fun Shop.getSetOfProductsOrderedByEveryCustomer(): Set<Product> {
    val allProducts = customers.flatMap { it.orders.flatMap { it.products }}.toSet()
    return customers.fold(allProducts, {
        orderedByAll, customer ->
        orderedByAll.intersect(customer.orders.flatMap { it.products }.toSet())
    })
}