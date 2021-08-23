package com.alperen.mvvmpatienttracker.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alperen.mvvmpatienttracker.R
import com.alperen.mvvmpatienttracker.model.ModelPatient
import com.alperen.mvvmpatienttracker.utils.PatientsAdapter.PatientsViewHolder
import kotlin.collections.ArrayList

class PatientsAdapter(private val patientsList: ArrayList<ModelPatient>) :
    RecyclerView.Adapter<PatientsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_recycler_item, parent, false)
        return PatientsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientsViewHolder, position: Int) {
        holder.tvPatientName.text = patientsList[position].patientName
        holder.tvPatientSurname.text = patientsList[position].patientSurname
        holder.tvPatientProblem.text = patientsList[position].patientProblem
    }

    override fun getItemCount(): Int {
        return patientsList.size
    }

    class PatientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPatientName = itemView.findViewById<TextView>(R.id.tvPatientName)
        val tvPatientSurname = itemView.findViewById<TextView>(R.id.tvPatientSurname)
        val tvPatientProblem = itemView.findViewById<TextView>(R.id.tvPatientProblem)
    }
}