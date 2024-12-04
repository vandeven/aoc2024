import kotlin.math.max
import kotlin.math.min

fun main() {
    fun parseInput(input: List<String>): List<List<Int>> = input.map {
        it.split(" ").map { it.toInt() }
    }

    fun part1(input: List<String>): Int {
        val parseInput = parseInput(input)
        return parseInput.filter { a ->
            a.windowed(2).all {
                val difference = max(it[0], it[1]) - min(it[0], it[1])
                difference in 1..3
            }
        }.filter {
            it.sorted() == it || it.sortedDescending() == it
        }.size
    }

    fun part2(input: List<String>): Int = parseInput(input)
        .filter { a ->
            // Map to a list of lists containing a list with 1 number missing for each position
            List(a.size) { i -> a.filterIndexed { i2, _ -> i != i2 } }
                .any { c: List<Int> ->
                    c.windowed(2).all { d ->
                        val difference = max(d[0], d[1]) - min(d[0], d[1])
                        difference in 1..3
                    } && (c.sorted() == c || c.sortedDescending() == c)
                }
        }.size

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}