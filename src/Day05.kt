fun main() {

    data class Input(val rules: List<Pair<Int, Int>>, val updates: List<List<Int>>)

    fun parseInput(input: List<String>): Input {
        val rules =
            input.takeWhile { it.isNotBlank() }.map { it.split("|").map { it.toInt() } }.map { Pair(it[0], it[1]) }
        val updates = input.takeLastWhile { it.isNotBlank() }.map { it.split(",").map { it.toInt() } }
        return Input(rules, updates)
    }

    fun checkUpdate(update: List<Int>, rules: List<Pair<Int, Int>>): Boolean {
        return update.filterIndexed { i, u ->
            val previous = update.subList(0, i)
            val applicableRules = rules.filter { it.first == u }
            applicableRules.filter { previous.contains(it.second) }.size == 0
        }.size == update.size
    }

    fun order(update: List<Int>, rules: List<Pair<Int, Int>>) : List<Int>{
        if(checkUpdate(update, rules)){
            return update
        }
        update.indices.forEach { i ->
            val previous = update.subList(0, i)
            val applicableRules = rules.filter { it.first == update[i] }
            if(applicableRules.filter { previous.contains(it.second) }.isNotEmpty()){
                val i1 = update[i]
                val toMutableList = update.toMutableList()
                toMutableList.removeAt(i)
                toMutableList.add(i-1, i1)
                return order(toMutableList, rules)
            }
        }
        return emptyList()
    }

    fun part1(input: List<String>): Int {
        val parseInput = parseInput(input)
        return parseInput.updates.filter { checkUpdate(it, parseInput.rules) }
            .sumOf { it[Math.divideExact(it.size, 2)] }
    }

    fun part2(input: List<String>): Int {
        val parseInput = parseInput(input)
        return parseInput.updates.filterNot { checkUpdate(it, parseInput.rules) }
            .map { order(it, parseInput.rules) }.sumOf { it[Math.divideExact(it.size, 2)] }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
