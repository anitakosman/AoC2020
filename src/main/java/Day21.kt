import java.io.File

fun main() {
    val input = File("src/main/resources/day21.txt").readLines().map { line ->
        val ingredients = line.substringBefore(" (").split(" ").toSet()
        val allergens = line.substringAfter("contains ").dropLast(1).split(", ").toSet()
        Pair(ingredients, allergens)
    }
    val solved = solve(input)
    part1(input, solved)
    part2(solved)
}

private fun part1(input: List<Pair<Set<String>, Set<String>>>, solved: Map<String, String>){
    println(input.flatMap { it.first }.distinct().filter { it !in solved.values }.sumBy { ingredient -> input.count { ingredient in it.first } })
}

fun solve(input: List<Pair<Set<String>, Set<String>>>): Map<String, String> {
    val allergens = input.flatMap { it.second }.distinct()
    val possibilities = allergens.map { allergen -> allergen to input.filter { allergen in it.second }.map { it.first }.reduce(Set<String>::intersect)}.toMutableList()
    val m = mutableMapOf<String, String>()
    while (possibilities.isNotEmpty()){
        val next = possibilities.find { it.second.size == 1 }!!
        m[next.first] = next.second.first()
        possibilities.remove(next)
        possibilities.replaceAll { it.first to it.second.minusElement(next.second.first()) }
    }
    return m
}

private fun part2(input: Map<String, String>){
    println(input.entries.sortedBy { it.key }.joinToString(",") { it.value })
}