fun main() {
    val d = Day06()
    println(d.part1())
    println(d.part2())

}

class Day06 {
    private val input = readInput("Day06")

    fun part1(): Int {
        return input.map { line -> line.split(" ").mapNotNull { it.toIntOrNull() } }.let { (time, distance) ->
            time.zip(distance)
        }.map { (time, distance) ->
            (0..time).map {
                (time - it) * it
            }.count() { it > distance }
        }.reduce {
            acc, i -> acc * i
        }
    }

    fun part2(): Int {
        val (time, distance) = input.map { it.substringAfter(" ").replace(" ","").toLong() }
        return (0..time).map { (time - it) * it }.count { it > distance }
    }
}