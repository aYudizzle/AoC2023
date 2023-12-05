fun main() {
    val d = Day05()
    println(d.part1())
    println(d.part2())

}

class Day05 {
//    private val input = readInput("Day05_test")
    private val input = readInput("Day05")

    fun part1(): Long {
        val seeds = getListOfSeeds()

        val fertilizerMaps = getFertilizerMap()

        return seeds.minOf { seed ->
            fertilizerMaps.fold(seed) { acc, map ->
                map.entries.firstOrNull() {
                    acc in it.key
                }?.let { (source, destination) ->
                    destination.first + (acc - source.first) } ?: acc
            }
        }
    }

    fun part2(): Long {
        val seeds = input.first().substringAfter(" ").split(" ").map { it.toLong() }.chunked(2).map {
            it.first()..<it.first() + it.last()
        }

        val fertilizerMaps = getFertilizerMap()
        return seeds.flatMap { seedsRange ->
            fertilizerMaps.fold(listOf(seedsRange)) { acc, map ->
                acc.flatMap { getOutputRanges(map, it) }
            }
        }.minOf { it.first }

    }

    private fun getListOfSeeds(): List<Long> {
        return input.first().substringAfter(" ").split(" ").map { it.toLong() }
    }

    private fun getFertilizerMap(): List<Map<LongRange,LongRange>> {
        return input.drop(2).joinToString("\n").split("\n\n").map { fertilizerMap ->
            fertilizerMap.lines().drop(1).associate {
                it.split(" ")
                    .map { stringNumber -> stringNumber.toLong() }
                    .let { (destination, source, length) ->
                        val key = source..(source + length)
                        val value = destination..(destination + length)
                        key to value
                    }
            }
        }
    }
}

fun getOutputRanges(map:Map<LongRange,LongRange>, input: LongRange): List<LongRange> {
    val mappedInputRange = mutableListOf<LongRange>()
    val outputRanges = map.entries.mapNotNull { (source, destination) ->
        val start = maxOf(source.first, input.first)
        val end = minOf(source.last, input.last)
        if(start <= end) {
            mappedInputRange += start..end
            (destination.first - source.first).let { (start + it)..(end + it) }
        } else null
    }
    val cuts = listOf(input.first) + mappedInputRange.sortedBy { it.first }.flatMap { listOf(it.first, it.last) } + listOf(input.last)
    val unmappedInputRanges = cuts.chunked(2).mapNotNull { (first, second) ->
        if(second>first) {
            if(second == cuts.last()) first..second else first ..< second
        } else null
    }
    return outputRanges + unmappedInputRanges
}