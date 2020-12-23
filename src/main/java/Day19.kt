import java.io.File

fun main() {
    val ruleZero: String
    val messages: List<String>
    File("src/main/resources/day19.txt").readText().split("\r\n\r\n").let {
        val rules = getRules(it[0].lines())
        ruleZero = rules[0]!!
        messages = it[1].lines()
    }
    part1(ruleZero, messages)
    part2(ruleZero, messages)
}

fun getRules(lines: List<String>): Map<Int, String> {
    var rules = lines.map {
        it.substringBefore(": ").toInt() to it.substringAfter(": ").replace("\"", "")
    }.toMap()
    val toParse = rules.keys.toMutableSet()
    while (0 in toParse){
        //println(rules)
        //println(toParse)
        val key = toParse.filter { !rules[it]!!.contains(Regex("\\d")) }.minOrNull()!!
        val s = rules[key]!!.let { if (it.contains("|")) "($it)" else it }
        rules = rules.mapValues { (_, v) ->
            v.replace(Regex("(?<!\\d)$key(?!\\d)"), s)
        }
        toParse.remove(key)
    }
    return rules.mapValues { (_, v) -> v.replace(" ", "") }
}

private fun part1(ruleZero: String, messages: List<String>) {
    println(messages.count { Regex(ruleZero).matchEntire(it) != null })
}

private fun part2(ruleZero: String, messages: List<String>) {
    println(messages.count { Regex(ruleZero).matchEntire(it) != null })
}