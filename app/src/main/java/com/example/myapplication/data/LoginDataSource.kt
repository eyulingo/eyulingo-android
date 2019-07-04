package com.example.myapplication.data


import com.example.myapplication.data.model.LoggedInUser
import okhttp3.*
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    var client: OkHttpClient = OkHttpClient()

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication

            var requestBody:RequestBody = RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"),"")
            var request:Request = Request.Builder().url("http://10.166.108.44:8080/login?phone=$username&password=$password").post(requestBody).build()
            var call:Call = client.newCall(request)
            var response:Response = call.execute()


            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

