package com.eyulingo.data

import com.eyulingo.data.model.LoggedInUser
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    var client: OkHttpClient = OkHttpClient()

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication

            val requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{a:a}")
            val request = Request.Builder().url("http://47.103.15.32:8080/login?username=$username&password=$password").post(requestBody).build()
            val response = client.newCall(request).execute()

            val resString = response.body().string()
            val res: JsonElement = JsonParser().parse(resString)
            val status = res.asJsonObject.get("status")

            if (status.asString.equals("ok")) {
                val user = LoggedInUser(username)
                return Result.Success(user)
            }

            return Result.Error(Exception(status.asString))
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}
