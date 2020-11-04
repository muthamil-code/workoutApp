package com.tam.workoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.activity_finish_layout.*
import kotlinx.android.synthetic.main.dialog_confirmation.*
import java.util.*
import kotlin.collections.ArrayList


class ExerciseActivity : AppCompatActivity(),TextToSpeech.OnInitListener {

   private  var restTimer : CountDownTimer? = null
   private var resetProgress = 0
    private var restTimerDuration : Long = 1

   private  var exerciseTimer : CountDownTimer? = null
   private  var exerciseProgress = 0
   private var exerciseDuration : Long = 1
    private var tts : TextToSpeech? = null
   // private var player : MediaPlayer? = null

   private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1
    private var exerciseStatus : ExerciseStatus? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(tool_bar_exercise_id)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tool_bar_exercise_id.setNavigationOnClickListener{
            customDialog()
        }
        setUpRestView()
        exerciseList = Constants.defaultExerciseList()
        tts = TextToSpeech(this,this)
        setUpExerciseStatus()

    }

    override fun onDestroy() {
        if(restTimer != null){
            restTimer!!.cancel()
            resetProgress = 0
        }
        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
//
//        if(player != null){
//            player!!.stop()
//        }

        super.onDestroy()
    }

    private fun setRestProgress(){
        time_pro.progress = resetProgress
        restTimer = object : CountDownTimer(restTimerDuration * 1000,1000){
            override fun onTick(p0: Long) {
                resetProgress++
                time_pro.progress = 10-resetProgress
                tvTimer.text = (10 - resetProgress).toString()

                upComeId.text = exerciseList!![currentExercisePosition].getName()

            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onFinish() {

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseStatus!!.notifyDataSetChanged()

                setUpExerciseView()
            }
        }.start()

    }
    private fun setExerciseProgress(){
        time_pro_excersis.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(exerciseDuration * 1000,1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                time_pro_excersis.progress = exerciseDuration.toInt()-exerciseProgress
                tvTimer_exersise.text = (exerciseDuration.toInt() - exerciseProgress).toString()

            }

            override fun onFinish() {
               if(currentExercisePosition < exerciseList?.size!! - 1){
                   exerciseList!![currentExercisePosition].setIsSelected(false)
                   exerciseList!![currentExercisePosition].setIsCompleted(true)
                   exerciseStatus!!.notifyDataSetChanged()
                   setUpRestView()
               }else{

                   val intent = Intent(this@ExerciseActivity,FinishLayout::class.java)
                   startActivity(intent)
                   //exerciseList!!.size - 1
               }

            }
        }.start()

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpExerciseView(){
        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE
        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())
        setExerciseProgress()
        IvImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExercise.text = exerciseList!![currentExercisePosition].getName()

    }

    private fun setUpRestView(){

//        try{
//            player = MediaPlayer.create(applicationContext,R.raw.h)
//            player!!.isLooping = false
//            player!!.start()
//        }catch (e : Exception){
//            e.printStackTrace()
//        }


        currentExercisePosition++
        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE
        if(restTimer != null){
            restTimer!!.cancel()
            resetProgress = 0
        }
        setRestProgress()
    }

    override fun onInit(status: Int) {

        if(status == TextToSpeech.SUCCESS){
           var result = tts!!.setLanguage(Locale.ENGLISH)
        if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            Log.e("tts","this language not supported")

        }else{
            Log.e("tts","initiaization is failed")
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun speakOut(text : String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")

    }

    private fun setUpExerciseStatus(){

        rvExercise.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
         exerciseStatus = ExerciseStatus(exerciseList!!,this)
        rvExercise.adapter = exerciseStatus
    }

    private fun customDialog(){
        val custom = Dialog(this)
        custom.setContentView(R.layout.dialog_confirmation)
        custom.yes.setOnClickListener {
            finish()
            custom.dismiss()
        }
        custom.no.setOnClickListener {
            custom.dismiss()
        }
        custom.show()

    }
}