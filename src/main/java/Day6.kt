import java.io.File

fun main() {
    val input = File("src/main/resources/day6.txt").readText().split("\r\n\r\n").map { lines ->
        lines.lines()
    }
    part1(input)
    part2(input)
}

private fun part1(input: List<List<String>>) {
    println(input.sumBy { group -> group.joinToString("").chars().distinct().count().toInt() })
}

private fun part2(input: List<List<String>>) {
    println(input.sumBy { group -> group[0].chars().filter {c ->  group.all { line -> line.chars().anyMatch { it == c } } }.count().toInt() })
}
