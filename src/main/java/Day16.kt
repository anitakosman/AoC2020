import java.io.File

fun main() {
    val parts = File("src/main/resources/day16.txt").readText().split("\r\n\r\n")
    val rules = parts[0].lines().map { line ->
        line.substringBefore(": ") to line.substringAfter(": ").split(" or ").map {
            ((it.substringBefore('-').toInt())..(it.substringAfter('-').toInt())).toSet()
        }.reduce(Set<Int>::plus) }.toMap()
    val myticket = parts[1].lines()[1].split(",").map { it.toInt() }
    val tickets = parts[2].lines().drop(1).map { ticket -> ticket.split(",").map { it.toInt() } }
    val valids = part1(rules, tickets)
    part2(rules, valids.plusElement(myticket), myticket)
}

private fun part1(rules: Map<String, Set<Int>>, tickets: List<List<Int>>): List<List<Int>> {
    val range = rules.values.reduce(Set<Int>::plus)
    val partition = tickets.partition { ticket -> ticket.any { it !in range } }
    println(partition.first.flatten().filter { it !in range }.sum())
    return partition.second
}

private fun part2(rules: Map<String, Set<Int>>, tickets: List<List<Int>>, myticket: List<Int>) {
    println( findDeparture(rules.keys.sortedBy { rules[it]!!.size }, emptyList(), rules, tickets)?.map { myticket[it].toLong() }?.reduce(Long::times))
}

fun findDeparture(fields: List<String>, order: List<String>, rules: Map<String, Set<Int>>, tickets: List<List<Int>>): List<Int>? {
    if (fields.isEmpty()) {
        return order.withIndex().filter { it.value.startsWith("departure") }.map { it.index }
    } else {
        val n = order.size
        fields.forEach { field ->
            if (tickets.all { rules[field]!!.contains(it[n]) }) {
                val r = findDeparture(fields.minusElement(field), order.plusElement(field), rules, tickets)
                if (r != null)
                    return r
            }
        }
    }
    return null
}
