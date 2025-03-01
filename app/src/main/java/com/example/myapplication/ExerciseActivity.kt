package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.Constraints
import com.example.myapplication.data.ExerciseModel
import com.example.myapplication.databinding.ActivityExerciseBinding
import com.example.myapplication.databinding.DialogBinding
import java.util.Locale

const val workoutTime = 3 // 30 sec
const val restTime = 3 // 10 sec
class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    var binding : ActivityExerciseBinding? = null
    private var restTimer : CountDownTimer? = null
    private var workoutTimer : CountDownTimer? = null
    var restProgress = 0
    var workoutProgress = 0
    private lateinit var exerciseList: ArrayList<ExerciseModel>
    var currentExercisePosition = -1
    private lateinit var tts : TextToSpeech
    private lateinit var player : MediaPlayer
    private lateinit var adapter: ExerciseItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        //setContentView(R.layout.activity_exercise)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding?.toolBarExercise)
        if (supportActionBar != null){
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
        binding?.toolBarExercise?.setNavigationOnClickListener {
            showDialogForBack()
        }
        binding?.toolBarExercise?.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        setupRestView()
        exerciseList = Constraints.defaultExerciseList()
        tts = TextToSpeech(this,this)
        val soundURI = Uri.parse("android.resource://com.example.myapplication/"+R.raw.press_start)
        player = MediaPlayer.create(applicationContext,soundURI)
        setRecycleView()

    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        if(workoutTimer != null){
            workoutTimer?.cancel()
            workoutProgress = 0
        }
        binding = null
        if(tts != null){
            tts.stop()
            tts.shutdown()
        }
        if(player != null){
              player.stop()
        }

    }

    private fun setRestProgress(){
        binding?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer((restTime*1000).toLong(),1000){
            override fun onTick(p0: Long) {
                 if(restProgress == 0){
                    speakOut("take rest for 10 seconds")
                }
                restProgress += 1
                binding?.progressBar?.progress = restTime - restProgress
                binding?.tvProgress?.text = (restTime - restProgress).toString()
            }
            override fun onFinish() {
                currentExercisePosition++
                exerciseList[currentExercisePosition].setIsSelected(true)
                adapter.notifyDataSetChanged()
                setupWorkoutView()
            }
        }.start()

    }
    private fun setupRestView(){

        try {
            player.isLooping = false
            player.start()
        }catch (e : Exception){
            e.printStackTrace()
        }
        binding?.frameLayout?.visibility = View.VISIBLE
        binding?.frameLayoutWorkout?.visibility = View.INVISIBLE
        binding?.IvExercise?.visibility = View.INVISIBLE
        //binding?.tvNextExercise?.visibility = View.VISIBLE

        if(currentExercisePosition == -1){
            binding?.tvTitle?.text = "get ready for exercise"
        }
        else{
            binding?.tvTitle?.text = "take rest"
        }

        if(restTimer!= null){
            restTimer?.cancel()
            restProgress = 0
        }
        //binding?.tvNextExercise?.text = exerciseList[currentExercisePosition+1].getName()
        setRestProgress()
    }
    private fun setupWorkoutView(){
        binding?.frameLayout?.visibility = View.GONE
//        binding?.tvNextExercise?.visibility = View.GONE
        binding?.frameLayoutWorkout?.visibility = View.VISIBLE
        binding?.IvExercise?.visibility = View.VISIBLE
        binding?.frameLayoutWorkout?.visibility = View.VISIBLE
        if (workoutTimer != null){
            workoutTimer!!.cancel()
            workoutProgress = 0
        }
        setWorkoutProgress()
    }

    private fun setWorkoutProgress(){
        binding?.progressBarWorkout?.progress = workoutProgress
        binding?.tvTitle?.text = exerciseList[currentExercisePosition].getName()
        binding?.IvExercise?.setImageResource(exerciseList[currentExercisePosition].getImage())
        workoutTimer = object : CountDownTimer((workoutTime*1000).toLong(),1000){
            override fun onTick(p0: Long) {
                //speakOut()
                if(workoutProgress == 0){
                    speakOut( "do " + binding?.tvTitle?.text.toString() )
                }
                workoutProgress += 1
                binding?.progressBarWorkout?.progress = workoutTime - workoutProgress
                binding?.tvProgressWorkout?.text = (workoutTime - workoutProgress).toString()
            }

            override fun onFinish() {
                exerciseList[currentExercisePosition].setIsSelected(false)
                exerciseList[currentExercisePosition].setIsCompleted(true)

                adapter.notifyDataSetChanged()
                if(currentExercisePosition < exerciseList.size-1){
                    setupRestView()
                }else{
                    finish()
                    Toast.makeText(this@ExerciseActivity,
                        "Exercise is completed", Toast.LENGTH_SHORT).show()
                    navFinishExercise()
                }

            }
        }.start()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.ENGLISH)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "not supported", Toast.LENGTH_SHORT).show()
            } else {

            }
        }
    }

    private fun speakOut(text : String){
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    // RecycleView
    private fun setRecycleView(){
        binding?.recycleViewExercise?.layoutManager =
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        adapter = ExerciseItemAdapter(exerciseList)
        binding?.recycleViewExercise?.adapter = adapter
    }
    // Navigate to Finish Exercise Activity
    private fun navFinishExercise(){
        val intent = Intent(this@ExerciseActivity,FinishExerciseActivity::class.java)
        startActivity(intent)
    }
    // Custom Dialog
    private fun showDialogForBack(){
        val customDialog = Dialog(this)
        val dialogBinding : DialogBinding = DialogBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }
//
        customDialog.show()

    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        showDialogForBack()
    }
}