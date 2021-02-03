package com.example.firebasedemo

import com.google.firebase.auth.FirebaseAuth


class Firebase {
    fun getInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}