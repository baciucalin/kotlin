package com.example.calinbaciu.kotutorial

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal val INITAL_SCORE = 0
    internal var score = INITAL_SCORE
    internal lateinit var countDownTimer : CountDownTimer
    internal var gameStarted = false
    internal val initialCountDown: Long = 5000
    internal val countDownInterval: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tapMeButton.setOnClickListener {
            startGame()
            incrementScore()
        }
        gameScoreTv.text = getString(R.string.your_score, score.toString())

        resetGame()
    }

    private fun incrementScore(){
        score++
        val newScore = getString(R.string.your_score, score.toString())
        gameScoreTv.text = newScore
    }

    private fun startGame(){
        startCountDown()
        gameStarted = true
    }

    private fun startCountDown(){
        if(!gameStarted){
            countDownTimer.start()
        }
    }

    private fun endGame(){
        Toast.makeText(this, getString(R.string.game_finished_your_score, score.toString()), Toast.LENGTH_LONG).show()
        resetGame()
    }

    private fun resetGame(){
        score = INITAL_SCORE
        gameScoreTv.text = getString(R.string.your_score, score.toString())
        val initialTimeLeft = initialCountDown / 1000
        timeLeftTv.text = getString(R.string.time_left, initialTimeLeft.toString())

        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval){

            override fun onTick(p0: Long) {
                val timeLeft =  p0 / 1000
                timeLeftTv.text = getString(R.string.time_left, timeLeft.toString())
            }

            override fun onFinish() {
                endGame()
            }
        }
        gameStarted = false
    }

}
