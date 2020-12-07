import java.io.File

fun main() {
    val input = File("src/main/resources/day7.txt").readLines().map { line ->
        Regex("(\\w+ \\w+) bags contain (?:no other bags|((?:\\d+ \\w+ \\w+ bags?(?:, )?)+))\\.").matchEntire(line)!!.groups.let {
            it[1]!!.value to it[2]?.value
        }
    }.toMap()
    part1(input)
    part2(input)
}

private fun part1(input: Map<String, String?>) {
    var old = setOf("shiny gold")
    var new = findOuterBags(input,old)
    while (new != old){
        old = new
        new = findOuterBags(input, old).union(old)
    }
    println(new.size)
}

private fun findOuterBags(input: Map<String, String?>, inner: Set<String>) =
        input.entries.filter { rule -> inner.any { rule.value?.contains(it) ?: false } }.map { it.key }.toSet()

private fun part2(input: Map<String, String?>) {
    println(getNrOfBags(input, "shiny gold"))
}

private val nrOfBags = mutableMapOf<String, Int>()

private fun getNrOfBags(input: Map<String, String?>, color: String): Int {
    val v = nrOfBags[color]
    if (v != null)
        return v

    val newV = when (val rule = input[color]){
        null -> 0
        else -> Regex("(\\d+) (\\w+ \\w+) bags?").findAll(rule).map {
            it.groups[1]!!.value.toInt() * (getNrOfBags(input, it.groups[2]!!.value) + 1) }.sum()
    }
    nrOfBags[color] = newV
    return newV
}
