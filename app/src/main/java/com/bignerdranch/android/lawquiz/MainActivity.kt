package com.bignerdranch.android.lawquiz

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders


private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val KEY_RESULT = "result"
private const val KEY_CHEAT = "cheat"

class MainActivity : AppCompatActivity() {
    private lateinit var answerButton1: Button
    private lateinit var answerButton2: Button
    private lateinit var answerButton3: Button
    private lateinit var answerButton4: Button
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton: Button
    private val quizViewModel: ListQ4A by
    lazy {
        ViewModelProviders.of(this).get(ListQ4A::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: (0..quizViewModel.questionList.size - 1).random()
        val result_User = intent.getIntExtra(KEY_RESULT, 0)
        val cheat = savedInstanceState?.getInt(KEY_CHEAT, 2)?:2
        quizViewModel.currentIndex = currentIndex
        quizViewModel.result = result_User
        quizViewModel.cheat = cheat
        Log.d(TAG, "onCreate(Bundle?) called")
        answerButton1 = findViewById(R.id.answer_button1)
        answerButton2 = findViewById(R.id.answer_button2)
        answerButton3 = findViewById(R.id.answer_button3)
        answerButton4 = findViewById(R.id.answer_button4)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)
        nextButton = findViewById(R.id.next_button)

        answerButton1.setOnClickListener{
            checkAnswer(answerButton1.text.toString(), answerButton1)
            findAnswer(answerButton2.text.toString(), answerButton2)
            findAnswer(answerButton3.text.toString(), answerButton3)
            findAnswer(answerButton4.text.toString(), answerButton4)
            cheatButton.visibility = View.INVISIBLE
            nextButton.visibility= View.VISIBLE
        }
        answerButton2.setOnClickListener{
            checkAnswer(answerButton2.text.toString(), answerButton2)
            findAnswer(answerButton1.text.toString(), answerButton1)
            findAnswer(answerButton3.text.toString(), answerButton3)
            findAnswer(answerButton4.text.toString(), answerButton4)
            cheatButton.visibility = View.INVISIBLE
            nextButton.visibility= View.VISIBLE
        }
        answerButton3.setOnClickListener{
            checkAnswer(answerButton3.text.toString(), answerButton3)
            findAnswer(answerButton1.text.toString(), answerButton1)
            findAnswer(answerButton2.text.toString(), answerButton2)
            findAnswer(answerButton4.text.toString(), answerButton4)
            cheatButton.visibility = View.INVISIBLE
            nextButton.visibility= View.VISIBLE
        }
        answerButton4.setOnClickListener{
            checkAnswer(answerButton4.text.toString(), answerButton4)
            findAnswer(answerButton1.text.toString(), answerButton1)
            findAnswer(answerButton2.text.toString(), answerButton2)
            findAnswer(answerButton3.text.toString(), answerButton3)
            cheatButton.visibility = View.INVISIBLE
            nextButton.visibility= View.VISIBLE
        }
        cheatButton.setOnClickListener(){
            quizViewModel.cheat -= 1
            if (quizViewModel.cheat <= 0){
                cheatButton.visibility = View.INVISIBLE
            }
            var i = 0
            while(i != 2){
                var rnd = (0..3).random()
                when{
                    quizViewModel.questionList[quizViewModel.currentIndex].answer[rnd] != quizViewModel.currentQuestionAnswer -> {
                        when{
                            quizViewModel.questionList[quizViewModel.currentIndex].answer[rnd] == answerButton1.text && answerButton1.visibility == View.VISIBLE -> {
                                answerButton1.visibility = View.INVISIBLE
                                i += 1
                            }
                            quizViewModel.questionList[quizViewModel.currentIndex].answer[rnd] == answerButton2.text && answerButton2.visibility == View.VISIBLE-> {
                                answerButton2.visibility = View.INVISIBLE
                                i += 1
                            }
                            quizViewModel.questionList[quizViewModel.currentIndex].answer[rnd] == answerButton3.text && answerButton3.visibility == View.VISIBLE-> {
                                answerButton3.visibility = View.INVISIBLE
                                i += 1
                            }
                            quizViewModel.questionList[quizViewModel.currentIndex].answer[rnd] == answerButton4.text && answerButton4.visibility == View.VISIBLE-> {
                                answerButton4.visibility = View.INVISIBLE
                                i += 1
                            }
                        }
                    }
                }
            }
        }
        nextButton.setOnClickListener {
            answerButton1.visibility= View.VISIBLE
            answerButton2.visibility= View.VISIBLE
            answerButton3.visibility= View.VISIBLE
            answerButton4.visibility= View.VISIBLE
            if (quizViewModel.cheat > 0){
                cheatButton.visibility = View.VISIBLE
            }
            answerButton1.setBackgroundColor(Color.parseColor("#0F8EC8"))
            answerButton2.setBackgroundColor(Color.parseColor("#0F8EC8"))
            answerButton3.setBackgroundColor(Color.parseColor("#0F8EC8"))
            answerButton4.setBackgroundColor(Color.parseColor("#0F8EC8"))
            quizViewModel.moveToNext()
            when{
                quizViewModel.count == 6 -> {
                    /*Toast.makeText(this, "Your result = " + quizViewModel.result, Toast.LENGTH_SHORT).show()*/
                    val intent = StartActivity.newIntent(this@MainActivity, quizViewModel.result)
                    startActivity(intent)

                }
                else -> updateQuestion()
            }
        }
        updateQuestion()
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
        savedInstanceState.putInt(KEY_RESULT, quizViewModel.result)
        savedInstanceState.putInt(KEY_CHEAT, quizViewModel.cheat)
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        answerButton1.setText("")
        answerButton2.setText("")
        answerButton3.setText("")
        answerButton4.setText("")
        var i = 0
        while(i != 4){
            var rnd = (0..3).random()
            when{
                answerButton1.text == "" -> {
                    answerButton1.setText(quizViewModel.questionList[quizViewModel.currentIndex].answer[rnd])
                    i+=1
                }
                answerButton2.text == "" && answerButton1.text != quizViewModel.questionList[quizViewModel.currentIndex].answer[rnd] -> {
                    answerButton2.setText(quizViewModel.questionList[quizViewModel.currentIndex].answer[rnd])
                    i+=1
                }
                answerButton3.text == "" && answerButton1.text != quizViewModel.questionList[quizViewModel.currentIndex].answer[rnd]
                        && answerButton2.text != quizViewModel.questionList[quizViewModel.currentIndex].answer[rnd]-> {
                    answerButton3.setText(quizViewModel.questionList[quizViewModel.currentIndex].answer[rnd])
                    i +=1
                }
                answerButton4.text == "" && answerButton1.text != quizViewModel.questionList[quizViewModel.currentIndex].answer[rnd]
                        && answerButton2.text != quizViewModel.questionList[quizViewModel.currentIndex].answer[rnd]
                        && answerButton3.text != quizViewModel.questionList[quizViewModel.currentIndex].answer[rnd]-> {
                    answerButton4.setText(quizViewModel.questionList[quizViewModel.currentIndex].answer[rnd])
                    i+=1
               }
            }
        }
    }
    private fun checkAnswer(userAnswer: String, answerButton: Button) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        if (userAnswer == correctAnswer) {
            quizViewModel.result += 2
            answerButton.setBackgroundColor(Color.GREEN)
        }
        else{
            answerButton.setBackgroundColor(Color.RED)
        }
    }
    private fun findAnswer(userAnswer: String, answerButton: Button){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        if (userAnswer == correctAnswer) {
            answerButton.setBackgroundColor(Color.GREEN)
        }
        else{
            answerButton.visibility = View.INVISIBLE
        }
    }
    companion object{
        fun newIntent(packageContext: Context, result: Int): Intent {
            return Intent(packageContext, MainActivity::class.java).apply {
                putExtra(KEY_RESULT, result)
            }
        }
    }
}