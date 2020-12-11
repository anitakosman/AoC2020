import java.io.File

fun main() {
    val lines = File("src/main/resources/day11.txt").readLines().map { it.toCharArray() }
    val input = (lines.indices).flatMap { i -> (lines[0].indices).map { i to it } }.filter { (i,j) -> lines[i][j] == 'L' }.toSet()
    part1(input)
    part2(input)
}

private fun part1(input: Set<Pair<Int,Int>>) {
    println(stabilize(input).size)
}

private fun stabilize(input: Set<Pair<Int, Int>>, lineOfSight: Boolean = false, tolerance: Int = 4) : Set<Pair<Int, Int>> {
    val seatings = mutableSetOf(emptySet<Pair<Int, Int>>())
    val rows = input.maxBy { it.first }!!.first
    val cols = input.maxBy { it.second }!!.second
    var next = chaos(emptySet(), input, rows, cols, lineOfSight, tolerance)
    while (next !in seatings) {
        seatings.add(next)
        next = chaos(next, input, rows, cols, lineOfSight, tolerance)
    }
    return next
}

fun chaos(occupied: Set<Pair<Int, Int>>, seats: Set<Pair<Int, Int>>, rows: Int, cols: Int, lineOfSight: Boolean, tolerance: Int): Set<Pair<Int, Int>> {
    return occupied.filter { neighbors(it, occupied, lineOfSight, seats, rows, cols) < tolerance }.toSet().plus(
            seats.filter { neighbors(it, occupied, lineOfSight, seats, rows, cols) == 0 })
}

fun neighbors(chair: Pair<Int, Int>, occupied: Set<Pair<Int, Int>>, lineOfSight: Boolean, seats: Set<Pair<Int, Int>>, rows: Int, cols: Int): Int {
    return (-1..1).map { i -> (-1..1).count { j ->
        (i != 0 || j != 0) && (((chair.first + i) to (chair.second + j)) in occupied
                || (lineOfSight && lineOfSightOccupied(chair, i, j, occupied, seats, rows, cols)))
    } }.sum()
}

fun lineOfSightOccupied(c: Pair<Int, Int>, i: Int, j: Int, occupied: Set<Pair<Int, Int>>, seats: Set<Pair<Int, Int>>, rows: Int, cols: Int): Boolean {
    var chair = (c.first + i to c.second + j)
    while (chair.first in 0..rows && chair.second in 0..cols){
        if (chair in seats)
            return chair in occupied
        chair = chair.first + i to chair.second + j
    }
    return false
}

private fun part2(input: Set<Pair<Int,Int>>) {
    println(stabilize(input, true, 5).size)
}
