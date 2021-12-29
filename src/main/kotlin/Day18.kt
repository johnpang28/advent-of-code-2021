import Day18.combinations
import Day18.input
import Day18.parse
import java.util.Stack

fun main() {

    val answer1 = input.map { parse(it) }.reduce { a, b -> a + b }.magnitude()
    println(answer1) // 4469

    val answer2 = input.combinations(2)
        .flatMap { (a, b) -> listOf(parse(a) + parse(b), parse(b) + parse(a)) }
        .maxOf { it.magnitude() }
    println(answer2) // 4770
}

object Day18 {

    sealed interface Number {
        fun magnitude(): Int
        fun flatten(): List<RegularNumber>
        fun split(): SnailFishNumber
        operator fun plus(n: Number): SnailFishNumber = SnailFishNumber(this, n).apply { reduce() }
    }

    class RegularNumber(var value: Int) : Number {
        override fun toString() = value.toString()
        override fun magnitude() = value
        override fun flatten() = listOf(this)
        override fun split() = SnailFishNumber(RegularNumber(value / 2), RegularNumber(value / 2 + value % 2))
    }

    class SnailFishNumber(var left: Number, var right: Number) : Number {
        override fun toString() = "[$left,$right]"
        override fun magnitude() = 3 * left.magnitude() + 2 * right.magnitude()
        override fun flatten() = left.flatten() + right.flatten()

        override fun split(): SnailFishNumber {
            val leftState = left.toString()
            if (this.left is RegularNumber) {
                if (left.magnitude() >= 10) left = left.split()
            } else left.split()

            if (leftState == left.toString()) { // left not changed
                if (this.right is RegularNumber) {
                    if (right.magnitude() >= 10) right = right.split()
                } else right.split()
            }
            return this
        }

        fun reduce() {
            do {
                val initState = toString()
                explode()
                split()
            } while (initState != toString())
        }

        private fun explode() {
            var path = findExplosionPath()

            while (path != null) {
                val last = path.last()
                val lastParent = path[path.lastIndex - 1]

                val zero = RegularNumber(0)

                if (lastParent.left == last) lastParent.left = zero
                else lastParent.right = zero

                val flat = path.first().flatten()
                val i = flat.indexOf(zero)
                if (i > 0) flat[i - 1].value += last.left.magnitude()
                if (i < flat.lastIndex) flat[i + 1].value += last.right.magnitude()

                path = findExplosionPath()
            }
        }

        private fun findExplosionPath(): List<SnailFishNumber>? {

            fun go(acc: List<SnailFishNumber>): List<SnailFishNumber>? =
                with(acc.last()) {
                    if (left is RegularNumber && right is RegularNumber) {
                        if (acc.size > 4) acc else null
                    } else {
                        if (left is SnailFishNumber)
                            go(acc + left as SnailFishNumber)
                                ?: if (right is SnailFishNumber) go(acc + right as SnailFishNumber) else null
                        else if (right is SnailFishNumber)
                            go(acc + right as SnailFishNumber) else null
                    }
                }

            return go(listOf(this))
        }
    }

    fun parse(s: String): SnailFishNumber {
        val ns = Stack<Number>()
        val ops = Stack<Char>()

        s.forEach { c ->
            when {
                c == '[' -> ops.push(c)
                c == ',' -> ops.push(c)
                c == ']' && ops.peek() == ',' -> ops.pop()
                ops.peek() == '[' && c.isDigit() -> ns.push(RegularNumber(c.digitToInt()))
                ops.peek() == ',' && c.isDigit() -> ns.push(SnailFishNumber(ns.pop(), RegularNumber(c.digitToInt())))
                else -> {
                    val r = ns.pop()
                    val l = ns.pop()
                    ns.push(SnailFishNumber(l, r))
                }
            }
        }
        return ns.pop() as SnailFishNumber
    }

