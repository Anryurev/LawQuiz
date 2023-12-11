package com.bignerdranch.android.lawquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

private const val TAG = "StartActivity"
private const val RESULT = ".result"
private const val CHEAT = ".cheat"

class StartActivity : AppCompatActivity() {
    private var result = -1
    private var cheat = 0
    private lateinit var playButton: Button
    private lateinit var resultText: TextView
    private lateinit var cheatText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        result = intent.getIntExtra(RESULT, savedInstanceState?.getInt(RESULT, -1) ?: -1)
        cheat = intent.getIntExtra(CHEAT, savedInstanceState?.getInt(RESULT, 0)?: 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        resultText = findViewById(R.id.textResult)
        cheatText = findViewById(R.id.textCheat)
        if (result != -1){
            resultText.visibility = View.VISIBLE
            resultText.setText(R.string.result)
            resultText.text = resultText.text.toString() + "$result"
            cheatText.visibility = View.VISIBLE
            cheatText.setText(R.string.cheat)
            cheatText.text = cheatText.text.toString() + "$cheat"
        }

        playButton = findViewById(R.id.play_button)

        playButton.setOnClickListener(){
            result = -1
            cheat = 0
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(RESULT, result)
        savedInstanceState.putInt(CHEAT, cheat)
    }
    companion object {
        fun newIntent(packageContext: Context, result: Int, cheat: Int): Intent {
            return Intent(packageContext, StartActivity::class.java).apply {
                putExtra(RESULT, result)
                putExtra(CHEAT, cheat)
            }
        }
        fun newIntent(packageContext: Context): Intent{
            return Intent(packageContext, StartActivity::class.java)
        }
    }
}