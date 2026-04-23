package com.example.userdraftapp.data.repo

import com.example.userdraftapp.data.UserJsonReader
import com.example.userdraftapp.data.models.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val jsonReader: UserJsonReader
) {

    fun getUsersList(): List<User> {
        return jsonReader.getUserList()
    }
}