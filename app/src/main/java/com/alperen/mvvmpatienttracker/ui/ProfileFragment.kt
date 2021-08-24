package com.alperen.mvvmpatienttracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.alperen.mvvmpatienttracker.databinding.FragmentProfileBinding
import com.alperen.mvvmpatienttracker.utils.AlertMaker
import com.alperen.mvvmpatienttracker.viewModel.ProfileViewModel

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding = FragmentProfileBinding.inflate(inflater)

        with(binding) {
            tvProfileEmail.text = viewModel.getUser()["email"]
            viewModel.getPatients().observe(viewLifecycleOwner) {
                tvPatientCount.text = it.size.toString()
            }

            etAddPatientName.addTextChangedListener {
                tvAddPatientName.text = it
            }

            etAddPatientSurname.addTextChangedListener {
                tvAddPatientSurname.text = it
            }

            etAddPatientProblem.addTextChangedListener {
                tvAddPatientProblem.text = it
            }

            btnAddPatient.setOnClickListener {
                val name = tvAddPatientName.text.toString()
                val surname = tvAddPatientSurname.text.toString()
                val problem = tvAddPatientProblem.text.toString()

                if (name.isBlank() ||
                    surname.isBlank() ||
                    problem.isBlank()
                ) {
                    AlertMaker(context).makeAlert("Hasta ekleme alanında boş alan olamaz.")
                } else {
                    viewModel.addPatient(it, name, surname, problem)
                }
            }

            btnUpdate.setOnClickListener {
                val email = etUpdateEmail.text
                val oldPassword = etOldPassword.text
                val newPassword = etNewPassword.text

                if (email.isNullOrEmpty() ||
                    oldPassword.isNullOrEmpty() ||
                    newPassword.isNullOrEmpty()
                ) {
                    AlertMaker(context).makeAlert("Güncelleme için boş alan olamaz.")
                } else {
                    viewModel.update(it, email, oldPassword, newPassword)
                }
            }

            btnLogout.setOnClickListener {
                viewModel.logout(it)
            }

            return root
        }
    }
}