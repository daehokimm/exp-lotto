package me.daehokimm

import org.junit.Test
import kotlin.test.assertFails
import kotlin.test.assertTrue

class TicketTest {

    @Test
    fun isWinner_win() {
        // given
        val winnerDraw = Draw(numberSet = setOf(1, 2, 3, 4, 5, 6))
        val ticket = Ticket()
        ticket.drawList = arrayListOf(winnerDraw)

        // when
        val isWon = ticket.isWinner(winnerDraw = winnerDraw)

        // then
        assertTrue { isWon }
    }

    @Test
    fun isWinner_fail() {
        // given
        val winnerDraw = Draw(numberSet = setOf(1, 2, 3, 4, 5, 6))
        val ticket = Ticket()
        ticket.drawList = arrayListOf(Draw(numberSet = setOf(1, 2, 3, 4, 5, 7)))    // 7

        // when
        val isWon = ticket.isWinner(winnerDraw = winnerDraw)

        // then
        assertTrue { !isWon }
    }

    @Test
    fun initTicket_isRenew() {
        // given
        val ticket = Ticket()
        val oldDraw = ticket.drawList.clone()

        // when
        ticket.initTicket()
        val newDraw = ticket.drawList

        assertTrue { oldDraw != newDraw }
    }
}