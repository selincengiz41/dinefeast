package com.selincengiz.dinefeast.ui.cart

import android.graphics.Paint
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
import com.selincengiz.dinefeast.common.Extensions.loadUrl
import com.selincengiz.dinefeast.common.HomeState
import com.selincengiz.dinefeast.data.model.Food
import com.selincengiz.dinefeast.data.model.FoodEntity
import com.selincengiz.dinefeast.databinding.FragmentCartBinding
import com.selincengiz.dinefeast.ui.search.CategoryAdapter
import com.selincengiz.dinefeast.ui.search.SearchFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class CartFragment : Fragment(), ItemCartListener {
    private lateinit var binding: FragmentCartBinding
    private val viewModel by viewModels<CartViewModel>()
    private var totalPrice: Double = 0.0
    private val cartAdapter by lazy { CartAdapter(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        binding.cartRecycler.adapter = cartAdapter
        binding.cartFunctions = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        viewModel.getCartFoods()
    }

    fun observe() {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->

            when (state) {
                HomeState.Loading -> {
                    binding.mainLayout.visibility = View.GONE
                    binding.progressBar3.visibility = View.VISIBLE
                }

                is HomeState.Data -> {
                    binding.mainLayout.visibility = View.VISIBLE
                    binding.progressBar3.visibility = View.GONE
                    cartAdapter.submitList(state.foods)
                    totalPrice = 0.0
                    state.foods.forEach {
                        when (it.saleState) {
                            true -> {
                                it.salePrice?.let {
                                    totalPrice += it
                                }
                            }

                            false -> {
                                it.price?.let {
                                    totalPrice += it
                                }

                            }

                            else -> {

                            }
                        }

                    }
                    binding.totalPrice = totalPrice
                }

                is HomeState.Error -> {
                    binding.mainLayout.visibility = View.GONE
                    binding.progressBar3.visibility = View.GONE
                    Toast.makeText(requireContext(), state.throwable.message, Toast.LENGTH_SHORT)
                        .show()

                }

                is HomeState.Cart -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                        .show()
                }


                else -> {

                }


            }

        }

    }

    fun checkOutClicked() {
        findNavController().navigate(CartFragmentDirections.cartToPayment())
    }

    fun clearClicked() {
        viewModel.clearCart()
        binding.cartRecycler.visibility=View.GONE
        viewModel.getCartFoods()


        binding.cartRecycler.visibility=View.VISIBLE

    }

    override fun onClicked(food: FoodEntity) {
        food.id?.let {
            findNavController().navigate(CartFragmentDirections.cartToDetail(it))

        } ?: kotlin.run {
            Toast.makeText(requireContext(), "Could not find food", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDeleteClicked(food: FoodEntity) {
        food.id?.let {
            viewModel.deleteCart(it)

        } ?: kotlin.run {
            Toast.makeText(requireContext(), "Deletion failed", Toast.LENGTH_SHORT).show()
        }
        binding.cartRecycler.visibility=View.GONE

        viewModel.getCartFoods()
        binding.cartRecycler.visibility=View.VISIBLE
    }


}