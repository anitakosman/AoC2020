import java.io.File

fun main() {
    val input = File("src/main/resources/day1.txt").readLines().map { it.toInt() }
    part1(input)
    part2(input)
}

private fun part1(input: List<Int>) {
    input.forEach { a ->
        val b = input.find { it != a && it + a == 2020 }
        if (b != null) {
            println("$a * $b = ${a * b}")
            return
        }
    }
}

private fun part2(input: List<Int>) {
    input.forEach { a ->
        input.filter { it != a }.forEach { b ->
            val c = input.find { it != a && it != b && it + a + b == 2020 }
            if (c != null) {
                println("$a * $b * $c = ${a * b * c}")
                return
            }
        }
    }
}