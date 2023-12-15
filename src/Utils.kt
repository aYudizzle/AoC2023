import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/input/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
fun lcm(x: Long, y: Long): Long = x / gcd(x, y) * y
fun gcd(x: Long, y: Long): Long {
    var a = x
    var b = y
    while (b != 0L) a = b.also { b = a.mod(b) }
    return a
}

fun String.blankSplit(): List<String> = split("\n\n")

data class Position(val row: Long, val column: Long) {
    constructor(row: Int, column: Int) : this(row.toLong(), column.toLong())

    val rowBefore: Sequence<Position> = (0..<column).asSequence().map { Position(row, it) }
    val columnBefore: Sequence<Position> = (0..<row).asSequence().map { Position(it, column) }

    operator fun plus(delta: Position) = Position(row + delta.row, column + delta.column)
    operator fun minus(term: Position) = Position(row - term.row, column - term.column)
    operator fun unaryMinus() = Position(-row, -column)
    operator fun times(f: Int) = times(f.toLong())
    operator fun times(f: Long) = Position(row * f, column * f)
    fun manhattanDistanceTo(o: Position) = abs(row - o.row) + abs(column - o.column)
    override fun toString(): String = "($row,$column)"

    companion object {
        val zero: Position = Position(0L, 0L)
    }
}

enum class DirectionE(val delta: Position) {
    N(Position(-1, 0)), E(Position(0, +1)), S(Position(+1, 0)), W(Position(0, -1)),
    NE(Position(-1, +1)), SE(Position(+1, +1)), NW(Position(-1, -1)), SW(Position(+1, -1));

    val inverse: DirectionE
        get() = when (this) {
            N -> S
            E -> W
            S -> N
            W -> E
            NE -> SW
            SE -> NW
            NW -> SE
            SW -> NE
        }
}
