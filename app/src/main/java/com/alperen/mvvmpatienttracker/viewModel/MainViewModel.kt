package com.alperen.mvvmpatienttracker.viewModel

import android.text.Editable
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.alperen.mvvmpatienttracker.R
import com.alperen.mvvmpatienttracker.model.ModelPatient
import com.alperen.mvvmpatienttracker.utils.AlertMaker
import com.alperen.mvvmpatienttracker.utils.FirebaseRepository
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

open class MainViewModel(private val state: SavedStateHandle) : ViewModel() {
    open val liveDate = state.getLiveData("liveData", Random.nextInt().toString())
    fun saveState() {
        state.set("liveData", liveDate.value)
    }

    // livedata method
    open fun getPatients(): MutableLiveData<ArrayList<ModelPatient>> {
        val patientsData = MutableLiveData<ArrayList<ModelPatient>>()
        FirebaseRepository.getFromDb()
        FirebaseRepository.patients.observeForever { event ->
            patientsData.value = event
        }
        return patientsData
    }

    fun getUser(): Map<String, String?> {
        val auth = FirebaseAuth.getInstance()
        return mapOf("email" to auth.currentUser?.email)
    }

    open fun signIn(view: View) {
        view.findNavController().navigate(R.id.action_loginFragment_to_patientsFragment)
    }

    fun logout(view: View) {
        Firebase.auth.signOut()
        view.findNavController().navigate(R.id.action_patientsFragment_to_loginFragment)
    }

    private fun forceLogout(view: View) {
        Firebase.auth.signOut()
        view.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
    }

    fun update(view: View, newEmail: Editable, oldPassword: Editable, newPassword: Editable) {
        val user = FirebaseAuth.getInstance().currentUser
        val mail = getUser()["email"].toString()
        val newMail = newEmail.toString()
        val pass = oldPassword.toString()
        val newPass = newPassword.toString()

        val credential =
            EmailAuthProvider.getCredential(mail, pass)

        user?.reauthenticate(credential)
            ?.addOnSuccessListener {
                user.updateEmail(newMail)
                    .addOnSuccessListener {
                        user.updatePassword(newPass)
                            .addOnSuccessListener {
                                AlertMaker(view.context).makeAlert("Bilgileriniz başarıyla güncellendi. " +
                                        "Yeniden giriş yapmanız gerekli.")
                                forceLogout(view)
                            }
                            .addOnFailureListener {
                                AlertMaker(view.context).makeAlert(it.message)
                            }
                    }
                    .addOnFailureListener {
                        AlertMaker(view.context).makeAlert(it.message)
                    }
            }
            ?.addOnFailureListener {
                AlertMaker(view.context).makeAlert(it.message)
            }
    }
}