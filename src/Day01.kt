import kotlin.math.max
import kotlin.math.min

fun main() {
    fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> {
        return input.map {
            val split = it.split("   ")
            Pair(split[0].toInt(), split[1].toInt())
        }.fold(Pair(listOf(), listOf())) { acc, e ->
            Pair(acc.first.plus(e.first), acc.second.plus(e.second))
        }
    }

    fun part1(input: List<String>): Int {
        val parseInput = parseInput(input)
        return parseInput.first.sorted().zip(parseInput.second.sorted())
            .sumOf { max(it.first, it.second) - min(it.first, it.second) }
    }

    fun part2(input: List<String>): Int {
        val parseInput = parseInput(input)
        val second = parseInput.second.groupBy { it }
        return parseInput.first.sumOf { second.getOrElse(it) { listOf() }.size * it }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
