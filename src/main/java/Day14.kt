import java.io.File

fun main() {
    val input = File("src/main/resources/day14.txt").readText().split(Regex("\\r\\n(?=mask)"))
        .flatMap { part ->
            val lines = part.lines()
            val mask = lines[0].substringAfter("= ")
            lines.drop(1).map { line ->
                Regex("mem\\[(\\d+)] = (\\d+)").matchEntire(line)!!.groups.let {
                    Instruction(it[1]!!.value.toInt(),it[2]!!.value.toInt(), mask)
                }
            }
        }
    part1(input)
    part2(input)
}

data class Instruction(val key: Int, val value: Int, val mask: String)

private fun part1(input: List<Instruction>) {
    println(input.reversed().distinctBy { it.key }.map { (_, value, mask) ->
        val s = value.toString(2).padStart(36,'0').toCharArray()
        mask.forEachIndexed { i, c -> if (c == '1') s[i] = '1' else if (c == '0') s[i] = '0' }
        s.joinToString("").toLong(2)
    }.sum())
}

private fun part2(input: List<Instruction>) {
    val m = mutableMapOf<Long, Long>()
    input.forEach { (key, value, mask) ->
        var keys = setOf(StringBuilder(key.toString(2).padStart(36,'0')))
        mask.forEachIndexed { index, c ->
            when (c) {
                '1' -> keys = keys.map { it.setCharAt(index, '1'); it }.toSet()
                'X' -> keys = keys.flatMap { key -> listOf(StringBuilder(key).also { it.setCharAt(index, '0') }, StringBuilder(key).also { it.setCharAt(index, '1') } ) }.toSet()
            }
        }
        keys.forEach { m[it.toString().toLong(2)] = value.toLong() }
    }
    println(m.values.sum())
}