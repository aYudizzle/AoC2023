package d3

data class Board(
    private val content: Map<Point2D, FieldContent>
) : Map<Point2D, FieldContent> by content {
    companion object {
        private val digit = "(\\d)+".toRegex()
        private fun String.extractNumbersInRow(y: Int) =
            digit.findAll(this).flatMap {
                val positions = it.range.map { x -> Point2D(x = x, y = y) }
                val number = FieldContentNumber(it.value.toInt(), positions)
                positions.map { pos -> pos to number }
            }

        private fun String.extractSymbolsInRow(y: Int) =
            withIndex()
                .filter { !it.value.isDigit() && it.value != '.' }
                .map { (x, c) -> Point2D(x = x, y = y) to FieldContentSymbol(c) }

        operator fun invoke(input: List<String>): Board =
            input.asSequence()
                .withIndex()
                .flatMap { (y, line) ->
                    line.extractNumbersInRow(y) + line.extractSymbolsInRow(y)
                }.toMap()
                .let(::Board)
    }
}

val Board.allSymbols: List<Pair<Point2D, FieldContentSymbol>>
    get() = filterValues { it is FieldContentSymbol }
        .map { it.key to it.value as FieldContentSymbol }

fun Board.numbersSurrounding(position: Point2D) =
    position.neighborHood
        .mapNotNull { pos -> this[pos] }
        .filterIsInstance<FieldContentNumber>()
        .toSet()


sealed interface FieldContent
data class FieldContentNumber(val num: Int, val positions: List<Point2D>): FieldContent
data class FieldContentSymbol(val symbol: Char): FieldContent

data class Point2D(val x: Long, val y: Long) {
    constructor(x: Int, y: Int): this(x.toLong(), y.toLong())
    operator fun plus(other: Point2D) = Point2D(x + other.x, y + other.y)
    operator fun minus(other: Point2D) = Point2D(x - other.x, y - other.y)
    val cardinalNeighbors: List<Point2D>
        get() = listOf(UP, DOWN, LEFT, RIGHT).map { it + this }

    val neighborHood: List<Point2D>
        get() = cardinalNeighbors +
                listOf(UP + LEFT, UP + RIGHT, DOWN + LEFT, DOWN + RIGHT).map { it + this }
    companion object {
        val UP = Point2D(0, -1)
        val DOWN = Point2D(0, 1)
        val LEFT = Point2D(-1, 0)
        val RIGHT = Point2D(1, 0)
    }
}