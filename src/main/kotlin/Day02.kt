import Day02.CommandRunner
import Day02.Direction.*
import Day02.input
import Day02.toCommand

fun main() {

    val commands = input.map { it.toCommand() }

    val answer1 = CommandRunner(mapOf(
        Down    to { sub, delta -> sub.copy(y = sub.y + delta) },
        Up      to { sub, delta -> sub.copy(y = sub.y - delta) },
        Forward to { sub, delta -> sub.copy(x = sub.x + delta) },
    )).run(commands)

    println(answer1) // 1690020

    val answer2 = CommandRunner(mapOf(
        Down    to { sub, delta -> sub.copy(aim = sub.aim + delta) },
        Up      to { sub, delta -> sub.copy(aim = sub.aim - delta) },
        Forward to { sub, delta -> sub.copy(x = sub.x + delta, y = sub.y + sub.aim * delta) },
    )).run(commands)

    println(answer2) // 1408487760
}

object Day02 {

    enum class Direction { Forward, Up, Down }
    data class Command(val direction: Direction, val delta: Int)
    data class Submarine(val x: Int = 0, val y: Int = 0, val aim: Int = 0)

    class CommandRunner(private val config: Map<Direction, (Submarine, Int) -> Submarine>) {
        fun run(commands: List<Command>): Int =
            commands.fold(Submarine()) { acc, n -> config.getValue(n.direction)(acc, n.delta) }.let { (x, y) -> x * y }
    }

    fun String.toCommand() =
        split(' ').let { (direction, delta) -> Command(Direction.valueOf(direction.capitalize()), delta.toInt()) }

