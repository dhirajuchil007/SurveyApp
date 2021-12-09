package com.example.surveyapp.data.model

import androidx.annotation.Keep

@Keep
data class Question(
    var id: String? = null,
    var questionText: String? = null,
    var questionType: Int? = null,
    var options: MutableList<Option>? = null
)
