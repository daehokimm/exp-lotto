package me.daehokimm

import java.io.File

const val TOTAL_EXPERIMENT_TIME = 1_000
const val PROGRESS_UNIT = 100_000
const val CSV_DELIMITER = ","

fun main() {
    val file = File("result/exp-${TOTAL_EXPERIMENT_TIME}_${System.currentTimeMillis()}.csv")
    val printWriter = file.printWriter()
    printWriter.write("DRAWING RESULT\n")
    printWriter.write(getLine("exp", "static", "dynamic"))

    val staticStat = Statistic()
    val dynamicStat = Statistic()

    for (exp in 1..TOTAL_EXPERIMENT_TIME) {

        // 1. init tickets
        val staticTicket = Ticket()
        val dynamicTicket = Ticket()

        // 2. drawing
        var drawingCount: Long = 0
        while (true) {
            drawingCount++
            if (drawingCount % PROGRESS_UNIT == 0L)
                print(".")

            // 2.1. winning
            val winnerDraw = Draw()
            if (!staticTicket.hasWon) {
                staticTicket.isWinner(winnerDraw)
            }
            if (!dynamicTicket.hasWon) {
                dynamicTicket.initTicket()
                dynamicTicket.isWinner(winnerDraw)
            }

            // 2.2. statistic
            if (staticTicket.hasWon && dynamicTicket.hasWon) {
                val staticTime = staticTicket.drawingTime
                val dynamicTime = dynamicTicket.drawingTime

                // calculate max, min
                staticStat.calculate(staticTime)
                dynamicStat.calculate(dynamicTime)

                // calculate lower count
                when {
                    staticTime < dynamicTime -> staticStat.lowerCount++
                    dynamicTime < staticTime -> dynamicStat.lowerCount++
                }

                break
            }
        }
        println()

        // 3. result
        println("========== #$exp RESULT ==========")
        println("STATIC  : ${staticTicket.drawingTime}")
        println("DYNAMIC : ${dynamicTicket.drawingTime}")
        printWriter.write(getLine(exp.toString(), staticTicket.drawingTime, dynamicTicket.drawingTime))
    }

    printWriter.write("\n")
    printWriter.write("STATISTIC RESULT\n")
    printWriter.write("type,lower,max,min\n")
    printWriter.write(getStatisticLine("static", staticStat))
    printWriter.write(getStatisticLine("dynamic", dynamicStat))

    printWriter.flush()
    printWriter.close()
}

private fun getStatisticLine(type: String, statistic: Statistic) =
    type + CSV_DELIMITER + statistic.lowerCount + CSV_DELIMITER + statistic.maxCount + CSV_DELIMITER + statistic.minCount + "\n"

fun getLine(exp: String, static: String, dynamic: String) =
    exp + CSV_DELIMITER + static + CSV_DELIMITER + dynamic + "\n"

fun getLine(exp: String, static: Long, dynamic: Long) = getLine(exp, static.toString(), dynamic.toString())
