package Q5


open class Human {
    open val name =""
    open fun attack():String="$name use Fist Attack!"
}

class Mage(override var name:String, mana:Int): Human() {
    override fun attack(): String {
        return "$name use Fireball!"
    }
}

fun main() {
    val name = Mage("Nicole",3)
    print(name.attack())

}