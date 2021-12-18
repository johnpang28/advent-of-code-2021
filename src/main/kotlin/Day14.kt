import Day14.sortedElementCounts
import Day14.input
import Day14.parseInput
import Day14.step

fun main() {

    val (template, ruleMap) = input.parseInput()

    val t10 = (0 until 10).fold(template) { acc, _ -> step(acc, ruleMap) }
    val answer1 = t10.groupBy { it }.values.map { it.size }.sorted().let { it.last() - it.first() }
    println(answer1) // 2549

    val templateMap = template.zipWithNext().associate { "${it.first}${it.second}" to 1L }
    val t40 = (0 until 40).fold(templateMap) { acc, _ -> step(acc, ruleMap) }
    val answer2 = sortedElementCounts(t40, template.last()).let { it.last() - it.first() }
    println(answer2) // 2516901104210
}

object Day14 {

    // brute force
    fun step(template: String, ruleMap: Map<String, String>): String =
        template.zipWithNext().joinToString("") { (e1, e2) ->
            e1 + ruleMap.getValue("$e1$e2")
        } + template.last()

    // Map of element pair to its count. i.e mapOf("HH" to 3, "PS" to 2, ...)
    fun step(templateMap: Map<String, Long>, ruleMap: Map<String, String>): Map<String, Long> =
        templateMap.entries.flatMap { (k, v) ->
            listOf(
                "${k[0]}${ruleMap[k]}" to v,
                "${ruleMap[k]}${k[1]}" to v,
            )
        }.groupBy { (k, _) -> k }.mapValues { (_, v) -> v.sumOf { it.second } }

    // We don't care about the actual element, just how many times it occurs
    fun sortedElementCounts(templateMap: Map<String, Long>, lastElement: Char): List<Long> =
        templateMap.entries
            .map { (k, v) -> k[0] to v }
            .groupBy { (k, _) -> k }
            .mapValues { (_, v) -> v.sumOf { it.second } }
            .toMutableMap().apply { this[lastElement] = this[lastElement]?.plus(1) ?: 1 }
            .values.toList().sorted()

    fun String.parseInput(): Pair<String, Map<String, String>> =
        split("\n\n").let { (template, rules) ->
            template to rules.lines().associate {
                it.split(" -> ").let { (elemIn, elemOut) ->
                    elemIn to elemOut
                }
            }
        }

    val input = """
        SHHNCOPHONHFBVNKCFFC

        HH -> K
        PS -> P
        BV -> H
        HB -> H
        CK -> F
        FN -> B
        PV -> S
        KK -> F
        OF -> C
        SF -> B
        KB -> S
        HO -> O
        NH -> N
        ON -> V
        VF -> K
        VP -> K
        PH -> K
        FF -> V
        OV -> N
        BO -> K
        PO -> S
        CH -> N
        FO -> V
        FB -> H
        FV -> N
        FK -> S
        VC -> V
        CP -> K
        CO -> K
        SV -> N
        PP -> B
        BS -> P
        VS -> C
        HV -> H
        NN -> F
        NK -> C
        PC -> V
        HS -> S
        FS -> S
        OB -> S
        VV -> N
        VO -> P
        KV -> F
        SK -> O
        BC -> P
        BP -> F
        NS -> P
        SN -> S
        NC -> N
        FC -> V
        CN -> S
        OK -> B
        SC -> N
        HN -> B
        HP -> B
        KP -> B
        CB -> K
        KF -> C
        OS -> B
        BH -> O
        PN -> K
        VN -> O
        KH -> F
        BF -> H
        HF -> K
        HC -> P
        NP -> H
        KO -> K
        CF -> H
        BK -> O
        OH -> P
        SO -> F
        BB -> F
        VB -> K
        SP -> O
        SH -> O
        PK -> O
        HK -> P
        CC -> V
        NB -> O
        NV -> F
        OO -> F
        VK -> V
        FH -> H
        SS -> C
        NO -> P
        CS -> H
        BN -> V
        FP -> N
        OP -> N
        PB -> P
        OC -> O
        SB -> V
        VH -> O
        KS -> B
        PF -> N
        KN -> H
        NF -> N
        CV -> K
        KC -> B
    """.trimIndent()
}