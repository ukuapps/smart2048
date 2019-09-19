package com.ericksilva.smart2048

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), GameLogic.BoardGame {


    var gameLogic : GameLogic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlay.setOnClickListener(View.OnClickListener { play()})
        btnLeft.setOnClickListener(View.OnClickListener { left()})
        btnDown.setOnClickListener(View.OnClickListener { down()})
        btnRight.setOnClickListener(View.OnClickListener { right()})
        btnUp.setOnClickListener(View.OnClickListener { up()})

        gameLogic = GameLogic(this)
    }

    fun play(){
        gameLogic?.initGame()
    }

    fun left(){
        gameLogic?.move(GameLogic.Direction.LEFT)
    }

    fun right(){
        gameLogic?.move(GameLogic.Direction.RIGHT)
    }

    fun down(){
        gameLogic?.move(GameLogic.Direction.DOWN)
    }

    fun up(){
        gameLogic?.move(GameLogic.Direction.UP)
    }

    override fun populate(matrix:Array<IntArray>){
        lblNum11.text = formatedString(matrix.get(0).get(0))
        lblNum12.text = formatedString(matrix.get(0).get(1))
        lblNum13.text = formatedString(matrix.get(0).get(2))
        lblNum14.text = formatedString(matrix.get(0).get(3))
        lblNum21.text = formatedString(matrix.get(1).get(0))
        lblNum22.text = formatedString(matrix.get(1).get(1))
        lblNum23.text = formatedString(matrix.get(1).get(2))
        lblNum24.text = formatedString(matrix.get(1).get(3))
        lblNum31.text = formatedString(matrix.get(2).get(0))
        lblNum32.text = formatedString(matrix.get(2).get(1))
        lblNum33.text = formatedString(matrix.get(2).get(2))
        lblNum34.text = formatedString(matrix.get(2).get(3))
        lblNum41.text = formatedString(matrix.get(3).get(0))
        lblNum42.text = formatedString(matrix.get(3).get(1))
        lblNum43.text = formatedString(matrix.get(3).get(2))
        lblNum44.text = formatedString(matrix.get(3).get(3))
    }

    override fun gameOver() {
        Toast.makeText(this,"GAME OVER!",Toast.LENGTH_LONG).show()
    }

    fun formatedString(value:Int):String{
        if (value != 0)
            return value.toString()
        else
            return ""
    }
}