    val input: List<String> = """
        forward 4
        down 8
        down 8
        up 2
        up 7
        forward 5
        forward 5
        up 7
        down 6
        down 3
        down 1
        forward 5
        forward 9
        up 2
        down 9
        forward 4
        up 5
        forward 7
        down 2
        forward 7
        down 2
        forward 4
        up 3
        down 9
        up 8
        down 2
        down 6
        up 1
        forward 3
        down 6
        down 2
        forward 9
        up 1
        forward 5
        down 1
        forward 2
        up 2
        forward 4
        down 3
        down 8
        up 2
        down 3
        up 4
        down 8
        forward 7
        forward 9
        down 7
        down 1
        forward 5
        up 3
        down 6
        down 6
        forward 1
        down 9
        forward 6
        forward 9
        forward 2
        forward 5
        forward 7
        down 1
        up 6
        up 7
        forward 8
        forward 6
        forward 2
        down 5
        up 3
        up 4
        down 9
        up 4
        down 9
        up 4
        down 5
        forward 3
        down 8
        up 2
        down 2
        forward 7
        down 7
        forward 6
        down 2
        forward 5
        down 1
        forward 9
        down 9
        down 5
        forward 2
        forward 3
        forward 6
        forward 1
        down 8
        forward 2
        forward 1
        forward 9
        down 8
        forward 8
        up 1
        up 2
        forward 2
        forward 7
        down 2
        up 9
        forward 5
        forward 5
        up 5
        down 1
        up 8
        forward 3
        up 5
        forward 2
        up 8
        up 7
        forward 4
        down 6
        up 1
        up 6
        forward 5
        down 8
        forward 4
        down 7
        forward 5
        down 4
        down 9
        forward 2
        down 5
        down 2
        down 3
        forward 8
        down 8
        down 2
        down 5
        down 6
        up 8
        down 1
        up 7
        up 4
        up 1
        up 6
        forward 6
        forward 6
        forward 8
        up 5
        forward 4
        forward 5
        forward 3
        down 8
        forward 9
        forward 6
        forward 6
        up 1
        up 8
        forward 2
        up 9
        down 1
        up 7
        up 3
        down 3
        forward 2
        down 5
        up 8
        forward 3
        up 5
        down 3
        down 3
        up 7
        forward 2
        forward 3
        forward 6
        forward 9
        up 3
        forward 1
        up 9
        down 8
        forward 5
        down 8
        forward 9
        down 1
        forward 7
        forward 9
        forward 2
        down 6
        up 6
        down 2
        down 1
        forward 7
        down 3
        forward 3
        down 3
        forward 1
        forward 6
        forward 1
        down 4
        down 4
        down 5
        forward 3
        forward 1
        up 8
        forward 7
        down 6
        up 6
        down 5
        up 6
        down 3
        down 8
        down 9
        forward 2
        up 8
        forward 1
        forward 2
        forward 7
        forward 5
        up 6
        down 9
        up 9
        forward 7
        forward 6
        forward 7
        down 8
        down 6
        forward 5
        down 2
        down 5
        down 3
        down 4
        up 5
        down 5
        forward 7
        forward 2
        down 1
        forward 6
        up 8
        down 3
        down 5
        down 3
        forward 3
        up 2
        forward 9
        forward 2
        up 4
        down 3
        down 7
        forward 9
        forward 6
        up 1
        up 2
        down 5
        up 8
        forward 9
        forward 2
        down 3
        down 6
        up 3
        down 9
        down 2
        up 4
        down 3
        up 7
        forward 3
        up 9
        down 3
        down 9
        down 1
        down 1
        forward 7
        down 9
        forward 3
        up 6
        down 8
        down 3
        forward 7
        forward 1
        up 4
        forward 8
        forward 1
        forward 9
        up 9
        forward 4
        up 2
        down 6
        down 5
        down 8
        down 2
        down 4
        forward 5
        down 8
        down 1
        forward 5
        forward 9
        down 4
        forward 5
        forward 4
        forward 4
        up 6
        down 7
        down 2
        forward 8
        down 7
        forward 7
        forward 7
        forward 3
        down 3
        forward 6
        down 5
        down 5
        forward 3
        down 7
        up 3
        up 6
        forward 8
        down 3
        down 6
        forward 5
        forward 4
        down 4
        down 3
        down 1
        down 4
        down 2
        forward 1
        forward 5
        down 9
        forward 8
        down 7
        forward 4
        down 5
        down 5
        forward 7
        forward 9
        down 5
        down 8
        up 9
        forward 1
        down 9
        up 1
        down 8
        forward 4
        up 8
        up 7
        down 4
        forward 2
        forward 9
        up 9
        forward 4
        forward 5
        forward 5
        forward 4
        forward 4
        down 8
        forward 3
        forward 3
        forward 1
        forward 7
        forward 7
        up 2
        forward 9
        down 8
        forward 3
        down 3
        down 3
        down 4
        forward 9
        forward 9
        forward 7
        forward 9
        down 6
        forward 6
        down 4
        forward 7
        down 3
        forward 2
        down 9
        down 9
        up 2
        down 7
        down 6
        up 5
        forward 6
        forward 5
        down 9
        forward 8
        down 9
        forward 9
        down 7
        up 8
        forward 5
        forward 1
        down 5
        forward 1
        down 4
        up 6
        up 1
        down 5
        forward 3
        down 1
        up 7
        down 8
        up 5
        down 8
        up 6
        forward 6
        down 8
        up 2
        forward 5
        down 5
        down 7
        down 7
        forward 8
        forward 6
        forward 2
        forward 3
        forward 3
        forward 9
        down 7
        up 8
        up 1
        forward 8
        down 5
        down 7
        forward 2
        down 9
        down 5
        down 5
        forward 6
        forward 1
        forward 8
        down 3
        down 3
        down 7
        up 3
        down 3
        down 5
        down 1
        forward 3
        forward 2
        forward 4
        forward 1
        forward 3
        forward 6
        down 6
        down 4
        forward 2
        down 8
        up 1
        down 7
        down 6
        down 3
        down 6
        forward 8
        up 7
        down 7
        up 7
        down 1
        forward 2
        forward 9
        up 8
        down 2
        down 3
        down 7
        down 2
        up 2
        down 1
        down 7
        up 6
        down 4
        forward 9
        down 8
        down 1
        forward 5
        forward 1
        up 7
        up 9
        up 9
        down 5
        down 7
        down 2
        down 6
        down 3
        forward 8
        forward 4
        up 3
        down 9
        up 3
        down 6
        up 8
        forward 7
        down 7
        up 5
        down 1
        down 3
        up 4
        forward 2
        down 7
        down 3
        down 7
        up 1
        forward 8
        down 3
        forward 7
        down 8
        forward 5
        forward 8
        down 8
        up 4
        up 8
        forward 3
        down 7
        up 6
        down 9
        forward 4
        forward 4
        forward 3
        up 4
        down 4
        down 7
        forward 6
        down 7
        down 8
        up 5
        down 4
        up 6
        up 6
        up 4
        down 7
        forward 7
        up 4
        down 2
        up 2
        forward 6
        down 5
        down 1
        forward 2
        up 1
        down 4
        up 2
        down 7
        down 5
        up 5
        forward 6
        up 2
        forward 2
        up 9
        up 4
        down 1
        down 3
        up 7
        up 5
        down 9
        down 2
        forward 9
        down 1
        up 9
        down 4
        down 8
        forward 3
        forward 1
        forward 4
        forward 9
        down 5
        down 5
        down 8
        up 4
        up 1
        down 9
        up 4
        forward 9
        up 1
        forward 7
        down 4
        up 2
        down 1
        forward 9
        down 9
        down 2
        forward 8
        up 2
        forward 6
        down 1
        up 9
        down 3
        down 2
        down 8
        down 2
        forward 8
        forward 2
        forward 8
        down 3
        up 6
        forward 5
        forward 4
        forward 7
        forward 1
        down 8
        forward 7
        down 9
        up 7
        up 5
        forward 1
        down 6
        down 6
        up 9
        up 9
        up 1
        forward 1
        forward 5
        up 1
        forward 2
        down 8
        up 9
        forward 2
        forward 8
        down 2
        up 5
        up 9
        down 5
        forward 2
        forward 4
        forward 2
        up 7
        down 9
        forward 5
        down 1
        down 6
        up 1
        forward 8
        down 1
        down 7
        up 2
        forward 4
        down 2
        up 6
        forward 6
        forward 3
        down 3
        forward 2
        down 2
        up 9
        forward 2
        up 1
        down 9
        down 4
        up 8
        forward 3
        down 9
        down 9
        forward 9
        forward 8
        up 8
        down 8
        up 8
        forward 4
        down 9
        up 5
        forward 8
        up 6
        forward 7
        up 6
        down 2
        down 3
        forward 9
        forward 5
        down 6
        forward 9
        down 5
        down 9
        down 7
        down 9
        down 3
        forward 4
        forward 2
        down 2
        down 7
        down 7
        up 2
        up 3
        forward 6
        up 7
        forward 4
        down 3
        forward 2
        down 1
        down 8
        forward 5
        down 3
        up 9
        forward 2
        forward 7
        down 4
        forward 1
        forward 8
        forward 9
        forward 5
        down 4
        up 3
        up 9
        forward 6
        forward 4
        forward 9
        down 3
        forward 1
        forward 9
        down 9
        down 5
        forward 9
        forward 4
        down 3
        down 9
        down 5
        up 6
        up 5
        forward 5
        up 8
        down 3
        forward 7
        up 3
        forward 9
        down 8
        forward 2
        forward 1
        forward 9
        down 9
        forward 1
        down 6
        forward 7
        up 3
        forward 7
        up 3
        down 1
        forward 5
        forward 5
        up 3
        forward 2
        down 3
        forward 8
        up 9
        forward 7
        down 7
        forward 5
        up 4
        forward 8
        down 1
        up 4
        down 2
        forward 2
        down 5
        down 5
        up 2
        forward 1
        down 3
        down 8
        forward 6
        forward 6
        down 5
        up 4
        down 7
        down 9
        up 9
        forward 7
        forward 4
        down 7
        down 5
        down 2
        down 9
        down 6
        down 7
        up 6
        up 7
        up 6
        down 4
        forward 9
        down 8
        down 7
        down 8
        down 4
        forward 5
        forward 1
        up 5
        forward 5
        forward 4
        down 3
        forward 8
        down 7
        down 9
        up 1
        down 1
        up 8
        up 6
        down 9
        up 9
        down 9
        forward 7
        down 3
        forward 6
        down 6
        forward 6
        down 9
        down 7
        up 1
        down 2
        up 2
        down 3
        down 1
        up 4
        forward 3
        down 3
        up 8
        down 3
        forward 3
        forward 6
        forward 6
        forward 6
        forward 7
        up 2
        forward 6
        forward 1
        up 4
        up 7
        down 5
        down 9
        forward 6
        down 4
        forward 6
        down 7
        down 2
        up 9
        up 3
        forward 8
        forward 5
        down 1
        down 6
        down 7
        down 5
        up 3
        up 9
        forward 2
        forward 5
        down 3
        down 2
        up 2
        forward 6
        forward 3
        down 8
        forward 7
        up 6
        forward 4
        down 8
        forward 6
        down 7
        forward 9
        forward 6
        forward 2
        forward 4
        up 5
        up 1
        forward 3
        forward 2
        up 3
        down 4
        down 3
        down 1
        up 8
        forward 6
        down 4
        down 9
        down 3
        up 8
        down 5
        forward 2
        down 3
        up 7
        down 3
        up 1
        up 1
        up 2
        up 1
        forward 4
        forward 1
        forward 4
        forward 3
        forward 8
        down 8
        up 5
        down 4
        down 4
        down 6
        down 9
        down 7
        forward 5
        forward 3
        up 3
        forward 6
        forward 5
        forward 2
        forward 6
        up 4
        forward 2
        up 3
        down 2
        forward 3
        down 8
        forward 1
        forward 2
        down 3
        down 5
        forward 6
        forward 3
        forward 6
        up 3
        forward 5
        forward 3
        forward 5
        down 6
        down 4
        down 4
        forward 3
        forward 3
        up 6
        up 8
        forward 5
        forward 1
        down 3
        down 8
        down 9
        up 3
        down 7
        forward 4
        forward 2
        down 2
        up 6
        down 1
        down 8
        forward 3
        up 1
        down 7
        down 7
        down 5
        forward 3
        down 8
        forward 3
        down 7
        down 5
        up 2
        forward 9
        down 8
        down 5
        forward 3
        forward 2
        forward 7
        up 8
        down 2
        down 5
        down 8
        down 9
        down 9
        down 1
        up 4
        forward 5
        up 1
        up 4
        forward 1
        down 1
        down 7
        up 9
        up 7
        down 5
        down 9
        down 9
        down 8
        forward 7
        down 3
        up 4
        down 7
        down 8
        forward 7
        forward 4
        up 9
        down 2
        up 7
        forward 5
        down 3
        forward 3
        forward 5
        forward 5
        down 2
        down 2
        down 7
        up 8
        up 9
        down 1
        forward 9
        forward 3
        up 3
        forward 9
        up 2
        down 7
        down 3
        forward 4
        down 5
        down 3
        up 5
        forward 4
    """.trimIndent().lines()
}
