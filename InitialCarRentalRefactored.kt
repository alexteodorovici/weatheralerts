//I used an enum to represent the car types for better code readability and type safety.
// only values of the defined enum type (CarType) can be used
enum class CarType {
    ECONOMY,
    SUPERCAR,
    MUSCLE
}

//I refactored the Car and Rental classes to use Kotlin's property syntax.
//We now have a clean class, no need for extra getters, setters, etc.
data class Car(val title: String, val priceCode: CarType)

data class Rental(val car: Car, val daysRented: Int)

class Customer(val name: String) {

    //changed the array list to kotlin's mutable list for easier operations
    // with the for loop instead of iterator. We use this list for a sequential access.
    //the array list was better for random access or dynamic resizing operations.
    private val rentals = mutableListOf<Rental>()

    fun addRental(rental: Rental) {
        rentals.add(rental)
    }

    //extracted this rental charge function to improve readability,
    // single resonsibility principle of this function, and easier to maintain.
    private fun calculateRentalCharge(rental: Rental): Double {
        var amount = 0.0
        when (rental.car.priceCode) {
            CarType.ECONOMY -> {
                amount += 80
                if (rental.daysRented > 2) {
                    //cleaned the syntax a little bit.
                    //the explicit call to toDouble() is not necessary because
                    // Kotlin's type promotion handles the conversion of the result
                    amount += (rental.daysRented - 2) * 30.0
                }
            }
            CarType.SUPERCAR -> amount += rental.daysRented * 200.0
            CarType.MUSCLE -> {
                amount += 200
                if (rental.daysRented > 3) {
                    amount += (rental.daysRented - 3) * 50.0
                }
            }
        }
        return amount
    }

    //same with this function, it gives us better readability and clean code.
    private fun calculateFrequentRenterPoints(rental: Rental): Int {
        var points = 1
        if (rental.car.priceCode == CarType.SUPERCAR && rental.daysRented > 1) {
            points++
        }
        return points
    }

    //same with this function, it gives us better readability and clean code.
    fun billingStatement(): String {
        var totalAmount = 0.0
        var frequentRenterPoints = 0

        //better to use String Builder for this result for better performance
        // and lower memory utilisation than a bunch of strings concatenated.
        val result = StringBuilder("Rental Record for $name\n")

        //replaced the while loop with the more clean and concise for loop.
        for (rental in rentals) {
            val amount = calculateRentalCharge(rental)
            frequentRenterPoints += calculateFrequentRenterPoints(rental)
            result.append("\t${rental.car.title}\t$amount\n")
            totalAmount += amount
        }

        //nicer string interpolation instead of string concatenation
        // for better readability and simplicity.
        result.append("Final rental payment owed $totalAmount\n")
        result.append("You received an additional $frequentRenterPoints frequent customer points")

        return result.toString()
    }
}

val rental1 = Rental(Car("Mustang", CarType.MUSCLE), 5)
val rental2 = Rental(Car("Lambo", CarType.SUPERCAR), 20)

val customer = Customer("Liviu")
customer.addRental(rental1)
customer.addRental(rental2)

val statement = customer.billingStatement()
println(statement)
