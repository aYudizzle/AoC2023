fun main() {
    val numbersAsWords: Map<String, Int> = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun part1(input: List<String>): Int {
        return input.sumOf { calibrationValue(it) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { row ->
            calibrationValue(
                row.mapIndexedNotNull { index, char ->
                    if (char.isDigit()) {
                        char
                    } else {
                        row.possibleWordsAt(index).firstNotNullOfOrNull { candidate ->
                            numbersAsWords[candidate]
                        }
                    }
                }.joinToString()
            )
        }
    }
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

private fun calibrationValue(row: String): Int {
    val firstDigit = row.first { it.isDigit() }
    val lastDigit = row.last { it.isDigit() }
    return "$firstDigit$lastDigit".toInt()
}

private fun String.possibleWordsAt(startingAt: Int): List<String> =
    (3..5).map { len ->
        substring(startingAt, (startingAt + len).coerceAtMost(length))
    }