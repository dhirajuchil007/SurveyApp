package com.example.surveyapp.data.model

import com.example.surveyapp.data.model.Question

data class Survey(
    var surveyId: String? = null,
    var surveyName: String? = null,
    var questions: MutableList<Question>? = null
)
