package com.example.surveyapp.ui.emailverififcation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.surveyapp.R
import com.example.surveyapp.databinding.ActivityEmailVerificationBinding
import com.example.surveyapp.ui.sureveylist.SurveyListActivity
import com.example.surveyapp.util.SharedPrefManager
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class EmailVerification : AppCompatActivity() {
    private val TAG = "EmailVerification"
    lateinit var binding: ActivityEmailVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")
        super.onCreate(savedInstanceState)
        binding = ActivityEmailVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {
            SharedPrefManager(this).addEmail(binding.edtEmail.text.toString())

        }

        checkEmailLink()
    }

    private fun checkEmailLink() {
        val auth = FirebaseAuth.getInstance()
        val intent = intent
        val emailLink = intent.data.toString()

// Confirm the link is a sign-in with email link.
        if (auth.isSignInWithEmailLink(emailLink)) {
            // Retrieve this from wherever you stored it
            val email = SharedPrefManager(this).getEmail()

            // The client SDK will parse the code from the link for you.
            auth.signInWithEmailLink(email, emailLink)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Successfully signed in with email link!")
                        val result = task.result

                        // You can access the new user via result.getUser()
                        // Additional user info profile *not* available via:
                        // result.getAdditionalUserInfo().getProfile() == null
                        // You can check if the user is new or existing:
                        // result.getAdditionalUserInfo().isNewUser()
                    } else {
                        Log.e(TAG, "Error signing in with email link", task.exception)
                    }
                }
        }

    }

    private fun sendVerificationEmail(email: String) {

        val actionCodeSettings =
            ActionCodeSettings.newBuilder() // URL you want to redirect back to. The domain (www.example.com) for this
                // URL must be whitelisted in the Firebase Console.
                .setUrl("") // This must be true
                .setHandleCodeInApp(true)
                .setIOSBundleId("com.example.ios")
                .setAndroidPackageName(
                    "com.example.surveyapp",
                    true,  /* installIfNotAvailable */
                    "12" /* minimumVersion */
                )
                .build()

        FirebaseAuth.getInstance().sendSignInLinkToEmail(email, actionCodeSettings)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                }
            }.addOnFailureListener {
                Log.e(TAG, "sendVerificationEmail: Task failed", it)
            }
    }

    private fun goToNextScreen() {

        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, SurveyListActivity::class.java))
        finish()
    }

}