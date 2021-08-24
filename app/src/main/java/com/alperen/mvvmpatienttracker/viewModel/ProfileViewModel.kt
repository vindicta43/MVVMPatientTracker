package com.alperen.mvvmpatienttracker.viewModel

import android.view.View
import androidx.lifecycle.SavedStateHandle
import com.alperen.mvvmpatienttracker.model.ModelPatient
import com.alperen.mvvmpatienttracker.utils.AlertMaker
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel(state: SavedStateHandle) : MainViewModel(state) {
    fun addPatient(view: View, name: String, surname: String, problem: String) {
        val patient = ModelPatient(name, surname, problem)
        FirebaseFirestore
            .getInstance()
            .collection("PATIENTS")
            .add(patient)
            .addOnSuccessListener {
                AlertMaker(view.context).makeAlert("Hasta başarıyla eklendi.")
            }
            .addOnFailureListener {
                AlertMaker(view.context).makeAlert(it.message)
            }
    }
}