
fun main() {
    val d = Day15()
    println(d.part1())
    println(d.part2())
}
class Day15 {
    val input = readInput("Day15")

    fun part1() : Int {
        return input.first().split(",").map { text ->
            text.fold(0) { acc, char ->
                ((acc + char.code) * 17) % 256
            }
        }.sum()
    }

    fun part2() : Int {
        val boxes = MutableList(256) { mutableListOf<Lens>() }
        input.first().split(",").forEach { step ->
            val label = step.substringBefore("=").substringBefore("-")
            val hash = label.fold(0) { acc, char -> ((acc + char.code) * 17) % 256 }
            if('=' in step) {
                val lens = Lens(label, step.substringAfter("=").toInt())
                val index = boxes[hash].indexOfFirst { it.label == label }
                if(index > -1) boxes[hash][index] = lens else boxes[hash] += lens
            } else  {
                boxes[hash].removeIf { it.label == label }
            }
        }
        return boxes.flatMapIndexed { boxIndex, box ->
            box.mapIndexed { lensIndexed, lens ->
                (1+boxIndex) * (lensIndexed + 1) * lens.focal
            }
        }.sum()
    }
}

data class Lens(val label: String, val focal: Int)