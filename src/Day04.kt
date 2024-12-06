import kotlin.math.max

fun main() {
    fun getHorizontalLines(input : List<String>) = input.map { it.split("").filter { it.isNotBlank() } }


    fun getVerticalLines(input: List<List<String>>) : List<List<String>> {
        val result = mutableListOf<List<String>>()
        for(x in input[0].indices){
            val res = mutableListOf<String>()
            for(y in input.indices){
                val element = input[y][x]
                res.add(element)
            }
            result.add(res)
        }
        return result
    }

    fun getDiagonalLines(input: List<List<String>>) : List<List<String>> {
        val result = mutableListOf<List<String>>()
        val maxXSize = input[0].size - 1
        for(y in input.indices){
            val res = mutableListOf<String>()
            var x = 0
            for(d in y downTo max(y- maxXSize,0)){
                res.add(input[d][x])
                x++
            }
            result.add(res)
        }
        for(x in 1..maxXSize){
            val res = mutableListOf<String>()
            val maxY = input.size - 1
            var i = x
            for(d in maxY downTo max((maxY - maxXSize) + i,0)){
                res.add(input[d][i])
                i++
            }
            result.add(res)
        }
        return result
    }

    fun countXmas(input : List<List<String>>) = input.map { it.joinToString("") }.sumOf {
        it.windowed(4).count { it == "XMAS" || it == "SAMX" }
    }

    fun part1(input: List<String>): Int {
        val horizontalLines = getHorizontalLines(input)
        val verticalLines = getVerticalLines(horizontalLines)
        val diagonalLines = getDiagonalLines(horizontalLines)
        val diagonalLines2 = getDiagonalLines(getVerticalLines(horizontalLines.map { it.reversed()})).map { it.reversed() }
        val countXmas = countXmas(horizontalLines.plus(verticalLines).plus(diagonalLines).plus(diagonalLines2))
        return countXmas
    }


    fun part2(input: List<String>): Int {
        val horizontalLines = getHorizontalLines(input)
        var counter = 0
        for(y in horizontalLines.indices){
            for(x in horizontalLines[0].indices){
                if(x == 0) continue
                if (y == 0) continue
                if(x == horizontalLines.size-1) continue
                if(y == (horizontalLines[0].size -1)) continue
                if(horizontalLines[y][x] != "A") continue
                if(horizontalLines[y-1][x-1] == "M"){
                    if(horizontalLines[y-1][x+1] == "S"){
                        if(horizontalLines[y+1][x-1] == "M"){
                            if(horizontalLines[y+1][x+1] == "S"){
                                counter++
                            }
                        }
                    }
                }
                if(horizontalLines[y-1][x-1] == "S"){
                    if(horizontalLines[y-1][x+1] == "S"){
                        if(horizontalLines[y+1][x-1] == "M"){
                            if(horizontalLines[y+1][x+1] == "M"){
                                counter++
                            }
                        }
                    }
                }
                if(horizontalLines[y-1][x-1] == "M"){
                    if(horizontalLines[y-1][x+1] == "M"){
                        if(horizontalLines[y+1][x-1] == "S"){
                            if(horizontalLines[y+1][x+1] == "S"){
                                counter++
                            }
                        }
                    }
                }
                if(horizontalLines[y-1][x-1] == "S"){
                    if(horizontalLines[y-1][x+1] == "M"){
                        if(horizontalLines[y+1][x-1] == "S"){
                            if(horizontalLines[y+1][x+1] == "M"){
                                counter++
                            }
                        }
                    }
                }
            }
        }
        return counter
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
