package com.example.surveyapp

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class SurveyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}