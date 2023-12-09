package com.bignerdranch.android.lawquiz

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val KEY_RESULT = "result"

class MainActivity : AppCompatActivity() {
    /*private var rand = -1*/
    private lateinit var answerButton1: Button
    private lateinit var answerButton2: Button
    private lateinit var answerButton3: Button
    private lateinit var answerButton4: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView
    private val quizViewModel: ListQ4A by
    lazy {
        ViewModelProviders.of(this).get(ListQ4A::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        val result_User = savedInstanceState?.getInt(KEY_RESULT, 0)?:0
        quizViewModel.currentIndex = currentIndex
        quizViewModel.result = result_User
        quizViewModel.currentIndex = currentIndex
        quizViewModel.result = result_User
        Log.d(TAG, "onCreate(Bundle?) called")
        answerButton1 = findViewById(R.id.answer_button1)
        answerButton2 = findViewById(R.id.answer_button2)
        answerButton3 = findViewById(R.id.answer_button3)
        answerButton4 = findViewById(R.id.answer_button4)
        questionTextView = findViewById(R.id.question_text_view)
        /*answerButton1.setText(quizViewModel.questionList[currentIndex].answer1)
        answerButton2.setText(quizViewModel.questionList[currentIndex].answer2)
        answerButton3.setText(quizViewModel.questionList[currentIndex].answer3)
        answerButton4.setText(quizViewModel.questionList[currentIndex].answer4)*/

        answerButton1.setOnClickListener{
            checkAnswer(quizViewModel.questionList[currentIndex].answer1, answerButton1)
            findAnswer(quizViewModel.questionList[currentIndex].answer2, answerButton2)
            findAnswer(quizViewModel.questionList[currentIndex].answer3, answerButton3)
            findAnswer(quizViewModel.questionList[currentIndex].answer4, answerButton4)
        }
        answerButton2.setOnClickListener{
            checkAnswer(quizViewModel.questionList[currentIndex].answer2, answerButton2)
            findAnswer(quizViewModel.questionList[currentIndex].answer1, answerButton1)
            findAnswer(quizViewModel.questionList[currentIndex].answer3, answerButton3)
            findAnswer(quizViewModel.questionList[currentIndex].answer4, answerButton4)
        }
        answerButton3.setOnClickListener{
            checkAnswer(quizViewModel.questionList[currentIndex].answer3, answerButton3)
            findAnswer(quizViewModel.questionList[currentIndex].answer1, answerButton1)
            findAnswer(quizViewModel.questionList[currentIndex].answer2, answerButton2)
            findAnswer(quizViewModel.questionList[currentIndex].answer4, answerButton4)
        }
        answerButton4.setOnClickListener{
            checkAnswer(quizViewModel.questionList[currentIndex].answer4, answerButton4)
            findAnswer(quizViewModel.questionList[currentIndex].answer1, answerButton1)
            findAnswer(quizViewModel.questionList[currentIndex].answer2, answerButton2)
            findAnswer(quizViewModel.questionList[currentIndex].answer3, answerButton3)
        }
        nextButton.setOnClickListener {
            answerButton1.visibility= View.VISIBLE
            answerButton2.visibility= View.VISIBLE
            answerButton3.visibility= View.VISIBLE
            answerButton4.visibility= View.VISIBLE
            answerButton1.setBackgroundColor(Color.MAGENTA)
            answerButton2.setBackgroundColor(Color.MAGENTA)
            answerButton3.setBackgroundColor(Color.MAGENTA)
            answerButton4.setBackgroundColor(Color.MAGENTA)
            quizViewModel.moveToNext()
            if(quizViewModel.count == 6){
                nextButton.visibility= View.INVISIBLE
            }
            updateQuestion()
        }
        updateQuestion()
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
        savedInstanceState.putInt(KEY_RESULT, quizViewModel.result)
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
        answerButton1.setText(quizViewModel.questionList[quizViewModel.currentIndex].answer1)
        answerButton2.setText(quizViewModel.questionList[quizViewModel.currentIndex].answer2)
        answerButton3.setText(quizViewModel.questionList[quizViewModel.currentIndex].answer3)
        answerButton4.setText(quizViewModel.questionList[quizViewModel.currentIndex].answer4)
    }
    private fun checkAnswer(userAnswer: String, answerButton: Button) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        if (userAnswer == correctAnswer) {
            quizViewModel.result += 1
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
    /*companion object{
        fun newIntent(packageContext: Context, rand: Int): Intent {
            return Intent(packageContext, MainActivity::class.java).apply {
                putExtra(RAND_TEXT, rand)
            }
        }
    }*/
}