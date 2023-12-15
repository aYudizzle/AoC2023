import kotlin.math.min

fun main() {
    val d = Day13()
    println(d.part1())
    println(d.part2())
}

class Day13 {
    val input = readInput("Day13")
    fun scan(grid: List<String> = input): Int? {
        return generateSequence(1 to emptyList<Int>()) { (start, list) ->
            if (start == -1) null else {
                val newList = if (grid[start] == grid[start - 1]) list.plusElement(start) else list
                val newStart = if (start == grid.lastIndex) -1 else start + 1
                newStart to newList
            }
        }.last().second.firstOrNull { foldIndexPlusOne ->
            val offset = min(grid.size - foldIndexPlusOne, foldIndexPlusOne) - 1
            (1..offset).all { shift ->
                val rightIndex = foldIndexPlusOne + shift
                val leftIndex = foldIndexPlusOne - 1 - shift
                grid[leftIndex] == grid[rightIndex]
            }
        }
    }

    fun strDiff(a: String, b: String) =
        a.zip(b).count { it.first != it.second }


    fun scanDefect(grid: List<String> = input): Int? {
        return generateSequence(1 to emptyList<Int>()) { (start, list) ->
            if (start == -1) null else {
                val newList = if (strDiff(grid[start], grid[start - 1]) <= 1) list.plusElement(start) else list
                val newStart = if (start == grid.lastIndex) -1 else start + 1
                newStart to newList
            }
        }.last().second.firstOrNull { foldIndexPlusOne ->
            val offset = min(grid.size - foldIndexPlusOne, foldIndexPlusOne) - 1
            (0..offset).sumOf { shift ->
                val rightIndex = foldIndexPlusOne + shift
                val leftIndex = foldIndexPlusOne - 1 - shift
                strDiff(grid[leftIndex], grid[rightIndex])
            } == 1
        }
    }

    fun transpose(lines: List<String> = input): List<String> {
        return (0..lines[0].lastIndex).fold(emptyList<String>()) { acc, i ->
            acc.plusElement(lines.map { it[i] }.joinToString(""))
        }
    }

    fun countMirrors(mirrors: List<Pair<Int, Int>>) =
        (100 * mirrors.sumOf { it.first } + mirrors.sumOf { it.second })


    fun loadGrids(lines: List<String> = input) = lines.fold(listOf(emptyList<String>())) { acc, s ->
        if (s.isEmpty()) {
            acc.plusElement(emptyList())
        } else {
            acc.dropLast(1).plusElement(acc.last() + listOf(s))
        }
    }


    fun part1(lines: List<String> = input): Long {
        val mirrors = loadGrids(lines).map { grid ->
            (scan(grid) ?: 0) to (scan(transpose(grid)) ?: 0)
        }
        return countMirrors(mirrors).toLong()
    }


    fun part2(lines: List<String> = input): Long {
        val mirrors = loadGrids(lines).map { grid ->
            (scanDefect(grid) ?: 0) to (scanDefect(transpose(grid)) ?: 0)
        }
        return countMirrors(mirrors).toLong()
    }
}
