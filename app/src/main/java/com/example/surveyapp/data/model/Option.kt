package com.example.surveyapp.data.model

import androidx.annotation.Keep
import com.google.firebase.database.Exclude

@Keep
data class Option(
    var optionText: String? = null,
    var optionId: String? = null,
    var optionPosition: Int? = null,
    @Exclude @JvmField
    var isSelected: Boolean = false
)
