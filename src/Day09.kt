fun main() {
    val d = Day09()
    println(d.part1())
    println(d.part2())

}

class Day09 {
    private val input = readInput("Day09")

    fun part1(): Int {
        return input.sumOf { line ->
            val sequences = mutableListOf(line.split(" ").map {
                it.toInt()
            })
            while (sequences.last().any { it != 0 }) {
                sequences += sequences.last().windowed(2).map { (a, b) ->
                    b - a
                }
            }
            sequences.sumOf { it.last() }
        }
    }

    fun part2(): Int {
        return input.sumOf { line ->
            val sequences = mutableListOf(line.split(" ").map {
                it.toInt()
            })
            while (sequences.last().any { it != 0 }) {
                sequences += sequences.last().windowed(2).map { (a, b) -> b - a }
            }
            sequences.map { it.first() }.reversed().reduce {  acc, i ->  i - acc }
        }
    }
}