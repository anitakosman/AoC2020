import java.io.File

fun main() {
    val input = File("src/main/resources/day2.txt").readLines().map { line ->
        Regex("(\\d+)-(\\d+) ([a-z]): ([a-z]+)").matchEntire(line)!!.groups.let {
            Password(it[1]!!.value.toInt(),it[2]!!.value.toInt(),it[3]!!.value[0],it[4]!!.value)
        }
    }
    part1(input)
    part2(input)
}

data class Password(val a: Int, val b: Int, val c: Char, val p: String, val n: Int = p.count { it == c })

private fun part1(input: List<Password>) {
    println(input.count { it.a <= it.n && it.n <= it.b })
}

private fun part2(input: List<Password>) {
    println(input.count { password -> listOf(password.p[password.a - 1], password.p[password.b - 1]).count { it == password.c } == 1 })
}