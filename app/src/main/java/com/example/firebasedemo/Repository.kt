package com.example.firebasedemo

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class Repository {

    lateinit var response: MutableLiveData<String>
    private val auth = Firebase().getInstance()

    fun signOut() {
        auth.signOut()
    }

    suspend fun registerUser(email: String, password: String) {
        response = MutableLiveData()
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            response.value = "Succeed"
        } catch (e: Exception) {
            response.value = e.message
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    suspend fun loginUser(email: String, password: String) {
        response = MutableLiveData()
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            response.value = "Succeed"
        } catch (e: Exception) {
            response.value = e.message
        }
    }

    suspend fun updateProfile(username: String, photoURI: Uri) {
        val user = getCurrentUser()
        response = MutableLiveData()

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .setPhotoUri(photoURI)
            .build()

        try {
            user?.updateProfile(profileUpdates)?.await()
            response.value = "Successfully updated user profile"
        } catch (e: Exception) {
            response.value = e.message
        }
    }
}