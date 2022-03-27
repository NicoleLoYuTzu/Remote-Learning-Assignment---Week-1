package Q6

open class Human(open var name: String) {
    open fun attack(): String = "$name use Fist Attack!"
}

class Mage(override var name: String, var mana: Int) : Human("") {
    override fun attack(): String {
        return "$name use Fireball!"
    }
    fun checkMana() {
        if (mana<1)
            println("$name has no mana")
    }
}

fun main() {
    val nicole = Human("Nicole")
    println(nicole.attack())

    val name = Mage("Nicole", 3)
    println(name.attack())

    val nicolea = Mage("Bill", 0)
    nicolea.checkMana()



}