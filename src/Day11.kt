import kotlin.math.abs

fun main() {
    val d = Day11()

    println(d.distances(d.galaxies(2)))
    println(d.distances(d.galaxies(1000000)))
}

class Day11 {
    private val input = readInput("Day11")

    private val rowsToExpand = input.indices.filter { y -> '#' !in input[y] }
    private val colsToExpand = input[0].indices.filter { x -> input.none { it[x] == '#' } }

    private fun makePoint(x: Int, y: Int, e: Long) = PointL(
        x.toLong() + colsToExpand.count { x > it } * (e - 1),
        y.toLong() + rowsToExpand.count { y > it } * (e - 1)
    )

    fun galaxies(e: Long) = input.flatMapIndexed { y, line ->
        line.mapIndexedNotNull { x, char -> if (char == '#') makePoint(x, y, e) else null }
    }

    private fun allPairs(points: Iterable<PointL>) =
        points.flatMap { a -> points.map { b -> a to b } }


    private fun distance(a: PointL, b: PointL) = abs(b.x - a.x) + abs(b.y - a.y)
    fun distances(points: Iterable<PointL>) = allPairs(points).sumOf { (a, b) -> distance(a, b) } /2
}

data class PointL(val x: Long, val y: Long)