package com.example.surveyapp.ui.sureveylist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.surveyapp.R
import com.example.surveyapp.databinding.ActivitySurveyListBinding
import com.example.surveyapp.ui.login.LoginActivity
import com.example.surveyapp.ui.sureveylist.adapter.SurveyListAdapter
import com.example.surveyapp.ui.surveyquestions.SurveyQuestionsActivity
import com.google.firebase.auth.FirebaseAuth

class SurveyListActivity : AppCompatActivity() {
    lateinit var binding: ActivitySurveyListBinding
    lateinit var viewModel: SurveyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyListBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(SurveyViewModel::class.java)
        setContentView(binding.root)
        setUpToolbar()
        setUpClickListeners()
        setUpObservers()
        viewModel.getSurveys()
    }

    private fun setUpObservers() {
        viewModel.surveys.observe(this) {
            binding.recyclerSurveyList.layoutManager = LinearLayoutManager(this)
            val adapter = SurveyListAdapter(it) {
                startActivity(Intent(this, SurveyQuestionsActivity::class.java).apply {
                    putExtra("surveyId", it)
                })
            }
            binding.recyclerSurveyList.adapter = adapter
        }
    }

    private fun setUpClickListeners() {


        binding.txtSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
            finish()
        }
    }

    private fun setUpToolbar() {
        binding.toolbar.txtTitle.text = getString(R.string.my_surveys)
        binding.toolbar.imgBack.setOnClickListener {
            onBackPressed()
        }
    }
}