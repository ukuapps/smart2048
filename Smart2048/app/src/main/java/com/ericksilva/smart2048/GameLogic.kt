package com.ericksilva.smart2048

import android.util.Log
import java.util.Random

/**
 * Created by ericksilva on 9/13/19.
 */

class GameLogic (var board: BoardGame) {


    val MS = 4 //MatrixSize

    enum class Direction{
        UP, DOWN, LEFT, RIGHT
    }

    val randomValues = arrayOf(2,4)

    var mMatrix = Array(MS, {IntArray(MS)})
    //matrix [row][col]


    fun initGame(){
        mMatrix = getCleanMatrix()
        addNewValue()
        board.populate(mMatrix)
    }

    fun move(direction:Direction){

        val oldMatrix = mMatrix

        when (direction){
            Direction.UP -> moveUp()
            Direction.DOWN -> moveDown()
            Direction.LEFT -> moveLeft()
            Direction.RIGHT -> moveRight()
        }

        if (!matrixCompare(oldMatrix,mMatrix)) {
            addNewValue()
            if (isGameOver()){
                board.gameOver()
            }
        }

        board.populate(mMatrix)
    }

    private fun moveUp(){

        var resultMatrix = getCleanMatrix() //Creo una nueva matriz resultante despues del movimiento

        for (col in 0..(MS-1)){//Aplico la logica columna a columna

            var lastPosition = 0
            val colResult = IntArray(MS)

            for (row in 0..MS-1){ //Pongo todos los numeros juntos
                val v = mMatrix[row][col]
                if (v != 0){
                    colResult[lastPosition] = v
                    lastPosition++
                }
            }

            var skip = false // si son iguales hay que saltear una columna
            var position = 0

            for (row in 0..MS-1){ //Sumo los valores iguales
                if (!skip) {
                    val a = colResult[row]
                    val nextRow = row + 1
                    var b = 0
                    if (nextRow < MS){
                        b = colResult[nextRow]
                    }
                    skip = a == b
                    if (skip) {
                        resultMatrix[position][col] = a + b
                    } else {
                        resultMatrix[position][col] = a
                    }
                    position++
                }else{
                    skip = false
                }
            }
        }

        mMatrix = resultMatrix
    }

    private fun moveDown(){

        var resultMatrix = getCleanMatrix() //Creo una nueva matriz resultante despues del movimiento

        for (col in 0..(MS-1)){//Aplico la logica columna a columna

            var lastPosition = MS-1
            val colResult = IntArray(MS)

            for (row in MS-1 downTo 0){ //Pongo todos los numeros juntos
                val v = mMatrix[row][col]
                if (v != 0){
                    colResult[lastPosition] = v
                    lastPosition--
                }
            }

            var skip = false // si son iguales hay que saltear una columna
            var position = MS-1

            for (row in MS-1 downTo 0){ //Sumo los valores iguales
                if (!skip) {
                    val a = colResult[row]
                    val nextRow = row - 1
                    var b = 0
                    if (nextRow >= 0){
                        b = colResult[nextRow]
                    }
                    skip = a == b
                    if (skip) {
                        resultMatrix[position][col] = a + b
                    } else {
                        resultMatrix[position][col] = a
                    }
                    position--
                }else{
                    skip = false
                }
            }

        }
        mMatrix = resultMatrix

    }

    private fun moveLeft(){

        var resultMatrix = getCleanMatrix() //Creo una nueva matriz resultante despues del movimiento

        for (row in 0..(MS-1)){//Aplico la logica columna a columna

            var lastPosition = 0
            val rowResult = IntArray(MS)

            for (col in 0..MS-1){ //Pongo todos los numeros juntos
                val v = mMatrix[row][col]
                if (v != 0){
                    rowResult[lastPosition] = v
                    lastPosition++
                }
            }

            var skip = false // si son iguales hay que saltear una columna
            var position = 0

            for (col in 0..MS-1){ //Sumo los valores iguales
                if (!skip) {
                    val a = rowResult[col]
                    val nextCol = col + 1
                    var b = 0
                    if (nextCol < MS){
                        b = rowResult[nextCol]
                    }
                    skip = a == b
                    if (skip) {
                        resultMatrix[row][position] = a + b
                    } else {
                        resultMatrix[row][position] = a
                    }
                    position++
                }else{
                    skip = false
                }
            }
        }

        mMatrix = resultMatrix
    }

    private fun moveRight(){
        var resultMatrix = getCleanMatrix() //Creo una nueva matriz resultante despues del movimiento

        for (row in 0..(MS-1)){//Aplico la logica columna a columna

            var lastPosition = MS-1
            val rowResult = IntArray(MS)

            for (col in MS-1 downTo 0){ //Pongo todos los numeros juntos
                val v = mMatrix[row][col]
                if (v != 0){
                    rowResult[lastPosition] = v
                    lastPosition--
                }
            }

            var skip = false // si son iguales hay que saltear una columna
            var position = MS-1

            for (col in MS-1 downTo 0){ //Sumo los valores iguales
                if (!skip) {
                    val a = rowResult[col]
                    val nextCol = col - 1
                    var b = 0
                    if (nextCol >= 0){
                        b = rowResult[nextCol]
                    }
                    skip = a == b
                    if (skip) {
                        resultMatrix[row][position] = a + b
                    } else {
                        resultMatrix[row][position] = a
                    }
                    position--
                }else{
                    skip = false
                }
            }
        }

        mMatrix = resultMatrix
    }

    private fun isGameOver() : Boolean {

        for (i in 0..mMatrix.lastIndex){
            for (j in 0..mMatrix.lastIndex){
                if (mMatrix[i][j] == 0){ //Si hay lugar vacio se puede seguir jugando
                    return false
                }
            }
        }

        for (i in 0..mMatrix.lastIndex){
            for (j in 0..mMatrix.lastIndex){
                val value = mMatrix[i][j]
                if (mMatrix[i].lastIndex != j) {
                    val nextValue = mMatrix[i][j + 1]
                    if (value == nextValue) return false //Si hay numeros iguales juntos se puede seguir jugando
                }

                if (mMatrix.lastIndex != i) {
                    val nextValue = mMatrix[i+1][j]
                    if (value == nextValue) return false //Si hay numeros iguales juntos se puede seguir jugando
                }

            }
        }

        return true
    }

    private fun matrixCompare(m:Array<IntArray>, n:Array<IntArray>) : Boolean {
        for (i in 0..m.lastIndex){
            val x = m[i]
            for (j in 0..x.lastIndex){
                if (x[j] != n[i][j] ) {
                    return false
                }
            }
        }
        return true
    }

    private fun getCleanMatrix() : Array<IntArray>{

        var matrix = Array(4, {IntArray(4)})
        for (i:Int in 0..3){
            (0..3).forEach{
                matrix[i][it] = 0
            }
        }
        return matrix
    }

    private fun addNewValue(){

        var posR = 0
        var posC = 0

        do {
            posR = getRandomRow()
            posC = getRandomCol()
            val actualValue = mMatrix[posR][posC]
        }while (actualValue!=0)

        val newValue = getRandomValue()
        mMatrix[posR][posC] = newValue
    }

    private fun getRandomValue() : Int {

        val random = Random()
        val i = random.nextInt(randomValues.size)

        return randomValues[i]
    }

    private fun getRandomRow(): Int {
        val random = Random()
        return random.nextInt(MS)
    }

    private fun getRandomCol(): Int {
        val random = Random()
        return random.nextInt(MS)
    }

    interface BoardGame{
        fun populate(matrix:Array<IntArray>)
        fun gameOver()
    }

}