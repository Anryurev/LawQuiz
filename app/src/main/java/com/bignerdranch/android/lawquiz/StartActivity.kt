package com.bignerdranch.android.lawquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

private const val RESULT = ".result"

class StartActivity : AppCompatActivity() {
    private var result = 0
    private lateinit var playButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        playButton = findViewById(R.id.play_button)

        playButton.setOnClickListener(){
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }
    companion object {
        fun newIntent(packageContext: Context, result: Int): Intent {
            return Intent(packageContext, StartActivity::class.java).apply {
                putExtra(RESULT, result)
            }
        }
    }
}