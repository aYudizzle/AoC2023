import java.lang.Integer.max

fun main() {
    val d = Day02()
    println(d.part1())
    println(d.part2())
}

class Day02 {
    val input = readInput("Day02")
    fun part1(): Int {
        return input.sumOf {input ->
            input.substringAfter(": ").split("; ").map { set ->
                set.split(", ").map { cubes ->
                    val n = cubes.substringBefore(" ").toInt()
                    val color = cubes.substringAfter(" ")
                    if(color == "red" && n > 12 || color == "green" && n > 13 || color == "blue" && n > 14) return@sumOf 0
                }
            }
            input.substringAfter(" ").substringBefore(":").toInt()
        }
    }

    fun part2(): Int {
        return input.sumOf { input ->
            val max = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
            input.substringAfter(": ").split("; ").forEach { set ->
                set.split(", ").map {cubes ->
                    val color = cubes.substringAfter(" ")
                    max[color] = max(max[color] ?: 0, cubes.substringBefore(" ").toInt())
                }
            }
            max["red"]!! * max["green"]!! * max["blue"]!!
        }
    }
}