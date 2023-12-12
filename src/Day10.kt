//import Direction.*
//
//fun main() {
//    val d = Day10()
//    println(d.part1())
//    println(d.part2())
//
//}
//
//class Day10 {
//    private val input = readInput("Day10")
//
//
//    fun part1(): Int {
//        val pipes = mutableMapOf(
//            'S' to listOf(NORTH, EAST, SOUTH, WEST),
//            '|' to listOf(SOUTH, NORTH),
//            '-' to listOf(WEST, EAST),
//            'L' to listOf(NORTH, EAST),
//            'J' to listOf(NORTH, WEST),
//            '7' to listOf(SOUTH, WEST),
//            'F' to listOf(SOUTH, EAST),
//        )
//        val grid = mutableMapOf<Point, Char>()
//        input.forEachIndexed { y, row ->
//            row.forEachIndexed { x, c ->
//                grid[Point(x, y)] = c
//            }
//        }
//
//        val start = grid.entries.first { it.value == 'S' }.key
//        val unexplored = mutableListOf(start to 0)
//        val explored = mutableMapOf(start to 0)
//
//        while (unexplored.isNotEmpty()) {
//            val (current, distance) = unexplored.removeFirst()
//            explored[current] = distance
//            pipes[grid[current]]!!.forEach {
//                val point = current.move(it)
//                if (point !in explored.keys && point in grid.keys && it.reverse() in pipes[grid[point]]!!) {
//                    unexplored += point to (distance + 1)
//                }
//            }
//
//        }
//        return explored.values.max()
//    }
//
//    fun part2(): Int {
//        val grid = mutableMapOf<Point, Char>()
//        input.forEachIndexed { y, row ->
//            row.forEachIndexed { x, c ->
//                grid[Point(x, y)] = c
//            }
//        }
//        val pipes = mutableMapOf(
//            'S' to listOf(NORTH, EAST, SOUTH, WEST),
//            '|' to listOf(SOUTH, NORTH),
//            '-' to listOf(WEST, EAST),
//            'L' to listOf(NORTH, EAST),
//            'J' to listOf(NORTH, WEST),
//            '7' to listOf(SOUTH, WEST),
//            'F' to listOf(SOUTH, EAST),
//        )
//
//        val start = grid.entries.first { it.value == 'S' }.key
//        val unexplored = mutableListOf(start)
//        val explored = mutableSetOf<Point>()
//
//        while (unexplored.isNotEmpty()) {
//            val current = unexplored.removeFirst()
//            explored += current
//            pipes[grid[current]]!!.forEach {
//                val point = current.move(it)
//                if (point !in explored) {
//                    val pipe = grid[point]
//                    if (pipe != null && it.reverse() in pipes[pipe]!!) unexplored += point
//                }
//            }
//        }
//
//        val expandedGrid = mutableMapOf<Point, Char>()
//        grid.forEach { (point, char) ->
//            val expandedPoint = Point(point.x * 3, point.y * 3)
//            expandedGrid[expandedPoint] = if(char != '.' && point in explored) '#' else '.'
//            expandedPoint.getAdjacent().forEach {
//                expandedGrid[it] = '.'
//            }
//            if(point in explored) pipes[char]!!.forEach { expandedGrid[expandedPoint.move(it)] = '#'}
//        }
//        val toFlood = mutableListOf(Point.ORIGIN)
//        while(toFlood.isNotEmpty()) {
//            val current = toFlood.removeFirst()
//            expandedGrid[current] = '='
//            toFlood += current.getAdjacent().filter { expandedGrid[it] == '.' && it !in toFlood }
//        }
//        return grid.keys.count() { expandedGrid[Point(it.x * 3, it.y * 3)] == '.'}
//    }
//}