import java.io.File

fun main() {
    val input = mutableSetOf<Triple<Int, Int, Int>>()
    File("src/main/resources/day17.txt").readLines().forEachIndexed() { y, line ->
        line.forEachIndexed { x, c -> if (c == '#') input.add(Triple(x, y, 0)) }
    }
    part1(input)
    part2(input)
}

private fun part1(input: Set<Triple<Int, Int, Int>>) {
    var cubes = input
    repeat(6) { cubes = live(cubes) }
    println(cubes.size)
}

fun live(cubes: Set<Triple<Int, Int, Int>>): Set<Triple<Int, Int, Int>> {
    val newCubes = mutableSetOf<Triple<Int, Int, Int>>()
    val minX = cubes.minByOrNull { it.first }!!.first
    val maxX = cubes.maxByOrNull { it.first }!!.first
    val minY = cubes.minByOrNull { it.second }!!.second
    val maxY = cubes.maxByOrNull { it.second }!!.second
    val minZ = cubes.minByOrNull { it.third }!!.third
    val maxZ = cubes.maxByOrNull { it.third }!!.third

    for (x in (minX - 1)..(maxX + 1)){
        for (y in (minY - 1)..(maxY + 1)){
            for (z in (minZ - 1)..(maxZ + 1)){
                val cube = Triple(x, y, z)
                val n = neighbors(cube, cubes)
                if (n == 3 || (n == 2 && cube in cubes)){
                    newCubes.add(cube)
                }
            }
        }
    }
    return newCubes
}

fun neighbors(cube: Triple<Int, Int, Int>, cubes: Set<Triple<Int, Int, Int>>): Int {
    return (-1..1).flatMap { dx -> (-1..1).map { dy -> (-1..1).count { dz ->
        (dx != 0 || dy != 0 || dz != 0) && Triple(cube.first + dx,cube.second + dy, cube.third + dz) in cubes
    } }}.sum()
}

private fun part2(input: Set<Triple<Int, Int, Int>>) {
    var cubes = input.map { Quadruplet(it.first, it.second, it.third, 0) }.toSet()
    repeat(6) { cubes = liveFourDimensions(cubes) }
    println(cubes.size)
}

data class Quadruplet <T1, T2, T3, T4> (val first: T1, val second: T2, val third: T3, val fourth: T4)

fun liveFourDimensions(cubes: Set<Quadruplet<Int, Int, Int, Int>>): Set<Quadruplet<Int, Int, Int, Int>> {
    val newCubes = mutableSetOf<Quadruplet<Int, Int, Int, Int>>()
    val minX = cubes.minByOrNull { it.first }!!.first
    val maxX = cubes.maxByOrNull { it.first }!!.first
    val minY = cubes.minByOrNull { it.second }!!.second
    val maxY = cubes.maxByOrNull { it.second }!!.second
    val minZ = cubes.minByOrNull { it.third }!!.third
    val maxZ = cubes.maxByOrNull { it.third }!!.third
    val minW = cubes.minByOrNull { it.fourth }!!.fourth
    val maxW = cubes.maxByOrNull { it.fourth }!!.fourth

    for (x in (minX - 1)..(maxX + 1)){
        for (y in (minY - 1)..(maxY + 1)){
            for (z in (minZ - 1)..(maxZ + 1)){
                for (w in (minW - 1)..(maxW + 1)) {
                    val cube = Quadruplet(x, y, z, w)
                    val n = neighbors(cube, cubes)
                    if (n == 3 || (n == 2 && cube in cubes)) {
                        newCubes.add(cube)
                    }
                }
            }
        }
    }
    return newCubes
}

fun neighbors(cube: Quadruplet<Int, Int, Int, Int>, cubes: Set<Quadruplet<Int, Int, Int, Int>>): Int {
    return (-1..1).flatMap { dx -> (-1..1).flatMap { dy -> (-1..1).map { dz -> (-1..1).count { dw ->
        (dx != 0 || dy != 0 || dz != 0 || dw != 0) && Quadruplet(cube.first + dx,cube.second + dy, cube.third + dz, cube.fourth + dw) in cubes
    } }}}.sum()
}