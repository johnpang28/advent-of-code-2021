import Day12.routeCount
import Day12.input
import Day12.parseToPathMap

fun main() {

    val pathMap = input.parseToPathMap()

    val answer1 = routeCount(pathMap) { route, cave -> !route.smallCaves.contains(cave) }
    println(answer1) // 3510

    val answer2 = routeCount(pathMap) { route, cave ->
        route.smallCave2Visits == null || (cave != route.smallCave2Visits && !route.smallCaves.contains(cave))
    }
    println(answer2) // 122880
}

object Day12 {

    data class Route(val caves: List<String> = listOf("start")) {
        val smallCaves: List<String> = caves.drop(0).filterNot { it.isLarge() }

        val smallCave2Visits: String? by lazy {
            smallCaves.groupBy { it }.values.firstOrNull { it.size > 1 }?.first()
        }

        fun isComplete(): Boolean = caves.lastOrNull() == "end"
    }

    fun routeCount(pathMap: Map<String, List<String>>, nextCaveOk: (Route, String) -> Boolean): Int {

        tailrec fun go(acc: List<Route>): List<Route> =
            if (acc.all { it.isComplete() }) acc
            else {
                val completedRoutes = acc.filter { it.isComplete() }
                val incompleteRoutes = acc - completedRoutes.toSet()
                val incrementedRoutes = incompleteRoutes.flatMap { route ->
                    val next = pathMap.getValue(route.caves.last()).filter { it.isLarge() || nextCaveOk(route, it) }
                    if (next.isEmpty()) emptyList()
                    else next.map { Route(route.caves + listOf(it)) }
                }
                go(completedRoutes + incrementedRoutes)
            }

        return go(listOf(Route())).size
    }

    private fun String.isLarge():Boolean = any { it.isUpperCase() }

    fun List<String>.parseToPathMap(): Map<String, List<String>> =
        flatMap { line ->
            line.split("-").let { (cave1, cave2) ->
                listOfNotNull(
                    cave1 to cave2,
                    if (cave1 != "start" && cave2 != "end") cave2 to cave1 else null
                )
            }
        }.groupBy({ it.first }, { it.second })

    val input: List<String> = """
        CI-hb
        IK-lr
        vr-tf
        lr-end
        XP-tf
        start-vr
        lr-io
        hb-qi
        end-CI
        tf-YK
        end-YK
        XP-lr
        XP-vr
        lr-EU
        tf-CI
        EU-vr
        start-tf
        YK-hb
        YK-vr
        start-EU
        lr-CI
        hb-XP
        XP-io
        tf-EU
    """.trimIndent().lines()
}
