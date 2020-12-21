import java.io.File
import kotlin.math.sqrt

fun main() {
    val input = File("src/main/resources/day20.txt").readText().split("\r\n\r\n").map { block ->
        var lines = block.lines()
        val id = lines[0].drop(5).dropLast(1).toInt()
        lines = lines.drop(1)
        val up = lines.first()
        val down = lines.last()
        val left = lines.joinToString("") { it.first().toString() }
        val right = lines.joinToString("") { it.last().toString() }
        val img = lines.drop(1).dropLast(1).map { it.drop(1).dropLast(1) }
        Img(id, up, right, down, left, img)
    }
    val n = sqrt(input.size.toDouble()).toInt()
    val p = part1(input, n)
    part2(p, n)
}

data class Img(val id: Int, val up: String, val right: String, val down: String, val left: String, val img: List<String>) {
    val placements by lazy { listOf(this, this.turn(), this.turn().turn(), this.turn().turn().turn(),
    this.flip(), this.flip().turn(), this.flip().turn().turn(), this.flip().turn().turn().turn()) }

    private fun flip() = Img(id, up.reversed(), left, down.reversed(), right, img.flipImg())
    private fun turn() = Img(id, left.reversed(), up, right.reversed(), down, img.turnImg())
}

private fun List<String>.turnImg(): List<String> {
    return this.indices.map { i ->
        this.reversed().map { it[i] }.joinToString("")
    }
}

private fun List<String>.flipImg(): List<String> {
    return this.map { it.reversed() }
}

private fun part1(input: List<Img>, n: Int): List<Img> {
    val p = puzzle(emptyList(), input, n)!!
    println(p[0].id.toLong() * p[n-1].id * p[n*n - 1].id * p[n*n - n].id)
    return p
}

fun puzzle(p: List<Img>, tiles: List<Img>, n: Int): List<Img>? {
    if (tiles.isEmpty())
        return p
    tiles.forEach { tile ->
        tile.placements.forEach {
            if (fits(p, it, n)){
                val r = puzzle(p.plusElement(it), tiles.minusElement(tile), n)
                if (r != null)
                    return r
            }
        }
    }
    return null
}

fun fits(p: List<Img>, tile: Img, n: Int): Boolean {
    val i = p.size
    val toTheLeft = if (i % n != 0) p.getOrNull(i - 1) else null
    if (toTheLeft != null && toTheLeft.right != tile.left)
        return false
    val above = p.getOrNull(i - n)
    if (above != null && above.down != tile.up)
        return false
    return true
}

private fun part2(input: List<Img>, n: Int) {
    var image = (0 until n).flatMap { row ->
        val imgs = (0 until n).map { col ->
            input[row * n + col].img
        }
        imgs[0].indices.map { i -> imgs.joinToString("") {it[i]} }
    }
    if (checkRotations(image))
        return
    image = image.flipImg()
    checkRotations(image)
}

private fun checkRotations(input: List<String>): Boolean {
    var image = input
    repeat(4) {
        image = image.turnImg()
        val seaMonsters = countSeaMonsters(image)
        if (seaMonsters > 0) {
            println(image.joinToString("\n"))
            println(image.sumBy { line -> line.count { it == '#' } } - seaMonsters * 15)
            return true
        }
    }
    return false
}

fun countSeaMonsters(image: List<String>): Int {
    val matches = seaMonsterRegex.findAll(image.joinToString("\n")).toList()
    val filtered = matches.filter { match ->
        match.groupValues.drop(1).distinctBy { it.length }.count() == 1
    }.toList()
    filtered.forEach {
        val i = it.groups[0]!!.range.first
        val i1 = image[0].length + 1
        println("(${i / i1}, ${i % i1})")
    }
    return filtered.count()
}

val seaMonsterRegex = Regex("(?=(.*).{19}#.*\\n(.*)#....##....##....###.*\\n(.*).#..#..#..#..#..#)")