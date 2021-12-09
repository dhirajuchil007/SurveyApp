package com.example.surveyapp.ui.surveyquestions

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.data.model.Question
import com.example.surveyapp.util.EventType
import com.example.surveyapp.util.FirebaseChildEvent
import com.example.surveyapp.util.FirebaseUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SurveyQuestionsViewModel : ViewModel() {
    private val TAG = "SurveyQuestionsViewMode"
    lateinit var questionFlow: Flow<FirebaseChildEvent<DataSnapshot>>

    val questionsList = mutableListOf<Question>()

    private val _questionState = MutableLiveData<MutableList<Question>>()
    var questionState = _questionState


    fun addQuestion(question: Question, surveyId: String) {
        FirebaseUtil.addQuestion(surveyId, question)
    }

    fun getSurveyQuestions(surveyId: String) {
        Log.d(TAG, "getSurveyQuestions() called")
        if (!this::questionFlow.isInitialized)
            viewModelScope.launch {
                Log.d(TAG, "getSurveyQuestions() called: Inside coroutine")
                questionFlow = FirebaseUtil.getQuestions(surveyId)

                questionFlow.collectLatest { firebaseChildEvent ->

                    Log.d(
                        TAG,
                        "getSurveyQuestions() called with: firebaseChildEvent = $firebaseChildEvent"
                    )

                    val question = firebaseChildEvent.data?.getValue<Question>()
                    question?.let {
                        when (firebaseChildEvent.eventType) {
                            EventType.ADDED -> {
                                questionsList.add(question)
                            }
                        }

                        _questionState.postValue(questionsList)
                    }
                }
            }

    }
}