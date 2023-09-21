package com.selincengiz.dinefeast.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {

    private var _message = MutableLiveData<String?>()
    val message: MutableLiveData<String?>
        get() = _message

    fun firebaseSignIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                task.addOnSuccessListener {
                    _message.value="Success"
                }

                task.addOnFailureListener {
                    _message.value=it.message
                }

            }
    }

}