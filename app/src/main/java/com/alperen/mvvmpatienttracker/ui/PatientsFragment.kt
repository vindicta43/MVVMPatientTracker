package com.alperen.mvvmpatienttracker.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alperen.mvvmpatienttracker.R
import com.alperen.mvvmpatienttracker.databinding.FragmentPatientsBinding
import com.alperen.mvvmpatienttracker.utils.PatientsAdapter
import com.alperen.mvvmpatienttracker.viewModel.PatientsViewModel

class PatientsFragment : Fragment() {
    private lateinit var binding: FragmentPatientsBinding
    private lateinit var viewModel: PatientsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("TAG", "created patients")

        binding = FragmentPatientsBinding.inflate(inflater)
        viewModel =
            ViewModelProvider(this, SavedStateViewModelFactory(activity?.application, this))
                .get(PatientsViewModel::class.java)

        with(binding) {
            recyclerPatients.layoutManager = LinearLayoutManager(context)

            viewModel.getPatients().observe(viewLifecycleOwner) {
                recyclerPatients.adapter = PatientsAdapter(it)
            }

            btnLogout.setOnClickListener {
                viewModel.logout(it)
            }

            btnProfile.setOnClickListener {
                findNavController().navigate(R.id.action_patientsFragment_to_profileFragment)
            }
            return root
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (::viewModel.isInitialized)
            viewModel.saveState()
        super.onSaveInstanceState(outState)
    }
}