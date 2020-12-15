import java.io.File

fun main() {
    val input = listOf(16,11,15,0,1,7)
    println(memoryGame(input, 2020))
    println(memoryGame(input, 30000000))
}

private fun memoryGame(input: List<Int>, n: Int): Int {
    val turn = mutableMapOf<Int, Int>()
    input.forEachIndexed { index, i -> turn[i] = index }
    var v = 0
    (input.size until n - 1).forEach { val tv = it - turn.getOrDefault(v, it); turn[v] = it; v = tv }
    return v
}
