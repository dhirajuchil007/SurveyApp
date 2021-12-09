package com.example.surveyapp.ui.surveyquestions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.surveyapp.R
import com.example.surveyapp.data.model.Question
import com.example.surveyapp.databinding.ActivitySurveyQuestionsBinding
import com.example.surveyapp.ui.login.LoginActivity
import com.example.surveyapp.ui.surveyquestions.adapters.QuestionsAdapter
import com.example.surveyapp.ui.thankyou.ThankYou
import com.example.surveyapp.util.NoScrollHorizontalLayoutManager
import com.google.firebase.auth.FirebaseAuth

class SurveyQuestionsActivity : AppCompatActivity() {
    private val TAG = "SurveyQuestionsActivity"
    lateinit var viewModel: SurveyQuestionsViewModel
    lateinit var binding: ActivitySurveyQuestionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar()
        viewModel = ViewModelProvider(this).get(SurveyQuestionsViewModel::class.java)


        /*viewModel.addQuestion(
            Question(
                null, "Select all the options that are fruits", 3, mutableListOf(
                    Option("Orange", null, 1),
                    Option("Tomato", null, 2),
                    Option("Grapes", null, 3),
                    Option("Potato", null, 4),
                )

            )
        )*/
        if (intent.hasExtra("surveyId")) {
            intent.getStringExtra("surveyId")?.let {
                viewModel.getSurveyQuestions(it)
            }
        }

        viewModel.questionState.observe(this) {
            Log.d(TAG, "Questions: ${it.size}")
            setUpRecyclerView(it)
        }


    }

    private fun setUpToolbar() {
        binding.toolbar.txtTitle.text = getString(R.string.sample_survey)
        binding.toolbar.imgBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpRecyclerView(mutableList: MutableList<Question>) {
        binding.recyclerQuestions.layoutManager =
            NoScrollHorizontalLayoutManager(this)
        val adapter = QuestionsAdapter(mutableList, this, object : QuestionsAdapter.OnItemClick {
            override fun next(position: Int) {
                if (position == mutableList.size - 1) {
                    startActivity(Intent(this@SurveyQuestionsActivity, ThankYou::class.java))
                    finish()
                } else
                    binding.recyclerQuestions.smoothScrollToPosition(position + 1)
            }

            override fun previous(position: Int) {
                binding.recyclerQuestions.smoothScrollToPosition(position - 1)
            }

        })

        /*  val snapHelper = PagerSnapHelper()
          snapHelper.attachToRecyclerView(binding.recyclerQuestions)*/
        binding.recyclerQuestions.adapter = adapter
    }
}