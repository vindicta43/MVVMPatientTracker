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
import com.alperen.mvvmpatienttracker.R
import com.alperen.mvvmpatienttracker.databinding.FragmentLoginBinding
import com.alperen.mvvmpatienttracker.model.ModelLogin
import com.alperen.mvvmpatienttracker.viewModel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("TAG", "created login")

        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.fragmentContainerView)
        val navController = navHostFragment?.findNavController()

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        loginViewModel =
            ViewModelProvider(this, SavedStateViewModelFactory(activity?.application, this))
                .get(LoginViewModel::class.java)

        with(binding) {
            loginViewModel.login(root, navController)

            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                loginViewModel.user = ModelLogin(email, password)
                loginViewModel.login(root, navController)
            }
            return root
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (::loginViewModel.isInitialized)
            loginViewModel.saveState()
        super.onSaveInstanceState(outState)
    }
}