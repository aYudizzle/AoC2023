fun main(){
    val d = Day12()

    d.part1().println()

    d.part2().println()
}

class Day12 {
    private val input = readInput("Day12")

    fun part1(): Long =
        input.map { s -> SpringConfig(s) }.sumOf { it.part1() }

    fun part2(): Long =
        input.map { s -> SpringConfig(s) }.map { it.unfold() }.sumOf { it.part1() }

    private fun SpringConfig(s: String) = s.split(' ').let { (base, groups) ->
        SpringConfig(base, groups.split(',').map { nr -> nr.toInt() })
    }


    data class SpringConfig(val s: String, val groups: List<Int>) {
        private val knownValues = mutableMapOf<Triple<Int, Int, Int>, Long>()
        private fun possibilities(idxS: Int, idxL: Int, groupRemainder: Int): Long =
            knownValues.getOrPut(Triple(idxS, idxL, groupRemainder)) { calculatePossibilities(idxS, idxL, groupRemainder) }

        private fun calculatePossibilities(idxS: Int, idxGroup: Int, groupRemainder: Int): Long {
            if (idxS >= s.length) return if (groupRemainder <= 0 && idxGroup == groups.size) 1 else 0
            return when (val next = s[idxS]) {
                SPR_WORKING -> if (groupRemainder <= 0) noActiveGroup(idxS, idxGroup) else 0
                SPR_BROKEN -> when {
                    groupRemainder < 0 -> startNewGroup(idxS, idxGroup)
                    groupRemainder == 0 -> 0
                    else -> continueCurrentGroup(idxS, idxGroup, groupRemainder)
                }

                SPR_UNKNOWN -> when {
                    groupRemainder < 0 -> startNewGroup(idxS, idxGroup) + noActiveGroup(idxS, idxGroup)
                    groupRemainder == 0 -> noActiveGroup(idxS, idxGroup)
                    else -> continueCurrentGroup(idxS, idxGroup, groupRemainder)
                }

                else -> {
                    error("Unexpected character: $next")
                }
            }
        }

        private fun noActiveGroup(idxS: Int, idxGroup: Int) =
            possibilities(idxS + 1, idxGroup, -1)

        private fun startNewGroup(idxS: Int, idxGroup: Int) =
            if (idxGroup >= groups.size) 0 else possibilities(idxS + 1, idxGroup + 1, groups[idxGroup] - 1)

        private fun continueCurrentGroup(idxS: Int, idxGroup: Int, groupRemainder: Int) =
            possibilities(idxS + 1, idxGroup, groupRemainder - 1)

        fun part1() = possibilities(0, 0, -1)
        fun unfold() = SpringConfig("$s?".repeat(5).dropLast(1), List(5) { groups }.flatten())
    }
}


private const val SPR_UNKNOWN = '?'
private const val SPR_BROKEN = '#'
private const val SPR_WORKING = '.'

