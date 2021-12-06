import Day06.input
import Day06.populationAt

fun main() {

    val answer1 = populationAt(input, 80)
    println(answer1) // 350149

    val answer2 = populationAt(input, 256)
    println(answer2)  // 1590327954513
}

object Day06 {

    fun populationAt(initial: List<Int>, days: Int): Long {

        tailrec fun go(n: Int, acc: Map<Int, Long>): Map<Int, Long> =
            if (n == days) acc
            else go(n + 1, mapOf(
                8 to acc.getOrDefault(0, 0),
                7 to acc.getOrDefault(8, 0),
                6 to acc.getOrDefault(7, 0) + acc.getOrDefault(0, 0),
                5 to acc.getOrDefault(6, 0),
                4 to acc.getOrDefault(5, 0),
                3 to acc.getOrDefault(4, 0),
                2 to acc.getOrDefault(3, 0),
                1 to acc.getOrDefault(2, 0),
                0 to acc.getOrDefault(1, 0),
            ))

        return go(0, initial.groupBy { it }.mapValues { (_, v) -> v.size.toLong() }).values.sum()
    }

    val input: List<Int> = "4,1,4,1,3,3,1,4,3,3,2,1,1,3,5,1,3,5,2,5,1,5,5,1,3,2,5,3,1,3,4,2,3,2,3,3,2,1,5,4,1,1,1,2,1,4,4,4,2,1,2,1,5,1,5,1,2,1,4,4,5,3,3,4,1,4,4,2,1,4,4,3,5,2,5,4,1,5,1,1,1,4,5,3,4,3,4,2,2,2,2,4,5,3,5,2,4,2,3,4,1,4,4,1,4,5,3,4,2,2,2,4,3,3,3,3,4,2,1,2,5,5,3,2,3,5,5,5,4,4,5,5,4,3,4,1,5,1,3,4,4,1,3,1,3,1,1,2,4,5,3,1,2,4,3,3,5,4,4,5,4,1,3,1,1,4,4,4,4,3,4,3,1,4,5,1,2,4,3,5,1,1,2,1,1,5,4,2,1,5,4,5,2,4,4,1,5,2,2,5,3,3,2,3,1,5,5,5,4,3,1,1,5,1,4,5,2,1,3,1,2,4,4,1,1,2,5,3,1,5,2,4,5,1,2,3,1,2,2,1,2,2,1,4,1,3,4,2,1,1,5,4,1,5,4,4,3,1,3,3,1,1,3,3,4,2,3,4,2,3,1,4,1,5,3,1,1,5,3,2,3,5,1,3,1,1,3,5,1,5,1,1,3,1,1,1,1,3,3,1".split(",").map { it.toInt() }
}
