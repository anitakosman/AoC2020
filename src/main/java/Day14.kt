import java.io.File

fun main() {
    val input = mutableMapOf<Int, Pair<String, String>>()
    File("src/main/resources/day14.txt").readText().split(Regex("\\r\\n(?=mask)"))
        .forEach { part ->
            val lines = part.lines()
            val mask = lines[0].substringAfter("= ")
            lines.drop(1).forEach { line ->
                Regex("mem\\[(\\d+)] = (\\d+)").matchEntire(line)!!.groups.let {
                    input[it[1]!!.value.toInt()] = Pair(it[2]!!.value, mask)
                }
            }
        }
    part1(input)
    part2(input)
}

private fun part1(input: Map<Int, Pair<String, String>>) {
    println(input.values.map { (value, mask) ->
        val s = value.toInt().toString(2).padStart(36,'0').toCharArray()
        mask.forEachIndexed { i, c -> if (c == '1') s[i] = '1' else if (c == '0') s[i] = '0' }
        s.joinToString("").toLong(2)
    }.sum())
}

private fun part2(input: Map<Int, Pair<String, String>>) {

}