import java.io.File

fun main() {
    val input = File("src/main/resources/day22.txt").readText().split("\r\n\r\n").map {
        it.lines().mapNotNull { it.toIntOrNull() }
    }
    part1(input)
    part2(input)
}

private fun part1(input: List<List<Int>>){
    val player1 = input[0].toMutableList()
    val player2 = input[1].toMutableList()
    while (player1.isNotEmpty() && player2.isNotEmpty()){
        val a = player1.removeAt(0)
        val b = player2.removeAt(0)
        if (a > b){
            player1.addAll(listOf(a, b))
        } else {
            player2.addAll(listOf(b, a))
        }
    }
    val winner = if (player1.isNotEmpty()) player1 else player2
    println(winner.reversed().mapIndexed { i: Int, n: Int -> (i + 1) * n }.sum())
}

private fun part2(input: List<List<Int>>){
    val player1 = input[0]
    val player2 = input[1]
    val (_, winner) = recursiveCombat(player1, player2)
    println(winner.reversed().mapIndexed { i: Int, n: Int -> (i + 1) * n }.sum())
}

private fun recursiveCombat(input1: List<Int>, input2: List<Int>): Pair<Int, List<Int>> {
    val player1 = input1.toMutableList()
    val player2 = input2.toMutableList()
    val rounds = mutableSetOf<String>()
    while (player1.isNotEmpty() && player2.isNotEmpty() && toString(player1, player2) !in rounds) {
        rounds.add(toString(player1, player2))
        val a = player1.removeAt(0)
        val b = player2.removeAt(0)
        val r = if (player1.size >= a && player2.size >= b) {
            recursiveCombat(player1.subList(0, a), player2.subList(0, b)).first
        } else if (a > b) 1 else 2
        if (r == 1)
            player1.addAll(listOf(a, b))
        else
            player2.addAll(listOf(b, a))
    }
    return if (player1.isNotEmpty()) 1 to player1 else 2 to player2
}

private fun toString(l1: List<Int>, l2: List<Int>) =
    l1.joinToString() + l2.joinToString(prefix = " - ")