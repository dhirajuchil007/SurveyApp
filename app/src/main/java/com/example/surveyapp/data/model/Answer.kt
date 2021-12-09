package com.example.surveyapp.data.model

import androidx.annotation.Keep

@Keep
data class Answer(
    var answerText: String,
    var selectedOptionPosition: Int,
    var multipleSelectedPositions: MutableList<Int>
)