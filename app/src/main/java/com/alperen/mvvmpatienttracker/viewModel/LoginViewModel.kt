package com.alperen.mvvmpatienttracker.viewModel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.alperen.mvvmpatienttracker.R
import com.alperen.mvvmpatienttracker.model.ModelLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class LoginViewModel(private val state: SavedStateHandle) : ViewModel() {
    private val liveDate = state.getLiveData("liveData", Random.nextInt().toString())

    fun saveState() {
        state.set("liveData", liveDate.value)
    }

    lateinit var auth: FirebaseAuth
    var user = ModelLogin("", "")

    fun login(view: View, navController: NavController?) {
        auth = Firebase.auth
        if (auth.currentUser == null) {
            val email = user.email.toString()
            val password = user.password.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(view.context, "Success", Toast.LENGTH_LONG)
                                .show()
                            Log.e("TAG", "Success")
                            signIn(navController)
                        } else {
                            Toast.makeText(
                                view.context,
                                it.exception?.message,
                                Toast.LENGTH_LONG
                            ).show()
                            Log.e("TAG", "${it.exception?.message}")
                        }
                    }
            }
        } else {
            signIn(navController)
        }
    }

    private fun signIn(navController: NavController?) {
        navController?.navigate(R.id.action_loginFragment_to_patientsFragment)
    }
}