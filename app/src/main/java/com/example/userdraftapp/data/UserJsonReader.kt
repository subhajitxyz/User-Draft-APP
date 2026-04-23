package com.example.userdraftapp.data

import android.content.Context
import android.util.Log
import com.example.userdraftapp.data.models.User
import com.example.userdraftapp.data.models.UserResponse
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserJsonReader @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getUserList(): List<User> {
        return try {
            val jsonString = context.assets.open("users.json").bufferedReader().use { it.readText() }

            // Parse the wrapper object instead of the Array
            val response = Gson().fromJson(jsonString, UserResponse::class.java)
            response.users
        } catch (e: Exception) {
            Log.e("testusers", "Parsing error", e)
            emptyList()
        }
    }
}