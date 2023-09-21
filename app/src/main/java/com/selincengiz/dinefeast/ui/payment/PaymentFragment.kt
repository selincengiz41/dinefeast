package com.selincengiz.dinefeast.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.selincengiz.dinefeast.R
import com.selincengiz.dinefeast.databinding.FragmentPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding
    private val viewModel by viewModels<PaymentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false)
        binding.paymentFunctions = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.etCartDate.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.length == 2) {
                        binding.etCartDate.setQuery(it + "/", false)
                    }
                    if (it.length > 5) {
                        binding.etCartDate.setQuery(it.substring(0, 5), false)
                    }

                }
                return true
            }
        })

        binding.etCartNumber.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                text?.let {
                    if (it.length == 4 || it.length == 9 || it.length == 14) {
                        binding.etCartNumber.setQuery(it + " ", false)
                    }
                    if (it.length > 19) {
                        binding.etCartNumber.setQuery(it.substring(0, 19), false)
                    }

                }
                return true
            }
        })


        binding.etInfoPhone.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                text?.let {
                    if (it.length == 4 || it.length == 8) {
                        binding.etInfoPhone.setQuery(it + " ", false)
                    }
                    if (it.length > 13) {
                        binding.etInfoPhone.setQuery(it.substring(0, 13), false)
                    }

                }
                return true
            }
        })
    }



    fun placeOrderClicked() =
        with(binding) {

            if (etAddress.text.isNullOrEmpty().not()
                && etAddressTitle.text.isNullOrEmpty().not()
                && etCartCvv.text.isNullOrEmpty().not()
                && etCartDate.query.isNullOrEmpty().not()
                && etCartName.text.isNullOrEmpty().not()
                && etCartNumber.query.isNullOrEmpty().not()
                && etInfoName.text.isNullOrEmpty().not()
                && etInfoPhone.query.isNullOrEmpty().not()
            ) {
                viewModel.clearCart()
                findNavController().navigate(PaymentFragmentDirections.paymentToSuccess())
            } else {
                Toast.makeText(requireContext(), "Please fill in the blanks", Toast.LENGTH_SHORT)
                    .show()
            }
        }


}