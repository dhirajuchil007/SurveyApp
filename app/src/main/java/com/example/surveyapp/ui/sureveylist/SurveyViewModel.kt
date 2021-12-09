package com.example.surveyapp.ui.sureveylist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.data.model.Survey
import com.example.surveyapp.util.EventType
import com.example.surveyapp.util.FirebaseUtil
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SurveyViewModel : ViewModel() {
    private val _surveys = MutableLiveData<MutableList<Survey>>()
    val surveys = _surveys

    private val surveyList = mutableListOf<Survey>()

    fun getSurveys() {
        viewModelScope.launch {
            FirebaseUtil.getSurveys().collectLatest {
                when (it.eventType) {
                    EventType.ADDED -> {

                        val data = it.data?.getValue<Survey>()
                        data?.let {
                            surveyList.add(data)
                            _surveys.postValue(surveyList)
                        }
                    }
                }
            }
        }

    }
}
