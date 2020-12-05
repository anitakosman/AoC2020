import java.io.File

fun main() {
    val input = File("src/main/resources/day4.txt").readText().split("\r\n\r\n").map { line ->
        Regex("(?:(?:byr:(?<byr>[^\\s]+)|iyr:(?<iyr>[^\\s]+)|eyr:(?<eyr>[^\\s]+)" +
            "|hgt:(?<hgt>[^\\s]+)|hcl:(?<hcl>[^\\s]+)|ecl:(?<ecl>[^\\s]+)|pid:(?<pid>[^\\s]+)" +
            "|cid:(?<cid>[^\\s]+))(\\s|\\r\\n)?)+").matchEntire(line)!!.groups.let {
            Credentials(it["byr"]?.value, it["iyr"]?.value, it["eyr"]?.value, it["hgt"]?.value,
                    it["hcl"]?.value, it["ecl"]?.value, it["pid"]?.value, it["cid"]?.value)
        }
    }
    part1(input)
    part2(input)
}

data class Credentials(val byr : String?, val iyr : String?, val eyr : String?, val hgt : String?,
                       val hcl : String?, val ecl : String?, val pid : String?, val cid : String?){
    val hasRequired = byr != null && iyr != null && eyr != null && hgt != null && hcl != null
            && ecl != null && pid != null
    val isValid = hasRequired && byr!!.matches(Regex("\\d{4}")) && byr.toInt() >= 1920
            && byr.toInt() <= 2002 && iyr!!.matches(Regex("\\d{4}")) && iyr.toInt() >= 2010
            && iyr.toInt() <= 2020 && eyr!!.matches(Regex("\\d{4}")) && eyr.toInt() >= 2020
            && eyr.toInt() <= 2030 && validHeight(hgt) && hcl!!.matches(Regex("#[0-9a-f]{6}"))
            && ecl!!.matches(Regex("(?:amb|blu|brn|gry|grn|hzl|oth)"))
            && pid!!.matches(Regex("\\d{9}"))

    private fun validHeight(hgt: String?): Boolean {
        if (hgt == null)
            return false
        val m = Regex("(?:(?<cm>\\d{3})cm|(?<in>\\d{2})in)").matchEntire(hgt)
        return (m?.groups?.get("cm")?.value?.toInt().let { it != null && it >= 150 && it <= 193 })
                || (m?.groups?.get("in")?.value?.toInt().let { it != null && it >= 59 && it <= 76 })
    }
}

private fun part1(input: List<Credentials>) {
    println(input.count(Credentials::hasRequired))
}

private fun part2(input: List<Credentials>) {
    println(input.count(Credentials::isValid))

}