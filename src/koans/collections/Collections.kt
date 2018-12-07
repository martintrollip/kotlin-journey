package koans.collections

/**
 * @author Martin Trollip
 * @since 2018/11/19 19:42
 */
fun main(args: Array<String>) {
    //Introduction
    println("All customers:")
    println(shop.getSetOfCustomers())

    //Filter map
    println("Customers locations")
    println(shop.getCitiesCustomersAreFrom())
    println("Customers from Tokyo")
    println(shop.getCustomersFrom(City("Tokyo")))

    //Predicates
    println("All Tokyo?")
    println(shop.checkAllCustomersAreFrom(City("Tokyo")))
    println("Any Tokyo?")
    println(shop.hasCustomerFrom(City("Tokyo")))
    println("Count from Tokyo")
    println(shop.countCustomersFrom(City("Tokyo")))
    println("Any from Tokyo")
    println(shop.findAnyCustomerFrom(City("Tokyo")))

    //Flat map
    println("Any customer's orders")
    println(shop.findAnyCustomerFrom(City("Tokyo"))?.orderedProducts)
    println("All products ordered")
    println(shop.allOrderedProducts)

    //Max, min
    println("Get Customer With Maximum Number of Orders")
    println(shop.getCustomerWithMaximumNumberOfOrders())
    println("Get most expensive ordered product")
    println(shop.getCustomerWithMaximumNumberOfOrders()?.getMostExpensiveOrderedProduct())

    //Sort
    println("Customers sorted by orders")
    println(shop.getCustomersSortedByNumberOfOrders())

    //Sum
    println("Customers total order")
    println(shop.getCustomerWithMaximumNumberOfOrders()?.getTotalOrderPrice())

    //Group by
    println("Customers by city")
    println(shop.groupCustomersByCity())

    //Partition
    println("Customers with more undelivered orders")
    println(shop.getCustomersWithMoreUndeliveredOrdersThanDelivered())

    //Fold
    println("Products ordered by every customer")
    println(shop.getSetOfProductsOrderedByEveryCustomer())

    //Compound tasks
    println("Most expensive delivered product")
    println(shop.getCustomerWithMaximumNumberOfOrders()?.getMostExpensiveDeliveredProduct())
    println("Number of times product was ordered")
    println(shop.getNumberOfTimesProductWasOrdered(shop.getCustomerWithMaximumNumberOfOrders()?.getMostExpensiveDeliveredProduct()!!))

    //Get used to new style
    println("doSomethingStrangeWithCollection")
//    println(doSomethingStrangeWithCollection(arrayListOf("A", "B", "C", "A", "B", "C")))
}

