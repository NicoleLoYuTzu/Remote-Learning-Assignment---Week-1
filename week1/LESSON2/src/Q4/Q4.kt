package Q4


class Human() {

    val name =""

    fun attack():String="$name use Fist Attack!"


}

fun main() {
    val name = Human()
    print(name.attack())

}