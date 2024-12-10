import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger

typealias Grid = MutableList<MutableList<String>>

fun main() {


    fun parseInput(input: List<String>): Grid = input.map {
        it.split("").filterNot { it.isEmpty() }.toMutableList()
    }.toMutableList()

    data class Move(
        val direction: String,
        val xMove: Int,
        val yMove: Int,
        val turn: String,
        val value: String,
    )

    val player = listOf(
        Move("^", 0, -1, ">", "U"),
        Move("<", -1, 0, "^", "L"),
        Move(">", 1, 0, "!", "R"),
        Move("!", 0, 1, "<", "D")
    )

    tailrec fun move(grid: Grid, grid2: Grid): Grid? {
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                val move = player.firstOrNull {
                    it.direction.any { it.toString() == grid[y][x] }
                }
                if (move != null) {
                    if (x + move.xMove >= grid[y].size || x + move.xMove < 0 || y + move.yMove >= grid.size || y + move.yMove < 0) {
                        grid[y][x] = "X"
                        grid2[y][x] = move.value
                        return grid
                    } else {
                        if (grid[y + move.yMove][x + move.xMove] == "#") {
                            grid[y][x] = move.turn
                            return move(grid, grid2)
                        }
                        grid[y][x] = "X"
                        if (grid2[y][x].split("").contains(move.value)) {
                            return null
                        } else {
                            grid2[y][x] = "${grid2[y][x]}${move.value}"
                        }

                        grid[y + move.yMove][x + move.xMove] = move.direction
                        return move(grid, grid2)
                    }
                }
            }
        }
        return grid
    }

    fun part1(input: List<String>): Int {
        val parseInput = parseInput(input)
        val grid2 = parseInput(input)
        val move = move(parseInput, grid2)
        return move!!.flatten().filter { it == "X" }.size
    }

    fun part2(input: List<String>): Int {
        val parseInput = parseInput(input)
        var counter = AtomicInteger(0)
        // Ok... lets brute force
        runBlocking {
            for (y in parseInput.indices) {
                for (x in parseInput[y].indices) {
                    async(Dispatchers.Default) {
                            if (parseInput[y][x] != "#") {
                                val i = parseInput(input)
                                val i2 = parseInput(input)
                                i[y][x] = "#"
                                i2[y][x] = "#"
                                val move = move(i, i2)
                                if (move == null) {
                                    counter.incrementAndGet()
                                }
                            }
                    }
                }
            }
        }
        return counter.get()
    }

        // Or read a large test input from the `src/Day01_test.txt` file:
        val testInput = readInput("Day06_test")
        check(part1(testInput) == 41)
//        check(part2(testInput) == 6)

        // Read the input from the `src/Day01.txt` file.
        val input = readInput("Day06")
        part1(input).println()
        part2(input).println()
    }
