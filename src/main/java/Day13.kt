import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("src/main/resources/day13.txt").readLines()
    val time = input[0].toInt()
    val busses = input[1].split(",").map { it.toIntOrNull() }
    part1(time, busses)
    part2(busses)
}

private fun part1(time: Int, busses: List<Int?>) {
    println(busses.filterNotNull().minByOrNull { it - (time % it) }!!.let { it * (it - (time % it)) })
}

private fun part2(busses: List<Int?>) {
    var t = 100000000000000
    val bussesWithIndex = busses.withIndex().filter { it.value != null }.map { IndexedValue(it.index,it.value!!.toLong()) }
    var step = 1L
    for ((index, bus) in bussesWithIndex){
        while((bus - (t % bus)) % bus != index % bus){
            t += step
        }
        step = lcm(step, bus)
    }
    println(t)
}

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b