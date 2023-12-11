package com.bignerdranch.android.lawquiz

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity2"
private const val KEY_INDEX = "index"
private const val KEY_RESULT = "result"
class MainActivity2 : AppCompatActivity(){
    private lateinit var answerButton1: Button
    private lateinit var answerButton2: Button
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private val quizViewModel: ListQ2A by
    lazy {
        ViewModelProviders.of(this).get(ListQ2A::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: (0..(quizViewModel.questionList.size - 1)).random()
        val result_User = savedInstanceState?.getInt(KEY_RESULT, 0) ?: 0
        quizViewModel.currentIndex = currentIndex
        quizViewModel.result = result_User
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main2)
        answerButton1 = findViewById(R.id.answer_button1)
        answerButton2 = findViewById(R.id.answer_button2)
        questionTextView = findViewById(R.id.question_text_view)
        nextButton = findViewById(R.id.next_button)

        answerButton1.setOnClickListener{
            checkAnswer(quizViewModel.questionList[quizViewModel.currentIndex].answer1, answerButton1)
            findAnswer(quizViewModel.questionList[quizViewModel.currentIndex].answer2, answerButton2)
            nextButton.visibility= View.VISIBLE
        }
        answerButton2.setOnClickListener{
            checkAnswer(quizViewModel.questionList[quizViewModel.currentIndex].answer2, answerButton2)
            findAnswer(quizViewModel.questionList[quizViewModel.currentIndex].answer1, answerButton1)
            nextButton.visibility= View.VISIBLE
        }
        nextButton.setOnClickListener {
            answerButton1.visibility= View.VISIBLE
            answerButton2.visibility= View.VISIBLE
            answerButton1.setBackgroundColor(Color.parseColor("#0F8EC8"))
            answerButton2.setBackgroundColor(Color.parseColor("#0F8EC8"))
            quizViewModel.moveToNext()
            when{
                quizViewModel.count == 4 -> {
                    val intent = MainActivity.newIntent(this@MainActivity2, quizViewModel.result)
                    startActivity(intent)
                    onDestroy() //DANGER!!!
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
    private fun findAnswer(checkedAnswer: String, answerButton: Button){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        if (checkedAnswer == correctAnswer) {
            answerButton.setBackgroundColor(Color.GREEN)
        }
        else{
            answerButton.visibility = View.INVISIBLE
        }
    }
}