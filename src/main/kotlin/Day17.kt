import Day17.Target
import Day17.Velocity
import Day17.input
import Day17.minXVelocity

fun main() {

    val target = Target.from(input)

    val minXVelocity = minXVelocity(target.xRange.first)
    val maxYVelocity = 200 // no idea what this should be, just guessing

    // use some brute force, sorry mom!
    val velocitiesWithHeight = (minXVelocity..target.xRange.last).flatMap { vx ->
        IntRange(target.yRange.first, maxYVelocity).mapNotNull { vy ->
            val v = Velocity(vx, vy)
            target.onTargetMaxHeight(v)?.let { maxY -> v to maxY }
        }
    }

    val answer1 = velocitiesWithHeight.maxOf { (_, h) -> h }
    println(answer1) // 9870

    val answer2 = velocitiesWithHeight.size
    println(answer2) // 5523
}

object Day17 {

    data class Position(val x: Int, val y: Int)

    data class Velocity(val x: Int, val y: Int) {

        fun decrease(): Velocity {
            val dx = when {
                x > 0 -> x - 1
                x < 0 -> x + 1
                else -> x
            }
            val dy = y - 1
            return Velocity(dx, dy)
        }
    }

    data class Target(val xRange: IntRange, val yRange: IntRange) {

        fun onTargetMaxHeight(v: Velocity): Int? {

            tailrec fun go(accP: Position, accV: Velocity, maxY: Int): Int? =
                if (isWithin(accP)) maxY
                else if (isPassed(accP)) null
                else {
                    val (newP, newV) = step(accP, accV)
                    go(newP, newV, if (newP.y > maxY) newP.y else maxY)
                }

            return go(Position(0, 0), v, 0)
        }

        private fun isWithin(p: Position) = xRange.contains(p.x) && yRange.contains(p.y)
        private fun isPassed(p: Position) = p.x > xRange.last || p.y < yRange.first
        private fun step(p: Position, v: Velocity) = Position(p.x + v.x, p.y + v.y) to v.decrease()

        companion object {
            private val regex = "target area: x=([0-9]+)..([0-9]+), y=(-?[0-9]+)..(-?[0-9]+)".toRegex()

            fun from(s: String): Target =
                regex.matchEntire(s)?.let { result ->
                    val (xFrom, xTo, yFrom, yTo) = result.destructured
                    Target(IntRange(xFrom.toInt(), xTo.toInt()), IntRange(yFrom.toInt(), yTo.toInt()))
                } ?: error("Could not parse $s")
        }
    }

    fun minXVelocity(target: Int): Int = generateSequence(1) { it + 1 }.takeWhile { xRange(it) < target }.last()

    private fun xRange(xVelocity: Int): Int = (xVelocity + 1) * xVelocity / 2

    const val input = "target area: x=119..176, y=-141..-84"
}
