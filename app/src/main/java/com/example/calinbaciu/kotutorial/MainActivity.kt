package com.example.calinbaciu.kotutorial

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal val INITAL_SCORE = 0
    internal var score = INITAL_SCORE
    internal val initialCountDown: Long = 10000
    internal val countDownInterval: Long = 1000

    internal lateinit var countDownTimer : CountDownTimer
    internal var gameStarted = false
    internal var timeLeftOnTimer: Long = 60000

    companion object {
        private val SCORE_KEY = "SCORE_KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tapMeButton.setOnClickListener {
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            tapMeButton.startAnimation(bounceAnimation)
            startGame()
            incrementScore()
            animateScore()
        }
        gameScoreTv.text = getString(R.string.your_score, score.toString())

        if(savedInstanceState != null){
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeftOnTimer = savedInstanceState.getLong(TIME_LEFT_KEY)
            restoreGame()
        }else{
            resetGame()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCORE_KEY, score)
        outState.putLong(TIME_LEFT_KEY, timeLeftOnTimer / 1000)
        countDownTimer.cancel()

    }

    private fun restoreGame(){
        gameScoreTv.text = getString(R.string.your_score,score.toString())
        timeLeftTv.text = getString(R.string.time_left, timeLeftOnTimer.toString())

        countDownTimer = object : CountDownTimer(timeLeftOnTimer *1000, countDownInterval){
            override fun onFinish() {
                endGame()
            }

            override fun onTick(p0: Long) {
                timeLeftOnTimer = p0
                val timeLeft =  p0 / 1000
                timeLeftTv.text = getString(R.string.time_left, timeLeft.toString())
            }
        }
        countDownTimer.start()
        gameStarted = true
    }

    private fun animateScore(){
        val alphaAnim = AlphaAnimation(0.0f,1.0f)
        alphaAnim.duration = 400
//        val alphaAnim = AnimationUtils.loadAnimation(this,R.anim.blink)
        gameScoreTv.startAnimation(alphaAnim)
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
                timeLeftOnTimer = p0
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
