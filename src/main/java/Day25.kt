fun main() {
    val ga = 15113849L
    val gb = 4206373L
    val m = 20201227L
    hack(ga, gb, m)
}

fun hack(ga: Long, gb: Long, m: Long) {
    var i = 0
    var r = 1L
    while (r != ga && r != gb){
        r = r * 7 % m
        i++
    }
    r = if (r == ga) gb else ga
    var s = 1L
    repeat(i){
        s = s * r  % m
    }
    println(s)
}
