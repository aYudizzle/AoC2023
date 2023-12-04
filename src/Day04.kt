import kotlin.math.pow

fun main() {
    val d = Day04()
    println(d.part1())
    println(d.part2())

}

class Day04 {
//    private val input = readInput("Day04_test")
    private val input = readInput("Day04")

    private val powNo = 2.0

    fun part1() =
        input.sumOf { input ->
            val winningNumbers = input.substringBefore("|").substringAfter(": ").chunked(3).map { it.trim().toInt() }
            val drawnNumbers =
                input.substringAfter("|").chunked(3).map { it.trim().toInt() }.count { it in winningNumbers }
            powNo.pow(drawnNumbers - 1).toInt()
        }

    fun part2(): Int {
        val scoredCards = input.mapIndexed { index, input ->
            val winningNumbers = input.substringBefore("| ").substringAfter(": ").chunked(3).map { it.trim().toInt() }
            (index + 1) to input.substringAfter("|").chunked(3).map { it.trim().toInt() }
                .count() { it in winningNumbers }
        }
        var counter = 0
        val winningCards = scoredCards.map { it.first }.toMutableList()
        while (winningCards.isNotEmpty()) {
            val index = winningCards.first()
            val cardsCounter = winningCards.count { it == index }
            winningCards.removeAll { it == index }
            val copies = scoredCards.drop(index).take(scoredCards.first {
                it.first == index
            }.second).map {
                it.first
            }
            winningCards += List(cardsCounter) { copies }.flatten()
            counter += cardsCounter
        }
        return counter
    }
}
