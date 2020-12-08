import java.io.File

fun main() {
    val input = File("src/main/resources/day8.txt").readLines().map { line ->
        Regex("(acc|jmp|nop) \\+?(-?\\d+)").matchEntire(line)!!.groups.let {
            it[1]!!.value to it[2]!!.value.toInt()
        }
    }
    part1(input)
    part2(input)
}

private fun part1(input: List<Pair<String, Int>>) {
    val (acc, _) = run(input)
    println(acc)
}

private fun run(input: List<Pair<String, Int>>): Pair<Int, Int> {
    var acc = 0
    var i = 0
    val l = mutableSetOf<Int>()
    while (i !in l && i < input.size) {
        l.add(i)
        when (input[i].first) {
            "acc" -> acc += input[i].second
            "jmp" -> i += input[i].second - 1
        }
        i++
    }
    return acc to i
}

private fun part2(input: List<Pair<String, Int>>) {
    input.indices.filter { input[it].first == "jmp"}.forEach { n ->
        val (acc, i) = run(input.toMutableList().apply { set(n, "nop" to input[n].second) })
        if (i == input.size){
            println(acc)
            return
        }
    }
    input.indices.filter { input[it].first == "nop"}.forEach { n ->
        val (acc, i) = run(input.toMutableList().apply { set(n, "jmp" to input[n].second) })
        if (i == input.size){
            println(acc)
            return
        }
    }
}