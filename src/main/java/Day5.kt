import java.io.File

fun main() {
    val input = File("src/main/resources/day5.txt").readLines().map { it.toSeat() }
    part1(input)
    part2(input)
}

private fun String.toSeat() : Seat {
    var a = 0
    var b = 128
    for (i in 0..6){
        val tempA = when (this[i]){
            'F' -> a
            'B' -> a + ((b - a) / 2)
            else -> throw IllegalArgumentException()
        }
        val tempB = when (this[i]){
            'F' -> b - ((b - a) / 2)
            'B' -> b
            else -> throw IllegalArgumentException()
        }
        a = tempA
        b = tempB
    }
    var c = 0
    var d = 8
    for (i in 7..9){
        val tempC = when (this[i]){
            'L' -> c
            'R' -> c + ((d - c) / 2)
            else -> throw IllegalArgumentException()
        }
        val tempD = when (this[i]){
            'L' -> d - ((d - c) / 2)
            'R' -> d
            else -> throw IllegalArgumentException()
        }
        c = tempC
        d = tempD
    }
    return Seat(a, c)
}

data class Seat(val row: Int, val col: Int, val seatId: Int = row * 8 + col)

private fun part1(input: List<Seat>) {
    println(input.maxBy { it.seatId }?.seatId)
}

private fun part2(input: List<Seat>) {
    println((0..127).flatMap { row -> (0..7).map { col -> Seat(row, col) } }
            .find { emptySeat -> emptySeat !in input
                    && input.any { it.seatId == emptySeat.seatId - 1 }
                    && input.any { it.seatId == emptySeat.seatId + 1 } }?.seatId)
}