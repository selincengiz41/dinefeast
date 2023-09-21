package com.selincengiz.dinefeast.ui.search

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
import com.selincengiz.dinefeast.common.HomeState
import com.selincengiz.dinefeast.data.model.Food
import com.selincengiz.dinefeast.data.model.FoodEntity
import com.selincengiz.dinefeast.databinding.FragmentSearchBinding
import com.selincengiz.dinefeast.ui.adapter.FoodAdapter
import com.selincengiz.dinefeast.ui.adapter.ItemListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), ItemCategoryListener, ItemListener,
    SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<SearchViewModel>()
    private val categoryAdapter by lazy { CategoryAdapter(this) }
    private val foodAdapter by lazy { FoodAdapter(this) }
    private val foodByFilterAdapter by lazy { FoodAdapter(this) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.searchFunctions = this
        binding.categoriesRecycler.adapter = categoryAdapter
        binding.popularRecycler.adapter = foodAdapter
        binding.foodsByFilterRecycler.adapter = foodByFilterAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        viewModel.getCategories()
        viewModel.getFoods()

        binding.searchView.setOnQueryTextListener(this)


    }

    fun observe() {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->

            when (state) {
                HomeState.Loading -> {
                    binding.btnBack.visibility = View.GONE
                    binding.searchView.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.mainLayout.visibility = View.INVISIBLE
                    binding.foodByCategoryLayout.visibility = View.INVISIBLE

                }

                is HomeState.Data -> {
                    binding.btnBack.visibility = View.GONE
                    binding.searchView.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.mainLayout.visibility = View.VISIBLE
                    binding.foodByCategoryLayout.visibility = View.GONE
                    foodAdapter.submitList(state.foods)
                }

                is HomeState.Error -> {
                    binding.btnBack.visibility = View.GONE
                    binding.searchView.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    binding.mainLayout.visibility = View.GONE
                    binding.foodByCategoryLayout.visibility = View.GONE
                    Toast.makeText(requireContext(), state.throwable.message, Toast.LENGTH_SHORT)
                        .show()

                }

                is HomeState.Category -> {
                    binding.btnBack.visibility = View.GONE
                    binding.searchView.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.mainLayout.visibility = View.VISIBLE
                    binding.foodByCategoryLayout.visibility = View.GONE
                    categoryAdapter.submitList(state.categories)

                }

                is HomeState.DataByFilter -> {
                    binding.btnBack.visibility = View.VISIBLE
                    binding.searchView.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.mainLayout.visibility = View.GONE
                    binding.foodByCategoryLayout.visibility = View.VISIBLE
                    foodByFilterAdapter.submitList(state.foods)

                }

                else -> {

                }


            }

        }
    }

    fun orderClicked() {
        findNavController().navigate(SearchFragmentDirections.searchToCart())

    }

    fun backClicked() {
        viewModel.getCategories()
        viewModel.getFoods()
    }

    override fun onClicked(category: String) {
        viewModel.getFoodsByCategory(category)
    }

    override fun onClicked(food: FoodEntity) {

        food.id?.let {
            findNavController().navigate(SearchFragmentDirections.searchToDetail(it))

        } ?: kotlin.run {
            Toast.makeText(requireContext(), "Could not find food", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onAddClicked(food: FoodEntity) {
        food.id?.let {
            viewModel.addCart(it)
            findNavController().navigate(SearchFragmentDirections.searchToCart())

        } ?: kotlin.run {
            Toast.makeText(requireContext(), "Addition is failed", Toast.LENGTH_SHORT).show()
        }


    }

    override fun favoriteClicked(food: FoodEntity) {
        viewModel.addFavorites(food)
        viewModel.getFoods()
    }

    override fun unFavoriteClicked(food: FoodEntity) {
        viewModel.deleteFavorites(food)
        viewModel.getFoods()
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        text?.let {
            if (it.length > 3) {
                viewModel.searchFoods(it)
            }
        }
        return true
    }

    override fun onQueryTextChange(text: String?): Boolean {

        text?.let {
            if (it.length > 3) {
                viewModel.searchFoods(it)
            }
        }

        return true
    }


}