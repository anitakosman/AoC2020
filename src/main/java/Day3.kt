import java.io.File

fun main() {
    val input = File("src/main/resources/day3.txt").readLines().map { it.toCharArray() }
    val n = input.size
    val m = input[0].size
    part1(input, n, m)
    part2(input, n, m)
}

fun part1(input: List<CharArray>, n: Int, m: Int) {
    println(treesOnSlope(input, n, m, 1, 3))
}

fun part2(input: List<CharArray>, n: Int, m: Int) {
    println(treesOnSlope(input, n, m, 1, 1) * treesOnSlope(input, n, m, 1, 3) * treesOnSlope(input, n, m, 1, 5) * treesOnSlope(input, n, m, 1, 7) * treesOnSlope(input, n, m, 2, 1))
}

private fun treesOnSlope(input: List<CharArray>, n: Int, m: Int, p: Int, q: Int) =
        (0 until n step p).count { input[it][(q * it / p) % m] == '#' }.toLong()