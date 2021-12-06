import Day06.input
import Day06.populationAt

fun main() {

    val timerCounts: Map<Int, Long> = input.groupBy { it }.mapValues { (_, v) -> v.size.toLong() }

    val answer1 = populationAt(timerCounts, 80)
    println(answer1) // 350149

    val answer2 = populationAt(timerCounts, 256)
    println(answer2)  // 1590327954513
}

object Day06 {

    fun populationAt(timerCounts: Map<Int, Long>, day: Int): Long {

        tailrec fun go(n: Int, acc: Map<Int, Long>): Map<Int, Long> =
            if (n == day) acc
            else go(n + 1, acc.mapValues {
                when (it.key) {
                    6 -> acc.getValue(0) + acc.getValue(7)
                    8 -> acc.getValue(0)
                    else -> acc.getValue(it.key + 1)
                }
            })

        val withDefaults = (0..8).associateWith { t -> timerCounts.getOrDefault(t, 0L) }
        return go(0, withDefaults).values.sum()
    }

    val input: List<Int> =
        "4,1,4,1,3,3,1,4,3,3,2,1,1,3,5,1,3,5,2,5,1,5,5,1,3,2,5,3,1,3,4,2,3,2,3,3,2,1,5,4,1,1,1,2,1,4,4,4,2,1,2,1,5,1,5,1,2,1,4,4,5,3,3,4,1,4,4,2,1,4,4,3,5,2,5,4,1,5,1,1,1,4,5,3,4,3,4,2,2,2,2,4,5,3,5,2,4,2,3,4,1,4,4,1,4,5,3,4,2,2,2,4,3,3,3,3,4,2,1,2,5,5,3,2,3,5,5,5,4,4,5,5,4,3,4,1,5,1,3,4,4,1,3,1,3,1,1,2,4,5,3,1,2,4,3,3,5,4,4,5,4,1,3,1,1,4,4,4,4,3,4,3,1,4,5,1,2,4,3,5,1,1,2,1,1,5,4,2,1,5,4,5,2,4,4,1,5,2,2,5,3,3,2,3,1,5,5,5,4,3,1,1,5,1,4,5,2,1,3,1,2,4,4,1,1,2,5,3,1,5,2,4,5,1,2,3,1,2,2,1,2,2,1,4,1,3,4,2,1,1,5,4,1,5,4,4,3,1,3,3,1,1,3,3,4,2,3,4,2,3,1,4,1,5,3,1,1,5,3,2,3,5,1,3,1,1,3,5,1,5,1,1,3,1,1,1,1,3,3,1".split(",").map { it.toInt() }
}
