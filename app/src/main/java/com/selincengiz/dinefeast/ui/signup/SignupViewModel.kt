package com.selincengiz.dinefeast.ui.signup

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val auth : FirebaseAuth): ViewModel() {

    private var _message = MutableLiveData<String?>()
    val message: MutableLiveData<String?>
        get() = _message

    fun firebaseSignUp(email: String, password: String, name: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                task.addOnSuccessListener {
                    firebaseUserProfileChange(name)
                }
                task.addOnFailureListener {
                    _message.value = it.message

                }

            }
    }


    fun firebaseUserProfileChange(name: String) {
        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }
        auth.currentUser?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->

                task.addOnSuccessListener {
                    _message.value = "Success"

                }
                task.addOnFailureListener {
                    _message.value = it.message
                }

            }
    }
}