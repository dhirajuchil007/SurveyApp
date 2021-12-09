package com.example.surveyapp.util

import com.example.surveyapp.data.model.Question
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow

object FirebaseUtil {
    var database = FirebaseDatabase.getInstance().reference
    var surveyRef = database.child("surveys")


    fun addQuestion(surveyId: String, question: Question) {
        val key = surveyRef.child(surveyId).child("questions").push().key
        question.id = key

        val childUpdate = hashMapOf<String, Any>(
            "$surveyId/questions/$key" to question
        )
        surveyRef.updateChildren(childUpdate)
    }

    fun getQuestions(surveyId: String): Flow<FirebaseChildEvent<DataSnapshot>> =
        surveyRef.child(surveyId).child("questions").observeChildEvent()

    fun getSurveys(): Flow<FirebaseChildEvent<DataSnapshot>> = surveyRef.observeChildEvent()

}