import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("src/main/resources/day12.txt").readLines().map { it[0] to it.substring(1).toInt() }
    part1(input)
    part2(input)
}

private enum class Direction(val dx: Int, val dy: Int){
    N(0, -1), E(1, 0), S(0, 1), W(-1, 0);

    fun turn(dir: Char, degrees: Int): Direction {
        return when(dir to degrees){
            'L' to 90, 'R' to 270 -> when(this){
                N -> W
                E -> N
                S -> E
                W -> S
            }
            'L' to 270, 'R' to 90 -> when(this){
                N -> E
                E -> S
                S -> W
                W -> N
            }
            'L' to 180, 'R' to 180 -> when(this){
                N -> S
                E -> W
                S -> N
                W -> E
            }
            else -> throw IllegalArgumentException()
        }
    }
}

private fun part1(input: List<Pair<Char,Int>>) {
    var dir = Direction.E
    var x = 0
    var y = 0
    input.forEach {
        when (it.first) {
            in "NESW" -> {
                val d = Direction.valueOf(it.first.toString())
                x += it.second * d.dx
                y += it.second * d.dy
            }
            'F' -> {
                x += it.second * dir.dx
                y += it.second * dir.dy
            }
            else -> {
                dir = dir.turn(it.first, it.second)
            }
        }
    }
    println(abs(x) + abs(y))
}

private fun part2(input: List<Pair<Char,Int>>) {
    var wx = 10
    var wy = -1
    var x = 0
    var y = 0
    input.forEach {
        when (it.first) {
            in "NESW" -> {
                val d = Direction.valueOf(it.first.toString())
                wx += it.second * d.dx
                wy += it.second * d.dy
            }
            'F' -> {
                x += it.second * wx
                y += it.second * wy
            }
            else -> {
                val (twx, twy) = turnWaypoint(wx, wy, it.first, it.second)
                wx = twx
                wy = twy
            }
        }
    }
    println(abs(x) + abs(y))
}

private fun turnWaypoint(wx: Int, wy: Int, dir: Char, degrees: Int): Pair<Int, Int>  {
    return when(dir to degrees) {
        'L' to 90, 'R' to 270 -> wy to -wx
        'L' to 270, 'R' to 90 -> -wy to wx
        'L' to 180, 'R' to 180 -> -wx to -wy
        else -> throw IllegalArgumentException()
    }
}
