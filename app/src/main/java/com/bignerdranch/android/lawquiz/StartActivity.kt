package com.bignerdranch.android.lawquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

private const val TAG = "StartActivity"
private const val RESULT = ".result"

class StartActivity : AppCompatActivity() {
    private var result = -1
    private lateinit var playButton: Button
    private lateinit var resultText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        result = intent.getIntExtra(RESULT, savedInstanceState?.getInt(RESULT, -1) ?: -1)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        resultText = findViewById(R.id.textResult)
        if (result != -1){
            resultText.visibility = View.VISIBLE
            resultText.setText("Last result: $result")
        }

        playButton = findViewById(R.id.play_button)

        playButton.setOnClickListener(){
            result = -1
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(RESULT, result)
    }
    companion object {
        fun newIntent(packageContext: Context, result: Int): Intent {
            return Intent(packageContext, StartActivity::class.java).apply {
                putExtra(RESULT, result)
            }
        }
    }
}