    // Taken from my solution to day 1, 2020
    // https://github.com/johnpang28/advent-of-code-2020/blob/main/src/main/kotlin/me/jp/aoc/Day01.kt
    fun <T> List<T>.combinations(size: Int): Sequence<List<T>> {

        val indexed = withIndex().toList()

        tailrec fun go(currentSize: Int, acc: Sequence<List<IndexedValue<T>>>): Sequence<List<IndexedValue<T>>> =
            when (currentSize) {
                size -> acc
                0 -> go(1, indexed.subList(0, this.size - size + 1).asSequence().map { listOf(it) })
                else -> go(
                    currentSize + 1,
                    acc.flatMap { x ->
                        indexed.subList(x.last().index + 1, this.size - size + currentSize + 1).map { x + it }
                    })
            }

        return go(0, emptySequence()).map { it.map { indexedValue -> indexedValue.value } }
    }

    val input: List<String> = """
        [[[[4,6],4],[1,7]],[[[1,6],[8,4]],[1,1]]]
        [[[[8,5],[9,2]],1],[[2,5],[[9,4],[5,9]]]]
        [[[[7,3],0],[8,9]],6]
        [[6,[[7,2],[6,2]]],[[[9,8],9],[9,6]]]
        [2,[[[9,2],6],[[5,3],[6,7]]]]
        [[[5,[9,6]],0],[[[2,8],[7,0]],[7,[4,4]]]]
        [[[[5,0],2],[0,1]],4]
        [2,[8,8]]
        [[[[2,5],[6,8]],[[9,8],4]],[[[2,3],[5,8]],[9,5]]]
        [[[[0,7],[9,4]],[[1,0],9]],[[[8,8],[7,2]],[3,[6,5]]]]
        [[[[3,2],8],1],[[4,[3,4]],[[6,5],[0,6]]]]
        [[[7,8],8],[0,[5,2]]]
        [[3,[3,3]],[[[6,9],[1,1]],[6,[2,9]]]]
        [[[[9,7],[6,8]],4],[[[8,2],[2,9]],[8,[6,2]]]]
        [[[[7,3],2],[9,6]],[[[1,7],[0,0]],[4,9]]]
        [[8,[7,[1,0]]],7]
        [[[7,[5,1]],0],[[8,[5,3]],4]]
        [1,[[[2,6],2],[1,[6,0]]]]
        [[[5,8],[[9,1],1]],[[3,[5,0]],5]]
        [[[[1,5],[4,9]],8],[[7,0],6]]
        [9,[[0,[1,0]],6]]
        [[[[6,8],6],9],[[7,3],2]]
        [[9,[[8,7],4]],[[[4,0],[9,0]],[8,1]]]
        [[[2,[4,4]],[7,[0,1]]],[8,[[8,6],[4,0]]]]
        [0,9]
        [[[[1,8],[7,4]],[[5,0],[6,1]]],[5,7]]
        [[[[8,2],[9,2]],[8,[8,4]]],[0,4]]
        [[[[0,7],[5,8]],3],6]
        [[[7,[3,4]],[3,[1,5]]],2]
        [[[1,[4,2]],5],[[1,2],1]]
        [[[[8,2],[0,9]],1],[[[9,0],[3,5]],[8,[8,0]]]]
        [[[0,5],[1,[3,3]]],[[[1,0],[5,2]],[7,5]]]
        [[[4,[7,3]],[0,9]],[[2,0],8]]
        [[[[2,2],8],[7,1]],5]
        [[1,[[3,8],7]],[[7,[5,8]],[4,[1,7]]]]
        [[[[2,7],4],[8,[9,1]]],[[5,2],[4,3]]]
        [[2,[7,2]],[[8,[0,8]],[0,[4,2]]]]
        [[6,[6,[7,4]]],[[7,[2,0]],[[8,2],8]]]
        [[[7,[1,7]],[[4,1],4]],[1,[4,6]]]
        [1,[[1,0],[[0,3],[6,9]]]]
        [[[[8,6],0],[[2,8],[3,0]]],[[[8,2],7],[[3,0],5]]]
        [[[[2,8],4],[2,[0,7]]],[[3,[1,2]],[[8,0],[4,2]]]]
        [1,8]
        [[5,6],6]
        [[[[1,0],[3,6]],[[4,0],1]],[0,7]]
        [[[5,[9,6]],[7,[1,2]]],2]
        [[[6,4],[[5,6],[1,8]]],[[[9,0],[7,7]],[[5,8],[6,8]]]]
        [8,5]
        [5,[[[6,8],8],0]]
        [[[[5,7],[0,0]],[6,[0,0]]],[[[5,5],3],[[1,1],[3,4]]]]
        [[[4,0],[[8,6],2]],[[3,[3,1]],[[2,8],[7,2]]]]
        [[[8,7],[[5,5],[5,3]]],4]
        [[[[5,4],1],[3,4]],[3,5]]
        [[[6,5],[[6,3],6]],4]
        [[[[2,2],[7,1]],[6,6]],[[8,[8,7]],[[1,6],[3,0]]]]
        [[4,[[5,0],[7,4]]],[3,1]]
        [[[3,[5,8]],5],[1,[[9,6],3]]]
        [[0,[[3,0],[8,7]]],[[1,3],3]]
        [5,[[3,[3,3]],[3,6]]]
        [[[[7,3],8],3],[2,[[9,8],2]]]
        [[[2,4],[[1,2],5]],[[[1,2],[6,0]],3]]
        [[9,[[1,1],[1,7]]],[1,[2,[9,1]]]]
        [[[5,[0,0]],5],[6,[0,1]]]
        [[3,[[6,5],7]],[[7,8],3]]
        [[5,[2,6]],8]
        [[6,[0,[3,0]]],[1,2]]
        [3,[[[3,7],2],[[4,0],6]]]
        [[[8,[2,7]],[4,1]],[[2,[4,2]],3]]
        [[3,2],[[[8,8],[8,6]],[[5,3],1]]]
        [1,[2,[[3,2],[2,9]]]]
        [8,[[9,1],[[8,4],[9,9]]]]
        [[[4,[4,6]],[1,8]],[[7,7],[[7,4],3]]]
        [[[8,2],[[9,7],[0,8]]],[[4,4],[[6,1],5]]]
        [[[3,[6,6]],[[8,6],[3,7]]],[[7,9],[[5,3],8]]]
        [[[8,9],[8,6]],[[[3,3],[2,9]],[[6,6],9]]]
        [8,[[[3,0],5],2]]
        [[[[1,3],1],[[1,9],4]],[7,[3,1]]]
        [[[[9,3],3],[[6,8],7]],[[[2,0],3],[8,[3,6]]]]
        [[[[7,1],[8,1]],[[4,6],[5,9]]],[[[4,5],3],5]]
        [6,[[3,[0,0]],[6,6]]]
        [[[[8,8],[7,6]],3],[[[7,7],[1,1]],[[1,8],[1,4]]]]
        [[9,[8,[3,4]]],[[6,2],[1,5]]]
        [[5,[3,3]],[5,[0,5]]]
        [[[[8,9],5],[1,9]],[[5,[2,8]],[[6,4],[9,4]]]]
        [2,6]
        [[[[1,4],8],5],[5,[0,[1,7]]]]
        [[[[1,0],[9,9]],[0,9]],[[[5,4],[1,6]],[9,[6,7]]]]
        [[[7,1],5],[[3,2],5]]
        [[9,[[8,8],[7,0]]],[5,[3,[1,3]]]]
        [[[[5,2],[7,5]],[4,[6,7]]],[[[8,1],6],[2,[6,6]]]]
        [[[5,7],[6,[8,2]]],[8,2]]
        [[[[5,7],8],[[9,8],2]],[[2,8],[[7,6],3]]]
        [[1,[[1,6],1]],[0,[[5,9],[9,1]]]]
        [[[[1,4],[5,0]],[[5,5],[9,3]]],[[6,4],[4,[4,6]]]]
        [7,[[5,[4,8]],[[5,9],2]]]
        [[[[2,9],[1,8]],[4,2]],0]
        [[5,[[0,9],[3,7]]],[2,[6,[4,8]]]]
        [[0,[5,5]],0]
        [[[5,0],[[0,5],8]],[6,[[8,7],[6,5]]]]
        [[[5,[8,2]],[8,4]],[[6,2],[8,[7,0]]]]
    """.trimIndent().lines()
}
