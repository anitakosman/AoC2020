import java.io.File

fun main() {
    val input = File("src/main/resources/day10.txt").readLines().map { it.toInt() }.plus(0).sorted()
            .zipWithNext().map { it.second - it.first }.plus(3)
    part1(input)
    part2(input)
}

private fun part1(input: List<Int>) {
    println(input.count { it == 1 } * input.count { it == 3 })
}

private fun part2(input: List<Int>) {
    println(arrangements(input, 0))
}

val arrangements = mutableMapOf<Int, Long>()

fun arrangements(input: List<Int>, n: Int): Long {
    if (arrangements.containsKey(n))
        return arrangements[n]!!
    if (n >= input.size - 1)
        return 1L
    var v = arrangements(input, n + 1)
    if (input[n] + input[n + 1] <= 3) {
        v += arrangements(input, n + 2)
        if (input[n] + input[n + 1] + (input.getOrNull(n + 2) ?: 3) <= 3){
            v += arrangements(input, n + 3)
        }
    }
    arrangements.put(n, v)
    return v
}
