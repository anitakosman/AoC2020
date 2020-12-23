fun main() {
    val input = "589174263".map { it.toString().toInt() }
    part1(input)
    part2(input)
}

private fun part1(input: List<Int>) {
    var cup = cups(input, 100)
    repeat(8){
        print(cup.label)
        cup = cup.next!!
    }
    println()
}

private fun part2(input: List<Int>) {
    val oneCup = cups(input.plus((10..1000000)), 10000000)
    println(oneCup.label * oneCup.next!!.label)
}

private fun cups(input: List<Int>, moves: Int): LinkedCup {
    val circle = CupCircle(input)
    repeat(moves) { circle.move(); if (it % 500 == 0) println(it) }
    while (circle.current.label != 1){
        circle.next()
    }
    return circle.current.next!!
}

data class LinkedCup (val label: Int, var next: LinkedCup?)

class CupCircle(private val max: Int) {
    lateinit var current: LinkedCup
    constructor(list: List<Int>) : this(list.maxOrNull()!!) {
        val last = LinkedCup(list.last(), null)
        var previous = last
        list.reversed().drop(1).forEach {
            val cup = LinkedCup(it, previous)
            previous = cup
        }
        current = previous
        last.next = current
    }

    fun next() { current = current.next!! }

    fun move(){
        //println(current.label  )
        val one = current.next!!
        val two = one.next!!
        val three = two.next!!

        val next = three.next!!
        three.next = null
        current.next = next

        val n = current.label
        var dest = n - 1
        while (dest <= 0 || dest in listOf(one.label, two.label, three.label)){
            if (dest <= 0) dest = max else dest--
        }

        var cup = next
        while (cup.label != dest)
            cup = cup.next!!
        val destNext = cup.next
        cup.next = one
        three.next = destNext

        current = current.next!!
    }
}