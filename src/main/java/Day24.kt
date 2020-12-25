import java.io.File

fun main() {
    val input = File("src/main/resources/day24.txt").readLines()
    part1(input)
    part2(input)
}

private fun part1(input: List<String>) {
    val flipped = mutableSetOf<Pair<Int, Int>>()
    input.forEach { s ->
        var x = 0
        var y = 0
        Regex("(w|nw|ne|e|se|sw)").findAll(s).forEach { m ->
            when(m.value) {
                "w" -> x -= 2
                "nw" -> {x--; y--}
                "ne" -> {x++; y--}
                "e" -> x += 2
                "se" -> {x++; y++}
                "sw" -> {x--; y++}
            }
        }
        if (!flipped.remove(x to y))
            flipped.add(x to y)
    }
    println(flipped.size)
}

private fun part2(input: List<String>) {

}