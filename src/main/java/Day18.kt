import java.io.File
import java.lang.IllegalStateException

fun main() {
    val input = File("src/main/resources/day18.txt").readLines()
    part1(input)
    part2(input)
}

private fun part1(input: List<String>) {
    println(input.map { eval(it, false) }.sum())
}

private fun part2(input: List<String>) {
    println(input.map { eval(it, true) }.sum())
}

const val parentheses = "\\(([^()]*)\\)"
const val addition = "(\\d+) \\+ (\\d+)"
const val multiplication = "(\\d+) \\* (\\d+)"
const val addOrMul = "(\\d+) ([+*]) (\\d+)"

private fun eval(s: String, precedence: Boolean): Long{
    var r = s
    var m = Regex(parentheses).find(r)
    while (m != null){
        r = r.replace(m.value, eval(m.groupValues[1], precedence).toString())
        m = Regex(parentheses).find(r)
    }
    if (precedence){
        m = Regex(addition).find(r)
        while (m != null){
            r = r.replaceFirst(m.value, (m.groupValues[1].toLong() + m.groupValues[2].toLong()).toString())
            m = Regex(addition).find(r)
        }
        m = Regex(multiplication).find(r)
        while (m != null){
            r = r.replaceFirst(m.value, (m.groupValues[1].toLong() * m.groupValues[2].toLong()).toString())
            m = Regex(multiplication).find(r)
        }
    } else {
        m = Regex(addOrMul).find(r)
        while (m != null){
            val a = m.groupValues[1].toLong()
            val b = m.groupValues[3].toLong()
            val n = when (m.groupValues[2]) {
                "+" -> a + b
                "*" -> a * b
                else -> throw IllegalStateException()
            }
            r = r.replaceFirst(m.value, n.toString())
            m = Regex(addOrMul).find(r)
        }
    }
    return r.toLong()
}