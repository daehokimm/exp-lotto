package me.daehokimm

import kotlin.random.Random

const val TICKET_DRAW_SIZE = 5
const val DRAW_NUMBER_SIZE = 6

data class Ticket(
    var drawList: ArrayList<Draw> = arrayListOf(),
    var drawingTime: Long = 0,
    var hasWon: Boolean = false
) {
    init {
        initTicket()
    }

    fun initTicket() {
        this.drawList.clear()
        repeat(TICKET_DRAW_SIZE) { drawList.add(Draw()) }
    }

    fun isWinner(winnerDraw: Draw): Boolean {
        drawingTime++
        for (ns in drawList) {
            if (ns == winnerDraw) {
                hasWon = true
                return true
            }
        }
        return false
    }
}

data class Draw(
    val numberSet: Set<Int> = generateNumberSet()
)

fun generateNumberSet(): Set<Int> {
    val numberSet = HashSet<Int>()
    while (numberSet.size < DRAW_NUMBER_SIZE) {
        val number = generateNumber()
        if (!numberSet.contains(number))
            numberSet.add(number)
    }
    return numberSet
}

private fun generateNumber(): Int {
    return Random.nextInt(45) + 1
}