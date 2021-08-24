package com.alperen.mvvmpatienttracker.viewModel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.alperen.mvvmpatienttracker.R
import com.alperen.mvvmpatienttracker.model.ModelLogin
import com.alperen.mvvmpatienttracker.utils.Toaster
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class LoginViewModel(state: SavedStateHandle): MainViewModel(state) {
    lateinit var auth: FirebaseAuth
    var user = ModelLogin("", "")

    fun login(view: View) {
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            val email = user.email.toString()
            val password = user.password.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toaster(view.context).makeToast("Success")
                            Log.e("TAG", "Success")
                            signIn(view)
                        } else {
                            Toaster(view.context).makeToast(it.exception?.message)
                            Log.e("TAG", "${it.exception?.message}")
                        }
                    }
            }
        } else {
            signIn(view)
        }
    }

    // function runs immediately when app started
    fun login(navController: NavController) {
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            return
        } else {
            navController.navigate(R.id.action_loginFragment_to_patientsFragment)
        }
    }
}