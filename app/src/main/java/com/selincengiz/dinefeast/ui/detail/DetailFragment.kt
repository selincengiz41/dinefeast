package com.selincengiz.dinefeast.ui.detail

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
import androidx.navigation.fragment.navArgs
import com.selincengiz.dinefeast.R
import com.selincengiz.dinefeast.common.Extensions.loadUrl
import com.selincengiz.dinefeast.common.HomeState
import com.selincengiz.dinefeast.databinding.FragmentDetailBinding
import com.selincengiz.dinefeast.ui.search.SearchFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModels<DetailViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.detailFunctions = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        viewModel.getFoodDetail(args.id)
    }

    fun observe() {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->

            when (state) {
                HomeState.Loading -> {
                    binding.mainLayout.visibility=View.GONE
                    binding.progressBar2.visibility = View.VISIBLE
                }

                is HomeState.Detail -> {
                    binding.mainLayout.visibility=View.VISIBLE
                    binding.progressBar2.visibility = View.GONE
                    binding.ivFood.loadUrl(state.food.imageOne)
                    binding.tvTitleFood.text=state.food.title
                    binding.tvDesc.text=state.food.description
                    binding.ratingBar.rating=state.food.rate!!.toFloat()

                    when(state.food.saleState){
                        true ->{
                            binding.tvPrice.text=state.food.salePrice.toString()
                            binding.tvSale.text=state.food.price.toString()
                            binding.tvSale.paintFlags = binding.tvSale.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                            binding.tvSale.visibility=View.VISIBLE
                        }

                        false -> {
                            binding.tvPrice.text=state.food.price.toString()
                            binding.tvSale.visibility=View.GONE
                        }
                        else ->{

                        }
                    }
                }

                is HomeState.Error -> {
                    binding.mainLayout.visibility=View.GONE
                    binding.progressBar2.visibility = View.GONE
                    Toast.makeText(requireContext(), state.throwable.message, Toast.LENGTH_SHORT)
                        .show()

                }


                else -> {

                }


            }

        }
    }


    fun backClicked() {
        findNavController().navigateUp()

    }

    fun addClicked() {
            viewModel.addCart(args.id)
            findNavController().navigate(DetailFragmentDirections.detailToCart())
    }


}