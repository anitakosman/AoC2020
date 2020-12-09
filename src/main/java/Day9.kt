import java.io.File

fun main() {
    val input = File("src/main/resources/day9.txt").readLines().map { it.toLong() }
    part1(input)
    part2(input)
}

var invalidNumber = 0L

private fun part1(input: List<Long>) {
    invalidNumber = input.withIndex().drop(25).first { !isValid(input.subList(it.index - 25, it.index), it.value) }.value
    println(invalidNumber)
}

fun isValid(l: List<Long>, v: Long): Boolean {
    for (i in 0..24) {
        val n = l[i]
        for (m in l.drop(i)){
            if (m != n && m + n == v)
                return  true
        }
    }
    return false
}

private fun part2(input: List<Long>) {
    for (i in input.indices){
        var j = i + 1
        var sum = input[i] + input[j]
        while (j < input.size && sum < invalidNumber){
            j++
            sum += input[j]
        }
        if (sum == invalidNumber) {
            val subList = input.subList(i, j + 1)
            println(subList.min()!! + subList.max()!!)
            return
        }
    }
}