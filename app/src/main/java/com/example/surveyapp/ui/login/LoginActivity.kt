package com.example.surveyapp.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.surveyapp.databinding.ActivityLoginBinding

import com.example.surveyapp.R
import com.example.surveyapp.ui.sureveylist.SurveyListActivity
import com.example.surveyapp.ui.surveyquestions.SurveyQuestionsActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"

    companion object {
        val SIGNIN = 1
        val REGISTER = 2
    }

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    var mode = REGISTER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading
        val confirmPassword = binding.confirmPassword

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }

            if (loginState.confirmPasswordError != null) {
                confirmPassword?.error = getString(loginState.confirmPasswordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.INVISIBLE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }

            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        username.afterTextChanged {
            updateInputState(username, password, confirmPassword)
        }

        confirmPassword?.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString(),
                confirmPassword?.text.toString(),
                mode
            )
        }

        password.apply {
            afterTextChanged {
                updateInputState(username, password, confirmPassword)
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        signUpWithFirebase(username.text.toString(), password.text.toString())
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE

                when (mode) {
                    REGISTER -> signUpWithFirebase(
                        username.text.toString(),
                        password.text.toString()
                    )
                    SIGNIN -> signInWithFirebase(username.text.toString(), password.text.toString())

                }

            }
        }

        binding.alreadyUser?.setOnClickListener {
            updateInputState(username, password, confirmPassword)
            if (mode == REGISTER) {
                switchMode(SIGNIN)
            } else
                switchMode(REGISTER)
        }
    }

    private fun updateInputState(
        username: EditText,
        password: EditText,
        confirmPassword: EditText?
    ) {
        loginViewModel.loginDataChanged(
            username.text.toString(),
            password.text.toString(),
            confirmPassword?.text.toString(),
            mode
        )
    }


    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    fun signUpWithFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(this, "Registration successful, Signing In", Toast.LENGTH_SHORT)
                        .show()
                    signInWithFirebase(email, password)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "SignUp failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.loading.visibility = View.INVISIBLE

                }
            }

    }

    private fun showError() {
        binding.loading.visibility = View.INVISIBLE
        Toast.makeText(this, "Login failed, username/passowrd incorrect", Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            goToNextScreen()
        }
    }

    private fun goToNextScreen() {

        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, SurveyListActivity::class.java))
        finish()
    }

    fun signInWithFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    goToNextScreen()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    /*  Toast.makeText(
                          baseContext, "Authentication failed.",
                          Toast.LENGTH_SHORT
                      ).show()*/
                    showError()
                }
            }

    }

    fun switchMode(mode: Int) {
        this.mode = mode
        when (mode) {
            SIGNIN -> {
                binding.confirmPassword?.visibility = View.GONE
                binding.login.text = getString(R.string.sign_in)
                binding.alreadyUser?.text = getString(R.string.new_user)
            }

            REGISTER -> {
                binding.confirmPassword?.visibility = View.VISIBLE
                binding.login.text = getString(R.string.action_register)
                binding.alreadyUser?.text = getString(R.string.already_a_user_tap_here_to_sign_in)
            }
        }
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}






