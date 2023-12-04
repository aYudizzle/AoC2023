import d3.Board
import d3.FieldContentNumber
import d3.allSymbols
import d3.numbersSurrounding

fun main() {
    val d = Day03()
    println(d.part1())
    println(d.part2())
}

class Day03 {
    private val input = readInput("Day03")

    fun part1() = with(Board(input)) {
        allSymbols.flatMap { (symbolPosition, _) ->
            numbersSurrounding(symbolPosition)
        }.sumOf { it.num }
    }

    fun part2() =  with(Board(input)) {
        allSymbols
            .filter { (_, symbol) -> symbol.symbol == '*' }
            .map { (symbolPosition, _) ->
                numbersSurrounding(symbolPosition)
            }.filter { it.size == 2 }
            .sumOf {
                // somehow compile needs it, but idea thinks it's not needed
                it.fold<FieldContentNumber, Int>(1) { a, b ->
                    a * b.num
                }
            }
    }

}

data class Number(
    val value: Int,
    val x: Int,
    val y: Int,
)