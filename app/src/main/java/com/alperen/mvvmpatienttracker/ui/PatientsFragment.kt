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
    private lateinit var patientsViewModel: PatientsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("TAG", "created patients")

        binding = FragmentPatientsBinding.inflate(inflater, container, false)
        patientsViewModel =
            ViewModelProvider(this, SavedStateViewModelFactory(activity?.application, this))
                .get(PatientsViewModel::class.java)

        with(binding) {
            recyclerPatients.layoutManager = LinearLayoutManager(context)

            patientsViewModel.getPatients().observe(viewLifecycleOwner) {
                recyclerPatients.adapter = PatientsAdapter(it)
            }

            btnLogout.setOnClickListener {
                val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.fragmentContainerView)
                val navController = navHostFragment?.findNavController()
                patientsViewModel.logout(navController)
            }
            return root
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (::patientsViewModel.isInitialized)
            patientsViewModel.saveState()
        super.onSaveInstanceState(outState)
    }
}