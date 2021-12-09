package com.example.surveyapp.ui.thankyou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.surveyapp.R
import com.example.surveyapp.databinding.ActivityThankYouBinding

class ThankYou : AppCompatActivity() {
    lateinit var binding: ActivityThankYouBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThankYouBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDone.setOnClickListener {
            finish()
        }
    }
}