package com.eyulingo.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eyulingo.R
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.lang.Exception

class RegisterViewModel : ViewModel() {

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    private val baseURL: String = "http://47.103.15.32:8080"

    private val client: OkHttpClient = OkHttpClient()

    fun register(email: String, confirm: String, username: String,
                 password: String, passwordConfirm: String)
    {
        //TO-DO("not implemented")

        try {
            val data: String = "{\"email\":\"$email\"," +
                    "\"username\":\"$username\"," +
                    "\"password\":\"$password\"," +
                    "\"confirm_password\":\"$passwordConfirm\"," +
                    "\"confirm_code\":\"$confirm\"}"
            val requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), data)
            val request = Request.Builder().url("$baseURL/register").post(requestBody).build()
            val response = client.newCall(request).execute()

            val resString = response.body().string()
            val res: JsonElement = JsonParser().parse(resString)
            val status = res.asJsonObject.get("status")

            if (status.asString.equals("ok")) {
                _registerResult.value = RegisterResult(success = R.string.register_success)
            }else{
                _registerResult.value = RegisterResult(error = R.string.register_failed)
            }
        }catch (e:Exception) {
            _registerResult.value = RegisterResult(error = R.string.register_failed)
        }
    }

    fun sendConfirmCode(email: String): Boolean {
        //TO-DO("not  implemented")
        //Send an confirm code to email
        try {
            val data: String = "{\"email\":\"$email\"}"
            val requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),data)
            val request = Request.Builder().url("$baseURL/getcode").post(requestBody).build()
            val response = client.newCall(request).execute()

            val resString = response.body().string()
            val res: JsonElement = JsonParser().parse(resString)
            val status = res.asJsonObject.get("status")

            if (status.asString.equals("ok")) {
                return true
            }
        }catch (e:Exception) {
            return false
        }

        return false
    }

    fun isRegisterFormReady(): Boolean {
        return _registerForm.value!!.isDataValid
    }

    fun registerDataChanged(email: String, confirm: String, username: String,
                            password: String, passwordConfirm: String)
    {
        //TO-DO("not implemented")
        if (!isEmailValid(email)) {
            _registerForm.value = RegisterFormState(emailError = R.string.invalid_register_email)
        } else if (!isConfirmCodeValid(confirm)) {
            _registerForm.value = RegisterFormState(confirmError = R.string.invalid_register_confirm_code)
        } else if (!isUsernameValid(username)) {
            _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else if (!isPasswordConfirmValid(password, passwordConfirm)) {
            _registerForm.value = RegisterFormState(passwordConfirmError = R.string.invalid_register_pwd_confirm)
        }

    }

    private fun isEmailValid(email: String): Boolean {
        //TO-DO("not implemented")
        return true
    }

    private fun isConfirmCodeValid(confirm: String): Boolean {
        return true
    }

    private fun isUsernameValid(username: String): Boolean {
        return true
    }

    private fun isPasswordValid(password: String): Boolean {
        return true
    }

    private fun isPasswordConfirmValid(password: String, passwordConfirm: String): Boolean {
        return password == passwordConfirm
    }
}