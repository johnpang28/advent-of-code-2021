import Day11.input
import Day11.step
import Day11.toEnergyMap

fun main() {

    val initialState = input.toEnergyMap()

    initialState.toMutableMap().let { energyMap ->
        val flashCount = (1..100).sumOf { step(energyMap) }
        println(flashCount) // part1 1620
    }

    initialState.toMutableMap().let { energyMap ->
        var stepCount = 0
        do {
            step(energyMap)
            stepCount++
        } while (energyMap.values.any { it != 0 })
        println(stepCount) // part2 371
    }
}

object Day11 {

    data class Point(val x: Int, val y: Int) {

        fun adjacents(): List<Point> =
            (y - 1..y + 1).flatMap { ay ->
                (x - 1..x + 1).map { ax ->
                    Point(ax, ay)
                }
            }.filterNot { it == this }
    }

    fun step(energyMap: MutableMap<Point, Int>): Int {
        energyMap.forEach { (k, v) -> energyMap[k] = v + 1 }

        while (true) {
            val flashers = energyMap.entries.filter { it.value > 9 }
            if (flashers.isEmpty()) break
            flashers.forEach { (point, _) ->
                energyMap[point] = 0
                point.adjacents().forEach { adj ->
                    energyMap[adj]?.let { energy -> if (energy != 0) energyMap[adj] = energy + 1 }
                }
            }
        }
        return energyMap.count { it.value == 0 }
    }

    fun String.toEnergyMap(): Map<Point, Int> =
        lines().flatMapIndexed { y, line ->
            line.mapIndexed { x, char ->
                Point(x, y) to char.digitToInt()
            }
        }.toMap()

    val input: String = """
        7147713556
        6167733555
        5183482118
        3885424521
        7533644611
        3877764863
        7636874333
        8687188533
        7467115265
        1626573134
    """.trimIndent()
}
