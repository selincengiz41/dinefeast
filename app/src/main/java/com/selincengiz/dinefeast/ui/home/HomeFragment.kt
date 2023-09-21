package com.selincengiz.dinefeast.ui.home

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
import com.selincengiz.dinefeast.common.HomeState
import com.selincengiz.dinefeast.data.model.Food
import com.selincengiz.dinefeast.data.model.FoodEntity
import com.selincengiz.dinefeast.databinding.FragmentHomeBinding
import com.selincengiz.dinefeast.ui.adapter.FoodAdapter
import com.selincengiz.dinefeast.ui.adapter.ItemListener
import com.selincengiz.dinefeast.ui.search.SearchFragmentDirections
import com.selincengiz.dinefeast.ui.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), ItemListener {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val saleFoodAdapter by lazy { FoodAdapter(this) }
    private val foodAdapter by lazy { FoodAdapter(this) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.saleRecycler.adapter = saleFoodAdapter
        binding.allFoodsRecycler.adapter = foodAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()

        viewModel.getFoods()
        viewModel.getSaleFoods()
    }

    fun observe() {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->

            when (state) {
                HomeState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.mainLayout.visibility = View.INVISIBLE

                }

                is HomeState.Data -> {

                    binding.progressBar.visibility = View.GONE
                    binding.mainLayout.visibility = View.VISIBLE
                    foodAdapter.submitList(state.foods)
                }

                is HomeState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.mainLayout.visibility = View.GONE
                    Toast.makeText(requireContext(), state.throwable.message, Toast.LENGTH_SHORT)
                        .show()

                }

                is HomeState.DataByFilter -> {

                    binding.progressBar.visibility = View.GONE
                    binding.mainLayout.visibility = View.VISIBLE
                    saleFoodAdapter.submitList(state.foods)

                }

                else -> {

                }

            }
        }

    }

    override fun onClicked(food: FoodEntity) {
        food.id?.let {
            findNavController().navigate(HomeFragmentDirections.homeToDetail(it))

        } ?: kotlin.run {
            Toast.makeText(requireContext(), "Could not find food", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAddClicked(food: FoodEntity) {
        food.id?.let {
            viewModel.addCart(it)
            findNavController().navigate(HomeFragmentDirections.homeToCart())

        } ?: kotlin.run {
            Toast.makeText(requireContext(), "Addition is failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun favoriteClicked(food: FoodEntity) {
        viewModel.addFavorites(food)
        viewModel.getFoods()
        viewModel.getSaleFoods()
    }

    override fun unFavoriteClicked(food: FoodEntity) {
        viewModel.deleteFavorites(food)
        viewModel.getFoods()
        viewModel.getSaleFoods()
    }


}