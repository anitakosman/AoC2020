fun main() {
    val input = "589174263".map { it.toString().toInt() }
    part1(input)
    part2(input)
}

private fun part1(input: List<Int>) {
    val circle = cups(input, 100)
    var cup = circle[1]
    repeat(8){
        print(cup)
        cup = circle[cup]!!
    }
    println()
}

private fun part2(input: List<Int>) {
    val circle = cups(input.plus((10..1000000)), 10000000)
    val cup = circle[1]!!
    println(cup.toLong() * circle[cup]!!)
}

private fun cups(input: List<Int>, moves: Int): Map<Int, Int> {
    val circle = input.zipWithNext().toMap().toMutableMap()
    circle[input.last()] = input.first()
    val max = input.size
    var current = input.first()
    repeat(moves) {
        val one = circle[current]!!
        val two = circle[one]!!
        val three = circle[two]!!

        val next = circle[three]!!
        circle[current] = next

        var dest = current - 1
        while (dest <= 0 || dest in listOf(one, two, three)){
            if (dest <= 0) dest = max else dest--
        }

        val destNext = circle[dest]!!
        circle[dest] = one
        circle[three] = destNext

        current = next
    }
    return circle
}