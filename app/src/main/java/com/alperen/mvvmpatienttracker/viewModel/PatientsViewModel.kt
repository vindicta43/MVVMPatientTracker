package com.alperen.mvvmpatienttracker.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.alperen.mvvmpatienttracker.R
import com.alperen.mvvmpatienttracker.model.ModelPatient
import com.alperen.mvvmpatienttracker.utils.FirebaseRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class PatientsViewModel(private val state: SavedStateHandle) : ViewModel() {
    private val liveDate = state.getLiveData("liveData", Random.nextInt().toString())

    fun saveState() {
        state.set("liveData", liveDate.value)
    }

    private var patientsData = MutableLiveData<ArrayList<ModelPatient>>()

    // livedata method
    fun getPatients(): MutableLiveData<ArrayList<ModelPatient>> {
        FirebaseRepository.getFromDb()
        FirebaseRepository.patients.observeForever { event ->
            patientsData.value = event
        }
        return patientsData
    }

    fun logout(navController: NavController?) {
        val auth = Firebase.auth
        auth.signOut()
        navController?.navigate(R.id.action_patientsFragment_to_loginFragment)
    }
}
