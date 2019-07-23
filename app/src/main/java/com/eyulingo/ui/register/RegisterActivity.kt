package com.eyulingo.ui.register

import android.app.Activity
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.eyulingo.R
import com.eyulingo.ui.login.afterTextChanged

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        val email = findViewById<EditText>(R.id.register_email)
        val confirm = findViewById<EditText>(R.id.confirm_num)
        val username = findViewById<EditText>(R.id.register_username)
        val password = findViewById<EditText>(R.id.register_pwd)
        val pwd_confirm = findViewById<EditText>(R.id.register_pwd_confirm)

        val register = findViewById<Button>(R.id.register_button)
        val sendConfirm = findViewById<Button>(R.id.send_confirm_button)

        val loading = findViewById<ProgressBar>(R.id.register_loading)


        registerViewModel = ViewModelProviders.of(this, RegisterViewModelFactory())
            .get(RegisterViewModel::class.java)

        registerViewModel.registerFormState.observe(this@RegisterActivity, Observer {
            val registerState = it ?: return@Observer

            // register.isEnabled = registerState.isDataValid

            if (registerState.emailError != null) {
                email.error = getString(registerState.emailError)
            }
            if (registerState.confirmError != null) {
                confirm.error = getString(registerState.confirmError)
            }
            if (registerState.usernameError != null) {
                username.error = getString(registerState.usernameError)
            }
            if (registerState.passwordError != null) {
                password.error = getString(registerState.passwordError)
            }
            if (registerState.passwordConfirmError != null) {
                pwd_confirm.error = getString(registerState.passwordConfirmError)
            }
        })

        registerViewModel.registerResult.observe(this@RegisterActivity, Observer {
            val registerResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (registerResult.error != null) {
                showRegisterFailed(registerResult.error)
            }
            if (registerResult.success != null) {
                showRegisterSuccess(registerResult.success)

                setResult(Activity.RESULT_OK)
                finish()
            }
        })

        email.afterTextChanged {
            registerViewModel.registerDataChanged(
                email.text.toString(),
                confirm.text.toString(),
                username.text.toString(),
                password.text.toString(),
                pwd_confirm.text.toString()
            )
        }

        confirm.afterTextChanged {
            registerViewModel.registerDataChanged(
                email.text.toString(),
                confirm.text.toString(),
                username.text.toString(),
                password.text.toString(),
                pwd_confirm.text.toString()
            )
        }

        username.afterTextChanged {
            registerViewModel.registerDataChanged(
                email.text.toString(),
                confirm.text.toString(),
                username.text.toString(),
                password.text.toString(),
                pwd_confirm.text.toString()
            )
        }

        password.afterTextChanged {
            registerViewModel.registerDataChanged(
                email.text.toString(),
                confirm.text.toString(),
                username.text.toString(),
                password.text.toString(),
                pwd_confirm.text.toString()
            )
        }

        pwd_confirm.apply {
            afterTextChanged {
                registerViewModel.registerDataChanged(
                    email.text.toString(),
                    confirm.text.toString(),
                    username.text.toString(),
                    password.text.toString(),
                    pwd_confirm.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        if (registerViewModel.isRegisterFormReady()) {
                            registerViewModel.register(
                                email.text.toString(),
                                confirm.text.toString(),
                                username.text.toString(),
                                password.text.toString(),
                                pwd_confirm.text.toString()
                            )
                        }
                    }
                }
                false
            }
        }

        register.setOnClickListener {
            loading.visibility = View.VISIBLE
            registerViewModel.register(
                email.text.toString(),
                confirm.text.toString(),
                username.text.toString(),
                password.text.toString(),
                pwd_confirm.text.toString()
            )
        }

        sendConfirm.setOnClickListener {
            if (registerViewModel.sendConfirmCode(email.text.toString()))
                Toast.makeText(applicationContext, R.string.send_confirm_success_toast, Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(applicationContext, R.string.send_confirm_failed_toast, Toast.LENGTH_SHORT).show()

        }

    }

    private fun showRegisterSuccess(@StringRes successString: Int) {
        Toast.makeText(applicationContext, successString, Toast.LENGTH_SHORT).show()
    }

    private fun showRegisterFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}