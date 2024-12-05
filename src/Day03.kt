fun main() {

    fun part1(input: List<String>): Int {
        val findAll = """(mul\(\d+,\d+\))""".toRegex().findAll(input.first())
        return findAll.map {
            val numbers = it.groupValues[0]
                .replace("mul(", "")
                .replace(")", "")
                .split(",")
                .map { it.toInt() }
            numbers[0] * numbers[1]
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val findAll = """((don't\(\))|(do\(\))|(mul\(\d+,\d+\)))""".toRegex().findAll(input.first())
        var doEnabled = true
        val result = mutableListOf<Int>()
        for(cg in findAll){
            val value = cg.groups[0]!!.value
            if(value == "don't()"){
                doEnabled = false
                continue
            } else if(value == "do()"){
                doEnabled = true
                continue
            }
            if(doEnabled){
                val numbers = value
                    .replace("mul(", "")
                    .replace(")", "")
                    .split(",")
                    .map { it.toInt() }
                result.add(numbers[0] * numbers[1])
            }
        }
        return result.sum()
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}