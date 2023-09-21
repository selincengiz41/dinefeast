package com.selincengiz.dinefeast.ui.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.selincengiz.dinefeast.R
import com.selincengiz.dinefeast.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        binding.signInFunctions = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeMessage()
    }

    fun observeMessage() {
        viewModel.message.observe(viewLifecycleOwner) {
            if (it.equals("Success")) {
                Toast.makeText(requireContext(), "Successfully signed in!", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(SignInFragmentDirections.signInToHome())
            } else {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun signInClicked(email: String, password: String) {
        if (email.isNullOrEmpty().not() && password.isNullOrEmpty().not()) {
            if (password.length >= 6) {
                viewModel.firebaseSignIn(email, password)

            } else {
                Toast.makeText(
                    requireContext(),
                    "Password must be at least 6 characters.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        } else {
            Toast.makeText(requireContext(), "Please fill in the blanks.", Toast.LENGTH_SHORT)
                .show()
        }
    }


    fun toSignUpClicked() {
        findNavController().navigate(SignInFragmentDirections.signInToSignUp())
    }

}