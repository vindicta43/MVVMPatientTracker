package com.alperen.mvvmpatienttracker.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.alperen.mvvmpatienttracker.model.ModelPatient
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseRepository : MutableLiveData<ArrayList<ModelPatient>>() {
    val patients = MutableLiveData<ArrayList<ModelPatient>>()

    fun getFromDb() {
        FirebaseFirestore
            .getInstance()
            .collection("PATIENTS")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("TAG", error.message.toString())
                    return@addSnapshotListener
                }

                val docArray = arrayListOf<ModelPatient>()
                for (doc in value!!) {
                    val docItem = ModelPatient(
                        doc["patientName"].toString(),
                        doc["patientSurname"].toString(),
                        doc["patientProblem"].toString(),
                    )
                    docArray.add(docItem)
                }
                Log.e("LISTEN","Listened ${value.documents.size} items")
                patients.value = docArray
            }
    }
}